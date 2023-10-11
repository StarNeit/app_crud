package com.test.delivery.config;

import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class RequestLoggingFilter extends CommonsRequestLoggingFilter {
    public static final String REQUEST_ID_TAG = "request_id";

    /**
     * Forwards the request to the next filter in the chain and delegates down to the subclasses
     * to perform the actual request logging both before and after the request is processed.
     *
     * @see #beforeRequest
     * @see #afterRequest
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        long startMillis = System.currentTimeMillis();
        final String requestId = UUID.randomUUID().toString();
        request.setAttribute(REQUEST_ID_TAG, requestId);

        boolean isFirstRequest = !isAsyncDispatch(request);
        HttpServletRequest requestToUse = request;

        if (isIncludePayload() && isFirstRequest && !(request instanceof ContentCachingRequestWrapper)) {
            requestToUse = new ContentCachingRequestWrapper(request, getMaxPayloadLength());
        }

        try {
            filterChain.doFilter(requestToUse, response);
        } finally {
            if (shouldLog(requestToUse) && !isAsyncStarted(requestToUse)) {
                long stopMillis = System.currentTimeMillis();
                long durationMs = stopMillis - startMillis;

                String logMessage = "requestId: " + requestId + "," +
                        " method: " + requestToUse.getMethod() + "," +
                        " uri: " + requestToUse.getRequestURI() + "," +
                        " status: " + response.getStatus() + "," +
                        " durationMs: " + durationMs + "ms";
                Object errorMessage = request.getAttribute("message");
                if (errorMessage != null) {
                    logMessage += ", errorMessage: " + errorMessage;
                }

                afterRequest(requestToUse, logMessage);
            }
        }
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        logger.info(message);
    }

}

package com.test.delivery.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeSerializer extends JsonSerializer<Date> {
    private static final OffsetDateTimeSerializer instance = OffsetDateTimeSerializer.INSTANCE;
    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (date == null) {
            instance.serialize(null, jsonGenerator, serializerProvider);
        } else {
            instance.serialize(OffsetDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()), jsonGenerator, serializerProvider);
        }
    }
}

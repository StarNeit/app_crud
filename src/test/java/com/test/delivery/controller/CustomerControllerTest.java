package com.test.delivery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.delivery.config.CustomResponseEntityExceptionHandler;
import com.test.delivery.model.CustomerModel;
import com.test.delivery.service.CustomerService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {
    //implement unit test for CustomerController

    private MockMvc mockMvc;
    private CustomerService customerService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        customerService = Mockito.mock(CustomerService.class);
        var controller = new CustomerController(customerService);
        var customResponseEntityExceptionHandler = new CustomResponseEntityExceptionHandler();
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(customResponseEntityExceptionHandler)
                .build();
    }

    @SneakyThrows
    @Test
    void createCustomer_thenSuccess() {
        var customer = new CustomerModel();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("johndoe@mail.com");
        customer.setPhoneNumber("1234567890");
        when(customerService.createCustomer(Mockito.any())).thenReturn(customer);
        mockMvc.perform(MockMvcRequestBuilders.post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value(customer.getEmail()))
                .andExpect(jsonPath("$.phoneNumber").value(customer.getPhoneNumber()));
    }

    @SneakyThrows
    @Test
    void createCustomer_whenEmailInvalid_thenError() {
        //implement unit test for createCustomer
        var customer = new CustomerModel();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("johndoe@mail.com");
        customer.setPhoneNumber("ac1234567890");
        when(customerService.createCustomer(Mockito.any())).thenReturn(customer);
        mockMvc.perform(MockMvcRequestBuilders.post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().is(400));
    }

    @SneakyThrows
    @Test
    void createCustomer_whenPhoneNumberInvalid_thenError() {
        //implement unit test for createCustomer
        var customer = new CustomerModel();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("jabc..aks");
        customer.setPhoneNumber("1234567890");
        when(customerService.createCustomer(Mockito.any())).thenReturn(customer);
        mockMvc.perform(MockMvcRequestBuilders.post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().is(400));
    }

    //implement unit test for getCustomerById
    @SneakyThrows
    @Test
    void getCustomerById_thenSuccess() {
        var customer = new CustomerModel();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("johndoe@mail.com");
        customer.setPhoneNumber("1234567890");
        when(customerService.getCustomerById(Mockito.anyLong())).thenReturn(java.util.Optional.of(customer));
        mockMvc.perform(MockMvcRequestBuilders.get("/customer/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value(customer.getEmail()))
                .andExpect(jsonPath("$.phoneNumber").value(customer.getPhoneNumber()));
    }
}

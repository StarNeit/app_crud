package com.test.delivery.controller.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class CreateCustomerRequest {
    @NotNull
    @NotBlank
    @Length(min = 2, max = 20)
    private String firstName;
    @NotNull
    @NotBlank
    @Length(min = 2, max = 20)
    private String lastName;
    @Pattern(regexp = "\\+?\\d{8,15}$")
    private String phoneNumber;
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
    private String email;
}

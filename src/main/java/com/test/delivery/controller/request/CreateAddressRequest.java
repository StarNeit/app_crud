package com.test.delivery.controller.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class CreateAddressRequest {
    @NotNull
    @Length(min = 1, max = 100)
    private String city;
    @NotNull
    @Length(min = 1, max = 100)
    private String country;
    @NotNull
    @Length(min = 1, max = 100)
    private String type;
    @NotNull
    @Length(min = 2, max = 1000)
    private String addressLine;
}

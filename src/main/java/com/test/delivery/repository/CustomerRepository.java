package com.test.delivery.repository;

import com.test.delivery.model.CustomerModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    CustomerModel save(CustomerModel customer);
    Optional<CustomerModel> getCustomerById(Long id);

    Page<CustomerModel> getCustomers(Pageable pageable);

    List<CustomerModel> getCustomersByPhone(String regexPhoneNumber);

    List<CustomerModel> getCustomersInCity(String city);

}

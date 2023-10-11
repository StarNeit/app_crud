package com.test.delivery.repository.impl;

import com.test.delivery.model.CustomerModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CustomerDAO extends PagingAndSortingRepository<CustomerModel, Long> {

    List<CustomerModel> getCustomerModelsByPhoneNumberLike(String regexPhoneNumber);

    @Query(value = "select * from customers where id in (select customer_id from addresses where city = ?1)", nativeQuery = true)
    List<CustomerModel> getCustomersInCity(String city);
}

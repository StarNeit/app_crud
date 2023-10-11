package com.test.delivery.service;

import com.test.delivery.model.AddressModel;
import com.test.delivery.model.CustomerModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    /**
     * Create a new customer
     *
     * @param customer
     * @return new Customer object. {@link CustomerModel}
     */
    CustomerModel createCustomer(CustomerModel customer);

    /**
     * Get customer by id
     *
     * @param id
     * @return Customer object. {@link CustomerModel}
     */
    Optional<CustomerModel> getCustomerById(long id);

    /**
     * Get all customers
     *
     * @param page
     * @return Page of Customer objects. {@link CustomerModel}
     */
    Page<CustomerModel> getCustomers(Pageable page);

    /**
     * Add new address to customer
     *
     * @param customerId
     * @param addressModel {@link AddressModel}
     * @return Customer object. {@link CustomerModel}
     */
    CustomerModel addNewAddress(Long customerId, AddressModel addressModel);

    /**
     * Delete address from customer
     *
     * @param customerId
     * @param addressId
     */
    void deleteAddress(Long customerId, Long addressId);

    /**
     * Get customers by phone number prefix
     *
     * @param regexPhoneNumber
     * @return List of Customer objects. {@link CustomerModel}
     */
    List<CustomerModel> getCustomersByPhone(String regexPhoneNumber);

    /**
     * Get customers by city
     *
     * @param city
     * @return List of Customer objects. {@link CustomerModel}
     */
    List<CustomerModel> getCustomersInCity(String city);
}

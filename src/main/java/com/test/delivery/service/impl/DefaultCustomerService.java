package com.test.delivery.service.impl;

import com.test.delivery.exception.NotFoundException;
import com.test.delivery.model.AddressModel;
import com.test.delivery.model.CustomerModel;
import com.test.delivery.repository.AddressRepository;
import com.test.delivery.repository.CustomerRepository;
import com.test.delivery.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultCustomerService implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    @Override
    public CustomerModel createCustomer(CustomerModel customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Optional<CustomerModel> getCustomerById(long id) {
        return customerRepository.getCustomerById(id);
    }

    @Override
    public Page<CustomerModel> getCustomers(Pageable page) {
        return customerRepository.getCustomers(page);
    }

    @Override
    @Transactional
    public CustomerModel addNewAddress(Long customerId, AddressModel addressModel) {
        var customerModelOptional = getCustomerById(customerId);
        if (customerModelOptional.isEmpty()) {
            throw new NotFoundException("Customer not found with id: " + customerId);
        }
        addressModel.setCustomerId(customerId);
        addressModel.setCreatedDate(OffsetDateTime.now());
        addressModel.setModifiedDate(OffsetDateTime.now());
        var address = addressRepository.save(addressModel);
        var customer = customerModelOptional.get();
        if (customer.getAddresses() == null) {
            customer.setAddresses(List.of(address));
        } else {
            customer.getAddresses().add(address);
        }
        return customer;
    }

    @Override
    @Transactional
    public void deleteAddress(Long customerId, Long addressId) {
        var customerModelOptional = getCustomerById(customerId);
        if (customerModelOptional.isEmpty()) {
            throw new NotFoundException("Customer not found with id: " + customerId);
        }
        var address = addressRepository.findById(addressId).orElseThrow(() -> new NotFoundException("Address not found with id: " + addressId));
        if (address.getCustomerId() != customerId) {
            throw new NotFoundException("Address not found with id: " + addressId + " for customer id: " + customerId);
        }
        addressRepository.deleteById(addressId);
    }

    @Override
    public List<CustomerModel> getCustomersByPhone(String regexPhoneNumber) {
        return customerRepository.getCustomersByPhone(regexPhoneNumber);
    }

    @Override
    public List<CustomerModel> getCustomersInCity(String city) {
        return customerRepository.getCustomersInCity(city);
    }
}

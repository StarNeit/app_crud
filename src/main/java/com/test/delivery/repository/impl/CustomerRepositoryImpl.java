package com.test.delivery.repository.impl;

import com.test.delivery.model.CustomerModel;
import com.test.delivery.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {
    private final CustomerDAO customerDAO;
    @Override
    public CustomerModel save(CustomerModel customer) {
        return customerDAO.save(customer);
    }

    @Override
    public Optional<CustomerModel> getCustomerById(Long id) {
        return customerDAO.findById(id).map(CustomerModel::clone);
    }

    @Override
    public Page<CustomerModel> getCustomers(Pageable pageable){
        return customerDAO.findAll(pageable);
    }

    @Override
    public List<CustomerModel> getCustomersByPhone(String regexPhoneNumber) {
        return customerDAO.getCustomerModelsByPhoneNumberLike(regexPhoneNumber+"%");
    }

    @Override
    public List<CustomerModel> getCustomersInCity(String city) {
        return customerDAO.getCustomersInCity(city);
    }
}

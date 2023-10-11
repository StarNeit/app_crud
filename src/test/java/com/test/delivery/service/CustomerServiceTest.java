package com.test.delivery.service;

import com.test.delivery.model.AddressModel;
import com.test.delivery.model.CustomerModel;
import com.test.delivery.repository.impl.AddressDAO;
import com.test.delivery.repository.impl.AddressRepositoryImpl;
import com.test.delivery.repository.impl.CustomerDAO;
import com.test.delivery.repository.impl.CustomerRepositoryImpl;
import com.test.delivery.service.impl.DefaultCustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class CustomerServiceTest {
    private CustomerService customerService;
    @Autowired
    private CustomerDAO customerDAO;
    @Autowired
    private AddressDAO addressDAO;

    @BeforeEach
    void setUp() {
        var customerRepository = new CustomerRepositoryImpl(customerDAO);
        var addressRepository = new AddressRepositoryImpl(addressDAO);
        customerService = new DefaultCustomerService(customerRepository, addressRepository);
    }

    @AfterEach
    void tearDown() {
        customerDAO.deleteAll();
        addressDAO.deleteAll();
    }


    @Test
    void createCustomer_thenSuccess() {
        var customer = newCustomer(1);
        var response = customerService.createCustomer(customer);
        assertEquals(1, response.getId());
        assertEquals(customer.getFirstName(), response.getFirstName());
        assertEquals(customer.getLastName(), response.getLastName());
        assertEquals(customer.getEmail(), response.getEmail());
        assertEquals(customer.getPhoneNumber(), response.getPhoneNumber());
    }

    private static CustomerModel newCustomer(int id) {
        var customer = new CustomerModel();
        customer.setFirstName("John");
        customer.setLastName("Doe " + id);
        customer.setEmail("johndoe@mail.com" + id);
        customer.setPhoneNumber("123456789" + id);
        return customer;
    }

    @Test
    void getCustomers_thenSuccess() {
        for (int i = 0; i < 10; i++) {
            var customer = newCustomer(i);
            customerService.createCustomer(customer);
        }
        var page = customerService.getCustomers(PageRequest.of(1, 3));
        assertEquals(10, page.getTotalElements());
        assertEquals(3, page.getContent().size());
        assertEquals(3, page.getNumberOfElements());
    }

    @Test
    void getCustomersByPhonePrefix_thenSuccess() {
        for (int i = 0; i < 10; i++) {
            var customer = newCustomer(i);
            customerService.createCustomer(customer);
        }
        var customers = customerService.getCustomersByPhone("123456789");
        assertEquals(10, customers.size());
    }

    @Test
    void addAddressForCustomer_thenSuccess() {
        var customer = newCustomer(1);
        customer = customerService.createCustomer(customer);
        var address = new AddressModel();
        address.setCity("New York");
        address.setCountry("USA");
        address.setType("Home");
        address.setAddressLine("01 Wall Street");
        var response = customerService.addNewAddress(customer.getId(), address);
        assertEquals(1, response.getAddresses().size());
        assertEquals(address.getCity(), response.getAddresses().get(0).getCity());
        assertEquals(address.getCountry(), response.getAddresses().get(0).getCountry());
        assertEquals(address.getType(), response.getAddresses().get(0).getType());
        assertEquals(address.getAddressLine(), response.getAddresses().get(0).getAddressLine());
    }

    @Test
    void deleteAddressForCustomer_thenSuccess() {
        var customer = newCustomer(1);
        var c = customerService.createCustomer(customer);
        var address = new AddressModel();
        address.setCity("New York");
        address.setCountry("USA");
        address.setCustomerId(1L);
        address.setType("Home");
        address.setAddressLine("01 Wall Street");
        var response = customerService.addNewAddress(c.getId(), address);
        assertEquals(1, response.getAddresses().size());
        customerService.deleteAddress(c.getId(), response.getAddresses().get(0).getId());
        var response2 = customerService.getCustomerById(c.getId()).get();
        System.out.println("response2: " + response2);
        assertTrue(response2.getAddresses() == null || response2.getAddresses().isEmpty());
    }

    @Test
    void getCustomersInCity_thenSuccess() {
        for (int i = 0; i < 10; i++) {
            var customer = newCustomer(i);
            customer = customerService.createCustomer(customer);
            var address = new AddressModel();
            address.setCity("New York" + i % 2);
            address.setCountry("USA");
            address.setType("Home");
            address.setAddressLine("01 Wall Street");
            var rs = customerService.addNewAddress(customer.getId(), address);
            System.out.println("rs: %s%n" + rs);
        }

        var customers = customerService.getCustomersInCity("New York0");
        assertEquals(5, customers.size());
        customers = customerService.getCustomersInCity("New York1");
        assertEquals(5, customers.size());
    }
}

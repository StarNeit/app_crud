package com.test.delivery.controller;

import com.test.delivery.controller.request.CreateAddressRequest;
import com.test.delivery.controller.request.CreateCustomerRequest;
import com.test.delivery.controller.response.PageResponse;
import com.test.delivery.exception.NotFoundException;
import com.test.delivery.model.AddressModel;
import com.test.delivery.model.CustomerModel;
import com.test.delivery.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.time.OffsetDateTime;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    /**
     * Create a new customer
     *
     * @param request
     * @return new Customer object. {@link CustomerModel}
     */
    @PostMapping
    public ResponseEntity<CustomerModel> createCustomer(@RequestBody @Valid CreateCustomerRequest request) {
        var customer = new CustomerModel();
        customer.setEmail(request.getEmail());
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setCreatedDate(OffsetDateTime.now());
        customer.setModifiedDate(OffsetDateTime.now());
        var customerModel = customerService.createCustomer(customer);
        return ResponseEntity.ok(customerModel);
    }

    /**
     * Get customer by idq
     *
     * @param id
     * @return Customer object. {@link CustomerModel}
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerModel> getCustomerById(@PathVariable long id) {
        var customerModelOptional = customerService.getCustomerById(id);
        if (customerModelOptional.isEmpty()) {
            throw new NotFoundException("Customer not found with id: " + id);
        }
        return ResponseEntity.ok(customerModelOptional.get());
    }

    /**
     * Get all customers
     *
     * @param page
     * @param size
     * @return PageResponse object. {@link PageResponse}
     */
    @GetMapping
    public ResponseEntity<PageResponse<CustomerModel>> getCustomers(@RequestParam(defaultValue = "0", required = false) int page,
                                                                    @RequestParam(defaultValue = "10", required = false) int size) {
        var rs = customerService.getCustomers(PageRequest.of(page, size));
        return ResponseEntity.ok(PageResponse.of(rs.getTotalElements(), rs.getContent()));
    }

    /**
     * Create new address for a customer
     *
     * @param id
     * @param request
     * @return Customer object. {@link CustomerModel}
     */
    @PostMapping("/{id}/address")
    public ResponseEntity<CustomerModel> createCustomerAddress(@PathVariable Long id, @RequestBody @Valid CreateAddressRequest request) {
        var address = new AddressModel();
        address.setCity(request.getCity());
        address.setCountry(request.getCountry());
        address.setType(request.getType());
        address.setAddressLine(request.getAddressLine());
        var customer = customerService.addNewAddress(id, address);
        return ResponseEntity.ok(customer);
    }

    /**
     * Delete address of a customer
     *
     * @param id
     * @param addressId
     * @return Customer object. {@link CustomerModel}
     */
    @DeleteMapping("/{id}/address/{addressId}")
    public ResponseEntity<CustomerModel> deleteCustomerAddress(@PathVariable long id, @PathVariable long addressId) {
        var customerModelOptional = customerService.getCustomerById(id);
        if (customerModelOptional.isEmpty()) {
            throw new NotFoundException("Customer not found with id: " + id);
        }
        customerService.deleteAddress(id, addressId);
        return ResponseEntity.ok(customerService.getCustomerById(id).orElse(null));
    }

    /**
     * Get customers by phone number prefix
     *
     * @param phonePrefix
     * @return Customer object. {@link CustomerModel}
     */
    @GetMapping("/phone/{phonePrefix}")
    public ResponseEntity<PageResponse<CustomerModel>> getCustomerByPhone(@PathVariable @Pattern(regexp = "\\d{1,15}") String phonePrefix) {
        var customers = customerService.getCustomersByPhone(phonePrefix);
        return ResponseEntity.ok(PageResponse.of((long) customers.size(), customers));
    }

    /**
     * Get customers by city
     *
     * @param city
     * @return Customer object. {@link CustomerModel}
     */
    @GetMapping("/city/{city}")
    public ResponseEntity<PageResponse<CustomerModel>> getCustomerByCity(@PathVariable String city) {
        var customers = customerService.getCustomersInCity(city);
        return ResponseEntity.ok(PageResponse.of((long) customers.size(), customers));
    }
}

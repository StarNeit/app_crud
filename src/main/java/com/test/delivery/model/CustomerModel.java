package com.test.delivery.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Entity(name = "customers")
@Data
public class CustomerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_seq")
    @SequenceGenerator(name = "customer_id_seq", sequenceName = "customer_id_seq", schema = "public", allocationSize = 1)
    private Long id;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    @OneToMany(targetEntity = AddressModel.class, mappedBy = "customerId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    private List<AddressModel> addresses;
    private OffsetDateTime createdDate;
    private OffsetDateTime modifiedDate;

    public CustomerModel clone() {
        var customerModel = new CustomerModel();
        customerModel.setId(this.id);
        customerModel.setFirstName(this.firstName);
        customerModel.setLastName(this.lastName);
        customerModel.setPhoneNumber(this.phoneNumber);
        customerModel.setEmail(this.email);
        customerModel.setAddresses(this.addresses);
        customerModel.setCreatedDate(this.createdDate);
        customerModel.setModifiedDate(this.modifiedDate);
        return customerModel;
    }
}

package com.test.delivery.model;


import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity(name = "addresses")
@Data
public class AddressModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_id_seq")
    @SequenceGenerator(name = "address_id_seq", sequenceName = "address_id_seq", schema = "public", allocationSize = 1)
    private Long id;
    private Long customerId;
    private String city;
    private String country;
    private String addressLine;
    private String type;
    private OffsetDateTime createdDate;
    private OffsetDateTime modifiedDate;
}

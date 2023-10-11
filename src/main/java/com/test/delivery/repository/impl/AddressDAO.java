package com.test.delivery.repository.impl;

import com.test.delivery.model.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressDAO extends JpaRepository<AddressModel, Long> {
}

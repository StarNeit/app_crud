package com.test.delivery.repository;

import com.test.delivery.model.AddressModel;

import java.util.Optional;

public interface AddressRepository {
    AddressModel save(AddressModel address);

    Optional<AddressModel> findById(Long id);

    AddressModel deleteById(Long id);

}

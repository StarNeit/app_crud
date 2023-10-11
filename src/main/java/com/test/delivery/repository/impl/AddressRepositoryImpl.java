package com.test.delivery.repository.impl;

import com.test.delivery.exception.NotFoundException;
import com.test.delivery.model.AddressModel;
import com.test.delivery.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressRepository {
    private final AddressDAO addressDAO;

    @Override
    public AddressModel save(AddressModel address) {
        return addressDAO.saveAndFlush(address);
    }

    @Override
    public Optional<AddressModel> findById(Long id) {
        return addressDAO.findById(id);
    }

    @Override
    public AddressModel deleteById(Long id) {
        var address = addressDAO.findById(id).orElseThrow(() -> new NotFoundException("Address not found with ID=" + id));
        addressDAO.deleteById(id);
        return address;
    }
}

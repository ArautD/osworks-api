package com.albino.tecnologia.osworks.services;

import com.albino.tecnologia.osworks.controllers.dto.AddressDTO;
import com.albino.tecnologia.osworks.models.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AddressService {
    Address findById(Long id);
    Page<Address> listAllAddress(Pageable pageable);
    Address insertAddress(AddressDTO addressDTO);
    Address updateAddress(Long id, AddressDTO addressDTO);
    void inactivateAddress(Long id);

}

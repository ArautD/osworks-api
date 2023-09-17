package com.albino.tecnologia.osworks.services.impl;


import com.albino.tecnologia.osworks.controllers.dto.AddressDTO;
import com.albino.tecnologia.osworks.exceptions.BadRequestException;
import com.albino.tecnologia.osworks.models.Address;
import com.albino.tecnologia.osworks.repositories.AddressRepository;
import com.albino.tecnologia.osworks.services.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public Address findById(Long id) {

        log.info("Found a Address with id:'{}'", id);

        return addressRepository.findById(id)
                .orElseThrow(()-> new BadRequestException("Address Not Found"));
    }

    @Override
    public Page<Address> listAllAddress(Pageable pageable) {

        log.info("List All Address");

        return addressRepository.findAll(pageable);

    }

    @Override
    public Address insertAddress(AddressDTO addressDTO) {

        log.info("New Address '{}' was Created ", addressDTO);

        Address novoAddress = Address.builder()
                .postalCode(addressDTO.getPostalCode())
                .backyard(addressDTO.getBackyard())
                .number(addressDTO.getNumber())
                .complement(addressDTO.getComplement())
                .neighborhood(addressDTO.getNeighborhood())
                .city(addressDTO.getCity())
                .status("ativo")
                .uf(addressDTO.getUf())
                .build();

        return addressRepository.save(novoAddress);
    }

    @Override
    public Address updateAddress(Long id, AddressDTO addressDTO) {
        Address addressUpdated = findById(id);

        addressUpdated.setPostalCode(addressDTO.getPostalCode());
        addressUpdated.setBackyard(addressDTO.getBackyard());
        addressUpdated.setNumber(addressDTO.getNumber());
        addressUpdated.setComplement(addressDTO.getComplement());
        addressUpdated.setNeighborhood(addressDTO.getNeighborhood());
        addressUpdated.setCity(addressDTO.getCity());
        addressUpdated.setUf(addressDTO.getUf());

        log.info("Address of ID:'{}' Has Been Updated ", id);

        return addressRepository.save(addressUpdated);
    }

    @Override
    public void inactivateAddress(Long id) {

        Address address = findById(id);

        address.setStatus("inativo");

        addressRepository.save(address);

        log.info("Address of ID:'{}' Being Inactivated ", id);
    }
}

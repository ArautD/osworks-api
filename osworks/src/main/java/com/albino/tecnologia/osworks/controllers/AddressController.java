package com.albino.tecnologia.osworks.controllers;

import com.albino.tecnologia.osworks.controllers.dto.AddressDTO;
import com.albino.tecnologia.osworks.models.Address;
import com.albino.tecnologia.osworks.services.impl.AddressServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
@Log4j2
public class AddressController {

    private final AddressServiceImpl addressService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_FINANCEIRO')")
    public ResponseEntity<Address> findById(@PathVariable Long id) {

        log.info("Return a Address");

        Address address = addressService.findById(id);

        return ResponseEntity.ok(address);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_FINANCEIRO')")
    public ResponseEntity<Page<Address>> listAllAddress(Pageable pageable) {

        log.info("Get a List of Addresses");

        Page<Address> addresses = addressService.listAllAddress(pageable);

        return ResponseEntity.ok(addresses);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_FINANCEIRO')")
    public ResponseEntity<Address> insertAddress(@Valid @RequestBody AddressDTO addressDTO) {

        log.info("Insert a Address");

        Address createdAddress = addressService.insertAddress(addressDTO);

        return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_FINANCEIRO')")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @Valid @RequestBody AddressDTO addressDTO) {

        log.info("Update Address");

        Address updatedAddress = addressService.updateAddress(id, addressDTO);

        return ResponseEntity.ok(updatedAddress);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_FINANCEIRO')")
    public ResponseEntity<Void> inactivateAddress(@PathVariable Long id) {

        log.info("Inactivated Address");

        addressService.inactivateAddress(id);

        return ResponseEntity.noContent().build();
    }
}

package com.albino.tecnologia.osworks.controllers;

import com.albino.tecnologia.osworks.controllers.dto.ServiceOrderDTO;
import com.albino.tecnologia.osworks.models.Contract;
import com.albino.tecnologia.osworks.models.ServiceOrder;
import com.albino.tecnologia.osworks.services.impl.ServiceOrderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/os")
@RequiredArgsConstructor
@Log4j2
public class ServiceOrderController {

    private final ServiceOrderServiceImpl osService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_GP','ROLE_DIRETOR')")
    public ResponseEntity<ServiceOrder> findById(@PathVariable Long id) {

        log.info("Return a Service Order");

        ServiceOrder serviceOrder = osService.findById(id);

        return ResponseEntity.ok(serviceOrder);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_GP','ROLE_DIRETOR')")
    public ResponseEntity<Page<ServiceOrder>> listAllOS(Pageable pageable) {

        log.info("Get a List of Services Orders");

        Page<ServiceOrder> osList = osService.listAllOS(pageable);

        return ResponseEntity.ok(osList);
    }

    @GetMapping("/contract/{id}")
    @PreAuthorize("hasAnyRole('ROLE_GP','ROLE_DIRETOR')")
    public ResponseEntity<Contract> findContractByOS(@PathVariable Long id) {

        log.info("Get a Contract for the specified ServiceOrder");

        Contract contract = osService.findContractByOS(id);

        return ResponseEntity.ok(contract);
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ROLE_GP','ROLE_DIRETOR')")
    public ResponseEntity<List<ServiceOrder>> listAllOSbyStatus(@PathVariable String status) {

        log.info("Get a List of Services Orders  for the specified Status '{}'",status);

        List<ServiceOrder> orders = osService.listAllOSbyStatus(status);

        return ResponseEntity.ok(orders);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_GP')")
    public ResponseEntity<ServiceOrder> insertOS(@PathVariable Long id,
                                                 @Valid @RequestBody ServiceOrderDTO serviceOrderDTO) {

        log.info("Insert a Service Order");

        ServiceOrder createdServiceOrder = osService.insertOS(id, serviceOrderDTO);

        return new ResponseEntity<>(createdServiceOrder, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_GP')")
    public ResponseEntity<ServiceOrder> updateOS(@PathVariable Long id, @RequestBody ServiceOrderDTO serviceOrderDTO) {

        log.info("Update a Service Order");

        ServiceOrder updatedServiceOrder = osService.updateOS(id, serviceOrderDTO);

        return ResponseEntity.ok(updatedServiceOrder);
    }

    @PutMapping("/execute/{id}")
    @PreAuthorize("hasRole('ROLE_GP')")
    public ResponseEntity<ServiceOrder> executeOS(@PathVariable Long id) {

        log.info("ServiceOrder has been Executed");

        ServiceOrder executedServiceOrder = osService.executeOS(id);

        return ResponseEntity.ok(executedServiceOrder);
    }

    @PutMapping("/finalize/{id}")
    @PreAuthorize("hasRole('ROLE_GP')")
    public ResponseEntity<ServiceOrder> finalizeOS(@PathVariable Long id) {

        log.info("ServiceOrder has been Finalized");

        ServiceOrder finalizedServiceOrder = osService.finalizeOS(id);

        return ResponseEntity.ok(finalizedServiceOrder);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_GP')")
    public ResponseEntity<Void> inactivateOS(@PathVariable Long id) {

        log.info("Inactivated ServiceOrder");

        osService.inactivateOS(id);

        return ResponseEntity.noContent().build();
    }

}

package com.albino.tecnologia.osworks.controllers;

import com.albino.tecnologia.osworks.controllers.dto.ContractDTO;
import com.albino.tecnologia.osworks.models.Additive;
import com.albino.tecnologia.osworks.models.Contract;
import com.albino.tecnologia.osworks.models.ServiceOrder;
import com.albino.tecnologia.osworks.services.impl.ContractServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/contracts")
@RequiredArgsConstructor
@Log4j2
public class ContractController {

    private final ContractServiceImpl contractService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_FINANCEIRO','ROLE_DIRETOR','ROLE_GPP')")
    public ResponseEntity<Contract> findById(@PathVariable Long id) {

        log.info("Return a Contract");

        Contract contract = contractService.findById(id);

        return ResponseEntity.ok(contract);

    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_FINANCEIRO','ROLE_DIRETOR','ROLE_GPP')")
    public ResponseEntity<Page<Contract>> listAllContracts(Pageable pageable) {

        log.info("Get a List of Contract");

        Page<Contract> contracts = contractService.listAllContracts(pageable);

        return ResponseEntity.ok(contracts);
    }

    @GetMapping("/os/{id}")
    @PreAuthorize("hasRole('ROLE_GPP')")
    public ResponseEntity<List<ServiceOrder>> listAllOSsContract(@PathVariable Long id) {

        log.info("Get a List of ServiceOrder for the specified contract");

        List<ServiceOrder> oSsContract = contractService.listAllOSsContract(id);

        return ResponseEntity.ok(oSsContract);

    }

    @GetMapping("/additive/{id}")
    @PreAuthorize("hasRole('ROLE_GPP')")
    public ResponseEntity<List<Additive>> listAllAdditivesContract(@PathVariable Long id) {

        log.info("Get a List of Additives for the specified contract");

        List<Additive> additivesContract = contractService.listAllAdditivesContract(id);

        return ResponseEntity.ok(additivesContract);

    }

    @GetMapping("/expiration")
    @PreAuthorize("hasRole('ROLE_FINANCEIRO')")
    public ResponseEntity<List<Contract>> findContractByEndDate(@Param("dueDate") LocalDate endDate) {

        log.info("Get a List of Contract for the specified Vue Date");

        List<Contract> ContraindicationDate = contractService.findContractByEndDate(endDate);

        return ResponseEntity.ok(ContraindicationDate);

    }

    @GetMapping("/expiration/number/day/{numberDay}")
    @PreAuthorize("hasRole('ROLE_FINANCEIRO')")
    public ResponseEntity<List<Contract>> listContractByExpirationNumberDate(@PathVariable Integer numberDay) {

        log.info("Get a List of Contract for the specified Expiration Number Day");

        List<Contract> contractsNumberDay = contractService.listContractByExpirationNumberDate(numberDay);

        return ResponseEntity.ok(contractsNumberDay);

    }

    @GetMapping("/expiration/{id}")
    @PreAuthorize("hasRole('ROLE_FINANCEIRO')")
    public ResponseEntity<Integer> seeEndNumberDays(@PathVariable Long id) {

        log.info("Get a Expiration Date a Contract by Id");

        Integer expirationNumberDays = contractService.seeEndNumberDays(id);

        return ResponseEntity.ok(expirationNumberDays);

    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_FINANCEIRO')")
    public ResponseEntity<Contract> insertContract(@Valid @RequestBody ContractDTO contractDTO) {

        log.info("Insert a Contract");

        Contract createdContract = contractService.insertContract(contractDTO);

        return new ResponseEntity<>(createdContract, HttpStatus.CREATED);

    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_FINANCEIRO')")
    public ResponseEntity<Void> inactivateContract(@PathVariable Long id) {

        log.info("Inactivated a Contract");

        contractService.inactivateContract(id);

        return ResponseEntity.noContent().build();
    }
}


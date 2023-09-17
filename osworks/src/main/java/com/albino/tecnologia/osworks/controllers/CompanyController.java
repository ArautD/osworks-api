package com.albino.tecnologia.osworks.controllers;

import com.albino.tecnologia.osworks.controllers.dto.CompanyDTO;
import com.albino.tecnologia.osworks.models.Company;
import com.albino.tecnologia.osworks.services.impl.CompanyServiceImpl;
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
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
@Log4j2
public class CompanyController {

    private final CompanyServiceImpl companyService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_FINANCEIRO')")
    public ResponseEntity<Company> findById(@PathVariable Long id){

        log.info("Return a Company");

        Company company = companyService.findById(id);

        return ResponseEntity.ok(company);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_FINANCEIRO')")
    public ResponseEntity<Page<Company>> listAllCompanies(
            @RequestParam(value = "status",required = false) String status, Pageable pageable){

        log.info("Get a List of Companies");

        Page<Company> companies = companyService.listAllCompanies(status, pageable);

        return ResponseEntity.ok(companies);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_FINANCEIRO')")
    public ResponseEntity<Company> insertCompany(@Valid @RequestBody CompanyDTO companyDTO){

        log.info("Insert a Company");

        Company createdCompany = companyService.insertCompany(companyDTO);

        return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_FINANCEIRO')")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @Valid @RequestBody CompanyDTO companyDTO){

        log.info("Update Company");

        Company updatedCompany = companyService.updateCompany(id, companyDTO);

        return ResponseEntity.ok(updatedCompany);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_FINANCEIRO')")
    public ResponseEntity<Void> inactivateCompany(@PathVariable Long id){

        log.info("Inactivated Company");

        companyService.inactivateCompany(id);

        return ResponseEntity.noContent().build();
    }
}

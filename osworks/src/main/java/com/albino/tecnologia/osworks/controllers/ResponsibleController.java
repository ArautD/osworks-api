package com.albino.tecnologia.osworks.controllers;

import com.albino.tecnologia.osworks.controllers.dto.ResponsibleDTO;
import com.albino.tecnologia.osworks.models.Responsible;
import com.albino.tecnologia.osworks.services.impl.ResponsibleServiceImpl;
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
@RequestMapping("/api/v1/responsibilities")
@RequiredArgsConstructor
@Log4j2
public class ResponsibleController {

    private final ResponsibleServiceImpl responsibleService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_FINANCEIRO')")
    public ResponseEntity<Responsible> findById(@PathVariable Long id) {

        log.info("Return a Responsible");

        Responsible responsible = responsibleService.findById(id);

        return ResponseEntity.ok(responsible);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_FINANCEIRO')")
    public ResponseEntity<Page<Responsible>> listAllResponsibilities(Pageable pageable) {

        log.info("Get a List of Responsibilities");

        Page<Responsible> responsibilities = responsibleService.listAllResponsibilities(pageable);

        return ResponseEntity.ok(responsibilities);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_FINANCEIRO')")
    public ResponseEntity<Responsible> insertResponsible(@Valid @RequestBody ResponsibleDTO responsibleDTO) {

        log.info("Insert Responsible");

        Responsible createdResponsible = responsibleService.insertResponsible(responsibleDTO);

        return new ResponseEntity<>(createdResponsible, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_FINANCEIRO')")
    public ResponseEntity<Responsible> updateResponsible(@PathVariable Long id,
                                                            @RequestBody ResponsibleDTO responsibleDTO) {

        log.info("Update Responsible");

        Responsible updatedResponsible = responsibleService.updateResponsible(id, responsibleDTO);

        return ResponseEntity.ok(updatedResponsible);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_FINANCEIRO')")
    public ResponseEntity<Void> inactivateResponsible(@PathVariable Long id) {

        log.info("Inactivated Company");

        responsibleService.inactivateResponsible(id);

        return ResponseEntity.noContent().build();
    }
}

package com.albino.tecnologia.osworks.controllers;

import com.albino.tecnologia.osworks.controllers.dto.AdditiveDTO;
import com.albino.tecnologia.osworks.models.Additive;
import com.albino.tecnologia.osworks.services.impl.AdditiveServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/additives")
@RequiredArgsConstructor
@Log4j2
public class AdditiveController {

    private final AdditiveServiceImpl additiveService;

    @PostMapping("/date/{id}")
    @PreAuthorize("hasRole('ROLE_FINANCEIRO')")
    public ResponseEntity<Additive> addEndDate(@PathVariable Long id, @RequestBody AdditiveDTO additiveDTO) {

        log.info("Add of Date Additive");

        Additive dateAdditive = additiveService.addEndDate(id, additiveDTO);

        return new ResponseEntity<>(dateAdditive, HttpStatus.CREATED);
    }

    @PostMapping("/valor/{id}")
    @PreAuthorize("hasRole('ROLE_FINANCEIRO')")
    public ResponseEntity<Additive> addUnitValue(@PathVariable Long id, @RequestBody AdditiveDTO additiveDTO) {

        log.info("Add Unit Value Additive");

        Additive unitValueAdditive = additiveService.addUnitValue(id, additiveDTO);

        return new ResponseEntity<>(unitValueAdditive, HttpStatus.CREATED);
    }

    @PostMapping("/description/{id}")
    @PreAuthorize("hasRole('ROLE_FINANCEIRO')")
    public ResponseEntity<Additive> addDescriptions(@PathVariable Long id, @RequestBody AdditiveDTO additiveDTO) {

        log.info("Add Descriptions Additive");

        Additive descriptionsAdditive = additiveService.addDescriptions(id, additiveDTO);

        return new ResponseEntity<>(descriptionsAdditive, HttpStatus.CREATED);
    }

    @PostMapping("/type/{id}")
    @PreAuthorize("hasRole('ROLE_FINANCEIRO')")
    public ResponseEntity<Additive> addTypeContract(@PathVariable Long id, @RequestBody AdditiveDTO additiveDTO) {

        log.info("Add Type Contract Additive");

        Additive typeContractAdditive = additiveService.addTypeContract(id, additiveDTO);

        return new ResponseEntity<>(typeContractAdditive, HttpStatus.CREATED);
    }

}

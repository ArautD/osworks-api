package com.albino.tecnologia.osworks.reports.controller;

import com.albino.tecnologia.osworks.reports.generator.impl.ContractModelServiceGenerator;
import com.albino.tecnologia.osworks.reports.generator.impl.ServiceOrderModelServiceGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/model")
@RequiredArgsConstructor
@Log4j2
public class ModelGeneratorController {

    private final ServiceOrderModelServiceGenerator modelServiceGenerator;
    private final ContractModelServiceGenerator modelContractGenerator;

    @GetMapping("/os/{id}")
    @PreAuthorize("hasAnyRole('ROLE_DIRETOR','ROLE_GP')")
    public void generatorServiceOrderModel(HttpServletResponse response, @PathVariable Long id) {

        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=os_" + currentDate + ".pdf";

        response.setHeader(headerKey, headerValue);

        modelServiceGenerator.export(response, id);
    }

    @GetMapping("/contract/{id}")
    @PreAuthorize("hasAnyRole('ROLE_DIRETOR','ROLE_FINANCEIRO')")
    public void generatorContractModel(HttpServletResponse response, @PathVariable Long id) {

        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=contract_" + currentDate + ".pdf";

        response.setHeader(headerKey, headerValue);

        modelContractGenerator.export(response, id);
    }
}

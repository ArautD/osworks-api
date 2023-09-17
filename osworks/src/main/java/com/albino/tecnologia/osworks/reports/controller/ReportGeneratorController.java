package com.albino.tecnologia.osworks.reports.controller;

import com.albino.tecnologia.osworks.reports.generator.impl.ContractReportServiceGenerator;
import com.albino.tecnologia.osworks.reports.generator.impl.ServiceOrderReportServiceGenerator;
import com.albino.tecnologia.osworks.reports.generator.impl.ProjectReportServiceGenerator;
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
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@Log4j2
public class ReportGeneratorController {

    private final ContractReportServiceGenerator contractGenerator;
    private final ServiceOrderReportServiceGenerator serviceOrderGenerator;
    private final ProjectReportServiceGenerator projectGenerator;


    @GetMapping("/contract/{id}")
    @PreAuthorize("hasRole('ROLE_DIRETOR')")
    public void generatorContractReport(HttpServletResponse response , @PathVariable Long id){

        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=relatorio_contr_" + currentDate + ".pdf";

        response.setHeader(headerKey,headerValue);

        contractGenerator.export(response,id);
    }

    @GetMapping("/os/{id}")
    @PreAuthorize("hasRole('ROLE_DIRETOR')")
    public void generatorServiceOrderReport(HttpServletResponse response , @PathVariable Long id){

        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=relatorio_os_" + currentDate + ".pdf";

        response.setHeader(headerKey,headerValue);

        serviceOrderGenerator.export(response,id);
    }

    @GetMapping("/project/{id}")
    @PreAuthorize("hasRole('ROLE_DIRETOR')")
    public void generatorProjectReport(HttpServletResponse response , @PathVariable Long id){

        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=relatorio_proj_" + currentDate + ".pdf";

        response.setHeader(headerKey,headerValue);

        projectGenerator.export(response,id);
    }

}

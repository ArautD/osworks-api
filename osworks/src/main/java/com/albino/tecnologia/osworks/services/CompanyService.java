package com.albino.tecnologia.osworks.services;

import com.albino.tecnologia.osworks.controllers.dto.CompanyDTO;
import com.albino.tecnologia.osworks.models.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService {
    Company findById(Long id);
    Page<Company> listAllCompanies(String status, Pageable pageable);
    Company insertCompany(CompanyDTO companyDTO);
    Company updateCompany(Long id, CompanyDTO companyDTO);
    void inactivateCompany(Long id);
}

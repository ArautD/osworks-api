package com.albino.tecnologia.osworks.services.impl;

import com.albino.tecnologia.osworks.controllers.dto.CompanyDTO;
import com.albino.tecnologia.osworks.exceptions.BadRequestException;
import com.albino.tecnologia.osworks.models.Company;
import com.albino.tecnologia.osworks.models.Address;
import com.albino.tecnologia.osworks.models.Responsible;
import com.albino.tecnologia.osworks.repositories.CompanyRespository;
import com.albino.tecnologia.osworks.services.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRespository companyRespository;

    @Override
    public Company findById(Long id) {

        log.info("Found a Company with id:'{}'", id);

        return companyRespository.findById(id)
                .orElseThrow(() -> new BadRequestException("Company not Found"));
    }

    @Override
    public Page<Company> listAllCompanies(String status, Pageable pageable) {

        if (status == null) {

            log.info("List All Companies");

            return companyRespository.findAll(pageable);
        }

        log.info("List All Companies");

        return companyRespository.findByStatus(status, pageable);
    }

    @Override
    @Transactional
    public Company insertCompany(CompanyDTO companyDTO) {

        log.info("New Address '{}' was Created ", companyDTO.getAddress().getBackyard());

        String createdStatus = "ativo";

        Address companyAddress = Address.builder()
                .postalCode(companyDTO.getAddress().getPostalCode())
                .backyard(companyDTO.getAddress().getBackyard())
                .number(companyDTO.getAddress().getNumber())
                .complement(companyDTO.getAddress().getComplement())
                .neighborhood(companyDTO.getAddress().getNeighborhood())
                .city(companyDTO.getAddress().getCity())
                .uf(companyDTO.getAddress().getUf())
                .status(createdStatus)
                .build();

        log.info("New Responsible '{}' was Created ", companyDTO.getResponsible());

        Responsible companyResponsible = Responsible.builder()
                .cpf(companyDTO.getResponsible().getCpf())
                .name(companyDTO.getResponsible().getName())
                .rg(companyDTO.getResponsible().getRg())
                .phoneNumber(companyDTO.getResponsible().getPhoneNumber())
                .email(companyDTO.getResponsible().getEmail())
                .department(companyDTO.getResponsible().getDepartment())
                .post(companyDTO.getResponsible().getPost())
                .address(companyDTO.getResponsible().getAddress())
                .status(createdStatus)
                .build();

        log.info("New Company '{}' was Created ", companyDTO.getCompanyName());

        Company novaCompany = Company.builder()
                .cnpj(companyDTO.getCnpj())
                .companyName(companyDTO.getCompanyName())
                .stateRegistration(companyDTO.getStateRegistration())
                .phoneNumber(companyDTO.getPhoneNumber())
                .companyType(companyDTO.getCompanyType())
                .email(companyDTO.getEmail())
                .address(companyAddress)
                .responsible(companyResponsible)
                .bornDate(companyDTO.getBornDate())
                .status(createdStatus)
                .build();

        return companyRespository.save(novaCompany);
    }

    @Override
    @Transactional
    public Company updateCompany(Long id, CompanyDTO companyDTO) {

        Company companyUpdated = findById(id);

        companyUpdated.setCompanyName(companyDTO.getCompanyName());
        companyUpdated.setStateRegistration(companyDTO.getStateRegistration());
        companyUpdated.setPhoneNumber(companyDTO.getPhoneNumber());
        companyUpdated.setEmail(companyDTO.getEmail());
        companyUpdated.setBornDate(companyDTO.getBornDate());

        log.info("Company of ID:'{}' Has Been Updated ", id);

        return companyRespository.save(companyUpdated);
    }

    @Override
    @Transactional
    public void inactivateCompany(Long id) {

        log.info("Company of ID:'{}' Being Inactivated Along With Your Address and Responsible", id);

        String deleteStatus = "inativo";

        Company company = findById(id);

        company.setStatus(deleteStatus);
        company.getAddress().setStatus(deleteStatus);
        company.getResponsible().setStatus(deleteStatus);
        company.getResponsible().getAddress().setStatus(deleteStatus);

        companyRespository.save(company);

    }
}

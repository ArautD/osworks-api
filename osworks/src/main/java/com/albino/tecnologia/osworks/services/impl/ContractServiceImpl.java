package com.albino.tecnologia.osworks.services.impl;

import com.albino.tecnologia.osworks.controllers.dto.ContractDTO;
import com.albino.tecnologia.osworks.exceptions.BadRequestException;
import com.albino.tecnologia.osworks.models.Additive;
import com.albino.tecnologia.osworks.models.Company;
import com.albino.tecnologia.osworks.models.Contract;
import com.albino.tecnologia.osworks.models.ServiceOrder;
import com.albino.tecnologia.osworks.repositories.ContractRepository;
import com.albino.tecnologia.osworks.repositories.CompanyRespository;
import com.albino.tecnologia.osworks.services.ContractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ContractServiceImpl implements ContractService {
    private static int id = 1;

    private final ContractRepository contractRepository;
    private final CompanyRespository companyRespository;

    @Override
    public Contract findById(Long id) {

        log.info("Found a contract with id:'{}'", id);

        return contractRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Contract not Found"));
    }

    @Override
    public Page<Contract> listAllContracts(Pageable pageable) {

        log.info("Listing All Contracts");

        return contractRepository.findAll(pageable);
    }

    @Override
    public List<ServiceOrder> listAllOSsContract(Long id) {

        log.info("Listing All OSs of Contract with ID: '{}' ", id);

        Contract contract = findById(id);

        return contract.getOs();
    }

    @Override
    public List<Additive> listAllAdditivesContract(Long id) {

        log.info("Listing All Additives of Contract with ID: '{}' ", id);

        Contract contract = findById(id);

        return contract.getAdditives();
    }

    @Override
    public List<Contract> findContractByEndDate(LocalDate endDate) {

        log.info("Listing All Contracts with the expiration date'{}' ", endDate);

        return contractRepository.findByEndDate(endDate);
    }

    @Override
    public List<Contract> listContractByExpirationNumberDate(Integer numbersDay) {

        LocalDate beginDate = LocalDate.now();
        LocalDate endDate = beginDate.plusDays(numbersDay);

        log.info("Listing All Contracts With Expiration Dates Of '{}' Days ", numbersDay);

        return contractRepository.findByEndDateBetween(beginDate, endDate);
    }

    @Override
    public Integer seeEndNumberDays(Long id) {

        log.info("Contract Deadline with id '{}' was saw",id);

        return contractRepository.findEndNumberDays(id);
    }



    @Override
    public Contract insertContract(ContractDTO contractDTO) {

        Integer contador = genarateCodeContract();
        String codeContract = String.format("%03d/%d", contador, LocalDate.now().getYear());

        Company company = companyRespository.findById(contractDTO.getCompanyId()).get();

        if (company.getStatus().equals("inativa")) throw new BadRequestException("The Company is Inactive");

        BigDecimal valorTotal =
                contractDTO.getUnitValue().multiply(BigDecimal.valueOf(contractDTO.getNumberFunctionPoints()));

        Contract novoContract = Contract.builder()
                .contractCode(codeContract)
                .beginDate(contractDTO.getBeginDate())
                .endDate(contractDTO.getEndDate())
                .numberFunctionPointsTotal(contractDTO.getNumberFunctionPoints())
                .numberFunctionPoints(contractDTO.getNumberFunctionPoints())
                .unitValue(contractDTO.getUnitValue())
                .unitValueTotal(valorTotal)
                .descriptions(contractDTO.getDescriptions())
                .company(company)
                .status("ativo")
                .contractTypes(contractDTO.getContractTypes())
                .build();

        log.info("New Contract '{}' was Created ", novoContract.getContractCode());

        return contractRepository.save(novoContract);
    }


    @Override
    public void inactivateContract(Long id) {

        Contract contract = findById(id);

        contract.setStatus("inativo");

        log.info("Contract '{}' has been Inactivated", id);

        contractRepository.save(contract);

    }

    public synchronized static Integer genarateCodeContract() {

        Integer ano = LocalDate.now().getYear();

        if (ano != LocalDate.now().getYear()) {
            id = 0;
        }

        return id++;
    }
}

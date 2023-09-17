package com.albino.tecnologia.osworks.services.impl;

import com.albino.tecnologia.osworks.controllers.dto.ServiceOrderDTO;
import com.albino.tecnologia.osworks.exceptions.BadRequestException;
import com.albino.tecnologia.osworks.models.Contract;
import com.albino.tecnologia.osworks.models.Company;
import com.albino.tecnologia.osworks.models.ServiceOrder;
import com.albino.tecnologia.osworks.models.Responsible;
import com.albino.tecnologia.osworks.repositories.ContractRepository;
import com.albino.tecnologia.osworks.repositories.CompanyRespository;
import com.albino.tecnologia.osworks.repositories.ServiceOrderRepository;
import com.albino.tecnologia.osworks.repositories.ResponsibleRepository;
import com.albino.tecnologia.osworks.services.ServiceOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ServiceOrderServiceImpl implements ServiceOrderService {
    private static int id = 1;

    private final ServiceOrderRepository serviceOrderRepository;
    private final ContractRepository contractRepository;
    private final CompanyRespository companyRespository;
    private final ResponsibleRepository responsibleRepository;


    @Override
    public ServiceOrder findById(Long id) {

        log.info("Found a ServiceOrder with id:'{}'", id);

        return serviceOrderRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("ServiceOrder Not Found"));

    }

    @Override
    public Page<ServiceOrder> listAllOS(Pageable pageable) {

        log.info("List All Services Orders");

        return serviceOrderRepository.findAll(pageable);
    }

    @Override
    public List<ServiceOrder> listAllOSbyStatus(String status) {

        log.info("Listing All Services Orders With Status of '{}'",status);

        return serviceOrderRepository.findAllByStatus(status);
    }

    @Override
    public Contract findContractByOS(Long id) {

        log.info("Find Contract of Services Orders with ID: '{}'",id);

        ServiceOrder serviceOrder = findById(id);

        return serviceOrder.getContract();
    }

    @Override
    @Transactional
    public ServiceOrder insertOS(Long id, ServiceOrderDTO serviceOrderDTO) {

        Contract contract = contractRepository.findById(id).get();
        Company company = companyRespository.findById(serviceOrderDTO.getCompanyId()).get();
        Responsible responsible = responsibleRepository.findById(serviceOrderDTO.getResponsibleId()).get();

        Long numberFunctionPoints = contract.getNumberFunctionPoints();
        Long numberFunctionPointsUpdated = numberFunctionPoints - serviceOrderDTO.getNumberFunctionPoints();
        contract.setNumberFunctionPoints(numberFunctionPointsUpdated);

        Integer counter = generateCodeOS();
        String osCode = String.format("OS-Nº%05d", counter);

        isOS(serviceOrderDTO,id);

        log.info("New ServiceOrder Created '{}'", osCode);

        ServiceOrder novaServiceOrder = ServiceOrder.builder()
                .osCode(osCode)
                .description(serviceOrderDTO.getDescription())
                .hoursNumber(serviceOrderDTO.getHoursNumber())
                .numberFunctionPoints(serviceOrderDTO.getNumberFunctionPoints())
                .contract(contract)
                .responsible(responsible)
                .company(company)
                .status("ativo")
                .openDate(LocalDate.now())
                .build();

        return serviceOrderRepository.save(novaServiceOrder);

    }

    @Override
    @Transactional
    public ServiceOrder updateOS(Long id, ServiceOrderDTO serviceOrderDTO) {

        ServiceOrder serviceOrderUpdated = findById(id);

        serviceOrderUpdated.setDescription(serviceOrderDTO.getDescription());
        serviceOrderUpdated.setHoursNumber(serviceOrderDTO.getHoursNumber());
        serviceOrderUpdated.setNumberFunctionPoints(serviceOrderDTO.getNumberFunctionPoints());

        log.info("ServiceOrder with ID:'{}' Updated",id);

        return serviceOrderRepository.save(serviceOrderUpdated);
    }

    @Override
    public ServiceOrder executeOS(Long id) {

        ServiceOrder serviceOrder = findById(id);

        serviceOrder.setStatus("em execucao");

        return serviceOrderRepository.save(serviceOrder);
    }

    @Override
    public ServiceOrder finalizeOS(Long id) {

        ServiceOrder serviceOrder = findById(id);

        log.info("ServiceOrder with Code:'{}' Has Been Finalized", serviceOrder.getOsCode());

        serviceOrder.setStatus("finalizada");

        return serviceOrderRepository.save(serviceOrder);
    }

    @Override
    public void inactivateOS(Long id) {

        ServiceOrder inactivatedOS = findById(id);
        inactivatedOS.setStatus("inativo");

        serviceOrderRepository.save(inactivatedOS);

        log.info("ServiceOrder of ID:'{}' has been Inactivated", id);
    }

    public static Integer generateCodeOS(){
        return id++;
    }

    public void isOS(ServiceOrderDTO serviceOrderDTO, Long id){

        Contract contract = contractRepository.findById(id).get();
        Company company = companyRespository.findById(serviceOrderDTO.getCompanyId()).get();
        Responsible responsible = responsibleRepository.findById(serviceOrderDTO.getResponsibleId()).get();

        if (contract.getStatus().equals("inativo")) throw new BadRequestException("Contract is Inactivated");
        if (company.getStatus().equals("inativo")) throw new BadRequestException("Company is Inactivated");
        if (responsible.getStatus().equals("inativo")) throw new BadRequestException("Responsible is Inactivated");

    }

}

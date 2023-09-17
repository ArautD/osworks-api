package com.albino.tecnologia.osworks.services;

import com.albino.tecnologia.osworks.controllers.dto.ContractDTO;
import com.albino.tecnologia.osworks.models.Additive;
import com.albino.tecnologia.osworks.models.Contract;
import com.albino.tecnologia.osworks.models.ServiceOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ContractService {
    Contract findById(Long id);
    Page<Contract> listAllContracts(Pageable pageable);
    List<ServiceOrder> listAllOSsContract(Long id);
    List<Additive> listAllAdditivesContract(Long id);
    List<Contract> findContractByEndDate(LocalDate dataDeVencimento);
    List<Contract> listContractByExpirationNumberDate(Integer numerosDeDias);
    Integer seeEndNumberDays(Long id);
    Contract insertContract(ContractDTO contractDTO);
    void inactivateContract(Long id);
}

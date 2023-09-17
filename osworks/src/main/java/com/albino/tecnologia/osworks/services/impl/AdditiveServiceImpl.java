package com.albino.tecnologia.osworks.services.impl;

import com.albino.tecnologia.osworks.controllers.dto.AdditiveDTO;
import com.albino.tecnologia.osworks.exceptions.BadRequestException;
import com.albino.tecnologia.osworks.models.Additive;
import com.albino.tecnologia.osworks.models.Contract;
import com.albino.tecnologia.osworks.repositories.AdditiveRepository;
import com.albino.tecnologia.osworks.repositories.ContractRepository;
import com.albino.tecnologia.osworks.services.AdditiveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class AdditiveServiceImpl implements AdditiveService {

    private final ContractRepository contractRepository;
    private final AdditiveRepository additiveRepository;

    Additive additive = new Additive();

    @Override
    @Transactional
    public Additive addEndDate(Long id, AdditiveDTO additiveDTO) {

        log.info("Adding new contract expiration date with ID: '{}' ",id);

        Contract contract = findContractById(id);
        additive.setEndDate(additiveDTO.getEndDate());
        additive.setContract(contract);

        additiveRepository.save(additive);

        contract.getAdditives().add(additive);
        contractRepository.save(contract);

        return additive;
    }

    @Override
    @Transactional
    public Additive addUnitValue(Long id, AdditiveDTO additiveDTO) {

        log.info("Adding New Contract Unit Value With ID:'{}'",id);

        Contract contract = findContractById(id);

        additive.setUnitValue(additiveDTO.getUnitValue());
        additive.setContract(contract);

        additiveRepository.save(additive);

        contract.getAdditives().add(additive);
        contractRepository.save(contract);

        return additive;
    }

    @Override
    @Transactional
    public Additive addDescriptions(Long id, AdditiveDTO additiveDTO) {

        log.info("Adding new contract descriptions with ID:'{}'",id);

        Contract contract = findContractById(id);

        additive.setDescriptions(additiveDTO.getDescriptions());
        additive.setContract(contract);

        additiveRepository.save(additive);

        contract.getAdditives().add(additive);
        contractRepository.save(contract);

        return additive;
    }

    @Override
    @Transactional
    public Additive addTypeContract(Long id, AdditiveDTO additiveDTO) {

        log.info("Adding New Contract Type in Contract with ID:'{}'",id);

        Contract contract = findContractById(id);

        additive.setContractTypes(additiveDTO.getContractTypes());
        additive.setContract(contract);

        additiveRepository.save(additive);

        contract.getAdditives().add(additive);
        contractRepository.save(contract);

        return additive;
    }

    public Contract findContractById(Long id){

        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Contract not found"));

        if (contract.getStatus().equals("inativo")) throw new BadRequestException("The contract is inactive");

        return contract;
    }
}

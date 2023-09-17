package com.albino.tecnologia.osworks.services.impl;

import com.albino.tecnologia.osworks.controllers.dto.ResponsibleDTO;
import com.albino.tecnologia.osworks.models.Responsible;
import com.albino.tecnologia.osworks.repositories.ResponsibleRepository;
import com.albino.tecnologia.osworks.services.ResponsibleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ResponsibleServiceImpl implements ResponsibleService {

    private final ResponsibleRepository responsibleRepository;

    @Override
    public Responsible findById(Long id) {

        log.info("Found a Responsible with id:'{}'", id);

        return responsibleRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Responsible Not Found"));
    }

    @Override
    public Page<Responsible> listAllResponsibilities(Pageable pageable) {

        log.info("List All Responsibilities");

        return responsibleRepository.findAll(pageable);
    }

    @Override
    public Responsible insertResponsible(ResponsibleDTO responsibleDTO) {

        Responsible newResponsible = Responsible.builder()
                .cpf(responsibleDTO.getCpf())
                .name(responsibleDTO.getName())
                .rg(responsibleDTO.getRg())
                .phoneNumber(responsibleDTO.getPhoneNumber())
                .email(responsibleDTO.getEmail())
                .status("ativo")
                .department(responsibleDTO.getDepartment())
                .post(responsibleDTO.getPost())
                .build();

        log.info("New Responsible Created '{}'", responsibleDTO);

        return responsibleRepository.save(newResponsible);
    }

    @Override
    public Responsible updateResponsible(Long id, ResponsibleDTO responsibleDTO) {

        Responsible responsibleUpdated = findById(id);

        responsibleUpdated.setCpf(responsibleDTO.getCpf());
        responsibleUpdated.setName(responsibleDTO.getName());
        responsibleUpdated.setRg(responsibleDTO.getRg());
        responsibleUpdated.setPhoneNumber(responsibleDTO.getPhoneNumber());
        responsibleUpdated.setEmail(responsibleDTO.getEmail());
        responsibleUpdated.setDepartment(responsibleDTO.getDepartment());
        responsibleUpdated.setPost(responsibleDTO.getPost());

        log.info("Responsible of ID:'{}' Has Been Updated", id);

        return responsibleRepository.save(responsibleUpdated);
    }

    @Override
    public void inactivateResponsible(Long id) {

        Responsible InactivatedResponsible = findById(id);

        InactivatedResponsible.setStatus("inativo");

        responsibleRepository.save(InactivatedResponsible);

        log.info("Responsible with ID:'{}' has been Inactivated", id);
    }
}

package com.albino.tecnologia.osworks.services;

import com.albino.tecnologia.osworks.controllers.dto.ResponsibleDTO;
import com.albino.tecnologia.osworks.models.Responsible;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ResponsibleService {
    Responsible findById(Long id);
    Page<Responsible> listAllResponsibilities(Pageable pageable);
    Responsible insertResponsible(ResponsibleDTO responsibleDTO);
    Responsible updateResponsible(Long id, ResponsibleDTO responsibleDTO);
    void inactivateResponsible(Long id);
}

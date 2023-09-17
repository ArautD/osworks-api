package com.albino.tecnologia.osworks.services;

import com.albino.tecnologia.osworks.controllers.dto.ServiceOrderDTO;
import com.albino.tecnologia.osworks.models.Contract;
import com.albino.tecnologia.osworks.models.ServiceOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ServiceOrderService {
    ServiceOrder findById(Long id);
    Page<ServiceOrder> listAllOS(Pageable pageable);
    List<ServiceOrder> listAllOSbyStatus(String status);
    Contract findContractByOS(Long id);
    ServiceOrder insertOS(Long id, ServiceOrderDTO serviceOrderDTO);
    ServiceOrder updateOS(Long id, ServiceOrderDTO serviceOrderDTO);
    ServiceOrder executeOS(Long id);
    ServiceOrder finalizeOS(Long id);
    void inactivateOS(Long id);
}

package com.albino.tecnologia.osworks.repositories;

import com.albino.tecnologia.osworks.models.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {

    List<ServiceOrder> findAllByStatus(String status);
}

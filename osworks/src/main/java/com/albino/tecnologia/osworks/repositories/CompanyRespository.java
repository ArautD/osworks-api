package com.albino.tecnologia.osworks.repositories;

import com.albino.tecnologia.osworks.models.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CompanyRespository extends JpaRepository<Company,Long> {

    Page<Company> findByStatus(String status, Pageable pageable);
}

package com.albino.tecnologia.osworks.repositories;
import com.albino.tecnologia.osworks.models.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract,Long> {

    List<Contract> findByEndDate(LocalDate endDate);
    List<Contract> findByEndDateBetween(LocalDate dataInicial, LocalDate dataFinal);
    @Query(value = "SELECT CASE WHEN data_termino < CURRENT_DATE THEN 0 ELSE " +
            "DATE_PART('day', AGE(data_termino, CURRENT_DATE)) END AS dias_para_vencer FROM contrato " +
            "WHERE id = :id", nativeQuery = true)
    int findEndNumberDays(@Param("id") Long id);
}

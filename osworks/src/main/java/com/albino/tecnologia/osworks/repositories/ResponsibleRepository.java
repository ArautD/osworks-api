package com.albino.tecnologia.osworks.repositories;

import com.albino.tecnologia.osworks.models.Responsible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponsibleRepository extends JpaRepository<Responsible, Long> {

}

package com.albino.tecnologia.osworks.repositories;

import com.albino.tecnologia.osworks.models.Additive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.nio.file.LinkOption;

@Repository
public interface AdditiveRepository extends JpaRepository<Additive, Long> {

}

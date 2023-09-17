package com.albino.tecnologia.osworks.repositories;

import com.albino.tecnologia.osworks.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}

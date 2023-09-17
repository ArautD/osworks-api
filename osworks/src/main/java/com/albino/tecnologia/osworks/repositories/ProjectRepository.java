package com.albino.tecnologia.osworks.repositories;

import com.albino.tecnologia.osworks.models.Project;
import com.albino.tecnologia.osworks.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {

    List<Project> findByProjectManager(UserModel projectManager);

}

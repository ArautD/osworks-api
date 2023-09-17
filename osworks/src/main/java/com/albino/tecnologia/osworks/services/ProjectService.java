package com.albino.tecnologia.osworks.services;


import com.albino.tecnologia.osworks.controllers.dto.ProjectDTO;
import com.albino.tecnologia.osworks.controllers.dto.UserByIdDTO;
import com.albino.tecnologia.osworks.controllers.dto.UserByUsernameDTO;
import com.albino.tecnologia.osworks.models.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectService {
    Project findById(Long id);
    Page<Project> listAllProjects(Pageable pageable);
    List<Project> listProjectsByProjectManager(Long id);
    Project insertProject(ProjectDTO projectDTO);
    Project updateProject(Long id, ProjectDTO projectDTO);
    Project deliverProject(Long id, UserByIdDTO usuarioDTO);
    Project deliverProjectByUsername(Long id, UserByUsernameDTO usernameDTO);
    Project finalizeProject(Long id);
    void inactivateProject(Long id);
}

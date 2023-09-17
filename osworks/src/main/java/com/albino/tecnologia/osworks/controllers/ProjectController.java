package com.albino.tecnologia.osworks.controllers;

import com.albino.tecnologia.osworks.controllers.dto.ProjectDTO;
import com.albino.tecnologia.osworks.controllers.dto.UserByIdDTO;
import com.albino.tecnologia.osworks.controllers.dto.UserByUsernameDTO;
import com.albino.tecnologia.osworks.models.Project;
import com.albino.tecnologia.osworks.services.impl.ProjectServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
@Log4j2
public class ProjectController {

    private final ProjectServiceImpl projectService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_GPP')")
    public ResponseEntity<Project> findById(@PathVariable Long id) {

        log.info("Return a Project");

        Project project = projectService.findById(id);

        return ResponseEntity.ok(project);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_GPP')")
    public ResponseEntity<Page<Project>> listAllProjects(Pageable pageable) {

        log.info("Get a List of Projects");

        Page<Project> projects = projectService.listAllProjects(pageable);

        return ResponseEntity.ok(projects);
    }

    @GetMapping("/gp/{id}")
    @PreAuthorize("hasRole('ROLE_GP')")
    public ResponseEntity<List<Project>> listProjectsByProjectManager(@PathVariable Long id) {

        log.info("Get a List of Projects for the specified Project Manager");

        List<Project> projectsByProjectManager = projectService.listProjectsByProjectManager(id);

        return ResponseEntity.ok(projectsByProjectManager);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_GPP')")
    public ResponseEntity<Project> insertProject(@Valid @RequestBody ProjectDTO projectDTO) {

        log.info("Insert a Project");

        Project createdProject = projectService.insertProject(projectDTO);

        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_GPP')")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody ProjectDTO projectDTO) {

        log.info("Update Project");

        Project updatedProject = projectService.updateProject(id, projectDTO);

        return ResponseEntity.ok(updatedProject);

    }

    @PutMapping("deliver/{id}")
    @PreAuthorize("hasRole('ROLE_GPP')")
    public ResponseEntity<Project> deliverProject(@PathVariable Long id, @RequestBody @Valid UserByIdDTO userDTO) {

        log.info("Project has been Delivered");

        Project deliveredProject = projectService.deliverProject(id, userDTO);

        return ResponseEntity.ok(deliveredProject);

    }

    @PutMapping("deliver/username/{id}")
    @PreAuthorize("hasRole('ROLE_GPP')")
    public ResponseEntity<Project> deliverProjectByUsername(@PathVariable Long id,
                                                            @Valid @RequestBody UserByUsernameDTO userDTO) {

        log.info("Project has been Delivered By Username");

        Project deliveredProject = projectService.deliverProjectByUsername(id, userDTO);

        return ResponseEntity.ok(deliveredProject);

    }

    @PutMapping("finalize/{id}")
    @PreAuthorize("hasRole('ROLE_GP')")
    public ResponseEntity<Project> finalizeProject(@PathVariable Long id) {

        log.info("Update Company");

        Project finalizedProject = projectService.finalizeProject(id);

        return ResponseEntity.ok(finalizedProject);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_GPP')")
    public ResponseEntity<Void> inactivateProject(@PathVariable Long id) {

        log.info("Inactivated Company");

        projectService.inactivateProject(id);

        return ResponseEntity.noContent().build();
    }
}

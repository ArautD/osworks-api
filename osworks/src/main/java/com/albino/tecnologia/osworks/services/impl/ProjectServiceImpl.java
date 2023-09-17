package com.albino.tecnologia.osworks.services.impl;

import com.albino.tecnologia.osworks.controllers.dto.ProjectDTO;
import com.albino.tecnologia.osworks.controllers.dto.UserByIdDTO;
import com.albino.tecnologia.osworks.controllers.dto.UserByUsernameDTO;
import com.albino.tecnologia.osworks.exceptions.BadRequestException;
import com.albino.tecnologia.osworks.models.ServiceOrder;
import com.albino.tecnologia.osworks.models.Project;
import com.albino.tecnologia.osworks.models.UserModel;
import com.albino.tecnologia.osworks.repositories.ServiceOrderRepository;
import com.albino.tecnologia.osworks.repositories.ProjectRepository;
import com.albino.tecnologia.osworks.repositories.UserModelRepository;
import com.albino.tecnologia.osworks.services.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ServiceOrderRepository serviceOrderRepository;
    private final UserModelRepository userModelRepository;

    @Override
    public Project findById(Long id) {

        log.info("Found a Project with id:'{}'",id);

        return projectRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Project Not Found"));
    }


    @Override
    public Page<Project> listAllProjects(Pageable pageable) {

        log.info("List All Projects");

        return projectRepository.findAll(pageable);
    }

    @Override
    public List<Project> listProjectsByProjectManager(Long id) {

        UserModel userModel = findGppById(new UserByIdDTO(id));

        log.info("Listing All Projects From Project Manager '{}' By Id", userModel.getName());

        return projectRepository.findByProjectManager(userModel);
    }

    @Override
    public Project insertProject(ProjectDTO projectDTO) {

        log.info("New Project Created '{}'", projectDTO);

        ServiceOrder serviceOrder = serviceOrderRepository.findById(projectDTO.getServiceOrderId()).get();

        if (serviceOrder.getStatus().equals("inativo") || serviceOrder.getStatus().equals("finalizada"))
            throw new BadRequestException("ServiceOrder is Inactive or Finished");

        Project novoProject = Project.builder()
                .name(projectDTO.getName())
                .description(projectDTO.getDescription())
                .beginDate(projectDTO.getBeginDate())
                .serviceOrder(serviceOrder)
                .endDate(projectDTO.getEndDate())
                .status("ativo")
                .build();

        return projectRepository.save(novoProject);
    }

    @Override
    public Project updateProject(Long id, ProjectDTO projectDTO) {

        Project projectUpdated = findById(id);

        projectUpdated.setName(projectDTO.getName());
        projectUpdated.setDescription(projectDTO.getDescription());
        projectUpdated.setBeginDate(projectDTO.getBeginDate());
        projectUpdated.setEndDate(projectDTO.getEndDate());

        log.info("Project from ID:'{}' Has been updated ", id);

        return projectRepository.save(projectUpdated);
    }

    @Override
    public Project deliverProject(Long id, UserByIdDTO userDTO) {

        Project projectDelivered = findById(id);
        UserModel userModel = findGppById(new UserByIdDTO(userDTO.getUserId()));

        log.info("Project with Id '{}' has been distributed To '{}'",id, userModel.getName());

        projectDelivered.setProjectManager(userModel);
        projectDelivered.setStatus("em andamento");

        return projectRepository.save(projectDelivered);
    }

    @Override
    public Project deliverProjectByUsername(Long id, UserByUsernameDTO usernameDTO) {

        Project projectDelivered = findById(id);
        UserModel userModel = findGppByUsername(new UserByUsernameDTO(usernameDTO.getUsername()));

        log.info("Project with Id '{}' has been distributed To '{}'",id, userModel.getName());

        projectDelivered.setProjectManager(userModel);
        projectDelivered.setStatus("em andamento");

        return projectRepository.save(projectDelivered);
    }

    @Override
    public Project finalizeProject(Long id) {

        Project projectFinalized = findById(id);

        projectFinalized.setStatus("concluido");

        log.info("Project with Id '{}' Finalized",id);

        return projectRepository.save(projectFinalized);
    }

    @Override
    public void inactivateProject(Long id) {

        Project projectInactivated = findById(id);
        projectInactivated.setStatus("inativo");

        projectRepository.save(projectInactivated);

        log.info("Project of ID:'{}' has been inactivated", id);
    }

    private UserModel findGppById(UserByIdDTO userByIdDTO){

        UserModel userModel = userModelRepository.findById(userByIdDTO.getUserId())
                .orElseThrow(() -> new BadRequestException("User Not Found"));

        boolean roleGp = userModel.getRoles().stream().anyMatch(role -> role.getRoleName().name().equals("ROLE_GP"));
        if (!roleGp) throw new BadRequestException("User is Not Manager Project, please Verify");

        if (userModel.getStatus().equals("inativo"))
            throw new BadRequestException("User is Deactivated");

        return userModel;
    }

    private UserModel findGppByUsername(UserByUsernameDTO usernameDTO){

        UserModel userModel = userModelRepository.findByUsername(usernameDTO.getUsername())
                .orElseThrow(() -> new BadRequestException("User Not Found"));

        boolean roleGp = userModel.getRoles().stream().anyMatch(role -> role.getRoleName().name().equals("ROLE_GP"));
        if (!roleGp) throw new BadRequestException("User is Not Manager Project, please Verify");

        if (userModel.getStatus().equals("inativo"))
            throw new BadRequestException("User is Deactivated");

        return userModel;
    }
}

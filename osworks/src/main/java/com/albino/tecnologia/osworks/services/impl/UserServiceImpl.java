package com.albino.tecnologia.osworks.services.impl;

import com.albino.tecnologia.osworks.controllers.dto.ChangePasswordDTO;
import com.albino.tecnologia.osworks.controllers.dto.UserDTO;
import com.albino.tecnologia.osworks.models.Role;
import com.albino.tecnologia.osworks.models.UserModel;
import com.albino.tecnologia.osworks.repositories.RoleRepository;
import com.albino.tecnologia.osworks.repositories.UserModelRepository;
import com.albino.tecnologia.osworks.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserModelRepository userModelRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserModel findById(Long id) {

        log.info("Found a User with id:'{}'", id);

        return userModelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    @Override
    public Page<UserModel> listAllUsers(Pageable pageable) {

        log.info("List All Users");

        return userModelRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public UserModel insertUser(UserDTO userDTO) {

        log.info("Creating a new User '{}'", userDTO.getUsername());

        UserModel novoUserModel = UserModel.builder()
                .name(userDTO.getName())
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .status("ativo")
                .creationDate(LocalDateTime.now())
                .build();

        List<Role> roles = new ArrayList<>();

        log.info("Creating Roles for the User");

        for (Long roleId : userDTO.getRoleIds()) {
            Role role = roleRepository.findById(roleId).get();
            roles.add(role);
        }

        log.info("The Roles of User '{}' will be '{}'", userDTO.getUsername(),roles);

        novoUserModel.setRoles(roles);

        return userModelRepository.save(novoUserModel);
    }

    @Override
    public UserModel updateUser(Long id, UserDTO userDTO) {

        UserModel userModelUpdated = findById(id);

        userModelUpdated.setName(userDTO.getName());
        userModelUpdated.setUsername(userDTO.getUsername());
        userModelUpdated.setEmail(userDTO.getEmail());

        log.info("User '{}' Has Been Updated",userModelUpdated.getUsername());

        return userModelRepository.save(userModelUpdated);
    }

    @Override
    public void inactivateUser(Long id) {

        UserModel userModelInactivated = findById(id);
        userModelInactivated.setStatus("inativo");

        userModelRepository.save(userModelInactivated);

        log.info("UserModel '{}' has been inactivated", userModelInactivated);
    }

    @Override
    public UserModel changePasswordById(Long id, ChangePasswordDTO senhaDTO) {

        UserModel userModelUpdated = findById(id);

        log.info("User's password '{}' has been changed", userModelUpdated.getUsername());

        userModelUpdated.setPassword(passwordEncoder.encode(senhaDTO.getPassword()));

        return userModelRepository.save(userModelUpdated);
    }

    @Override
    public UserModel changePasswordByUsername(String username, ChangePasswordDTO senhaDTO) {

        UserModel userModelUpdated = userModelRepository.findByUsername(username).get();

        log.info("User's password '{}' has been changed to username", userModelUpdated.getUsername());

        userModelUpdated.setPassword(passwordEncoder.encode(senhaDTO.getPassword()));

        return userModelRepository.save(userModelUpdated);
    }

    @Override
    public UserModel changePasswordByEmail(String email, ChangePasswordDTO senhaDTO) {

        UserModel userModelUpdated = userModelRepository.findByEmail(email).get();

        log.info("User's password '{}' has been changed to E-mail", userModelUpdated.getUsername());

        userModelUpdated.setPassword(passwordEncoder.encode(senhaDTO.getPassword()));

        return userModelRepository.save(userModelUpdated);
    }
}

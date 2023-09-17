package com.albino.tecnologia.osworks.controllers;

import com.albino.tecnologia.osworks.controllers.dto.ChangePasswordDTO;
import com.albino.tecnologia.osworks.controllers.dto.UserDTO;
import com.albino.tecnologia.osworks.models.UserModel;
import com.albino.tecnologia.osworks.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Log4j2
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserModel> findById(@PathVariable Long id) {

        log.info("Return a User");

        UserModel userModel = userService.findById(id);

        return ResponseEntity.ok(userModel);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<UserModel>> listAllUsers(Pageable pageable) {

        log.info("Get a List of Users");

        Page<UserModel> users = userService.listAllUsers(pageable);

        return ResponseEntity.ok(users);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserModel> insertUser(@Valid @RequestBody UserDTO userDTO) {

        log.info("Insert a User");

        UserModel createdUser = userService.insertUser(userDTO);

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserModel> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {

        log.info("Update User");

        UserModel updatedUser = userService.updateUser(id, userDTO);

        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/password/id/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserModel> changePasswordById(@PathVariable Long id,
                                                        @RequestBody ChangePasswordDTO passwordDTO) {

        log.info("Change Password By Id");

        UserModel userModel = userService.changePasswordById(id, passwordDTO);

        return ResponseEntity.ok(userModel);
    }

    @PutMapping("/password/username/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserModel> changePasswordByUsername(@PathVariable String username,
                                                              @RequestBody ChangePasswordDTO passwordDTO) {

        log.info("Change Password By Username");

        UserModel userModel = userService.changePasswordByUsername(username, passwordDTO);

        return ResponseEntity.ok(userModel);
    }

    @PutMapping("/password/email/{email}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserModel> changePasswordByEmail(@PathVariable String email,
                                                           @RequestBody ChangePasswordDTO passwordDTO) {

        log.info("Change Password By E-mail");

        UserModel userModel = userService.changePasswordByEmail(email, passwordDTO);

        return ResponseEntity.ok(userModel);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> inactivateUser(@PathVariable Long id) {

        log.info("Inactivated User");

        userService.inactivateUser(id);

        return ResponseEntity.noContent().build();
    }

}

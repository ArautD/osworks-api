package com.albino.tecnologia.osworks.services;

import com.albino.tecnologia.osworks.controllers.dto.ChangePasswordDTO;
import com.albino.tecnologia.osworks.controllers.dto.UserDTO;
import com.albino.tecnologia.osworks.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserModel findById(Long id);
    Page<UserModel> listAllUsers(Pageable pageable);
    UserModel insertUser(UserDTO userDTO);
    UserModel updateUser(Long id, UserDTO userDTO);
    void inactivateUser(Long id);
    UserModel changePasswordById(Long id, ChangePasswordDTO senhaDTO);
    UserModel changePasswordByUsername(String username, ChangePasswordDTO senhaDTO);
    UserModel changePasswordByEmail(String email, ChangePasswordDTO senhaDTO);
}

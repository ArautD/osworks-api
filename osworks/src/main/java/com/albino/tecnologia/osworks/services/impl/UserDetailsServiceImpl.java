package com.albino.tecnologia.osworks.services.impl;

import com.albino.tecnologia.osworks.exceptions.BadRequestException;
import com.albino.tecnologia.osworks.models.UserModel;
import com.albino.tecnologia.osworks.repositories.UserModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserModelRepository userModelRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserModel userModel = userModelRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with This " + username));


        if (userModel.getStatus().equals("inativo")) {
            throw new BadRequestException("User " + userModel.getUsername() + " is Inactivated");
        }

        return new User(userModel.getUsername(), userModel.getPassword(), true,true,
                true,true, userModel.getAuthorities());
    }

}

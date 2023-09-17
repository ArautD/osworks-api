package com.albino.tecnologia.osworks.beans;

import com.albino.tecnologia.osworks.models.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {

    @Bean
    Company company() {
        return new Company();
    }

    @Bean
    Contract contract() {
        return new Contract();
    }

    @Bean
    Responsible responsible() {
        return new Responsible();
    }

    @Bean
    Address address() {
        return new Address();
    }

    @Bean
    Project project() {
        return new Project();
    }

    @Bean
    ServiceOrder serviceOrder() {
        return new ServiceOrder();
    }

    @Bean
    Role role(){
        return new Role();
    }

    @Bean
    UserModel userModel(){
        return new UserModel();
    }


}

package com.albino.tecnologia.osworks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class OsworksApplication {

	public static void main(String[] args) {
		SpringApplication.run(OsworksApplication.class, args);
	}

}

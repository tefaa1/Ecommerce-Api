package com.ecommerce_api.demo;

import com.ecommerce_api.demo.model.entity.Role;
import com.ecommerce_api.demo.model.entity.User;
import com.ecommerce_api.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(UserRepository userRepository,
								  PasswordEncoder passwordEncoder,
								  @Value("${admin.email}") String email,
								  @Value("${admin.password}") String password){

		return args -> {
			if(!userRepository.existsByEmail(email)){
				User admin = User.builder()
						.firstName("Mohamed")
						.lastName("Abdellatif")
						.email(email)
						.password(passwordEncoder.encode(password))
						.role(Role.ADMIN)
						.build();
				userRepository.save(admin);
			}
		};
	}
}

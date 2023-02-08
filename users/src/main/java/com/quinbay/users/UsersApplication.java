package com.quinbay.users;

import com.quinbay.users.controller.UserController;
import com.quinbay.users.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
//@EnableJpaRepositories("com.quinbay.users.repository")
//@EntityScan("com.quinbay.users.model")
//@ComponentScan({"com.quinbay.users.service", "com.quinbay.users.controller​", "com.quinbay.users.repository"})
//
//@ComponentScan(basePackageClasses=UserController.class)
public class UsersApplication {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/robotpart/*").allowedOrigins("").allowedMethods("GET", "POST","PUT", "DELETE");
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);
	}

}

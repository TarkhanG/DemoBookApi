package com.books;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "DemoBook REST API Documentations",
				description = "DemoBook REST API Documentation",
				contact = @Contact(name = "Tarkhan Gurbanli",
						email = "tarkhangurbanli@gmail.com",
						url = "https://www.demobook.com"),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.demobook.com")),
		externalDocs = @ExternalDocumentation(
				description = "DemoBook REST API Documentation",
				url = "http://localhost:9090/swagger-ui/index.html"))
public class DemoBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoBookApplication.class, args);
	}

}

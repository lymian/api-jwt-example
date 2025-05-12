package com.mian.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiRestJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiRestJwtApplication.class, args);
		System.out.println("--------------------------");
		System.out.println("|    API REST RUNNING    |");
		System.out.println("--------------------------");
		System.out.println("Port: 8080");
		System.out.println("Swagger UI: http://localhost:8080/swagger-ui/index.html");
	}

}

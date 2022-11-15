package com.seeit.holycode;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Locations API", version = "2.0", description = "Locations Information"))
public class HolycodeTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(HolycodeTestApplication.class, args);
	}

}

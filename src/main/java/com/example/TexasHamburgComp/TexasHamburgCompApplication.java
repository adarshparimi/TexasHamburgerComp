package com.example.TexasHamburgComp;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "OpenApiTitle",version = "1.0",description = "OpenAPIdemo"))
public class TexasHamburgCompApplication {

	public static void main(String[] args) {
		SpringApplication.run(TexasHamburgCompApplication.class, args);
	}


}

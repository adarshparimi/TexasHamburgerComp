package com.example.TexasHamburgComp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
//@OpenAPIDefinition(info = @Info(title = "OpenApiTitle",version = "1.0",description = "OpenAPIdemo"))
public class TexasHamburgCompApplication {

	public static void main(String[] args) {
		SpringApplication.run(TexasHamburgCompApplication.class, args);
	}


}

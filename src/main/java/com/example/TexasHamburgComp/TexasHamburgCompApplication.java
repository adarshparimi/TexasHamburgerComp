package com.example.TexasHamburgComp;


import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class TexasHamburgCompApplication {

	public static void main(String[] args) {
		SpringApplication.run(TexasHamburgCompApplication.class, args);
	}


}

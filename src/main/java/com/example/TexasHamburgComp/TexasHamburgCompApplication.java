package com.example.TexasHamburgComp;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, MongoAutoConfiguration.class, MongoDataAutoConfiguration.class},scanBasePackages = {"com.bean"})
@EnableSwagger2
//@OpenAPIDefinition(info = @Info(title = "OpenApiTitle",version = "1.0",description = "OpenAPIdemo"))
public class TexasHamburgCompApplication {

	public static void main(String[] args) {
		SpringApplication.run(TexasHamburgCompApplication.class, args);
	}


}

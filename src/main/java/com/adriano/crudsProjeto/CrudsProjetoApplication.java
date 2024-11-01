package com.adriano.crudsProjeto;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "API Gestão de Consultas Médicas",
				version = "1.0",
				description = "Trata-se da API que está sendo desenvolvida na disicplina TEPW",
				contact = @Contact(
						name = "Rodrigo&Gabriel",
						email = "rodrigoqueiroz2501@gmail.com",
						url = "https://www.google.com")
		)
)

public class CrudsProjetoApplication {
	public static void main(String[] args) {
		SpringApplication.run(CrudsProjetoApplication.class, args);
	}

}

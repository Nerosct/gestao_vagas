package br.com.gestaodevagas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

// @OpenAPIDefinition(info = @Info(title = "Gestão de Vagas", version = "1.0", description = "API de gestão de vagas"))
// @SecurityScheme(name = "jwt_auth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
public class GestaoVagasApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoVagasApplication.class, args);
	}

}

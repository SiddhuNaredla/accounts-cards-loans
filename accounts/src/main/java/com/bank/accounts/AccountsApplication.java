package com.bank.accounts;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@ComponentScans({@ComponentScan("external.packages.com"),..})
//@EnableJpaRepositories("external.packages.repository.com")
//@EntityScan("external.packages.entities.com")
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts Microservice Documentation",
				description = "Accounts Document",
				contact = @Contact(
						name="siddhu",
						url="www.siddhu.com",
						email="siddhu.com"
				),
				license = @License(
						name ="licience-book",
						url="licience.com"
				),
				version = "v1"
		),
		externalDocs = @ExternalDocumentation(
				description = "external docs for accounts microservice",
				url = "external-docs.com"
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}

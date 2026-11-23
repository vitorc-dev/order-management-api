package br.com.vitorcarvalho.order_management_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Order Management API", description = "API responsible for managing orders for a store.", version = "1"))
public class OrderManagementApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderManagementApiApplication.class, args);
	}

}

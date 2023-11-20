package com.awecommerce.inventoryservice;

import com.awecommerce.inventoryservice.domain.Inventory;
import com.awecommerce.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args -> {
			var iPhone13 = new Inventory();
			iPhone13.setSku("iPhone13");
			iPhone13.setCount(100);
			inventoryRepository.save(iPhone13);

			var iPhone13Pro = new Inventory();
			iPhone13Pro.setSku("iPhone13_Pro");
			iPhone13Pro.setCount(200);
			inventoryRepository.save(iPhone13Pro);
		};
	}
}

package com.tourin.inventoryservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(ProductRepository productRepository, RepositoryRestConfiguration restConfiguration){
		// projection sinon vc http://localhost:8888/PRODUCT-SERVICE/products?projection=fullproduct waloooo tu va pas voir l id par defaut with rest
		// pour afficher l id il faut creer une classe de projection ou ajouter la ligne suivante
		restConfiguration.exposeIdsFor(Product.class);
 		return args -> {
			productRepository.save(new Product(null,"Ordinateur",88,12));
			productRepository.save(new Product(null,"Imprimante",88,15));
			productRepository.save(new Product(null,"Smartphone",1288,55));

			productRepository.findAll().forEach(p->{
				System.out.println(p.getName());
			});
		};
	}
}

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
class Product {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private double price;
	private double quantity;

}

@RepositoryRestResource
interface ProductRepository extends JpaRepository<Product,Long> {

}
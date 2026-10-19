package com.gon.store;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.gon.store.model.Product;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
class StoreApplicationTests {
	@Autowired
	TestRestTemplate restTemplate;
	
	@Test
	@DirtiesContext 
	void shouldSaveANewProduct(){
		Product newProduct = new Product(null, "Baldurs Gate 3", "PC", "RPG", new BigDecimal("59.99"), 10, LocalDate.parse("2023-08-03"));
		ResponseEntity<Void> createResponse = restTemplate.postForEntity("/api/videojuegos", newProduct,Void.class);
		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		URI locationOfNewProduct = createResponse.getHeaders().getLocation();
		ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewProduct,String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	@DirtiesContext 
	void shouldSaveAWrongProduct(){
		Product newProduct = new Product(null, "Albion Online", "PC", "MMORPG", null, 0, LocalDate.parse("2012-08-12"));
		ResponseEntity<Void> response = restTemplate.postForEntity("/api/videojuegos", newProduct, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(response.getHeaders().getLocation()).isNull();

		Product newProduct1 = new Product(null, "Albion Online", "PC", "MMORPG", new BigDecimal("19.99"), -1, LocalDate.parse("2012-08-12"));
		ResponseEntity<Void> response1 = restTemplate.postForEntity("/api/videojuegos", newProduct1, Void.class);
		assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(response1.getHeaders().getLocation()).isNull();
	}

	@Test
	void shouldReturnAProduct(){
		ResponseEntity<String> response = restTemplate.getForEntity("/api/videojuegos/99",String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		DocumentContext documentContext = JsonPath.parse(response.getBody());

		String title = documentContext.read("$.title");
		assertEquals("The Legend of Zelda: Tears of the Kingdom", title);

		String platform = documentContext.read("$.platform");
		assertEquals("Nintendo Switch", platform);
	}

	@Test
	void shouldReturnAProductWhenIsNotSaved(){
		ResponseEntity<String> response = restTemplate.getForEntity("/api/videojuegos/50",String.class);
		assertEquals(response.getStatusCode(),HttpStatus.NOT_FOUND);
	}

	@Test
	void shouldReturnAllProducts(){
		ResponseEntity<String> response = restTemplate.getForEntity("/api/videojuegos", String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());

		int count = documentContext.read("$.length()");
		assertEquals(7, count);
	}

	@Test
	void shouldReturnProductsByCategory(){
		ResponseEntity<String> response = restTemplate.getForEntity("/api/videojuegos/platform/PC",String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());

		DocumentContext documentContext = JsonPath.parse(response.getBody());

		int productCount = documentContext.read("$.length()");
		assertEquals(2, productCount);
	}

	@Test
	@DirtiesContext
	void shouldUpdateAnExistingProduct(){
		Product product = new Product(null, "Hollow Knight", "PC", "Metroidvania", new BigDecimal(5.00), 50, LocalDate.parse("2017-02-24"));
		HttpEntity<Product> request = new HttpEntity<>(product);
		ResponseEntity<Void> response = restTemplate.exchange("/api/videojuegos/102", HttpMethod.PUT, request, Void.class);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

		ResponseEntity<String> responseEntity = restTemplate.getForEntity("/api/videojuegos/102", String.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		DocumentContext documentContext = JsonPath.parse(responseEntity.getBody());
		Number price = documentContext.read("$.price");
		assertEquals(5.00, price);
	}

	@Test
	@DirtiesContext 
	void shouldDeleteAnExistingProduct(){
		ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/videojuegos/100", HttpMethod.DELETE, null, Void.class);
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
	}

}

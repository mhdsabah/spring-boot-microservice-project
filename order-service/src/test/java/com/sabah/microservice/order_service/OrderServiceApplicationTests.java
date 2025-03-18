package com.sabah.microservice.order_service;

import static org.junit.Assert.assertThat;
// import static org.junit.Assert.assertThat;
// import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;
// import org.testcontainers.shaded.org.hamcrest.Matchers;
import org.hamcrest.Matchers;
import io.restassured.RestAssured;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceApplicationTests {

	@ServiceConnection
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0");

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup(){
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mySQLContainer.start();
	}

	@Test
	void shouldCreateProduct() {
		String requestBody = """
						{
						"skuCode": "Iphone 15",
						"price": 234,
						"quantity": 1
						}
				""";

			var responseBodyString = RestAssured.given()
							.contentType("application/json")
							.body(requestBody)
							.when()
							.post("api/product")
							.then()
							.log().all()
							.statusCode(201)
							.extract()
							.body().asString();
							System.out.println("\n\n" + responseBodyString + "\n\n\n");

							// assertEquals("Order Placed Successfully", responseBodyString);
							assertThat(responseBodyString, Matchers.is("Order Placed Successfully"));
							
	}

}

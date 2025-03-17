package com.sabah.microservice.product_service;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

import io.restassured.RestAssured;

@Import(TestcontainersConfiguration.class)
//using random port because when we run integration test, test will spin up application
//and by default it runs on port 8080, to run it on random port we are using this
//because it can cause conflict
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

//by adding this we dont need to manually specify mongodb
	@ServiceConnection
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");


	//whenever app is running it will inject the below port
	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup() {
		//not giving port 8080 here because we will be using random port here
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;

	}

	//to start mongodbtest container before running the test
	static{
		mongoDBContainer.start();
	}

	@Test
	void shouldCreateProduct() {
		String requestBody  = """
				{
				    "name":"Iphone 17",
				    "description": "Iphone 16 from Apple",
				    "price":10000
				}
				""";
		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("api/product")
				.then()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("name",Matchers.equalTo("Iphone 17"))
				.body("description", Matchers.equalTo("Iphone 16 from Apple"))
				.body("price",Matchers.equalTo(10000));


}
}
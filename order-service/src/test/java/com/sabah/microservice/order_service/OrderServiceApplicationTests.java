package com.sabah.microservice.order_service;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

// import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;

import com.sabah.microservice.order_service.stubs.InventoryClientStub;

// import org.testcontainers.shaded.org.hamcrest.Matchers;
import org.hamcrest.Matchers;
import io.restassured.RestAssured;

//port = 0 will make sure it runs on random port
@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class OrderServiceApplicationTests {

	@SuppressWarnings("rawtypes")
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

	@SuppressWarnings("deprecation")
	@Test
	void shouldCreateProduct() {
		String requestBody = """
						{
						"skuCode": "iphone_15",
						"price": 24,
						"quantity": 10
						}
				""";
			
			InventoryClientStub.stubInventoryCall("iphone_15", 10);
			var responseBodyString = RestAssured.given()
							.contentType("application/json")
							.body(requestBody)
							.when()
							.post("api/order")
							.then()
							.log().all()
							.statusCode(201)
							.extract()
							.body().asString();
							// System.out.println("\n\n" + responseBodyString + "\n\n\n");

							

							// assertEquals("Order Placed Successfully", responseBodyString);
							// assertTrue("Order Placed Successfully",isProductCreated);
							assertThat(responseBodyString, Matchers.is("Order placed successfully"));
							
	}
	// @SuppressWarnings("deprecation")
	@Test
	void shouldNotCreateProduct() {
		String requestBody = """
						{
						"skuCode": "iphone_15",
						"price": 24,
						"quantity": 1001
						}
				""";
			
			InventoryClientStub.stubInventoryCallFail("iphone_15", 1001);
			var responseBodyString = RestAssured.given()
							.contentType("application/json")
							.body(requestBody)
							.when()
							.post("api/order")
							.then()
							.log().all()
							.statusCode(500)
							.extract()
							.body().asString();
							System.out.println("\n\n Response =====" + responseBodyString + "\n\n\n");

							

							// assertEquals("Order Placed Successfully", responseBodyString);
							// assertTrue("Order Placed Successfully",isProductCreated);
							
							assertTrue(responseBodyString.contains("Internal Server Error"));
							
	}

}

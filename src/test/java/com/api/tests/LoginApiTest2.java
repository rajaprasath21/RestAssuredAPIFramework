package com.api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
public class LoginApiTest2 {
	@Test
	public void loginTest2() {
		Response response = given().baseUri("http://64.227.160.186:8080")
			.header("Content-Type","application/json")
			.body("{\"username\":\"rajaprasath\",\"password\":\"welcome\"}")
			.post("/api/auth/login");
		
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.getStatusCode(),200);	
	}

}

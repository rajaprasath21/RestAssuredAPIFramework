package com.api.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
@Listeners(com.api.listeners.TestListener.class)
public class LoginApiTest {
	
	@Test
	public void loginTest1() {
		
		RestAssured.baseURI="http://64.227.160.186:8080";
		/*
 		Response postResponse = RestAssured
								.given()
								.header("Content-Type","application/json")
								.body("{\"username\": \"rajaprasath\", \"password\": \"welcome\" }")
								.post("/api/auth/login");
		*/
		RequestSpecification given = RestAssured.given();
		RequestSpecification header = given.header("Content-Type","application/json");
		RequestSpecification body = header.body("{\"username\": \"rajaprasath\", \"password\": \"welcome\" }");
		Response postResponse = body.post("/api/auth/login");

		System.out.println(postResponse.asPrettyString());
		System.out.println("Status code - "+postResponse.getStatusCode());
		
		Assert.assertEquals(postResponse.getStatusCode(),200);
	}

}

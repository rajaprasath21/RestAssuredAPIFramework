package com.api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.base.AuthService;
import com.api.models.request.SignUpRequest;

import io.restassured.response.Response;

public class AccountCreationTest {
	@Test(description = "verify if SignUp API is working...")
	public void createAccountTest() {
		//Using the builder design pattern instead of below line
		//SignUpRequest signUpRequest=new SignUpRequest(null, null, null, null, null, null);
		
		SignUpRequest signUpRequest = new SignUpRequest.Builder()
			.userName("Raja21").password("Raja21")
			.firstName("Raja21").lastName("prasath21")
			.email("raja21@gmail.com").mobileNumber("2355435455")
			.build();
		
		AuthService authService=new AuthService();
		Response response = authService.signUp(signUpRequest);
		System.out.println(response.asPrettyString());
		
		Assert.assertEquals(response.asPrettyString(), "User registered successfully!"); 
		Assert.assertEquals(response.getStatusCode(), 200);
	}

}

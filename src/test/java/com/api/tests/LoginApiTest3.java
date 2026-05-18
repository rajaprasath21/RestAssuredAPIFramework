package com.api.tests;

import org.testng.Assert;
//import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.base.AuthService;
import com.api.models.request.LoginRequest;
import com.api.models.response.LoginResponse;

import io.restassured.response.Response;

//@Listeners(com.api.listeners.TestListener.class)
public class LoginApiTest3 {
	
	@Test
	public void loginTest() {
		
		LoginRequest loginRequest = new LoginRequest("rajaprasath", "welcome"); 

		AuthService authService=new AuthService();
		Response response = authService.login(loginRequest);
		
		LoginResponse loginResponse = response.as(LoginResponse.class); //for Deserailization
		
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.getStatusCode(),200);
		
		System.out.println(loginResponse.getToken());
		System.out.println(loginResponse.getEmail());
		System.out.println(loginResponse.getId());
		
		Assert.assertTrue(loginResponse.getToken()!=null);
		Assert.assertEquals(loginResponse.getId(),5199);
		Assert.assertEquals(loginResponse.getEmail(),"rajaprasath21@gmail.com");	
	}
}
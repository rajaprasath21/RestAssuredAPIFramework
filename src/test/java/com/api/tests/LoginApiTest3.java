package com.api.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.testng.Assert;
//import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.api.base.AuthService;
import com.api.filters.LoggingFilter;
import com.api.models.request.LoginRequest;
import com.api.models.response.LoginResponse;
import com.api.utilities.DataProviders;
import com.api.utilities.ExtentManager;

import io.restassured.response.Response;

//@Listeners(com.api.listeners.TestListener.class)
public class LoginApiTest3 {
	private static final Logger logger=LogManager.getLogger(LoggingFilter.class);
	@Test(dataProvider = "validLoginData",dataProviderClass = DataProviders.class)
	public void loginTest(String username, String password) {
		SoftAssert softAssert=new SoftAssert();
		
		LoginRequest loginRequest = new LoginRequest(username, password); 
		ExtentManager.logStep("Login Resquest sent");

		AuthService authService=new AuthService();
		Response response = authService.login(loginRequest);
		
		LoginResponse loginResponse = response.as(LoginResponse.class); //for Deserailization
		
		if(loginResponse.getToken()!=null) {
			ExtentManager.logStep("API Loginned!");
			ExtentManager.logStep("Token id: "+loginResponse.getToken());
		}
		
		logger.info(response.asPrettyString());
		softAssert.assertEquals(response.getStatusCode(),200);
		
		logger.info(loginResponse.getToken());
		logger.info(loginResponse.getEmail());
		logger.info(loginResponse.getId());
		
		softAssert.assertTrue(loginResponse.getToken()!=null);
		softAssert.assertEquals(loginResponse.getId(),5199);
		softAssert.assertEquals(loginResponse.getEmail(),"rajaprasath21@gmail.com");	
		softAssert.assertAll();
	}
}
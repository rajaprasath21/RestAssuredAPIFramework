package com.api.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.api.base.AuthService;
import com.api.base.UserProfileManagementService;
import com.api.filters.LoggingFilter;
import com.api.models.request.LoginRequest;
import com.api.models.request.ResetPasswordRequest;
import com.api.models.response.LoginResponse;
import com.api.models.response.UserProfileResponse;
import com.api.utilities.DataProviders;
import com.api.utilities.ExtentManager;

import io.restassured.response.Response;

public class ResetPasswordTest {
	private static final Logger logger=LogManager.getLogger(LoggingFilter.class);
	
	@Test(dataProvider = "validLoginData",dataProviderClass = DataProviders.class)
	public void resetPasswordTest(String username, String password) {
		
		SoftAssert softAssert=new SoftAssert();
		
		AuthService authService=new AuthService();
		Response response = authService.login(new LoginRequest(username,password));
		ExtentManager.logStep("Login Resquest sent");

		LoginResponse loginResponse = response.as(LoginResponse.class);
		if(loginResponse.getToken()!=null) {
			ExtentManager.logStep("API Loginned!");
		}
		logger.info(response.asPrettyString());
		logger.info("----------------------------------");
		
		UserProfileManagementService userProfileManagementService=new UserProfileManagementService();
		
		Response response2 = userProfileManagementService.getProfile(loginResponse.getToken());
		logger.info(response2.asPrettyString());
		
		UserProfileResponse userProfileResponse= response2.as(UserProfileResponse.class);
		logger.info(userProfileResponse.getUsername());
		ExtentManager.logStep("Loginned User : "+userProfileResponse.getUsername());
		
		if (userProfileResponse.getUsername().equals(username)) {
			ExtentManager.logStepValidationForAPI("Username Validation Passed!");
			logger.info("Username Validation Passed!: "+ userProfileResponse.getUsername());
			softAssert.assertTrue(true);
		}else {
			ExtentManager.logFailureAPI("Username Validation Failed!");
			logger.info("Username Validation Failed!: "+ userProfileResponse.getUsername());
			softAssert.assertTrue(false);
		}
		
		logger.info("----------------------------------");
		
		ResetPasswordRequest resetPasswordRequest=new ResetPasswordRequest(loginResponse.getToken(), "welcome", "welcome");
		
		Response response3 = userProfileManagementService.resetPassword(loginResponse.getToken(), resetPasswordRequest);
		logger.info(response3.asPrettyString());
		logger.info(response3.getStatusCode());
		
		softAssert.assertAll();
		
	}
	
}

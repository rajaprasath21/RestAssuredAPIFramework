package com.api.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.api.base.AuthService;
import com.api.base.UserProfileManagementService;
import com.api.filters.LoggingFilter;
import com.api.models.request.LoginRequest;
import com.api.models.request.ProfileRequest;
import com.api.models.response.LoginResponse;
import com.api.models.response.UserProfileResponse;
import com.api.utilities.ExtentManager;
import com.api.utilities.DataProviders;

import io.restassured.response.Response;

@Listeners(com.api.listeners.TestListener.class)
public class UpdateProfileTest {
	private static final Logger logger=LogManager.getLogger(LoggingFilter.class);
	
	@Test(dataProvider = "validLoginData",dataProviderClass = DataProviders.class)
	public void updateProfileTest(String username, String password) {
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
		
		//softAssert.assertEquals(userProfileResponse.getUsername(), "rajaprasath");
		
		logger.info("----------------------------------");
		
		ProfileRequest profileRequest=new ProfileRequest.Builder()
				.firstName("rajaprasath")
				.lastName("marimuthu")
				.email("rajaprasath21@gmail.com")
				.mobileNumber("1234567890")
				.build();

		Response response3 = userProfileManagementService.updateProfile(loginResponse.getToken(), profileRequest);
		logger.info(response3.asPrettyString());
		logger.info(response3.getStatusCode());
		
		if (response3.getStatusCode()==200) {
			softAssert.assertTrue(response3.getStatusCode()==200, "The Profile updated Successfully!");
			ExtentManager.logStepValidationForAPI("The Profile updated Successfully!");
			logger.info("The Profile updated Successfully!");
		}else {
			ExtentManager.logFailureAPI("The Profile updation got failed!");
			softAssert.assertFalse(response3.getStatusCode()==200, "The Profile updation got failed!");
			logger.info("The Profile updation got failed!");
		}
		
		softAssert.assertAll();
		
	}

}

package com.api.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.api.base.AuthService;
import com.api.base.UserProfileManagementService;
import com.api.models.request.LoginRequest;
import com.api.models.request.ProfileRequest;
import com.api.models.response.LoginResponse;
import com.api.models.response.UserProfileResponse;
import com.api.utilities.ExtentManager;

import io.restassured.response.Response;

@Listeners(com.api.listeners.TestListener.class)
public class UpdateProfileTest {
	@Test
	public void updateProfileTest() {
		SoftAssert softAssert=new SoftAssert();
		
		AuthService authService=new AuthService();
		Response response = authService.login(new LoginRequest("rajaprasath","welcome"));
		ExtentManager.logStep("Login Resquest sent");

		LoginResponse loginResponse = response.as(LoginResponse.class);
		if(loginResponse.getToken()!=null) {
			ExtentManager.logStep("API Loginned!");
		}
		System.out.println(response.asPrettyString());
		System.out.println("----------------------------------");
		
		UserProfileManagementService userProfileManagementService=new UserProfileManagementService();
		
		Response response2 = userProfileManagementService.getProfile(loginResponse.getToken());
		System.out.println(response2.asPrettyString());
		
		UserProfileResponse userProfileResponse= response2.as(UserProfileResponse.class);
		System.out.println(userProfileResponse.getUsername());
		ExtentManager.logStep("Loginned User : "+userProfileResponse.getUsername());
		
		if (userProfileResponse.getUsername().equals("rajaprasath")) {
			ExtentManager.logStepValidationForAPI("Username Validation Passed!");
			softAssert.assertTrue(true);
		}else {
			ExtentManager.logFailureAPI("Username Validation Failed!");
			softAssert.assertTrue(false);
		}
		
		//softAssert.assertEquals(userProfileResponse.getUsername(), "rajaprasath");
		
		System.out.println("----------------------------------");
		
		ProfileRequest profileRequest=new ProfileRequest.Builder()
				.firstName("rajaprasath")
				.lastName("marimuthu")
				.email("rajaprasath21@gmail.com")
				.mobileNumber("1234567890")
				.build();

		Response response3 = userProfileManagementService.updateProfile(loginResponse.getToken(), profileRequest);
		System.out.println(response3.asPrettyString());
		System.out.println(response3.getStatusCode());
		
		
		if (response3.getStatusCode()==200) {
			ExtentManager.logStepValidationForAPI("The Profile updated Successfully!");
		}else {
			ExtentManager.logFailureAPI("The Profile updation got failed!");
		}
		
		softAssert.assertAll();
		
	}

}

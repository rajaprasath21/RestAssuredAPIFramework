package com.api.tests;

import org.testng.annotations.Test;

import com.api.base.AuthService;
import com.api.base.UserProfileManagementService;
import com.api.models.request.LoginRequest;
import com.api.models.response.LoginResponse;
import com.api.models.response.UserProfileResponse;

import io.restassured.response.Response;

public class GetProfileRequestTest {
	@Test
	public void getProfileInfoTest() {
		
		AuthService authService=new AuthService();
		Response response = authService.login(new LoginRequest("Rajaprasath", "welcome"));
		LoginResponse loginResponse = response.as(LoginResponse.class);
		System.out.println(loginResponse.getToken());
		
		UserProfileManagementService userProfileManagementService=new UserProfileManagementService();
		Response response2 = userProfileManagementService.getProfile(loginResponse.getToken());
		System.out.println(response2.asPrettyString());
		UserProfileResponse userProfileResponse = response2.as(UserProfileResponse.class);
		System.out.println(userProfileResponse.getUsername());
		
	}
}
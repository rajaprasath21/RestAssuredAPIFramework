package com.api.base;

import java.util.HashMap;

import com.api.models.request.LoginRequest;
import com.api.models.request.SignUpRequest;

import io.restassured.response.Response;
 
public class AuthService extends BaseService{
	
	private static final String BASE_PATH="/api/auth/";
	
	public Response login(LoginRequest payload) {
		return postRequest(payload, BASE_PATH+"login");
	}
	
	public Response signUp(SignUpRequest payload) {
		return postRequest(payload, BASE_PATH+"signup");
	}
	
	//if the payload have single field only, No need to create the seperate class for the payload. instead of we can use it like below 
	public Response forgotPassword(String emailAddress) {
		HashMap<String, String> payload=new HashMap<String, String>();
		payload.put("email", emailAddress);
		System.out.println("manual\n"+payload);
		return postRequest(payload, BASE_PATH+"forgot-password");
	}
 
}

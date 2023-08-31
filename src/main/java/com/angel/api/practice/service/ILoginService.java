package com.angel.api.practice.service;

import com.angel.api.practice.errorhandle.ApiResponse;
import com.angel.api.practice.model.Login;

public interface ILoginService {

	public ApiResponse login(Login  login);
	public ApiResponse userProfile();
	public ApiResponse getToken(Login login);
	
}

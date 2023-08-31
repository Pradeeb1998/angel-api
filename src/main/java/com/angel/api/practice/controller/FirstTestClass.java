package com.angel.api.practice.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.angel.api.practice.errorhandle.ApiResponse;
import com.angel.api.practice.model.Login;
import com.angel.api.practice.service.ILoginService;

@RestController
@RequestMapping(path = "/Api")
public class FirstTestClass {
    
    @Autowired
    private ILoginService loginService;
    
	
    @ResponseBody
    @PostMapping(path = "/home",produces="application/json", consumes="application/json")
    public ApiResponse login(@org.springframework.web.bind.annotation.RequestBody Login  login)  {

    	ApiResponse loginResponse=loginService.login(login);
    	
		return loginResponse;
		}

      @ResponseBody
    @PostMapping(path = "/getToken",produces="application/json", consumes="application/json")
    public ApiResponse getToken(@org.springframework.web.bind.annotation.RequestBody Login  login)  {
      System.out.println("come to login");
    	ApiResponse loginResponse=loginService.getToken(login);
    	
		return loginResponse;
		}

    @ResponseBody
    @GetMapping(path="/userProfile")
    public ApiResponse getUserProfile() throws IOException{
    	ApiResponse userProfile=loginService.userProfile();
		return userProfile;
	}

}

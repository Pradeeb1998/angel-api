package com.angel.api.practice.serviceimpl;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.angel.api.practice.errorhandle.ApiResponse;
import com.angel.api.practice.model.Login;
import com.angel.api.practice.service.ILoginService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class LoginServiceImpl implements ILoginService {
	
    private static String jwtToken=null;
    private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public ApiResponse login(Login login) {

        try {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
                   
            String requestBodyJson =objectMapper.writeValueAsString(login);
              
            RequestBody body = RequestBody.create(mediaType, requestBodyJson);
            
            Request request = new Request.Builder().url("https://apiconnect.angelbroking.com/rest/auth/angelbroking/user/v1/loginByPassword")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .addHeader("X-UserType", "USER")
                    .addHeader("X-SourceID", "WEB")
                    .addHeader("X-ClientLocalIP", "CLIENT_LOCAL_IP")
                    .addHeader("X-ClientPublicIP", "CLIENT_PUBLIC_IP")
                    .addHeader("X-MACAddress", "MAC_ADDRESS")
                    .addHeader("X-PrivateKey", "RCvbyZRP")
                    .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                
             // Parse the JSON response body
                JsonNode responseJson = objectMapper.readTree(responseBody);
        
             // Print the jwtToken
                System.out.println("jwtToken: " + jwtToken);
                return new ApiResponse(true, false, responseJson);
            } else {
                return new ApiResponse(false, false, "Request failed with response code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse(false, true, e.getMessage());
        }
	}

	@Override
	public ApiResponse userProfile() {
		try {
		    OkHttpClient client = new OkHttpClient();
		    Request request = new Request.Builder().url("https://apiconnect.angelbroking.com/rest/secure/angelbroking/user/v1/getProfile")
		        .method("GET", null)
		        .addHeader("Authorization", "Bearer " + jwtToken)
		        .addHeader("Accept", "application/json")
		        .addHeader("X-UserType", "USER")
		        .addHeader("X-SourceID", "WEB")
		        .addHeader("X-ClientLocalIP", "CLIENT_LOCAL_IP")
		        .addHeader("X-ClientPublicIP", "CLIENT_PUBLIC_IP")
		        .addHeader("X-MACAddress", "MAC_ADDRESS")
		        .addHeader("X-PrivateKey", "API_KEY")
		        .build();
		    Response response = client.newCall(request).execute();

		    if (response.isSuccessful()) {
		        String responseBody = response.body().string();

		        // Parse the JSON response body
		        JsonNode responseJson = objectMapper.readTree(responseBody);

		        return new ApiResponse(true, false, responseJson);
		    } else {
		        return new ApiResponse(false, false, "Request failed with response code: " + response.code());
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		    return new ApiResponse(false, true, e.getMessage());
		}
	}

    @Override
    public ApiResponse getToken(Login login) {
        
        try {
            
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
                   
            String requestBodyJson =objectMapper.writeValueAsString(login);
              
            RequestBody body = RequestBody.create(mediaType, requestBodyJson);
            
            Request request = new Request.Builder().url("https://apiconnect.angelbroking.com/rest/auth/angelbroking/jwt/v1/generateTokens")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .addHeader("X-UserType", "USER")
                    .addHeader("X-SourceID", "WEB")
                    .addHeader("X-ClientLocalIP", "CLIENT_LOCAL_IP")
                    .addHeader("X-ClientPublicIP", "CLIENT_PUBLIC_IP")
                    .addHeader("X-MACAddress", "MAC_ADDRESS")
                    .addHeader("X-PrivateKey", "RCvbyZRP")
                    .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                
             // Parse the JSON response body
                JsonNode responseJson = objectMapper.readTree(responseBody);
             
             // Get the "data" object
                JsonNode dataNode = responseJson.get("data");
             
             // Get the "jwtToken" value from the "data" object
                 jwtToken = dataNode.get("jwtToken").asText();

             // Print the jwtToken
                System.out.println("jwtToken: " + jwtToken);
                return new ApiResponse(true, false, responseJson);
            } else {
                return new ApiResponse(false, false, "Request failed with response code: " + response.code());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(false, true, e.getMessage());        }

    }

}

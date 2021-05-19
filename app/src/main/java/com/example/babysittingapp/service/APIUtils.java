package com.example.babysittingapp.service;

import com.example.babysittingapp.entity.LoginToken;

public class APIUtils {
    public static final String BASE_URL = "http://192.168.1.113:8000/api/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
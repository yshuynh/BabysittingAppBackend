package com.example.babysittingapp.service;
import com.example.babysittingapp.entity.LoginInfo;
import com.example.babysittingapp.entity.LoginToken;
import com.example.babysittingapp.entity.Parent;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {

    @GET("parent")
    Call<List<Parent>> getParentList();

    @POST("user/login")
    Call<LoginToken> postLogin(@Body LoginInfo loginInfo);
}
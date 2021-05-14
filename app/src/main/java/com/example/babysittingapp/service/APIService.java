package com.example.babysittingapp.service;
import com.example.babysittingapp.entity.LoginInfo;
import com.example.babysittingapp.entity.LoginToken;
import com.example.babysittingapp.entity.Parent;
import com.example.babysittingapp.entity.Post;
import com.example.babysittingapp.entity.PostCreatePost;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {

    @GET("parent")
    Call<List<Parent>> getParentList();

    @POST("user/login")
    Call<LoginToken> postLogin(@Body LoginInfo loginInfo);

    @GET("parent/{parent_id}/post")
    Call<ArrayList<Post>> getPostList(@Path("parent_id") String parent_id);

    @PUT("post")
    Call<String> putPost(@Body PostCreatePost post);
}
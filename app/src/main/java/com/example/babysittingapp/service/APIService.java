package com.example.babysittingapp.service;
import com.example.babysittingapp.entity.LoginInfo;
import com.example.babysittingapp.entity.LoginToken;
import com.example.babysittingapp.entity.Parent;
import com.example.babysittingapp.entity.Post;
import com.example.babysittingapp.entity.PostCreatePost;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APIService {

    @GET("parent")
    Call<List<Parent>> getParentList();

    @POST("user/login")
    Call<LoginToken> postLogin(@Body LoginInfo loginInfo);

    @GET("parent/{parent_id}/post")
    Call<ArrayList<Post>> getPostList(@Path("parent_id") String parent_id);

    @GET("post")
    Call<ArrayList<Post>> getPostList();

    @PUT("post")
    Call<ResponseBody> putPost(@Body PostCreatePost post);

    @Multipart
    @PUT("post/{post_id}")
    Call<ResponseBody> updatePost(@Path("post_id") String post_id, @Part("babysister") RequestBody babysiterID);
}
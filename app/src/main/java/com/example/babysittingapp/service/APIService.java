package com.example.babysittingapp.service;
import com.example.babysittingapp.entity.Code;
import com.example.babysittingapp.entity.LoginInfo;
import com.example.babysittingapp.entity.LoginToken;
import com.example.babysittingapp.entity.Notification;
import com.example.babysittingapp.entity.Parent;
import com.example.babysittingapp.entity.Post;
import com.example.babysittingapp.entity.PostCreatePost;
import com.example.babysittingapp.entity.RatingDetail;
import com.example.babysittingapp.entity.User;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
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
    Call<User> postLogin(@Body LoginInfo loginInfo);

    @GET("parent/{parent_id}/post")
    Call<ArrayList<Post>> getPostList(@Path("parent_id") String parent_id);

    @GET("post")
    Call<ArrayList<Post>> getPostList();

    @PUT("post")
    Call<ResponseBody> putPost(@Body PostCreatePost post);

    @Multipart
    @PUT("post/{post_id}/request")
    Call<ResponseBody> babyRequestPost(@Path("post_id") String post_id, @Part("babysister_request") RequestBody babysisterID);

    @Multipart
    @PUT("post/{post_id}")
    Call<ResponseBody> updatePost(@Path("post_id") String post_id, @Part("babysister") RequestBody babysiterID, @Part("status") RequestBody status);

    @Multipart
    @POST("code")
    Call<Code> createPostCode(@Part("post") RequestBody post_id);

    @Multipart
    @PUT("code")
    Call<String> updatePostCode(@Part("post") RequestBody post_id, @Part("code") RequestBody code);

    @Multipart
    @PUT("post/{post_id}/status")
    Call<Post> updatePostStatus(@Path("post_id") String post_id, @Part("status") RequestBody status);

    @GET("user/{user_id}/rating_detail")
    Call<ArrayList<RatingDetail>> getRatingDetail(@Path("user_id") String user_id);

    @POST("rating")
    Call<RatingDetail> rateUser(@Body RatingDetail ratingDetail);

    @GET("rating/{user_id}/{post_id}")
    Call<RatingDetail> getRateUser(@Path("user_id") String user_id, @Path("post_id") String post_id);

    @GET("notification/{user_id}")
    Call<ArrayList<Notification>> getNotificationListUser(@Path("user_id") String user_id);

    @Multipart
    @POST("user/{user_id}/favorite")
    Call<ArrayList<User>> addFavoriteUsers(@Path("user_id") String user_id, @Part("to_user_id") String to_user_id);

    @Multipart
    @PUT("user/{user_id}/favorite")
    Call<ArrayList<User>> removeFavoriteUsers(@Path("user_id") String user_id, @Part("to_user_id") String to_user_id);

    @GET("user/{user_id}/favorite")
    Call<ArrayList<User>> getFavoriteUsers(@Path("user_id") String user_id);
}
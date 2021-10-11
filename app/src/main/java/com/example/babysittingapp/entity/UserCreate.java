package com.example.babysittingapp.entity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserCreate {

    @SerializedName("money")
    @Expose
    private Integer money;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("rating")
    @Expose
    private Rating rating;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("job")
    @Expose
    private String job;
    @SerializedName("date_of_bird")
    @Expose
    private String dateOfBird;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private Boolean gender;
    @SerializedName("cmnd_truoc")
    @Expose
    private String cmnd_truoc;
    @SerializedName("cmnd_sau")
    @Expose
    private String cmnd_sau;


}
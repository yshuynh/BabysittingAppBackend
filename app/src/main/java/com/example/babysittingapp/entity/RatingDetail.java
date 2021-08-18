package com.example.babysittingapp.entity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RatingDetail {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("is_hide")
    @Expose
    private Boolean isHide;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("post")
    @Expose
    private String post;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getIsHide() {
        return isHide;
    }

    public void setIsHide(Boolean isHide) {
        this.isHide = isHide;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

}
package com.example.babysittingapp.entity;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdatePostForm {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("child_number")
    @Expose
    private Integer childNumber;
    @SerializedName("age_avg")
    @Expose
    private Integer ageAvg;
    @SerializedName("time_start")
    @Expose
    private String timeStart;
    @SerializedName("time_end")
    @Expose
    private String timeEnd;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("parent")
    @Expose
    private String parent;
    @SerializedName("babysister")
    @Expose
    private String babysister;
    @SerializedName("babysister_request")
    @Expose
    private List<Object> babysisterRequest = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getChildNumber() {
        return childNumber;
    }

    public void setChildNumber(Integer childNumber) {
        this.childNumber = childNumber;
    }

    public Integer getAgeAvg() {
        return ageAvg;
    }

    public void setAgeAvg(Integer ageAvg) {
        this.ageAvg = ageAvg;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getBabysister() {
        return babysister;
    }

    public void setBabysister(String babysister) {
        this.babysister = babysister;
    }

    public List<Object> getBabysisterRequest() {
        return babysisterRequest;
    }

    public void setBabysisterRequest(List<Object> babysisterRequest) {
        this.babysisterRequest = babysisterRequest;
    }

}
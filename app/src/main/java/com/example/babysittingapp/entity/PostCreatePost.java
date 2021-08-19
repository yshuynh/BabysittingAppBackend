package com.example.babysittingapp.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostCreatePost {
    @SerializedName("parent")
    @Expose
    private String parent;
    @SerializedName("child_number")
    @Expose
    private Integer childNumber;
    @SerializedName("age_avg")
    @Expose
    private Integer ageAvg;
    @SerializedName("date_time_start")
    @Expose
    private String dateTimeStart;
//    @SerializedName("time_end")
//    @Expose
//    private String timeEnd;
public String getTimeStart() {
    return timeStart;
}

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    @SerializedName("time_start")
    @Expose
    private String timeStart;
    @SerializedName("time_end")
    @Expose
    private String timeEnd;
    @SerializedName("price")
    @Expose
    private Integer price;

    public void setParent(String parent) {
        this.parent = parent;
    }

    public void setChildNumber(Integer childNumber) {
        this.childNumber = childNumber;
    }

    public void setAgeAvg(Integer ageAvg) {
        this.ageAvg = ageAvg;
    }

    public void setDateTimeStart(String dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}

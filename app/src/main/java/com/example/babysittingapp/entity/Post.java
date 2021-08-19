package com.example.babysittingapp.entity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Post {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("parent")
    @Expose
    private User parent;
    @SerializedName("babysister")
    @Expose
    private User babysister;
    @SerializedName("babysister_request")
    @Expose
    private List<User> babysisterRequest = null;
    @SerializedName("child_number")
    @Expose
    private Integer childNumber;
    @SerializedName("age_avg")
    @Expose
    private Integer ageAvg;
    @SerializedName("date_time_start")
    @Expose
    private String dateTimeStart;

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
//    @SerializedName("date_time_end")
//    @Expose
//    private String timeEnd;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("status")
    @Expose
    private String status;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @SerializedName("address")
    @Expose
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getParent() {
        return parent;
    }

    public void setParent(User parent) {
        this.parent = parent;
    }

    public User getBabysister() {
        return babysister;
    }

    public void setBabysister(User babysister) {
        this.babysister = babysister;
    }

    public List<User> getBabysisterRequest() {
        return babysisterRequest;
    }

    public void setBabysisterRequest(List<User> babysisterRequest) {
        this.babysisterRequest = babysisterRequest;
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

    public String getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(String dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
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

}
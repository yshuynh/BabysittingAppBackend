package com.example.babysittingapp.service;

import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babysittingapp.entity.Babysister;
import com.example.babysittingapp.entity.LoginToken;
import com.example.babysittingapp.entity.Post;
import com.example.babysittingapp.entity.User;
import com.example.babysittingapp.ui.home.PostAdapter;

import java.util.ArrayList;
import java.util.Observable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaticData extends Observable {
    public static StaticData instance = null;
    public LoginToken loginToken = null;
    private ArrayList<Post> post_list;
    private String currentPostID = "";
    public User currentUser = null;

    public ArrayList<Post> getPost_list() {
        return post_list;
    }

    public static StaticData getInstance() {
        if (instance == null) {
            instance = new StaticData();
        }
        return instance;
    }

    public void setCurrentPostID(String id) {
        currentPostID = id;
    }

    public Post getCurrentPost() {
        for (Post post : post_list) {
            if (post.getId().equals(currentPostID)) return post;
        }
        return null;
    }

    public void refreshData() {
        if (loginToken == null) return;
        APIService service = APIUtils.getAPIService();
        if (loginToken.getRole().equals("parent")) {
            service.getPostList(loginToken.getId()).enqueue(new Callback<ArrayList<Post>>() {
                @Override
                public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                    post_list = response.body();
                    customNotify();
                }

                @Override
                public void onFailure(Call<ArrayList<Post>> call, Throwable t) {

                }
            });
        } else {
            Log.d("abc", "all post");
            service.getPostList().enqueue(new Callback<ArrayList<Post>>() {
                @Override
                public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                    post_list = response.body();
                    customNotify();
                }

                @Override
                public void onFailure(Call<ArrayList<Post>> call, Throwable t) {

                }
            });
        }

    }

    public void customNotify() {
        setChanged();
        notifyObservers();
    }
}

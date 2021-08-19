package com.example.babysittingapp.service;

import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babysittingapp.entity.Babysister;
import com.example.babysittingapp.entity.LoginToken;
import com.example.babysittingapp.entity.Notification;
import com.example.babysittingapp.entity.Post;
import com.example.babysittingapp.entity.User;
import com.example.babysittingapp.ui.home.PostAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaticData extends Observable {
    public static StaticData instance = null;
    private User loginToken = null;
    private ArrayList<Post> post_list;
    private String currentPostID = "";
    public User currentUser = null;
    private ArrayList<Notification> notification_list;

    public User getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(User loginToken) {
        this.loginToken = loginToken;
        APIService service = APIUtils.getAPIService();
        service.getFavoriteUsers(loginToken.getId()).enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                if (!response.isSuccessful()) {
                    try {
                        Log.d("abc", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                StaticData.getInstance().setFavoriteUsers(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Log.d("abc", t.getMessage());
            }
        });
    }

    public ArrayList<User> getFavoriteUsers() {
        return favoriteUsers;
    }

    public void setFavoriteUsers(ArrayList<User> favoriteUsers) {
        this.favoriteUsers = favoriteUsers;
    }

    public boolean isFavorite(User user) {
        for (User c_user : favoriteUsers) {
            if (c_user.getId().equals(user.getId())) return true;
        }
        return false;
    }

    public void addFavoriteUsers(User user) {
        APIService service = APIUtils.getAPIService();
        service.addFavoriteUsers(loginToken.getId(), user.getId()).enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                if (!response.isSuccessful()) {
                    Log.d("abc", "add favorite fail");
                }
                StaticData.getInstance().setFavoriteUsers(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {

            }
        });
    }

    public void removeFavoriteUser(User user) {
        APIService service = APIUtils.getAPIService();
        service.removeFavoriteUsers(loginToken.getId(), user.getId()).enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                if (!response.isSuccessful()) {
                    Log.d("abc", "remoive favorite fail");
                }
                StaticData.getInstance().setFavoriteUsers(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {

            }
        });
    }

    private ArrayList<User> favoriteUsers;

    public ArrayList<Post> getPost_list() {
        return post_list;
    }

    public static StaticData getInstance() {
        if (instance == null) {
            instance = new StaticData();
        }
        return instance;
    }

    private StaticData() {
        NotificationUtil.getInstance();
    }

    public ArrayList<Notification> getNotification_list() {
        return notification_list;
    }

    public void setNotification_list(ArrayList<Notification> notification_list) {
        this.notification_list = notification_list;
        customNotify();
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

package com.example.babysittingapp.service;

import com.example.babysittingapp.entity.LoginInfo;
import com.example.babysittingapp.entity.LoginToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestRetrofit {
    public static void main(String[] args) {
        APIService service = APIUtils.getAPIService();
//        service.getParentList().enqueue(new Callback<List<Parent>>() {
//            @Override
//            public void onResponse(Call<List<Parent>> call, Response<List<Parent>> response) {
//                if (response.isSuccessful()) {
//                    List<Parent> parent_list = response.body();
//                    System.out.println(parent_list.get(0).getName());
//                }
//                System.out.println("on response");
//            }
//
//            @Override
//            public void onFailure(Call<List<Parent>> call, Throwable t) {
//                System.out.println(t.getMessage());
//            }
//        });

        service.postLogin(new LoginInfo("admin2", "admin")).enqueue(new Callback<LoginToken>() {
            @Override
            public void onResponse(Call<LoginToken> call, Response<LoginToken> response) {
                if (response.isSuccessful()) {
                    LoginToken loginInfo = response.body();
                    System.out.println(loginInfo.getUsername());
                }
                else {
                    System.out.println("fail");
                }
            }

            @Override
            public void onFailure(Call<LoginToken> call, Throwable t) {

            }
        });

        System.out.println((service.getParentList()));
    }
}

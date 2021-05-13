package com.example.babysittingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.babysittingapp.entity.LoginInfo;
import com.example.babysittingapp.entity.LoginToken;
import com.example.babysittingapp.service.APIService;
import com.example.babysittingapp.service.APIUtils;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickBtnSubmit(View view) {
        EditText txtUsername = findViewById(R.id.editTextUsername);
        EditText txtPassword = findViewById(R.id.editTextPassword);
        LoginInfo loginInfo = new LoginInfo(txtUsername.getText().toString(), txtPassword.getText().toString());
        APIService service = APIUtils.getAPIService();
        Log.d("abc", txtPassword.getText().toString());
        service.postLogin(loginInfo).enqueue(new Callback<LoginToken>() {
            @Override
            public void onResponse(Call<LoginToken> call, Response<LoginToken> response) {
                if (response.isSuccessful()) {
                    Intent myIntent = new Intent(MainActivity.this, ParentActivity.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(myIntent);
                } else{
                    Snackbar snackbar = Snackbar
                            .make(view, "Đăng nhập thất bại", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                Log.d("abc", "on respone");
                System.out.println("on response");
            }

            @Override
            public void onFailure(Call<LoginToken> call, Throwable t) {
                Log.d("abc", "on failure" + t.getMessage());
            }
        });
    }
}
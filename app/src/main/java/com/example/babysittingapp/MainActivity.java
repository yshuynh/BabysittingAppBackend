package com.example.babysittingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.babysittingapp.entity.LoginInfo;
import com.example.babysittingapp.entity.LoginToken;
import com.example.babysittingapp.entity.User;
import com.example.babysittingapp.service.APIService;
import com.example.babysittingapp.service.APIUtils;
import com.example.babysittingapp.service.StaticData;
import com.example.babysittingapp.ui.RegisterParentFragment;
import com.example.babysittingapp.ui.home.CreateParentFragment;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button register = findViewById(R.id.dn_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterParentFragment nextFrag= new RegisterParentFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.register_framelayout, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    public void onClickBtnSubmit(View view) {
        EditText txtUsername = findViewById(R.id.editTextUsername);
        EditText txtPassword = findViewById(R.id.editTextPassword);
        LoginInfo loginInfo = new LoginInfo(txtUsername.getText().toString(), txtPassword.getText().toString());
        APIService service = APIUtils.getAPIService();
        Log.d("abc", txtPassword.getText().toString());
        service.postLogin(loginInfo).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User loginToken = response.body();
                    StaticData.getInstance().setLoginToken(loginToken);
                    Intent myIntent = new Intent(MainActivity.this, ParentActivity.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(myIntent);
                } else{
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String userMessage = jsonObject.getString("detail");
                        Snackbar snackbar = Snackbar
                                .make(view, userMessage, Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        Snackbar snackbar = Snackbar
                                .make(view, "Đăng nhập thất bại", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

                }
                Log.d("abc", "on respone");
                System.out.println("on response");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("abc", "on failure" + t.getMessage());
            }
        });
    }
}
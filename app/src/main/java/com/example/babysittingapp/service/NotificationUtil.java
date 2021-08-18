package com.example.babysittingapp.service;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.babysittingapp.entity.Notification;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationUtil {
    public static NotificationUtil instance = null;
    private Timer timer;
    private TimerTask timerTask;
    public static NotificationUtil getInstance() {
        if (instance == null) {
            instance = new NotificationUtil();
        }
        return instance;
    }
    private NotificationUtil() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                APIService service = APIUtils.getAPIService();
                service.getNotificationListUser(StaticData.getInstance().loginToken.getId()).enqueue(new Callback<ArrayList<Notification>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Notification>> call, Response<ArrayList<Notification>> response) {
                        if (!response.isSuccessful()) {
                            Log.d("abc", "can't not load notification");
                            return;
                        }
                        if (StaticData.getInstance().getNotification_list() == null || StaticData.getInstance().getNotification_list().size() != response.body().size()) {
                            StaticData.getInstance().setNotification_list(response.body());
                            Log.d("abc", "updated notifications");
                        }
                        Log.d("abc", StaticData.getInstance().getNotification_list().toString());
                        Log.d("abc", "not update notifications");
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Notification>> call, Throwable t) {
                        Log.d("abc", "can't not load notification" + t.getMessage());
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 5000);
    }
}

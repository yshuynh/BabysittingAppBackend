package com.example.babysittingapp.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babysittingapp.R;
import com.example.babysittingapp.entity.Notification;
import com.example.babysittingapp.entity.Post;
import com.example.babysittingapp.entity.User;
import com.example.babysittingapp.service.APIService;
import com.example.babysittingapp.service.APIUtils;
import com.example.babysittingapp.service.CustomUtils;
import com.example.babysittingapp.service.StaticData;
import com.example.babysittingapp.ui.dashboard.UserInfoFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationRealFragment extends Fragment implements Observer {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notification_real, container, false);
        createRecycleView(rootView);
        StaticData.getInstance().addObserver(this);
        Log.d("abc", "on created view notification");
        return rootView;
    }

    private void createRecycleView(View rootView) {
        RecyclerView recyclerView = rootView.findViewById(R.id.notireal_recycleView);
        ArrayList<Notification> noti_list = StaticData.getInstance().getNotification_list();
        NotificationAdapter adapter = new NotificationAdapter(noti_list, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void update(Observable o, Object arg) {
        Log.d("abc","update post detail fragment");
        if (getView() == null) {
            StaticData.getInstance().deleteObserver(this);
            onDestroy();
            return;
        }
        createRecycleView(getView());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        StaticData.getInstance().deleteObserver(this);
        Log.d("abc", "detroy post detail");
    }
}
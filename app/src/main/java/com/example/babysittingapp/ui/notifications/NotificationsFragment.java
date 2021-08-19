package com.example.babysittingapp.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babysittingapp.R;
import com.example.babysittingapp.databinding.ParentFragmentNotificationsBinding;
import com.example.babysittingapp.entity.Notification;
import com.example.babysittingapp.entity.Post;
import com.example.babysittingapp.entity.User;
import com.example.babysittingapp.service.StaticData;
import com.example.babysittingapp.ui.home.BabysisterAdapter;
import com.example.babysittingapp.ui.home.PostAdapter;

import java.util.ArrayList;
import java.util.Observable;

public class NotificationsFragment extends Fragment implements java.util.Observer {

    private NotificationsViewModel notificationsViewModel;
    private ParentFragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = ParentFragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        StaticData.getInstance().addObserver(this);
        createRecycleView();
        return root;
    }

    private void createRecycleView() {
        if (binding == null) {
            Log.d("abc", "biding null");
            return;
        }
        RecyclerView recyclerView = binding.notiRecycleview;
        ArrayList<User> list_String = StaticData.getInstance().getFavoriteUsers();
        Log.d("abc", "aaaa" + list_String.size());
        BabysisterAdapter adapter = new BabysisterAdapter(list_String, getContext(), false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void update(Observable observable, Object o) {
        Log.d("abc", "create recycle view notification");
        createRecycleView();
    }
}
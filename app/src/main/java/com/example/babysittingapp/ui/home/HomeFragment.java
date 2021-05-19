package com.example.babysittingapp.ui.home;

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
import com.example.babysittingapp.databinding.ParentFragmentHomeBinding;
import com.example.babysittingapp.entity.LoginToken;
import com.example.babysittingapp.entity.Post;
import com.example.babysittingapp.service.APIService;
import com.example.babysittingapp.service.APIUtils;
import com.example.babysittingapp.service.StaticData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Observable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements java.util.Observer {

    private HomeViewModel homeViewModel;
    private ParentFragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = ParentFragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        createFloatingButton();
        StaticData.getInstance().addObserver(this);
        StaticData.getInstance().refreshData();
        return root;
    }

    private void createFloatingButton() {
        FloatingActionButton fab = binding.floatingActionButton;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateParentFragment nextFrag= new CreateParentFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_holder, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void createRecycleView() {
        if (binding == null) {
            Log.d("abc", "biding null");
            return;
        }
        RecyclerView recyclerView = binding.recyclePost;
        ArrayList<Post> list_String = StaticData.getInstance().getPost_list();
        PostAdapter adapter = new PostAdapter(list_String, getContext());
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
    public void update(Observable o, Object arg) {
        Log.d("abc","update observer home");
        createRecycleView();

    }
}
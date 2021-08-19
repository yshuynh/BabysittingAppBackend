package com.example.babysittingapp.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.babysittingapp.R;
import com.example.babysittingapp.databinding.ParentFragmentDashboardBinding;
import com.example.babysittingapp.service.StaticData;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private ParentFragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = ParentFragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        StaticData.getInstance().currentUser = StaticData.getInstance().getLoginToken();
        UserInfoFragment nextFrag= new UserInfoFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_holder_dashboard, nextFrag, "userInfo")
                .commit();

//        final TextView textView = binding.textDashboard;
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
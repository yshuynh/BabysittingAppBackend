package com.example.babysittingapp.ui.home;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.babysittingapp.R;
import com.example.babysittingapp.entity.User;
import com.example.babysittingapp.service.CustomUtils;
import com.example.babysittingapp.service.StaticData;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserInfoFragment newInstance(String param1, String param2) {
        UserInfoFragment fragment = new UserInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_user_info, container, false);
        mapData(rootView);
        return rootView;
    }

    private void mapData(View rootView) {
        User userData = StaticData.getInstance().currentUser;
        // View
        TextView id = rootView.findViewById(R.id.ud_id);
        TextView gender = rootView.findViewById(R.id.ud_gender);
        TextView address = rootView.findViewById(R.id.ud_address);
        TextView email = rootView.findViewById(R.id.ud_email);
        TextView job = rootView.findViewById(R.id.ud_job);
        TextView phone = rootView.findViewById(R.id.ud_phone);
        ImageView avatar = rootView.findViewById(R.id.ud_avatar);
        TextView name = rootView.findViewById(R.id.ud_name);
        // Set Data
        id.setText(userData.getId());
        gender.setText(userData.getGender()?"Nam":"Nữ");
        address.setText(userData.getAddress());
        email.setText(userData.getEmail());
        job.setText(userData.getJob());
        phone.setText(userData.getPhoneNumber());
        new CustomUtils.DownloadImageTask(avatar)
                .execute(userData.getAvatar());
        name.setText(userData.getName());
    }
}
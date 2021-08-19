package com.example.babysittingapp.ui.home;

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

public class PostDetailFragment extends Fragment implements Observer {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post_detail, container, false);
        createRecycleView(rootView);
        mapData(rootView);
        mapBabyAccepted(rootView);
        mapParent(rootView);
        editButtonAction(rootView);
        StaticData.getInstance().addObserver(this);
        Log.d("abc", "on created view notification");
        return rootView;
    }

    private void editButtonAction(View rootView) {
        Button action = rootView.findViewById(R.id.pd_buttonAction);
        User token = StaticData.getInstance().loginToken;
        Post post = StaticData.getInstance().getCurrentPost();
        if (token.getRole().equals("parent")) {
            action.setText("Bắt đầu giữ trẻ");
            action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckInFragment nextFrag= new CheckInFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragment_holder, nextFrag, "check_in")
                            .addToBackStack(null)
                            .commit();
                }
            });
            if (post.getBabysister() == null) {
                action.setEnabled(false);
            } else {
                action.setEnabled(true);
            }
        } else {
            if (post.getBabysister() != null && post.getBabysister().getId().equals(token.getId())) {
                action.setText("Bắt đầu giữ trẻ");
                action.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckInFragment nextFrag= new CheckInFragment();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .add(R.id.fragment_holder, nextFrag, "check_in")
                                .addToBackStack(null)
                                .commit();
                    }
                });
            }
            else {
                if (isInclude(post.getBabysisterRequest(), token.getId())) {
                    action.setText("Hủy yêu cầu giữ trẻ");
                } else {
                    action.setText("Yêu cầu giữ trẻ");
                }
                action.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        APIService service = APIUtils.getAPIService();
                        RequestBody babyID = RequestBody.create(MediaType.parse("text/plain"), token.getId());
                        service.babyRequestPost(post.getId(), babyID).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful())
                                    StaticData.getInstance().refreshData();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }
                });
            }
        }
    }

    private boolean isInclude(List<User> babysisterRequest, String id) {
        for (User user : babysisterRequest) {
            if (user.getId().equals(id)) return true;
        }
        return false;
    }

    private void mapParent(View rootView) {
        User parentData = StaticData.getInstance().getCurrentPost().getParent();
        View parentView = rootView.findViewById(R.id.pd_parent);
        parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticData.getInstance().currentUser = parentData;
                UserInfoFragment nextFrag= new UserInfoFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_holder, nextFrag, "userInfo")
                        .addToBackStack(null)
                        .commit();
            }
        });

        parentView.setVisibility(View.VISIBLE);
        TextView name = parentView.findViewById(R.id.bb_name);
        TextView age = parentView.findViewById(R.id.bb_age);
        TextView gender = parentView.findViewById(R.id.bb_gender);
        TextView job = parentView.findViewById(R.id.bb_job);
        TextView rating = parentView.findViewById(R.id.rd_rate);
        ImageView avatar = parentView.findViewById(R.id.ud_avatar);
        name.setText(parentData.getName());
        Integer year = Calendar.getInstance().get(Calendar.YEAR);
        Integer birthYear = Integer.parseInt(parentData.getDateOfBird().split("-")[0]);
        Integer ageInt = year - birthYear;
        age.setText(ageInt.toString());
        gender.setText(parentData.getGender()?"Nam":"Nữ");
        job.setText(parentData.getJob());
        rating.setText(parentData.getRating().getAvg().toString());

        new CustomUtils.DownloadImageTask(avatar)
                .execute(parentData.getAvatar());

        Button button = parentView.findViewById(R.id.bb_button);
        button.setText("Xem");
        button.setVisibility(View.GONE);
    }

    private void mapBabyAccepted(View rootView) {
        if (rootView == null) return;
        User babyData = StaticData.getInstance().getCurrentPost().getBabysister();
        View babyItemAccepted = rootView.findViewById(R.id.bb_itemBabyAccepted);
        babyItemAccepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticData.getInstance().currentUser = babyData;
                UserInfoFragment nextFrag= new UserInfoFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_holder, nextFrag, "userInfo")
                        .addToBackStack(null)
                        .commit();
            }
        });
        if (babyData == null || babyData.getId().equals("US_bbnull")) {
            babyItemAccepted.setVisibility(View.GONE);
        }
        else {
            babyItemAccepted.setVisibility(View.VISIBLE);
            TextView name = babyItemAccepted.findViewById(R.id.bb_name);
            TextView age = babyItemAccepted.findViewById(R.id.bb_age);
            TextView gender = babyItemAccepted.findViewById(R.id.bb_gender);
            TextView job = babyItemAccepted.findViewById(R.id.bb_job);
            TextView rating = babyItemAccepted.findViewById(R.id.rd_rate);
            ImageView avatar = babyItemAccepted.findViewById(R.id.ud_avatar);
            name.setText(babyData.getName());
            Integer year = Calendar.getInstance().get(Calendar.YEAR);
            Integer birthYear = Integer.parseInt(babyData.getDateOfBird().split("-")[0]);
            Integer ageInt = year - birthYear;
            age.setText(ageInt.toString());
            gender.setText(babyData.getGender()?"Nam":"Nữ");
            job.setText(babyData.getJob());
            rating.setText(babyData.getRating().getAvg().toString());

            new CustomUtils.DownloadImageTask(avatar)
                    .execute(babyData.getAvatar());

            Button button = babyItemAccepted.findViewById(R.id.bb_button);
            button.setText("Bắt đầu");
            button.setVisibility(View.GONE);
        }

    }

    private void mapData(View rootView) {
        EditText dayStart = rootView.findViewById(R.id.pd_start);
        dayStart.setKeyListener(null);
        EditText dayEnd = rootView.findViewById(R.id.pd_end);
        dayEnd.setKeyListener(null);
        EditText cnt = rootView.findViewById(R.id.pd_cnt);
        cnt.setKeyListener(null);
        EditText avg = rootView.findViewById(R.id.pd_age);
        avg.setKeyListener(null);
        EditText price = rootView.findViewById(R.id.pd_price);
        price.setKeyListener(null);
        EditText address = rootView.findViewById(R.id.pd_address);
        address.setKeyListener(null);
        Post post = StaticData.getInstance().getCurrentPost();
        dayStart.setText(post.getDateTimeStart().split("T")[0]);
        dayEnd.setText(post.getTimeStart() + " - " + post.getTimeEnd());
        cnt.setText(post.getChildNumber().toString());
        avg.setText(post.getAgeAvg().toString());
        price.setText(post.getPrice().toString());
        address.setText(post.getAddress());
    }

    private void createRecycleView(View rootView) {
        RecyclerView recyclerView = rootView.findViewById(R.id.pd_recycleView);
        ArrayList<User> listBabysister = (ArrayList<User>) StaticData.getInstance().getCurrentPost().getBabysisterRequest();
        BabysisterAdapter adapter = new BabysisterAdapter(listBabysister, getContext());
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
        mapBabyAccepted(getView());
        editButtonAction(getView());
        createRecycleView(getView());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        StaticData.getInstance().deleteObserver(this);
        Log.d("abc", "detroy post detail");
    }
}
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
import com.example.babysittingapp.entity.Babysister;
import com.example.babysittingapp.entity.Post;
import com.example.babysittingapp.service.APIService;
import com.example.babysittingapp.service.APIUtils;
import com.example.babysittingapp.service.CustomUtils;
import com.example.babysittingapp.service.StaticData;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

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
        StaticData.getInstance().addObserver(this);
        return rootView;
    }

    private void mapBabyAccepted(View rootView) {
        Babysister babyData = StaticData.getInstance().getCurrentPost().getBabysister();
        View babyItemAccepted = rootView.findViewById(R.id.bb_itemBabyAccepted);
        if (babyData == null || babyData.getUser().getId().equals("US_bbnull")) {
            babyItemAccepted.setVisibility(View.GONE);
        }
        else {
            babyItemAccepted.setVisibility(View.VISIBLE);
            TextView name = babyItemAccepted.findViewById(R.id.bb_name);
            TextView age = babyItemAccepted.findViewById(R.id.bb_age);
            TextView gender = babyItemAccepted.findViewById(R.id.bb_gender);
            TextView job = babyItemAccepted.findViewById(R.id.bb_job);
            TextView rating = babyItemAccepted.findViewById(R.id.bb_rating);
            ImageView avatar = babyItemAccepted.findViewById(R.id.item_avatar);
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
        }

    }

    private void mapData(View rootView) {
        EditText dayStart = rootView.findViewById(R.id.pd_start);
        EditText dayEnd = rootView.findViewById(R.id.pd_end);
        EditText cnt = rootView.findViewById(R.id.pd_cnt);
        EditText avg = rootView.findViewById(R.id.pd_age);
        EditText price = rootView.findViewById(R.id.pd_price);
        Post post = StaticData.getInstance().getCurrentPost();
        dayStart.setText(post.getTimeStart().split("T")[0]);
        dayEnd.setText(post.getTimeEnd().split("T")[0]);
        cnt.setText(post.getChildNumber().toString());
        avg.setText(post.getAgeAvg().toString());
        price.setText(post.getPrice().toString());
    }

    private void createRecycleView(View rootView) {
        RecyclerView recyclerView = rootView.findViewById(R.id.pd_recycleView);
        ArrayList<Babysister> listBabysister = (ArrayList<Babysister>) StaticData.getInstance().getCurrentPost().getBabysisterRequest();
        BabysisterAdapter adapter = new BabysisterAdapter(listBabysister, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void update(Observable o, Object arg) {
        Log.d("abc","update post detail fragment");
        mapBabyAccepted(getView());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        StaticData.getInstance().deleteObserver(this);
    }
}
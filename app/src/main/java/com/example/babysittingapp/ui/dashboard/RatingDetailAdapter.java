package com.example.babysittingapp.ui.dashboard;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babysittingapp.ParentActivity;
import com.example.babysittingapp.R;
import com.example.babysittingapp.entity.Post;
import com.example.babysittingapp.entity.RatingDetail;
import com.example.babysittingapp.service.CustomUtils;
import com.example.babysittingapp.service.StaticData;
import com.example.babysittingapp.ui.home.PostDetailFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RatingDetailAdapter extends RecyclerView.Adapter<RatingDetailAdapter.ViewHolder> {
    //Dữ liệu hiện thị là danh sách sinh viên
    private ArrayList<RatingDetail> ratingDetailList;
    // Lưu Context để dễ dàng truy cập
    private Context mContext;

    public RatingDetailAdapter(ArrayList<RatingDetail> _student, Context mContext) {
        this.ratingDetailList = _student;
        this.mContext = mContext;
    }

    @NonNull
    @NotNull
    @Override
    public RatingDetailAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Nạp layout cho View biểu diễn phần tử sinh viên
        View view =
                inflater.inflate(R.layout.rating_detail_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RatingDetailAdapter.ViewHolder holder, int position) {
        RatingDetail ratingDetail = ratingDetailList.get(position);
        holder.textRate.setText(ratingDetail.getRating().toString() + "/5.0");
        holder.textComment.setText(ratingDetail.getComment());
    }

    @Override
    public int getItemCount() {
        return ratingDetailList.size();
    }

    /**
     * Lớp nắm giữ cấu trúc view
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private View itemview;
        public TextView textRate;
        public TextView textComment;

        public ViewHolder(View itemView) {
            super(itemView);
            itemview = itemView;
            textRate = itemView.findViewById(R.id.rd_rate);
            textComment = itemView.findViewById(R.id.rd_comment);
        }
    }


}

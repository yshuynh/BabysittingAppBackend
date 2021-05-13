package com.example.babysittingapp.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babysittingapp.R;
import com.example.babysittingapp.entity.Post;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    //Dữ liệu hiện thị là danh sách sinh viên
    private ArrayList<Post> postList;
    // Lưu Context để dễ dàng truy cập
    private Context mContext;

    public PostAdapter(ArrayList<Post> _student, Context mContext) {
        this.postList = _student;
        this.mContext = mContext;
    }

    @NonNull
    @NotNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Nạp layout cho View biểu diễn phần tử sinh viên
        View studentView =
                inflater.inflate(R.layout.post_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(studentView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PostAdapter.ViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.textStatus.setText(post.getStatus());
        holder.textPrice.setText(post.getPrice().toString());
        holder.textStartTime.setText(post.getTimeStart());
        holder.textEndTime.setText(post.getTimeEnd());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    /**
     * Lớp nắm giữ cấu trúc view
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private View itemview;
        public TextView textStatus;
        public TextView textPrice;
        public TextView textStartTime;
        public TextView textEndTime;
//        public Button detail_button;

        public ViewHolder(View itemView) {
            super(itemView);
            itemview = itemView;
            textStatus = itemView.findViewById(R.id.text_status);
            textPrice = itemView.findViewById(R.id.text_price);
            textStartTime = itemView.findViewById(R.id.textStartTime);
            textEndTime = itemView.findViewById(R.id.textEndTime);
//            detail_button = itemView.findViewById(R.id.detail_button);

//            //Xử lý khi nút Chi tiết được bấm
//            detail_button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(view.getContext(),
//                            studentname.getText() +" | "
//                                    + " Demo function", Toast.LENGTH_SHORT)
//                            .show();
//                }
//            });
        }
    }


}

package com.example.babysittingapp.ui.home;

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
import com.example.babysittingapp.service.CustomUtils;
import com.example.babysittingapp.service.StaticData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

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
        View view =
                inflater.inflate(R.layout.post_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PostAdapter.ViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.textStatus.setText(post.getStatus());
        holder.textPrice.setText(post.getPrice().toString());
        holder.textStartTime.setText(post.getTimeStart().split("T")[0]);
        holder.textEndTime.setText(post.getTimeEnd().split("T")[0]);
        holder.cntRequest.setText(((Integer)post.getBabysisterRequest().size()).toString());
//        holder.parentID.setText(post.getParent().getId());
        holder.parentName.setText(post.getParent().getName());

        holder.avatarParent.setImageBitmap(null);
        new CustomUtils.DownloadImageTask(holder.avatarParent)
                .execute(post.getParent().getAvatar());

        holder.avatar.setImageBitmap(null);
        if (post.getBabysister() != null)
            new CustomUtils.DownloadImageTask(holder.avatar)
                    .execute(post.getBabysister().getAvatar());
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
        public ImageView avatar;
        public TextView cntRequest;
        public TextView parentID;
        public TextView parentName;
        public ImageView avatarParent;
//        public Button detail_button;

        public ViewHolder(View itemView) {
            super(itemView);
            itemview = itemView;
            textStatus = itemView.findViewById(R.id.text_status);
            textPrice = itemView.findViewById(R.id.text_price);
            textStartTime = itemView.findViewById(R.id.textStartTime);
            textEndTime = itemView.findViewById(R.id.textEndTime);
            avatar = itemView.findViewById(R.id.ud_avatar);
            cntRequest = itemView.findViewById(R.id.pd_cntRequest);
//            parentID = itemView.findViewById(R.id.pd_parentName);
            parentName = itemView.findViewById(R.id.pd_parentName);
            avatarParent = itemView.findViewById(R.id.item_avatar_parent);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("abc", textStatus.getText().toString());
                    StaticData.getInstance().setCurrentPostID(postList.get(getAdapterPosition()).getId());
                    PostDetailFragment nextFrag= new PostDetailFragment();
                    ((ParentActivity)mContext).getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragment_holder, nextFrag, "post_detail")
                            .addToBackStack(null)
                            .commit();
                    Toast.makeText(view.getContext(),
                            ""+getAdapterPosition(), Toast.LENGTH_SHORT)
                            .show();
                }
            });
        }
    }


}

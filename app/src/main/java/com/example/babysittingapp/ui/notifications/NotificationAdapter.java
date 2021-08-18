package com.example.babysittingapp.ui.notifications;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babysittingapp.ParentActivity;
import com.example.babysittingapp.R;
import com.example.babysittingapp.entity.Notification;
import com.example.babysittingapp.entity.Post;
import com.example.babysittingapp.entity.User;
import com.example.babysittingapp.service.APIService;
import com.example.babysittingapp.service.APIUtils;
import com.example.babysittingapp.service.CustomUtils;
import com.example.babysittingapp.service.StaticData;
import com.example.babysittingapp.ui.dashboard.UserInfoFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    //Dữ liệu hiện thị là danh sách sinh viên
    private ArrayList<Notification> notificationArrayList;
    // Lưu Context để dễ dàng truy cập
    private Context mContext;

    public NotificationAdapter(ArrayList<Notification> _notificationArrayList, Context mContext) {
        this.notificationArrayList = _notificationArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @NotNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Nạp layout cho View biểu diễn phần tử sinh viên
        View view =
                inflater.inflate(R.layout.notification_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NotificationAdapter.ViewHolder holder, int position) {
        Notification notification = notificationArrayList.get(position);
        holder.title.setText(notification.getTitle());
        holder.content.setText(notification.getContent());
    }

    @Override
    public int getItemCount() {
        return notificationArrayList.size();
    }

    /**
     * Lớp nắm giữ cấu trúc view
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private View itemview;
        private TextView title;
        private TextView content;

        public ViewHolder(View itemView) {
            super(itemView);
            itemview = itemView;
            title = itemView.findViewById(R.id.noti_title);
            content = itemView.findViewById(R.id.noti_content);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ParentActivity)mContext).goToPostDetail(notificationArrayList.get(getAdapterPosition()).getTargetId());
                }
            });
        }
    }


}

package com.example.babysittingapp.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babysittingapp.ParentActivity;
import com.example.babysittingapp.R;
import com.example.babysittingapp.entity.Babysister;
import com.example.babysittingapp.entity.Post;
import com.example.babysittingapp.service.APIService;
import com.example.babysittingapp.service.APIUtils;
import com.example.babysittingapp.service.CustomUtils;
import com.example.babysittingapp.service.StaticData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.FormUrlEncoded;

public class BabysisterAdapter extends RecyclerView.Adapter<BabysisterAdapter.ViewHolder> {
    //Dữ liệu hiện thị là danh sách sinh viên
    private ArrayList<Babysister> babyList;
    // Lưu Context để dễ dàng truy cập
    private Context mContext;

    public BabysisterAdapter(ArrayList<Babysister> _student, Context mContext) {
        this.babyList = _student;
        this.mContext = mContext;
    }

    @NonNull
    @NotNull
    @Override
    public BabysisterAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Nạp layout cho View biểu diễn phần tử sinh viên
        View view =
                inflater.inflate(R.layout.babysister_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BabysisterAdapter.ViewHolder holder, int position) {
        Babysister babyData = babyList.get(position);
        holder.name.setText(babyData.getName());
        Integer year = Calendar.getInstance().get(Calendar.YEAR);
        Integer birthYear = Integer.parseInt(babyData.getDateOfBird().split("-")[0]);
        Integer ageInt = year - birthYear;
        holder.age.setText(ageInt.toString());
        holder.gender.setText(babyData.getGender()?"Nam":"Nữ");
        holder.job.setText(babyData.getJob());
        holder.rating.setText(babyData.getRating().getAvg().toString());

        holder.avatar.setImageBitmap(null);
        new CustomUtils.DownloadImageTask(holder.avatar)
                .execute(babyData.getAvatar());
    }

    @Override
    public int getItemCount() {
        return babyList.size();
    }

    /**
     * Lớp nắm giữ cấu trúc view
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private View itemview;
        TextView name;
        TextView age;
        TextView gender;
        TextView job;
        TextView rating;
        ImageView avatar;
        Button button;

        public ViewHolder(View itemView) {
            super(itemView);
            itemview = itemView;
            name = itemview.findViewById(R.id.bb_name);
            age = itemview.findViewById(R.id.bb_age);
            gender = itemview.findViewById(R.id.bb_gender);
            job = itemview.findViewById(R.id.bb_job);
            rating = itemview.findViewById(R.id.bb_rating);
            avatar = itemview.findViewById(R.id.item_avatar);
            button = itemView.findViewById(R.id.bb_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // API
                    Post post = StaticData.getInstance().getCurrentPost();
                    Babysister babysister = babyList.get(getAdapterPosition());
                    APIService service = APIUtils.getAPIService();
                    Log.d("abc", babysister.getUser().getId());
                    RequestBody babysisterID = RequestBody.create(MediaType.parse("text/plain"), babysister.getUser().getId());
                    service.updatePost(post.getId(), babysisterID).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                StaticData.getInstance().refreshData();
                            }
                            else Log.d("abc", "not successfull api");
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.d("abc", "fail api");
                        }
                    });
                }
            });
        }
    }


}

package com.example.babysittingapp.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.babysittingapp.R;
import com.example.babysittingapp.entity.Code;
import com.example.babysittingapp.entity.Post;
import com.example.babysittingapp.entity.RatingDetail;
import com.example.babysittingapp.service.APIService;
import com.example.babysittingapp.service.APIUtils;
import com.example.babysittingapp.service.StaticData;

import java.util.Observable;
import java.util.Observer;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckInFragment extends Fragment implements Observer {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String rate_user_id;

    public CheckInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CheckInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CheckInFragment newInstance(String param1, String param2) {
        CheckInFragment fragment = new CheckInFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_check_in, container, false);
        mapButton(rootView);
        StaticData.getInstance().addObserver(this);
        return rootView;
    }

    private void mapButton(View rootView) {
        Button btnCheckin = rootView.findViewById(R.id.ci_buttonCheckin);
        Button btnCheckout = rootView.findViewById(R.id.ci_buttonCheckout);
        Button btnThanhToan = rootView.findViewById(R.id.ci_buttonThanhtoan);
        Button btnDanhGia = rootView.findViewById(R.id.ci_buttonDanhGia);
        btnCheckin.setEnabled(false);
        btnCheckout.setEnabled(false);
        btnThanhToan.setEnabled(false);
        btnDanhGia.setEnabled(false);
        Post post = StaticData.getInstance().getCurrentPost();

        switch (StaticData.getInstance().getCurrentPost().getStatus()) {
            case "matched": {
                btnCheckin.setEnabled(true);
                break;
            }
            case "checked_in": {
                btnCheckout.setEnabled(true);
                break;
            }
            case "checked_out": {
                btnThanhToan.setEnabled(true);
                btnThanhToan.setText("Đang thanh toán...");
                RequestBody status = RequestBody.create(MediaType.parse("text/plain"), "completed");
                APIService service = APIUtils.getAPIService();
                service.updatePostStatus(post.getId(), status).enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        if (!response.isSuccessful()) return;
                        btnThanhToan.setText("Đã thanh toán");
                        StaticData.getInstance().refreshData();
                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {

                    }
                });
                break;
            }
            case "completed": {
                btnDanhGia.setEnabled(true);
                btnDanhGia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder
                                = new AlertDialog
                                .Builder(getActivity());
                        View mView = getLayoutInflater().inflate(R.layout.rating_bar_dialog,null);
                        RatingBar rateBar = (RatingBar)mView.findViewById(R.id.ratingBar);
                        EditText commentText = mView.findViewById(R.id.rb_comment);
                        APIService service = APIUtils.getAPIService();
                        rate_user_id = post.getBabysister().getId();
                        if (rate_user_id.equals(StaticData.getInstance().getLoginToken().getId()))
                            rate_user_id = post.getParent().getId();
                        service.getRateUser(rate_user_id, post.getId()).enqueue(new Callback<RatingDetail>() {
                            @Override
                            public void onResponse(Call<RatingDetail> call, Response<RatingDetail> response) {
                                if (!response.isSuccessful()) return;
                                rateBar.setRating(response.body().getRating());
                                commentText.setText(response.body().getComment());
                            }

                            @Override
                            public void onFailure(Call<RatingDetail> call, Throwable t) {

                            }
                        });
                        // Set the message show for the Alert time
                        // Set Alert Title
                        builder.setTitle("Đánh giá:");

                        // Set Cancelable false
                        // for when the user clicks on the outside
                        // the Dialog Box then it will remain show
                        builder.setCancelable(false);

                        // Set up the buttons
                        builder.setPositiveButton("Gửi", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RatingDetail ratingDetail = new RatingDetail();
                                ratingDetail.setComment(commentText.getText().toString());
                                ratingDetail.setRating((int)rateBar.getRating());
                                ratingDetail.setPost(post.getId());
                                ratingDetail.setUser(rate_user_id);
                                service.rateUser(ratingDetail).enqueue(new Callback<RatingDetail>() {
                                    @Override
                                    public void onResponse(Call<RatingDetail> call, Response<RatingDetail> response) {
                                        if (!response.isSuccessful()) return;
                                        Toast.makeText(getContext(), "Thành công!", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<RatingDetail> call, Throwable t) {

                                    }
                                });
                            }
                        });

                        // Set the Negative button with No name
                        // OnClickListener method is use
                        // of DialogInterface interface.
                        builder.setNegativeButton(
                                "Hủy",
                                new DialogInterface
                                        .OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which)
                                    {

                                        // If user click no
                                        // then dialog box is canceled.
                                        dialog.cancel();
                                    }
                                });

                        builder.setView(mView);

                        // Create the Alert dialog
                        AlertDialog alertDialog = builder.create();

                        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface arg0) {
                                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.Black));
                                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.Gray));
                            }
                        });
                        // Show the Alert Dialog box
                        alertDialog.show();
                    }
                });
                break;
            }
        }
        if (StaticData.getInstance().getLoginToken().getRole().equals("babysister")) {
            setUpButtonCheckinBaby(btnCheckin);
            setUpButtonCheckinBaby(btnCheckout);
        }
        else {
            setUpButtonCheckinParent(btnCheckin);
            setUpButtonCheckinParent(btnCheckout);
        }

    }

    private void setUpButtonCheckinBaby(Button btnCheckin) {
        btnCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the object of
                // AlertDialog Builder class
                AlertDialog.Builder builder
                        = new AlertDialog
                        .Builder(getActivity());

                // Set the message show for the Alert time
                builder.setMessage("Mã xác nhận: ");

                // Set up the input
                final EditText input = new EditText(getContext());
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
                input.setGravity(Gravity.CENTER_HORIZONTAL);
                input.setLayoutParams(new LinearLayout.LayoutParams(1,LinearLayout.LayoutParams.WRAP_CONTENT));
                builder.setView(input);

                // Set Alert Title
                builder.setTitle("Baby sitting check in:");

                // Set Cancelable false
                // for when the user clicks on the outside
                // the Dialog Box then it will remain show
                builder.setCancelable(false);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String m_Text = input.getText().toString();
                        APIService service = APIUtils.getAPIService();
                        RequestBody post_id = RequestBody.create(MediaType.parse("text/plain"), StaticData.getInstance().getCurrentPost().getId());
                        RequestBody code = RequestBody.create(MediaType.parse("text/plain"), m_Text);
                        service.updatePostCode(post_id, code).enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (!response.isSuccessful()) return;
                                Toast.makeText(getContext(), "Thành công!", Toast.LENGTH_SHORT).show();
                                StaticData.getInstance().refreshData();
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                // Set the Negative button with No name
                // OnClickListener method is use
                // of DialogInterface interface.
                builder.setNegativeButton(
                        "Hủy",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // If user click no
                                // then dialog box is canceled.
                                dialog.cancel();
                            }
                        });

                // Create the Alert dialog
                AlertDialog alertDialog = builder.create();

                // Show the Alert Dialog box
                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.Black));
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.Gray));
                    }
                });
                alertDialog.show();
            }
        });
    }

    private void setUpButtonCheckinParent(Button btnCheckin) {
        btnCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APIService service = APIUtils.getAPIService();
                RequestBody post_id = RequestBody.create(MediaType.parse("text/plain"), StaticData.getInstance().getCurrentPost().getId());
                service.createPostCode(post_id).enqueue(new Callback<Code>() {
                    @Override
                    public void onResponse(Call<Code> call, Response<Code> response) {
                        if (!response.isSuccessful()) return;
                        Code code = response.body();
                        // Create the object of
                        // AlertDialog Builder class
                        AlertDialog.Builder builder
                                = new AlertDialog
                                .Builder(getActivity());

                        // Set the message show for the Alert time
                        builder.setMessage("Mã xác nhận: " + code.getCode());

                        // Set Alert Title
                        builder.setTitle("Parent check in:");

                        // Set Cancelable false
                        // for when the user clicks on the outside
                        // the Dialog Box then it will remain show
                        builder.setCancelable(false);

                        // Set the Negative button with No name
                        // OnClickListener method is use
                        // of DialogInterface interface.
                        builder.setNegativeButton(
                                "Hủy",
                                new DialogInterface
                                        .OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which)
                                    {

                                        // If user click no
                                        // then dialog box is canceled.
                                        dialog.cancel();
                                    }
                                });

                        // Create the Alert dialog
                        AlertDialog alertDialog = builder.create();
                        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface arg0) {
                                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.Black));
                                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.Gray));
                            }
                        });
                        // Show the Alert Dialog box
                        alertDialog.show();
                    }

                    @Override
                    public void onFailure(Call<Code> call, Throwable t) {

                    }
                });
            }
        });
    }


    @Override
    public void update(Observable o, Object arg) {
        if (getView() != null) mapButton(getView());
    }
}
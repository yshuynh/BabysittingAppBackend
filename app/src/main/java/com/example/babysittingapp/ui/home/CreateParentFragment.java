package com.example.babysittingapp.ui.home;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.babysittingapp.R;
import com.example.babysittingapp.entity.PostCreatePost;
import com.example.babysittingapp.service.APIService;
import com.example.babysittingapp.service.APIUtils;
import com.example.babysittingapp.service.StaticData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateParentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateParentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateParentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateParentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateParentFragment newInstance(String param1, String param2) {
        CreateParentFragment fragment = new CreateParentFragment();
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
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    private void settingCalendar(View view, EditText editText) {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateTimeEnd(editText, myCalendar);
            }
        };
        editText.getText();
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DatePickerDialog dt = new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dt.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        dt.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.Black));
                        dt.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.Gray));
                    }
                });
                dt.show();
            }
        });
    }

    private void updateTimeEnd(EditText editText, Calendar myCalendar) {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(sdf.format(myCalendar.getTime()));
    }

    private void settingTimePicker(View view, EditText editText) {
        Calendar mcurrentTime = Calendar.getInstance();
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        editText.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        mTimePicker.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.Black));
                        mTimePicker.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.Gray));
                    }
                });
                mTimePicker.show();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_parent, container, false);
        EditText editText = rootView.findViewById(R.id.fc_textTimeStart);
        settingCalendar(rootView, editText);
        editText = rootView.findViewById(R.id.fc_timeStart);
        settingTimePicker(rootView, editText);
        editText = rootView.findViewById(R.id.fc_timeEnd);
        settingTimePicker(rootView, editText);

        Button submit = rootView.findViewById(R.id.cp_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostCreatePost post = new PostCreatePost();
//                EditText price = rootView.findViewById(R.id.fc_price);
//                post.setPrice(Integer.parseInt(price.getText().toString()));
                EditText age = rootView.findViewById(R.id.fc_textAge);
                post.setAgeAvg(Integer.parseInt(age.getText().toString()));
                EditText startDate = rootView.findViewById(R.id.fc_textTimeStart);
                post.setDateTimeStart(startDate.getText().toString());
                EditText timeStart = rootView.findViewById(R.id.fc_timeStart);
                post.setTimeStart(timeStart.getText().toString());
                EditText timeEnd = rootView.findViewById(R.id.fc_timeEnd);
                post.setTimeEnd(timeEnd.getText().toString());
                EditText childCount = rootView.findViewById(R.id.fc_numberchild);
                post.setChildNumber(Integer.parseInt(childCount.getText().toString()));
                post.setParent(StaticData.getInstance().getLoginToken().getId());

                APIService service = APIUtils.getAPIService();
                Log.d("abc","onclick");
                service.putPost(post).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        StaticData.getInstance().refreshData();
                        destroy();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });

        return rootView;
    }

    private void destroy() {
        Log.d("abc", "destroy: ");
        getActivity().getSupportFragmentManager().popBackStack();
    }


}
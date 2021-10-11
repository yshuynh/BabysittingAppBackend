package com.example.babysittingapp.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.babysittingapp.R;
import com.example.babysittingapp.entity.User;
import com.example.babysittingapp.entity.UserCreate;
import com.example.babysittingapp.service.APIService;
import com.example.babysittingapp.service.APIUtils;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterParentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterParentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterParentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterParentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterParentFragment newInstance(String param1, String param2) {
        RegisterParentFragment fragment = new RegisterParentFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_register_parent, container, false);
        mapView(rootView);
        return rootView;
    }

    private void updateTimeEnd(EditText editText, Calendar myCalendar) {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(sdf.format(myCalendar.getTime()));
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

    private void mapView(View rootView) {
        EditText name = rootView.findViewById(R.id.re_name);
        RadioButton gender = rootView.findViewById(R.id.radioButton_nam);
        EditText dob = rootView.findViewById(R.id.re_dob);
        settingCalendar(rootView, dob);
        EditText phone = rootView.findViewById(R.id.re_phone);
        EditText job = rootView.findViewById(R.id.re_job);
        EditText address = rootView.findViewById(R.id.re_address);
        EditText avatar = rootView.findViewById(R.id.re_avatar);
        EditText cmnd_truoc = rootView.findViewById(R.id.re_cmnd_truoc);
        EditText cmnd_sau = rootView.findViewById(R.id.re_cmnd_sau);
        EditText username = rootView.findViewById(R.id.re_username);
        EditText password = rootView.findViewById(R.id.re_password);
        Button submit = rootView.findViewById(R.id.re_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText name = rootView.findViewById(R.id.re_name);
                RadioButton gender = rootView.findViewById(R.id.radioButton_nam);
                EditText dob = rootView.findViewById(R.id.re_dob);
                EditText phone = rootView.findViewById(R.id.re_phone);
                EditText job = rootView.findViewById(R.id.re_job);
                EditText address = rootView.findViewById(R.id.re_address);
                EditText avatar = rootView.findViewById(R.id.re_avatar);
                EditText cmnd_truoc = rootView.findViewById(R.id.re_cmnd_truoc);
                EditText cmnd_sau = rootView.findViewById(R.id.re_cmnd_sau);
                EditText username = rootView.findViewById(R.id.re_username);
                EditText password = rootView.findViewById(R.id.re_password);
                EditText email = rootView.findViewById(R.id.re_email);
                User user = new User();
                user.setName(name.getText().toString());
                user.setGender(gender.isChecked());
                user.setDateOfBird(dob.getText().toString());
                user.setPhoneNumber(phone.getText().toString());
                user.setJob(job.getText().toString());
                user.setAddress(address.getText().toString());
                user.setAvatar(avatar.getText().toString());
                user.setCmnd_truoc(cmnd_truoc.getText().toString());
                user.setCmnd_sau(cmnd_sau.getText().toString());
                user.setUsername(username.getText().toString());
                user.setPassword(password.getText().toString());
                user.setEmail(email.getText().toString());
                APIService service = APIUtils.getAPIService();
                service.userRegister(user).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (!response.isSuccessful()) {
                            Snackbar snackbar = Snackbar
                                    .make(view, "Lỗi không xác định.", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            return;
                        }
                        Snackbar snackbar = Snackbar
                                .make(view, "Đang ký thành công. Hãy chờ admin review thông tin và kích hoạt tài khoản của bạn.", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        getActivity().getSupportFragmentManager().popBackStack();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Snackbar snackbar = Snackbar
                                .make(view, "Lỗi không xác định.", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                });
            }
        });
    }
}
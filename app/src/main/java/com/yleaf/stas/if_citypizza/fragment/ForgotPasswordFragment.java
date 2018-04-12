package com.yleaf.stas.if_citypizza.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.yleaf.stas.if_citypizza.CustomToast;
import com.yleaf.stas.if_citypizza.R;
import com.yleaf.stas.if_citypizza.Resource;
import com.yleaf.stas.if_citypizza.activity.LoginActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by stas on 21.03.18.
 */

public class ForgotPasswordFragment extends Fragment implements View.OnClickListener {
    private static View view;

    private static String TAG = ForgotPasswordFragment.class.getSimpleName();

    private static EditText emailId;
    private static TextView submit, back;
    private static ProgressDialog pd;

    public ForgotPasswordFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.forgotpassword_layout, container,
                false);
        initViews();
        setListeners();
        return view;
    }

    // Initialize the views
    private void initViews() {
        emailId = view.findViewById(R.id.registered_emailid);
        submit = view.findViewById(R.id.forgot_button);
        back = view.findViewById(R.id.backToLoginBtn);
    }

    // Set Listeners over buttons
    private void setListeners() {
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backToLoginBtn:

                // Replace Login Fragment on Back Presses
                new LoginActivity().replaceLoginFragment();
                break;

            case R.id.forgot_button:

                // Call Submit button task
                submitButtonTask();
                break;
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void sendForgotEmailId(String eMail) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(eMail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if (pd.isShowing())
                                pd.dismiss();
                            Toast.makeText(getActivity(), "SUCCESS", Toast.LENGTH_SHORT).show();
                        } else {
                            if (pd.isShowing())
                                pd.dismiss();
                            Toast.makeText(getActivity(), "FAIL", Toast.LENGTH_SHORT).show();
                            task.getException().printStackTrace();
                        }
                    }
                });
    }

    private boolean findEmail(String eMail) {
        return true;
    }

    private void submitButtonTask() {
        String getEmailId = emailId.getText().toString();

        // Pattern for email id validation
        Pattern p = Pattern.compile(Resource.regEx);

        // Match the pattern
        Matcher m = p.matcher(getEmailId);

        // First check if email id is not null else show error toast
        if (getEmailId.isEmpty()) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Please enter your Email Id.");

            // Check if email id is valid or not
        }  else if (!m.find()) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Your Email Id is Invalid.");
            // Else submit email id and fetch passwod or do your stuff
        } else if(!findEmail(getEmailId)) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Your Email Id is Not Found.");
        } else {
            if(isOnline()) {
                pd = ProgressDialog.show(getActivity(), "Sending",
                        "Please Wait...");
                sendForgotEmailId(getEmailId);
            } else {
                new CustomToast().Show_Toast(getActivity(), view,
                        "No Internet Connection");
            }
        }
    }
}

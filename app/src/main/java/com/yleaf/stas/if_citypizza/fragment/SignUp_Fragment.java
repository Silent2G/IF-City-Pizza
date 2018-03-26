package com.yleaf.stas.if_citypizza.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.yleaf.stas.if_citypizza.CustomToast;
import com.yleaf.stas.if_citypizza.R;
import com.yleaf.stas.if_citypizza.Utils;
import com.yleaf.stas.if_citypizza.activity.LoginActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by stas on 22.03.18.
 */

public class SignUp_Fragment extends Fragment implements View.OnClickListener {
    private static View view;
    private static EditText fullName, emailId, mobileNumber, location,
            password, confirmPassword;
    private static TextView login;
    private static Button signUpButton;
    private static CheckBox terms_conditions;

    private FirebaseAuth mAuth;

    private static final String TAG = SignUp_Fragment.class.getSimpleName();

    public SignUp_Fragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signup_layout, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initialize all views
    private void initViews() {
        fullName = (EditText) view.findViewById(R.id.fullName);
        emailId = (EditText) view.findViewById(R.id.userEmailId);
        mobileNumber = (EditText) view.findViewById(R.id.mobileNumber);
        location = (EditText) view.findViewById(R.id.location);
        password = (EditText) view.findViewById(R.id.password);
        confirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
        signUpButton = (Button) view.findViewById(R.id.signUpBtn);
        login = (TextView) view.findViewById(R.id.already_user);
        terms_conditions = (CheckBox) view.findViewById(R.id.terms_conditions);
    }

    // Set Listeners
    private void setListeners() {
        signUpButton.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpBtn:

                // Call checkValidation method
                checkValidation();
                break;

            case R.id.already_user:

                // Replace login fragment
                new LoginActivity().replaceLoginFragment();
                break;
        }

    }


    private void fireBaseSignInWithEmail(final String getFullName,
                                         final String getEmailId,
                                         final String getPassword,
                                         final String getMobileNumber,
                                         final String getLocation) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(getEmailId, getPassword)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                          //  FileManager.addUser(new User(getFullName, getEmailId, getMobileNumber, getLocation), getContext(), view);
                            Toast.makeText(getActivity(), "Success registration =)", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof FirebaseAuthWeakPasswordException) {
                    new CustomToast().Show_Toast(getActivity(), view, "Your Password Less Than 6 Chars");
                }
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    new CustomToast().Show_Toast(getActivity(), view, "Your Email Is Incorrect");
                }
                if (e instanceof FirebaseAuthUserCollisionException) {
                    new CustomToast().Show_Toast(getActivity(), view, "This User Is Exist");
                }
            }
        });
    }

    // Check Validation Method
    private void checkValidation() {

        // Get all edittext texts
        String getFullName = fullName.getText().toString();
        String getEmailId = emailId.getText().toString();
        String getMobileNumber = mobileNumber.getText().toString();
        String getLocation = location.getText().toString();
        String getPassword = password.getText().toString();
        String getConfirmPassword = confirmPassword.getText().toString();

        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        // Check if all strings are null or not
        if (getFullName.isEmpty() || getFullName.length() == 0
                || getEmailId.isEmpty() || getEmailId.length() == 0
                || getMobileNumber.isEmpty() || getMobileNumber.length() == 0
                || getLocation.isEmpty() || getLocation.length() == 0
                || getPassword.isEmpty() || getPassword.length() == 0
                || getConfirmPassword.isEmpty() || getConfirmPassword.length() == 0)

            new CustomToast().Show_Toast(getActivity(), view,
                    "All fields are required.");

            // Check if email id valid or not
        else if (!m.find())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Your Email Id is Invalid.");

            // Check if both password should be equal
        else if (!getConfirmPassword.equals(getPassword))
            new CustomToast().Show_Toast(getActivity(), view,
                    "Both password doesn't match.");

            // Make sure user should check Terms and Conditions checkbox
        else if (!terms_conditions.isChecked())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Please select Terms and Conditions.");

            // Else do signUp or do your stuff
        else {
            if (Login_Fragment.isOnline()) {
                fireBaseSignInWithEmail(getFullName, getEmailId, getPassword, getMobileNumber, getLocation);
            } else {
                new CustomToast().Show_Toast(getActivity(), view,
                        "No internet connection");
            }

        }


    }
}
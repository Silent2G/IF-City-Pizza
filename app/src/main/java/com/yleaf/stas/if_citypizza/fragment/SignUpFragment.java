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
import com.yleaf.stas.if_citypizza.toast.CustomToastFail;
import com.yleaf.stas.if_citypizza.R;
import com.yleaf.stas.if_citypizza.Resource;
import com.yleaf.stas.if_citypizza.RxEditText;
import com.yleaf.stas.if_citypizza.activity.LoginActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func6;

/**
 * Created by stas on 22.03.18.
 */

public class SignUpFragment extends Fragment implements View.OnClickListener {
    private View view;
    private EditText fullName, emailId, mobileNumber, location,
            password, confirmPassword;
    private TextView login;
    private Button signUpButton;
    private CheckBox terms_conditions;

    private FirebaseAuth mAuth;

    private static final String TAG = SignUpFragment.class.getSimpleName();

    public SignUpFragment() {

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

        signUpButton.setEnabled(false);

        // rxJava
        Observable<String> fullNameObservable = RxEditText.getTextWatcherObservable(fullName);
        Observable<String> emailIdObservable = RxEditText.getTextWatcherObservable(emailId);
        Observable<String> mobileNumberObservable = RxEditText.getTextWatcherObservable(mobileNumber);
        Observable<String> locationObservable = RxEditText.getTextWatcherObservable(location);
        Observable<String> passwordObservable = RxEditText.getTextWatcherObservable(password);
        Observable<String> confirmPasswordObservable = RxEditText.getTextWatcherObservable(confirmPassword);

        Observable.combineLatest(fullNameObservable, emailIdObservable, mobileNumberObservable, locationObservable,
                passwordObservable, confirmPasswordObservable, new Func6<String, String, String, String, String, String, Boolean>() {
                    @Override
                    public Boolean call(String s, String s2, String s3, String s4, String s5, String s6) {
                        if(s.isEmpty() || s2.isEmpty() || s3.isEmpty() || s4.isEmpty() || s5.isEmpty() || s6.isEmpty()) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                }).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        signUpButton.setEnabled(aBoolean);
                    }
                });

        setListeners();
        return view;
    }

    // Initialize all views
    private void initViews() {
        fullName = view.findViewById(R.id.fullName);
        emailId = view.findViewById(R.id.userEmailId);
        mobileNumber = view.findViewById(R.id.mobileNumber);
        location = view.findViewById(R.id.location);
        password = view.findViewById(R.id.password);
        confirmPassword = view.findViewById(R.id.confirmPassword);
        signUpButton = view.findViewById(R.id.signUpBtn);
        login = view.findViewById(R.id.already_user);
        terms_conditions = view.findViewById(R.id.terms_conditions);
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
                            Toast.makeText(getActivity(), "Success registration =)", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof FirebaseAuthWeakPasswordException) {
                    new CustomToastFail().showToast(getActivity(), view, "Your Password Less Than 6 Chars");
                } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    new CustomToastFail().showToast(getActivity(), view, "Your Email Is Incorrect zzzz");
                } else if (e instanceof FirebaseAuthUserCollisionException) {
                    new CustomToastFail().showToast(getActivity(), view, "This User Is Exist");
                }
            }
        });
    }

    // Check Validation Method
    private void checkValidation() {

        // Get all editText texts
        String getFullName = fullName.getText().toString();
        String getEmailId = emailId.getText().toString();
        String getMobileNumber = mobileNumber.getText().toString();
        String getLocation = location.getText().toString();
        String getPassword = password.getText().toString();
        String getConfirmPassword = confirmPassword.getText().toString();

        // Pattern match for email id
        Pattern p = Pattern.compile(Resource.regEx);
        Matcher m = p.matcher(getEmailId);

            // Check if email id valid or not
        if (!m.find())
            new CustomToastFail().showToast(getActivity(), view,
                    "Your Email Id is Invalid.");

            // Check if both password should be equal
        else if (!getConfirmPassword.equals(getPassword))
            new CustomToastFail().showToast(getActivity(), view,
                    "Both password doesn't match.");

            // Make sure user should check Terms and Conditions checkbox
        else if (!terms_conditions.isChecked())
            new CustomToastFail().showToast(getActivity(), view,
                    "Please select Terms and Conditions.");

            // Else do signUp or do your stuff
        else {
            if (LoginFragment.isOnline()) {
                fireBaseSignInWithEmail(getFullName, getEmailId, getPassword, getMobileNumber, getLocation);
            } else {
                new CustomToastFail().showToast(getActivity(), view,
                        "No internet connection");
            }

        }


    }
}
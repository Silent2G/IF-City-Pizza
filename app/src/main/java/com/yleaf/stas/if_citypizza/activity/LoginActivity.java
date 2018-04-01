package com.yleaf.stas.if_citypizza.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.yleaf.stas.if_citypizza.R;
import com.yleaf.stas.if_citypizza.Utils;
import com.yleaf.stas.if_citypizza.fragment.LoginFragment;

public class LoginActivity extends AppCompatActivity {

    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // exit app onBackPressed User
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

        // if user entered start MainActivity
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(MainActivity.newIntent(getApplicationContext()));
            finish();
        }

        setContentView(R.layout.activity_login);
        fragmentManager = getFragmentManager();

        // If savedInstanceState is null then replace login fragment
        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frameContainer, new LoginFragment(),
                            Utils.Login_Fragment).commit();
        }

        // On close icon click finish activity
        findViewById(R.id.close_activity).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        finish();

                    }
                });
    }

    // Replace Login Fragment with animation
    public void replaceLoginFragment() {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_righ, 0,0)
                .replace(R.id.frameContainer, new LoginFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        // Find the tag of signUp and forgot password fragment
        Fragment SignUp_Fragment = fragmentManager
                .findFragmentByTag(Utils.SignUp_Fragment);
        Fragment ForgotPassword_Fragment = fragmentManager
                .findFragmentByTag(Utils.ForgotPassword_Fragment);

        // Check if both are null or not
        // If both are not null then replace login fragment else do backPressed
        // task

        if (SignUp_Fragment != null)
            replaceLoginFragment();
        else if (ForgotPassword_Fragment != null)
            replaceLoginFragment();
        else
            super.onBackPressed();
    }
}

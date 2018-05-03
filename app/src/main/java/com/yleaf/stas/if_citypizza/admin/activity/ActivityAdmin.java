package com.yleaf.stas.if_citypizza.admin.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.yleaf.stas.if_citypizza.R;
import com.yleaf.stas.if_citypizza.admin.ModelAdmin;
import com.yleaf.stas.if_citypizza.admin.PresenterAdmin;

public class ActivityAdmin extends AppCompatActivity {

    private static final String TAG = ActivityAdmin.class.getSimpleName();

    public Button getDataBtn;
    public Button pushDataBtn;

    private PresenterAdmin presenter;

    private ImageView imagePizzaGetData;
    private ImageView imagePizzaPushData;
    private ImageView imagePizzaDownload;
    public ImageView imagePizzaPizza;
    public ImageView imagePizzaMan;

    private static Animation animationTrance;
    private static Animation animationRotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout_admin);

        // init widgets
        getDataBtn = findViewById(R.id.get_data_btn);
        pushDataBtn = findViewById(R.id.push_data_btn);

        pushDataBtn.setEnabled(false);

        imagePizzaGetData = findViewById(R.id.image_pizza_get_data);
        imagePizzaPushData = findViewById(R.id.image_pizza_push_data);
        imagePizzaDownload = findViewById(R.id.image_pizza_download);
        imagePizzaPizza = findViewById(R.id.image_pizza_pizza);
        imagePizzaMan = findViewById(R.id.image_pizza_man);

        imagePizzaPushData.setVisibility(View.INVISIBLE);
        imagePizzaDownload.setVisibility(View.INVISIBLE);
        imagePizzaPizza.setVisibility(View.INVISIBLE);
        imagePizzaMan.setVisibility(View.INVISIBLE);

        animationTrance = AnimationUtils.loadAnimation(this,R.anim.trance);
        animationRotate = AnimationUtils.loadAnimation(this,R.anim.rotate);

        //firstAnimation
        getDataAnimationStart();

        getDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getData();

                // animation
                getDataAnimationStop();
                downloadAnimationStart();
            }

        });

        pushDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.pushData();

                // animation
                pushDataAnimationStop();
                downloadAnimationStart();
                imagePizzaPizza.setVisibility(View.GONE);
            }
        });

        ModelAdmin model = new ModelAdmin();

        presenter = new PresenterAdmin(model);
        presenter.attachView(this);
    }

    // animation methods -----------------------------------

    public void getDataAnimationStart() {
        animationTrance.setRepeatCount(Animation.INFINITE);
        imagePizzaGetData.startAnimation(animationTrance);
    }

    public void getDataAnimationStop() {
        imagePizzaGetData.clearAnimation();
        imagePizzaGetData.setVisibility(View.GONE);
    }

    public void pushDataAnimationStart() {
        animationTrance.setRepeatCount(Animation.INFINITE);
        imagePizzaPushData.startAnimation(animationTrance);
    }

    public void pushDataAnimationStop() {
        imagePizzaPushData.setVisibility(View.GONE);
        imagePizzaPushData.clearAnimation();
    }

    public void downloadAnimationStart() {
        animationRotate.setRepeatCount(Animation.INFINITE);
        imagePizzaDownload.startAnimation(animationRotate);
    }

    public void downloadAnimationStop() {
        imagePizzaDownload.clearAnimation();
        imagePizzaDownload.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
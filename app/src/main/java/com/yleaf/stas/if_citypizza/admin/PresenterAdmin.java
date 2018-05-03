package com.yleaf.stas.if_citypizza.admin;

import android.view.View;

import com.yleaf.stas.if_citypizza.admin.activity.ActivityAdmin;

public class PresenterAdmin {

    private static final String TAG = PresenterAdmin.class.getSimpleName();

    private ActivityAdmin view;
    private final ModelAdmin model;

    public PresenterAdmin(ModelAdmin model) {
        this.model = model;
    }

    public void attachView(ActivityAdmin mainActivityAdmin) {
        view = mainActivityAdmin;
    }

    public void detachView() {
        view = null;
    }

    public void getData() {
        model.loadData(new ModelAdmin.CompleteCallback() {
            @Override
            public void onComplete() {
                view.downloadAnimationStop();

                view.pushDataBtn.setEnabled(true);
                view.getDataBtn.setEnabled(false);
                view.imagePizzaPizza.setVisibility(View.VISIBLE);

                view.pushDataAnimationStart();
            }
        });
    }

    public void pushData() {
        model.pushData(new ModelAdmin.CompleteCallback() {
            @Override
            public void onComplete() {
                view.downloadAnimationStop();

                view.imagePizzaMan.setVisibility(View.VISIBLE);

                view.pushDataBtn.setEnabled(false);
            }
        });
    }
}


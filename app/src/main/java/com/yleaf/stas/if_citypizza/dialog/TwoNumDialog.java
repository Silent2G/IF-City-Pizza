package com.yleaf.stas.if_citypizza.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.yleaf.stas.if_citypizza.R;
import com.yleaf.stas.if_citypizza.model.Manufacturer;

public class TwoNumDialog extends DialogFragment {
    private static final String MANUFACTURER = "manufacturer";

    private Manufacturer manufacturer;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        manufacturer = getArguments().getParcelable(MANUFACTURER);

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.two_dialog_layout);

        ImageButton vodafone = dialog.findViewById(R.id.image_vodafone);
        ImageButton kyivstar = dialog.findViewById(R.id.image_kyivstar);

        vodafone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call(manufacturer.getVodafone());
            }
        });

        kyivstar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call(manufacturer.getKyivstar());
            }
        });

        return dialog;
    }

    private void call(String number) {
        startActivity(new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", number, null)));
    }

    public static TwoNumDialog newInstance(Manufacturer manufacturer) {

        Bundle args = new Bundle();
        args.putParcelable(MANUFACTURER, manufacturer);

        TwoNumDialog fragment = new TwoNumDialog();
        fragment.setArguments(args);
        return fragment;
    }
}

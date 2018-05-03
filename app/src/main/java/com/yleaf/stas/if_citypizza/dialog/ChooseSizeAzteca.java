package com.yleaf.stas.if_citypizza.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.yleaf.stas.if_citypizza.toast.CustomToastSuccess;
import com.yleaf.stas.if_citypizza.DataHolder;
import com.yleaf.stas.if_citypizza.R;
import com.yleaf.stas.if_citypizza.fragment.AztecaFragment;

public class ChooseSizeAzteca extends DialogFragment {

    private static final String TAG = ChooseSizeAzteca.class.getSimpleName();

    private static final String ID = "id";
    private int id;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());

        id = getArguments().getInt(ID);

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.choose_size_azteca);

        LinearLayout linearLayoutStandart = dialog.findViewById(R.id.linear_layout_standart);
        linearLayoutStandart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataHolder.getData(getActivity()).addReservedPizzaToAzteca(id, false);
                new CustomToastSuccess().showToast(getActivity(), AztecaFragment.getFragmentView(), "30");
                dialog.dismiss();

                Log.i(TAG, String.valueOf(DataHolder.getData(getActivity()).getAztecaReserved().size()));
            }
        });

        LinearLayout linearLayoutLarge = dialog.findViewById(R.id.linear_layout_large);
        linearLayoutLarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataHolder.getData(getActivity()).addReservedPizzaToAzteca(id, true);
                new CustomToastSuccess().showToast(getActivity(), AztecaFragment.getFragmentView(), "50");
                dialog.dismiss();

                Log.i(TAG, String.valueOf(DataHolder.getData(getActivity()).getAztecaReserved().size()));
            }
        });

        return dialog;
    }

    public static ChooseSizeAzteca newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(ID, id);

        ChooseSizeAzteca fragment = new ChooseSizeAzteca();
        fragment.setArguments(args);
        return fragment;
    }
}

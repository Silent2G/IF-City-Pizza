package com.yleaf.stas.if_citypizza.toast;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yleaf.stas.if_citypizza.R;

public class CustomToastSuccess {
    // Custom Toast Method
    public void showToast(Context context, View view, String success) {

        // Layout Inflater for inflating custom view
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Inflate the layout over view
        View layout = inflater.inflate(R.layout.custom_toast_success,
                (ViewGroup) view.findViewById(R.id.toast_root));

        // Get TextView id and set error
        TextView text = layout.findViewById(R.id.toast_success);
        text.setText(success);

        Toast toast = new Toast(context); // Get Toast Context
        toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);// Set

        toast.setDuration(Toast.LENGTH_SHORT);// Set Duration
        toast.setView(layout); // Set Custom View over toast

        toast.show();
    }
}

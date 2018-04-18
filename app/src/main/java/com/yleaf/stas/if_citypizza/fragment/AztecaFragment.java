package com.yleaf.stas.if_citypizza.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yleaf.stas.if_citypizza.DataHolder;
import com.yleaf.stas.if_citypizza.R;
import com.yleaf.stas.if_citypizza.Resource;
import com.yleaf.stas.if_citypizza.activity.MainActivity;
import com.yleaf.stas.if_citypizza.admin.Manufacturer;
import com.yleaf.stas.if_citypizza.admin.Pizza;

public class AztecaFragment extends Fragment {

    private static final String TAG = AztecaFragment.class.getSimpleName();
    private final static String PIZZA_ID = "pizzaId";

    private Context appContext;
    private Pizza pizza;
    private Manufacturer manufacturer;
    private int pizzaId;

    private ImageView imageViewHeader;
    private ImageView imageViewManufacturer;
    private TextView textViewDescription;
    private TextView textViewSizeStandart;
    private TextView textViewSizeBig;
    private TextView textViewPriceStandart;
    private TextView textViewPriceBig;
    private Toolbar toolbar;
    private Button buttonCall;
    private Button buttonAddToCart;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appContext = getContext().getApplicationContext();
        pizzaId = getArguments().getInt(PIZZA_ID);
        pizza = DataHolder.getData(appContext).getPizzaById(Resource.AZTECA, pizzaId);
        manufacturer = DataHolder.getData(appContext).getManufacturerByName(Resource.AZTECA);
    }

    private void initWidgets(View view) {
        toolbar = view.findViewById(R.id.toolbar_azteca);
        imageViewHeader = view.findViewById(R.id.image_header_azteca);
        textViewDescription = view.findViewById(R.id.tv_description_azteca);
        textViewSizeStandart = view.findViewById(R.id.tv_size_standart_azteca);
        textViewSizeBig = view.findViewById(R.id.tv_size_big_azteca);
        textViewPriceStandart = view.findViewById(R.id.tv_price_standart_azteca);
        textViewPriceBig = view.findViewById(R.id.tv_price_big_azteca);
        buttonCall = view.findViewById(R.id.button_call_azteca);
        buttonAddToCart = view.findViewById(R.id.button_add_azteca);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.azteca_fragment_layout, container, false);

        initWidgets(view);

        toolbar.setTitle(pizza.getTitle());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        Glide.with(appContext).load(manufacturer.getLogoSrc()).into(imageViewHeader);

        imageViewManufacturer = view.findViewById(R.id.image_manufacturer_azteca);
        Glide.with(appContext)
                .load(pizza.getImageSrc())
                .into(imageViewManufacturer);

        textViewDescription.setText(pizza.getDescription());
        textViewSizeStandart.setText(pizza.getDiameterStandart());
        textViewSizeBig.setText(pizza.getDiameterLarge());
        textViewPriceStandart.setText(pizza.getPriceStandart());
        textViewPriceBig.setText(pizza.getPriceLarge());

        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeDialog();
            }
        });

        buttonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    private void makeDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_layout);
      //  TextView textView1 = dialog.findViewById(R.id.text);
      //  textView1.setText("Stas");
      //  dialog.setCanceledOnTouchOutside(false);
     //   dialog.setCancelable(false);

    //    dialog.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
    //        @Override
    //        public void onClick(View v) {
    //            dialog.dismiss();
    //        }
    //    });

   //     dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
   //         @Override
   //         public void onClick(View v) {
   //             Toast.makeText(WelcomeActivity.this,"Press Ok =)", Toast.LENGTH_SHORT).show();
   //         }
   //     });

        dialog.show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PIZZA_ID, pizzaId);
    }

    public static AztecaFragment newInstance(int pizzaId) {

        Bundle args = new Bundle();
        args.putInt(PIZZA_ID, pizzaId);

        AztecaFragment fragment = new AztecaFragment();
        fragment.setArguments(args);
        return fragment;
    }
}

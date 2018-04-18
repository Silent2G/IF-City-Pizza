package com.yleaf.stas.if_citypizza.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yleaf.stas.if_citypizza.DataHolder;
import com.yleaf.stas.if_citypizza.R;
import com.yleaf.stas.if_citypizza.Resource;
import com.yleaf.stas.if_citypizza.admin.Manufacturer;
import com.yleaf.stas.if_citypizza.admin.Pizza;

public class PizzaIFFragment extends Fragment {

    private static final String TAG = PizzaIFFragment.class.getSimpleName();
    private final static String PIZZA_ID = "pizzaId";

    private Context appContext;
    private Pizza pizza;
    private Manufacturer manufacturer;
    private int pizzaId;

    private ImageView imageViewHeader;
    private ImageView imageViewManufacturer;
    private Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appContext = getContext().getApplicationContext();
        pizzaId = getArguments().getInt(PIZZA_ID);
        pizza = DataHolder.getData(appContext).getPizzaById(Resource.PIZZAIF, pizzaId);
        manufacturer = DataHolder.getData(appContext).getManufacturerByName(Resource.PIZZAIF);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pizzaif_fragment_layout, container, false);

        toolbar = view.findViewById(R.id.toolbar_pizzaif);
        toolbar.setTitle(pizza.getTitle());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        imageViewHeader = view.findViewById(R.id.image_header_pizzaif);
        Glide.with(appContext).load(manufacturer.getLogoSrc()).into(imageViewHeader);

        imageViewManufacturer = view.findViewById(R.id.image_manufacturer_pizzaif);
        Glide.with(this)
                .load(pizza.getImageSrc())
                .into(imageViewManufacturer);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PIZZA_ID, pizzaId);
    }

    public static PizzaIFFragment newInstance(int pizzaId) {

        Bundle args = new Bundle();
        args.putInt(PIZZA_ID, pizzaId);

        PizzaIFFragment fragment = new PizzaIFFragment();
        fragment.setArguments(args);
        return fragment;
    }
}

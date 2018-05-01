package com.yleaf.stas.if_citypizza.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yleaf.stas.if_citypizza.DataHolder;
import com.yleaf.stas.if_citypizza.R;
import com.yleaf.stas.if_citypizza.Resource;
import com.yleaf.stas.if_citypizza.admin.Manufacturer;
import com.yleaf.stas.if_citypizza.model.Pizza;
import com.yleaf.stas.if_citypizza.dialog.FourNumDialog;

public class PizzaIFFragment extends Fragment {

    private static final String TAG = PizzaIFFragment.class.getSimpleName();
    private final static String PIZZA_ID = "pizzaId";
    private final static int REQUEST_CODE = 0;
    private final static String DIALOG_CALL = "call";

    private Context appContext;
    private Pizza pizza;
    private Manufacturer manufacturer;
    private int pizzaId;

    private ImageView imageViewHeader;
    private ImageView imageViewManufacturer;
    private TextView textViewDescription;
    private TextView textViewSize;
    private TextView textViewPrice;
    private TextView textViewWeight;
    private Toolbar toolbar;
    private Button buttonCall;
    private Button buttonAddToCart;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appContext = getContext().getApplicationContext();
        pizzaId = getArguments().getInt(PIZZA_ID);
        pizza = DataHolder.getData(appContext).getPizzaById(Resource.PIZZAIF, pizzaId);
        manufacturer = DataHolder.getData(appContext).getManufacturerByName(Resource.PIZZAIF);
    }

    private void initWidgets(View view) {
        toolbar = view.findViewById(R.id.toolbar_pizzaif);
        imageViewHeader = view.findViewById(R.id.image_header_pizzaif);
        imageViewManufacturer = view.findViewById(R.id.image_manufacturer_pizzaif);
        textViewDescription = view.findViewById(R.id.tv_description_pizzaif);
        textViewSize = view.findViewById(R.id.tv_size_standart_pizzaif);
        textViewPrice = view.findViewById(R.id.tv_price_standart_pizzaif);
        textViewWeight = view.findViewById(R.id.tv_weight_pizzaif);
        buttonCall = view.findViewById(R.id.button_call_pizzaif);
        buttonAddToCart = view.findViewById(R.id.button_add_pizzaif);
    }

    private void setWidgets() {
        toolbar.setTitle(pizza.getTitle());

        Glide.with(appContext).load(manufacturer.getLogoSrc()).into(imageViewHeader);
        Glide.with(appContext).load(pizza.getImageSrc()).into(imageViewManufacturer);

        textViewDescription.setText(pizza.getDescription());
        textViewSize.setText(pizza.getDiameterStandart());
        textViewPrice.setText(pizza.getPriceStandart());
        textViewWeight.setText(pizza.getWeight());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pizzaif_fragment_layout, container, false);

        initWidgets(view);

        setWidgets();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FourNumDialog dialog = FourNumDialog.newInstance(manufacturer);
                dialog.setTargetFragment(PizzaIFFragment.this, REQUEST_CODE);
                dialog.show(fm, DIALOG_CALL);
            }
        });

        buttonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    public void updateUI() {
        pizza = DataHolder.getData(appContext).getPizzaById(Resource.PIZZAIF, pizzaId);
        setWidgets();

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

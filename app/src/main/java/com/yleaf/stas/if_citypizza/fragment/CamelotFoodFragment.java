package com.yleaf.stas.if_citypizza.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yleaf.stas.if_citypizza.toast.CustomToastSuccess;
import com.yleaf.stas.if_citypizza.DataHolder;
import com.yleaf.stas.if_citypizza.R;
import com.yleaf.stas.if_citypizza.Resource;
import com.yleaf.stas.if_citypizza.model.Manufacturer;
import com.yleaf.stas.if_citypizza.model.Pizza;
import com.yleaf.stas.if_citypizza.dialog.TwoNumDialog;

public class CamelotFoodFragment extends Fragment {

    private static final String TAG = CamelotFoodFragment.class.getSimpleName();
    private final static String PIZZA_ID = "pizzaId";
    private final static int REQUEST_CODE = 0;
    private final static String DIALOG_CALL = "call";

    private Context appContext;
    private Pizza pizza;
    private Manufacturer manufacturer;
    private int pizzaId;
    private View view;

    private ImageView imageViewHeader;
    private ImageView imageViewManufacturer;
    private TextView textViewDescription;
    private TextView textViewSize;
    private TextView textViewPrice;
    private Toolbar toolbar;
    private Button buttonCall;
    private Button buttonAddToCart;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appContext = getContext().getApplicationContext();
        pizzaId = getArguments().getInt(PIZZA_ID);
        pizza = DataHolder.getData(appContext).getPizzaById(Resource.CAMELOTFOOD, pizzaId);
        manufacturer = DataHolder.getData(appContext).getManufacturerByName(Resource.CAMELOTFOOD);
    }

    private void initWidgets(View view) {
        toolbar = view.findViewById(R.id.toolbar_camelotfood);
        imageViewHeader = view.findViewById(R.id.image_header_camelotfood);
        imageViewManufacturer = view.findViewById(R.id.image_manufacturer_camelotfood);
        textViewDescription = view.findViewById(R.id.tv_description_camelotfood);
        textViewSize = view.findViewById(R.id.tv_size_standart_camelotfood);
        textViewPrice = view.findViewById(R.id.tv_price_standart_camelotfood);
        buttonCall = view.findViewById(R.id.button_call_camelotfood);
        buttonAddToCart = view.findViewById(R.id.button_add_camelotfood);
    }

    private void setWidgets() {
        toolbar.setTitle(pizza.getTitle());

        Glide.with(appContext).load(manufacturer.getLogoSrc()).into(imageViewHeader);
        Glide.with(appContext).load(pizza.getImageSrc()).into(imageViewManufacturer);

        textViewDescription.setText(pizza.getDescription());
        textViewSize.setText(pizza.getDiameterStandart());
        textViewPrice.setText(pizza.getPriceStandart());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.camelotfood_fragment_layout, container, false);

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
                TwoNumDialog dialog = TwoNumDialog.newInstance(manufacturer);
                dialog.setTargetFragment(CamelotFoodFragment.this, REQUEST_CODE);
                dialog.show(fm, DIALOG_CALL);
            }
        });

        buttonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataHolder.getData(appContext).addReservedPizzaToCamelotFood(pizzaId);
                new CustomToastSuccess().showToast(appContext, view, "32");

                Log.i(TAG, String.valueOf(DataHolder.getData(appContext).getCamelotFoodReserved().size()));
            }
        });

        return view;
    }

    public void updateUI() {
        pizza = DataHolder.getData(appContext).getPizzaById(Resource.CAMELOTFOOD, pizzaId);
        setWidgets();
    }

    public static CamelotFoodFragment newInstance(int pizzaId) {

        Bundle args = new Bundle();
        args.putInt(PIZZA_ID, pizzaId);

        CamelotFoodFragment fragment = new CamelotFoodFragment();
        fragment.setArguments(args);
        return fragment;
    }
}

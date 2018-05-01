package com.yleaf.stas.if_citypizza.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yleaf.stas.if_citypizza.DataHolder;
import com.yleaf.stas.if_citypizza.R;
import com.yleaf.stas.if_citypizza.Resource;
import com.yleaf.stas.if_citypizza.model.Pizza;

import java.util.ArrayList;

/**
 * Created by stas on 27.03.18.
 */

public class PizzaFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Pizza> pizzas;
    private PizzaAdapter adapter;

    private static final String TAG = PizzaFragment.class.getSimpleName();
    private static Fragment currentFragment;
    private static String fragmentType;

    private static final String KEY = "Container";
    private String domain;
    private Context appContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appContext = getContext().getApplicationContext();

        domain = getArguments().getString(KEY);
        if(domain != null) {
            choseContainer();
        } else {
            Log.i(TAG, "KEY IS NULL");
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_layout, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new PizzaAdapter();
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void choseContainer() {
        switch (domain) {
            case Resource.AZTECA:
                pizzas = DataHolder.getData(appContext).getAzteca();
                break;
            case Resource.PIZZAIF:
                pizzas = DataHolder.getData(appContext).getPizzaIF();
                break;
            case Resource.CAMELOTFOOD:
                pizzas = DataHolder.getData(appContext).getCamelotFood();
                break;
        }
    }

    private Fragment choseFragment(int pizzaId) {
        switch (domain) {
            case Resource.AZTECA:
                fragmentType = Resource.AZTECA;
                return currentFragment = AztecaFragment.newInstance(pizzaId);
            case Resource.PIZZAIF:
                fragmentType = Resource.PIZZAIF;
                return currentFragment = PizzaIFFragment.newInstance(pizzaId);
            case Resource.CAMELOTFOOD:
                fragmentType = Resource.CAMELOTFOOD;
                return currentFragment = CamelotFoodFragment.newInstance(pizzaId);
        }
        return null;
    }
    public void refreshAdapter() {
        if(adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public static Fragment getCurrentFragment() {
        return currentFragment;
    }

    public static String getFragmentType () {
        return fragmentType;
    }

    private class PizzaHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView titleItem;
        private TextView descriptionItem;
        private ImageView imageView;

        private Pizza myPizza;

        public PizzaHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            titleItem = itemView.findViewById(R.id.title_item);
            descriptionItem = itemView.findViewById(R.id.description_item);
            imageView = itemView.findViewById(R.id.image_view_item);
        }

        public void bindPizza(Pizza pizza, final int position) {
            myPizza = pizza;

            titleItem.setText(myPizza.getTitle());
            descriptionItem.setText(myPizza.getDescription());
            Glide.with(appContext).load(pizza.getImageSrc()).into(imageView);

        }

        @Override
        public void onClick(View v) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.beginTransaction()
                    .add(R.id.fragment_container,
                            choseFragment(myPizza.getId()))
                    .addToBackStack(null)
                    .commit();
        }


    }

    private class PizzaAdapter extends RecyclerView.Adapter<PizzaHolder> {

        public PizzaAdapter() {}

        @Override
        public PizzaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(appContext);
            View view = layoutInflater.inflate(R.layout.pizza_item, parent, false);
            return new PizzaHolder(view);
        }

        @Override
        public void onBindViewHolder(PizzaHolder holder, int position) {
            Pizza pizza = pizzas.get(position);
            holder.bindPizza(pizza, position);
        }

        @Override
        public int getItemCount() {
            return pizzas.size();
        }
    }

    public static PizzaFragment newInstance(String domainName) {

        Bundle args = new Bundle();
        args.putString(KEY, domainName);

        PizzaFragment fragment = new PizzaFragment();
        fragment.setArguments(args);
        return fragment;
    }

}

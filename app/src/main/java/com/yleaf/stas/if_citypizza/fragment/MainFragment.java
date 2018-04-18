package com.yleaf.stas.if_citypizza.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.yleaf.stas.if_citypizza.DataHolder;
import com.yleaf.stas.if_citypizza.R;
import com.yleaf.stas.if_citypizza.Resource;
import com.yleaf.stas.if_citypizza.adapter.ViewPagerAdapter;


/**
 * Created by stas on 26.03.18.
 */

public class MainFragment extends Fragment {

    public ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private MaterialSearchView searchView;

    private static final String TAG = MainFragment.class.getSimpleName();

    private static Context appContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appContext = getContext().getApplicationContext();

    }

    private void doSomething() {
        Log.i(TAG, "AZTEZA " + String.valueOf(DataHolder.getData(appContext).getAzteca().size()));
        Log.i(TAG, "PIZZAIF " + DataHolder.getData(appContext).getPizzaIF().size() + "");
        Log.i(TAG, "CAMELOTFOOD " + DataHolder.getData(appContext).getCamelotFood().size() + "");

        Log.i(TAG, "AZTEZA_INFO " + DataHolder.getData(appContext).getAztecaInfo().getAddress() + "");
        Log.i(TAG, "PIZZAIF_INFO " + DataHolder.getData(appContext).getPizzaIFInfo().getAddress() + "");
        Log.i(TAG, "CAMELOTFOOD_INFO " + DataHolder.getData(appContext).getCamelotFoodInfo().getAddress() + "");

        setWidgets();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab_layout, container, false);
        initWidgets(view);
        doSomething();

        return view;
    }

    private void initWidgets(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        viewPager = view.findViewById(R.id.view_pager);
        searchView = view.findViewById(R.id.search_view);
        tabLayout = view.findViewById(R.id.tab_layout);
    }

    private void setWidgets() {

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        setupViewPager(viewPager, new ViewPagerAdapter(getActivity().getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);//setting tab over viewpager

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }
        });
    }

    public static void setupViewPager(ViewPager viewPager, ViewPagerAdapter adapter) {

        adapter.addFrag(PizzaFragment.newInstance(Resource.AZTECA), Resource.AZTECA);
        adapter.addFrag(PizzaFragment.newInstance(Resource.PIZZAIF), Resource.PIZZAIF);
        adapter.addFrag(PizzaFragment.newInstance(Resource.CAMELOTFOOD), Resource.CAMELOTFOOD);

        viewPager.setAdapter(adapter);
    }
}

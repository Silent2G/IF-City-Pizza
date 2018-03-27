package com.yleaf.stas.if_citypizza.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.yleaf.stas.if_citypizza.R;
import com.yleaf.stas.if_citypizza.adapter.ViewPagerAdapter;

import java.util.ArrayList;

/**
 * Created by stas on 26.03.18.
 */

public class MainFragment extends Fragment {

    public ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private MaterialSearchView searchView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab_layout, container, false);
        initWidgets(view);
       // setWidgets();

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
                //   mRecyclerView.setAdapter(mAdapter);
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String res = "";
                if(newText != null && !newText.isEmpty()) {
                    ArrayList<String> listFound = new ArrayList<String>();
                    // for(String item : listResources) {
                    //    if((item.toLowerCase()).contains(newText.toLowerCase())) {
                    //         listFound.add(item);
                    //        res = res.concat(" " + item);
                    //     }
                    //  }

                    // Toast.makeText(getActivity(), res, Toast.LENGTH_SHORT).show();
                    //   mSearchAdapter = new SearchAdapter(listFound);
                    //   mContactRecyclerView.setAdapter(mSearchAdapter);

                } else {
                    //  mContactRecyclerView.setAdapter(mAdapter);
                }
                return true;
            }
        });
    }

    public static void setupViewPager(ViewPager viewPager, ViewPagerAdapter adapter) {

        adapter.addFrag(PizzaFragment.newInstance(), "Pizza One");
        adapter.addFrag(PizzaFragment.newInstance(), "Pizza Two");
        adapter.addFrag(PizzaFragment.newInstance(), "Pizza Three");

        viewPager.setAdapter(adapter);
    }
}

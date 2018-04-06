package com.yleaf.stas.if_citypizza.fragment;

import android.support.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.yleaf.stas.if_citypizza.R;
import com.yleaf.stas.if_citypizza.Resources;
import com.yleaf.stas.if_citypizza.adapter.ViewPagerAdapter;
import com.yleaf.stas.if_citypizza.admin.Manufacturer;
import com.yleaf.stas.if_citypizza.admin.Pizza;

import java.util.ArrayList;

/**
 * Created by stas on 26.03.18.
 */

public class MainFragment extends Fragment {

    public ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private MaterialSearchView searchView;

    private static final String TAG = MainFragment.class.getSimpleName();

    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ArrayList<Pizza> azteca;
    ArrayList<Pizza> pizzaIf;
    ArrayList<Pizza> camelot;

    ArrayList<Manufacturer> aztecaInfo;
    ArrayList<Manufacturer> pizzaIfInfo;
    ArrayList<Manufacturer> camelotInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initCollections();
        downloadBasicData();
        downloadInfoData();
    }

    private void initCollections() {
        azteca = new ArrayList<Pizza>();
        pizzaIf = new ArrayList<Pizza>();
        camelot = new ArrayList<Pizza>();

        aztecaInfo = new ArrayList<Manufacturer>();
        pizzaIfInfo = new ArrayList<Manufacturer>();
        camelotInfo = new ArrayList<Manufacturer>();
    }

    private void downloadBasicData() {
        downloadBasicCollection(Resources.AZTECA, azteca);
        downloadBasicCollection(Resources.PIZZAIF, pizzaIf);
        downloadBasicCollection(Resources.CAMELOTFOOD, camelot);
    }

    private void downloadInfoData() {
        downloadInfoCollection(Resources.AZTECAINFO, aztecaInfo);
        downloadInfoCollection(Resources.PIZZAIFINFO, pizzaIfInfo);
        downloadInfoCollection(Resources.CAMELOTFOODINFO, camelotInfo);
    }

    private void downloadBasicCollection(String nameCollection, final ArrayList<Pizza> pizzas) {
        db.collection(nameCollection)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                               // Log.i(TAG, document.getId() + " => " + document.toObject(Pizza.class).getTitle());
                                pizzas.add(document.toObject(Pizza.class));
                            }
                        } else {
                            Log.i(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void downloadInfoCollection(String nameCollection, final ArrayList<Manufacturer> manufacturers) {
        db.collection(nameCollection)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Log.i(TAG, document.getId() + " => " + document.toObject(Pizza.class).getTitle());
                                manufacturers.add(document.toObject(Manufacturer.class));
                            }
                        } else {
                            Log.i(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
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

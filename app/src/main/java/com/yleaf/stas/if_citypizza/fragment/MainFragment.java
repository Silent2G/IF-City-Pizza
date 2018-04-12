package com.yleaf.stas.if_citypizza.fragment;

import android.content.Context;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.yleaf.stas.if_citypizza.DataHolder;
import com.yleaf.stas.if_citypizza.R;
import com.yleaf.stas.if_citypizza.Resource;
import com.yleaf.stas.if_citypizza.adapter.ViewPagerAdapter;
import com.yleaf.stas.if_citypizza.admin.Manufacturer;
import com.yleaf.stas.if_citypizza.admin.Pizza;
import com.yleaf.stas.if_citypizza.db.AddDataToDB;
import com.yleaf.stas.if_citypizza.db.service.AztecaInfoService;
import com.yleaf.stas.if_citypizza.db.service.AztecaService;
import com.yleaf.stas.if_citypizza.db.service.CamelotFoodInfoService;
import com.yleaf.stas.if_citypizza.db.service.CamelotFoodService;
import com.yleaf.stas.if_citypizza.db.service.PizzaIFInfoService;
import com.yleaf.stas.if_citypizza.db.service.PizzaIFService;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by stas on 26.03.18.
 */

public class MainFragment extends Fragment {

    public ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private MaterialSearchView searchView;
    private AtomicInteger atomicInteger;

    private static final String TAG = MainFragment.class.getSimpleName();

    // Access a Cloud Firestore instance from your Activity
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static Context appContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appContext = getContext().getApplicationContext();
        atomicInteger = new AtomicInteger();

        DataHolder.getData(appContext).clearCollections();

        downloadBasicData();
        downloadInfoData();
    }

    private  void downloadBasicData() {
        downloadBasicCollection(Resource.AZTECA);
        downloadBasicCollection(Resource.CAMELOTFOOD);
        downloadBasicCollection(Resource.PIZZAIF);
    }

    private void downloadInfoData() {
        downloadInfoDocuments(Resource.AZTECAINFO);
        downloadInfoDocuments(Resource.PIZZAIFINFO);
        downloadInfoDocuments(Resource.CAMELOTFOODINFO);
    }

    private void downloadBasicCollection(final String nameCollection) {
        db.collection(nameCollection)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        switch (nameCollection) {
                            case Resource.AZTECA:
                                DataHolder.getData(appContext).getAzteca()
                                        .addAll(queryDocumentSnapshots.toObjects(Pizza.class));
                                break;
                            case Resource.PIZZAIF:
                                DataHolder.getData(appContext).getPizzaIF()
                                        .addAll(queryDocumentSnapshots.toObjects(Pizza.class));
                                break;
                            case Resource.CAMELOTFOOD:
                                DataHolder.getData(appContext).getCamelotFood()
                                        .addAll(queryDocumentSnapshots.toObjects(Pizza.class));
                                break;
                        }
                        checkAndIncrementSuccess();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "onFailure load collection " + nameCollection);
                        e.printStackTrace();
                    }
                });

    }

    private void downloadInfoDocuments(final String nameCollection) {
        db.collection(nameCollection).document("info")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        switch (nameCollection) {
                            case Resource.AZTECAINFO:
                                DataHolder.getData(appContext).setAztecaInfo(documentSnapshot.toObject(Manufacturer.class));
                                break;
                            case Resource.PIZZAIFINFO:
                                DataHolder.getData(appContext).setPizzaIFInfo(documentSnapshot.toObject(Manufacturer.class));
                                break;
                            case Resource.CAMELOTFOODINFO:
                                DataHolder.getData(appContext).setCamelotFoodInfo(documentSnapshot.toObject(Manufacturer.class));
                                break;
                        }
                        checkAndIncrementSuccess();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "onFailure load info " + nameCollection);
                        e.printStackTrace();
                    }
                });

    }

    private void checkAndIncrementSuccess() {
        if(atomicInteger.incrementAndGet() == 6) {
            doSomething();
        }

    }

    private void doSomething() {
        Log.i(TAG, "AZTEZA " + DataHolder.getData(appContext).getAzteca().size() + "");
        Log.i(TAG, "PIZZAIF " + DataHolder.getData(appContext).getPizzaIF().size() + "");
        Log.i(TAG, "CAMELOTFOOD " + DataHolder.getData(appContext).getCamelotFood().size() + "");

        Log.i(TAG, "AZTEZA_INFO " + DataHolder.getData(appContext).getAztecaInfo().getAddress() + "");
        Log.i(TAG, "PIZZAIF_INFO " + DataHolder.getData(appContext).getPizzaIFInfo().getAddress() + "");
        Log.i(TAG, "CAMELOTFOOD_INFO " + DataHolder.getData(appContext).getCamelotFoodInfo().getAddress() + "");

       // addDataToDB();  // Data was written
        setWidgets();

    }

    private void addDataToDB() {
        AddDataToDB addDataToDB = new AddDataToDB(appContext);
        Thread thread = new Thread(addDataToDB);
        thread.start();
    }

    private void clearDB() {
        new AztecaService(appContext).deleteAll();
        new PizzaIFService(appContext).deleteAll();
        new CamelotFoodService(appContext).deleteAll();

        new AztecaInfoService(appContext).deleteAll();
        new PizzaIFInfoService(appContext).deleteAll();
        new CamelotFoodInfoService(appContext).deleteAll();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab_layout, container, false);
        initWidgets(view);

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
              //  String res = "";
              //  if(newText != null && !newText.isEmpty()) {
               //     ArrayList<String> listFound = new ArrayList<String>();
                    // for(String item : listResources) {
                    //    if((item.toLowerCase()).contains(newText.toLowerCase())) {
                    //         listFound.add(item);
                    //        res = res.concat(" " + item);
                    //     }
                    //  }
                    // Toast.makeText(getActivity(), res, Toast.LENGTH_SHORT).show();
                    //   mSearchAdapter = new SearchAdapter(listFound);
                    //   mContactRecyclerView.setAdapter(mSearchAdapter);

              //  } else {
                    //  mContactRecyclerView.setAdapter(mAdapter);
              //  }
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

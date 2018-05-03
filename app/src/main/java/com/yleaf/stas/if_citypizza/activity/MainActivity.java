package com.yleaf.stas.if_citypizza.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.yleaf.stas.if_citypizza.DataHolder;
import com.yleaf.stas.if_citypizza.R;
import com.yleaf.stas.if_citypizza.Resource;
import com.yleaf.stas.if_citypizza.model.Manufacturer;
import com.yleaf.stas.if_citypizza.model.Pizza;
import com.yleaf.stas.if_citypizza.db.AddDataToDB;
import com.yleaf.stas.if_citypizza.db.service.AztecaInfoService;
import com.yleaf.stas.if_citypizza.db.service.AztecaService;
import com.yleaf.stas.if_citypizza.db.service.CamelotFoodInfoService;
import com.yleaf.stas.if_citypizza.db.service.CamelotFoodService;
import com.yleaf.stas.if_citypizza.db.service.PizzaIFInfoService;
import com.yleaf.stas.if_citypizza.db.service.PizzaIFService;
import com.yleaf.stas.if_citypizza.fragment.AztecaFragment;
import com.yleaf.stas.if_citypizza.fragment.CamelotFoodFragment;
import com.yleaf.stas.if_citypizza.fragment.MainFragment;
import com.yleaf.stas.if_citypizza.fragment.PizzaFragment;
import com.yleaf.stas.if_citypizza.fragment.PizzaIFFragment;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by stas on 26.03.18.
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    // Access a Cloud Firestore instance from your Activity
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static Context appContext;
    private AtomicInteger atomicInteger;

    private ProgressBar progressBar;

    private DocumentReference aztecaInfo;
    private DocumentReference pizzaIFInfo;
    private DocumentReference camelotFoodInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        if(savedInstanceState != null) {
            createFragment();
            progressBar = findViewById(R.id.progressBarContainer);
            progressBar.setVisibility(View.GONE);
        } else {
            atomicInteger = new AtomicInteger();
            appContext = getApplicationContext();
            progressBar = findViewById(R.id.progressBarContainer);
            progressBar.setVisibility(View.VISIBLE);

            downloadData();
            Log.i(TAG, "NULL savedInstanceState");
        }

        Log.i(TAG, "MAIN ACTIVITY");
    }

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, MainActivity.class);

    }

    private void createFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if(fragment == null) {
            fragment = new MainFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void downloadData() {
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
            progressBar.setVisibility(View.GONE);
            createFragment();

            // clearDB();
            // addDataToDB();  // Data was written

            listenObjects(Resource.AZTECAINFO, aztecaInfo);
            listenObjects(Resource.PIZZAIFINFO, pizzaIFInfo);
            listenObjects(Resource.CAMELOTFOODINFO, camelotFoodInfo);

            listenCollections(Resource.AZTECA, "weight");
            listenCollections(Resource.PIZZAIF, "diameterLarge");
            listenCollections(Resource.CAMELOTFOOD, "weight");
        }

    }

    private void listenObjects(final String containerName, DocumentReference reference) {
        reference = db.collection(containerName).document("info");
        reference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.wtf(TAG, "Listen failed.", e);
                    return;
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    switch (containerName) {
                        case Resource.AZTECAINFO:
                            DataHolder.getData(getApplicationContext())
                                    .setAztecaInfo(documentSnapshot.toObject(Manufacturer.class));
                            break;
                        case Resource.PIZZAIFINFO:
                            DataHolder.getData(getApplicationContext())
                                    .setPizzaIFInfo(documentSnapshot.toObject(Manufacturer.class));
                            break;
                        case Resource.CAMELOTFOODINFO:
                            DataHolder.getData(getApplicationContext())
                                    .setCamelotFoodInfo(documentSnapshot.toObject(Manufacturer.class));
                            break;
                    }

                    refreshCurrentPizza();

                    Log.w(TAG, "Current data: " + documentSnapshot.getData());
                } else {
                    Log.wtf(TAG, "Current data: NULL");
                }
            }
        });
    }

    private void listenCollections(final String containerName, String nullField) {
        db.collection(containerName)
                .whereEqualTo(nullField, null)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.wtf(TAG, "Listen failed.", e);
                            return;
                        }

                        switch (containerName) {
                            case Resource.AZTECA:
                                DataHolder.getData(getApplicationContext()).getAzteca().clear();
                                DataHolder.getData(getApplicationContext()).getAzteca()
                                        .addAll(value.toObjects(Pizza.class));
                                break;
                            case Resource.PIZZAIF:
                                DataHolder.getData(getApplicationContext()).getPizzaIF().clear();
                                DataHolder.getData(getApplicationContext()).getPizzaIF()
                                        .addAll(value.toObjects(Pizza.class));
                                break;
                            case Resource.CAMELOTFOOD:
                                DataHolder.getData(getApplicationContext()).getCamelotFood().clear();
                                DataHolder.getData(getApplicationContext()).getCamelotFood()
                                        .addAll(value.toObjects(Pizza.class));
                                break;
                        }

                        refreshTabLayout();
                        refreshCurrentPizza();

                        Log.w(TAG, "Current data: " + value.toObjects(Pizza.class));
                    }
                });
    }

    private void refreshTabLayout() {
        MainFragment.mainAdapter.refreshData();
    }

    private void refreshCurrentPizza() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0) {

            Fragment fragment = PizzaFragment.getCurrentFragment();

            switch (PizzaFragment.getFragmentType()) {
                case Resource.AZTECA:
                    ((AztecaFragment)fragment).updateUI();
                    break;
                case Resource.PIZZAIF:
                    ((PizzaIFFragment)fragment).updateUI();
                    break;
                case Resource.CAMELOTFOOD:
                    ((CamelotFoodFragment)fragment).updateUI();
                    break;
            }

        }
    }

    private void clearDB() {
        new AztecaService(appContext).deleteAll();
        new PizzaIFService(appContext).deleteAll();
        new CamelotFoodService(appContext).deleteAll();

        new AztecaInfoService(appContext).deleteAll();
        new PizzaIFInfoService(appContext).deleteAll();
        new CamelotFoodInfoService(appContext).deleteAll();
    }

    private void addDataToDB() {
        AddDataToDB addDataToDB = new AddDataToDB(appContext);
        Thread thread = new Thread(addDataToDB);
        thread.start();
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSearchRequested() {
        Bundle appData = new Bundle();
        appData.putBoolean(SearchableActivity.JARGON, true);
        startSearch(null, false, appData, false);

        Log.wtf(TAG, "onSearchRequested");
        return true;
    }
}
package com.yleaf.stas.if_citypizza.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.yleaf.stas.if_citypizza.DataHolder;
import com.yleaf.stas.if_citypizza.R;
import com.yleaf.stas.if_citypizza.Resource;
import com.yleaf.stas.if_citypizza.activity.LoginActivity;
import com.yleaf.stas.if_citypizza.activity.MainActivity;
import com.yleaf.stas.if_citypizza.adapter.ViewPagerAdapter;


/**
 * Created by stas on 26.03.18.
 */

public class MainFragment extends Fragment {

    private ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
   // private MaterialSearchView searchView;

    public static ViewPagerAdapter mainAdapter;

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

        setHasOptionsMenu(true);

        initWidgets(view);
        doSomething();

        return view;
    }

    private void initWidgets(View view) {
        toolbar = view.findViewById(R.id.main_toolbar);
        viewPager = view.findViewById(R.id.view_pager);
      //  searchView = view.findViewById(R.id.search_view);
        tabLayout = view.findViewById(R.id.tab_layout);
    }

    private void setWidgets() {

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        mainAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        setupViewPager(viewPager, mainAdapter);
        tabLayout.setupWithViewPager(viewPager);//setting tab over viewpager

        /*
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
        }); */
    }

    public static void setupViewPager(ViewPager viewPager, ViewPagerAdapter adapter) {

        adapter.addFrag(PizzaFragment.newInstance(Resource.AZTECA), Resource.AZTECA);
        adapter.addFrag(PizzaFragment.newInstance(Resource.PIZZAIF), Resource.PIZZAIF);
        adapter.addFrag(PizzaFragment.newInstance(Resource.CAMELOTFOOD), Resource.CAMELOTFOOD);

        viewPager.setAdapter(adapter);
    }

    SearchManager searchManager;
    SearchView searchView;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    //    MenuItem item = menu.findItem(R.id.action_search);
    //    searchView.setMenuItem(item);

        // Get the SearchView and set the searchable configuration
        searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      //  FragmentManager fm = getFragmentManager();

        switch (item.getItemId()) {
            case R.id.action_search:
             //   searchView.setMenuItem(item);
                // Assumes current activity is the searchable activity
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
                searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
                ((MainActivity)getActivity()).onSearchRequested();
                return true;

            case R.id.action_cart:
                return true;

            case R.id.action_out:
              logOut();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logOut() {
        getActivity().finish();
        startActivity(LoginActivity.newIntent(getContext()));

        // FireBaseAuth sign out
        FirebaseAuth.getInstance().signOut();
    }
}

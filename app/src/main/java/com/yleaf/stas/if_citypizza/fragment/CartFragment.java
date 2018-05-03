package com.yleaf.stas.if_citypizza.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yleaf.stas.if_citypizza.R;

public class CartFragment extends Fragment {

    private static final String TAG = CartFragment.class.getSimpleName();
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_fragment_layout, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    public static CartFragment newInstance() {

        Bundle args = new Bundle();

        CartFragment fragment = new CartFragment();
        fragment.setArguments(args);
        return fragment;
    }
}

package com.yleaf.stas.if_citypizza.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yleaf.stas.if_citypizza.Pizza;
import com.yleaf.stas.if_citypizza.R;

/**
 * Created by stas on 27.03.18.
 */

public class PizzaFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private PizzaAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_layout, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new PizzaAdapter();
     //   mRecyclerView.setAdapter(adapter);

        return view;
    }

    private class PizzaHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleItem;
        private TextView mMakerItem;
        private ImageButton mImageButton;

        private Pizza pizza;

        public PizzaHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleItem = itemView.findViewById(R.id.title_item);
            mMakerItem = itemView.findViewById(R.id.maker_item);
            mImageButton = itemView.findViewById(R.id.image_view_item);
        }

        @Override
        public void onClick(View v) {

        }


    }


    private class PizzaAdapter extends RecyclerView.Adapter<PizzaFragment.PizzaHolder> {
        @Override
        public PizzaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(PizzaHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

    public static PizzaFragment newInstance() {

        Bundle args = new Bundle();

        PizzaFragment fragment = new PizzaFragment();
        fragment.setArguments(args);
        return fragment;
    }
}

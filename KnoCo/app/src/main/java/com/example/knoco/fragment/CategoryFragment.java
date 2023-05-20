package com.example.knoco.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.knoco.R;
import com.example.knoco.adapter.CategoryAdapter;
import com.example.knoco.databinding.ActivityMainBinding;
import com.example.knoco.model.Category;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {


    private List<Category> mlist ;
    private GridView categoryGrid ;
    private MaterialToolbar toolbar ;

    private RecyclerView rcvCate ;
    ActivityMainBinding binding ;

    // TODO: Rename and change types and number of parameters

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mlist = new ArrayList<>();
        dataInitialize();

        rcvCate = view.findViewById(R.id.rcvCate);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),4);

        rcvCate.setLayoutManager(gridLayoutManager);
        rcvCate.setFocusable(false);
        rcvCate.setNestedScrollingEnabled(false);

        CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(),mlist);
        rcvCate.setAdapter(categoryAdapter);
    }

    private void dataInitialize() {
        mlist.add(new Category(R.drawable.love,"Romance"));
        mlist.add(new Category(R.drawable.actionthme,"Action"));
        mlist.add(new Category(R.drawable.war,"Philosophy"));
        mlist.add(new Category(R.drawable.van,"Literature"));
        mlist.add(new Category(R.drawable.foucalut,"Adventure"));
        mlist.add(new Category(R.drawable.foucalut,"Historical"));
        mlist.add(new Category(R.drawable.foucalut,"Inspiration"));
        mlist.add(new Category(R.drawable.foucalut,"Short stories"));

    }
}
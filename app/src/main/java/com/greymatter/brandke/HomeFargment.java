package com.greymatter.brandke;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greymatter.brandke.Adapter.CategoryAdapter;
import com.greymatter.brandke.Adapter.CategoryAdapterHomeFrag;
import com.greymatter.brandke.Adapter.RecyclerOnClickListener;
import com.greymatter.brandke.Models.Category;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFargment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFargment extends Fragment implements RecyclerOnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFargment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFargment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFargment newInstance(String param1, String param2) {
        HomeFargment fragment = new HomeFargment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    private RecyclerView recyclerView;
    private CategoryAdapterHomeFrag categoryAdapterHomeFrag;
    private ArrayList<Category> categoryArrayList;
    private RecyclerView.LayoutManager layoutManager;
    private LinearLayoutManager linearLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_fargment, container, false);
        recyclerView = view.findViewById(R.id.categoryRecycleView);
        buildRecyclerView();
        layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(),2));
        return view;
    }

    private void buildRecyclerView() {
        categoryArrayList = new ArrayList<>();
        categoryArrayList.add(new Category(R.drawable.product_img,"somex"));
        categoryArrayList.add(new Category(R.drawable.product_img,"somey"));
        categoryArrayList.add(new Category(R.drawable.product_img,"somez"));
        categoryArrayList.add(new Category(R.drawable.product_img,"some_alpha"));
        categoryAdapterHomeFrag = new CategoryAdapterHomeFrag(categoryArrayList,requireContext(),this);
        recyclerView.setAdapter(categoryAdapterHomeFrag);
    }

    @Override
    public void onRecyclerItemClick(int position) {
    }

    @Override
    public void onRecyclerItemClick(int position, int Size) {
        switch (position) {
            case 1:
                startActivity(new Intent(requireActivity(),CategoryActivity.class));
                break;
        }
    }
}
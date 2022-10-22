package com.greymatter.brandke;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greymatter.brandke.Adapter.CategoryListAdapter;
import com.greymatter.brandke.Models.Categorylist;

import java.util.ArrayList;


public class HomeFargment extends Fragment  {


    RecyclerView recyclerView;
    CategoryListAdapter categoryListAdapter;


    public HomeFargment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home_fargment, container, false);


        recyclerView = root.findViewById(R.id.categoryRecycleView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(gridLayoutManager);


         catgorylist();

        return root;
    }

    private void catgorylist() {

        ArrayList<Categorylist> categorylists = new ArrayList<>();

        Categorylist categorylist1 = new Categorylist("","Fruits","");
        Categorylist categorylist2 = new Categorylist("","Fruits","");
        Categorylist categorylist3 = new Categorylist("","Fruits","");
        Categorylist categorylist4 = new Categorylist("","Fruits","");

        categorylists.add(categorylist1);
        categorylists.add(categorylist2);
        categorylists.add(categorylist3);
        categorylists.add(categorylist4);



        categoryListAdapter = new CategoryListAdapter(getActivity(), categorylists);
        recyclerView.setAdapter(categoryListAdapter);




    }


}
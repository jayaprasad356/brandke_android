package com.greymatter.brandke;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.greymatter.brandke.Adapter.CategoryListAdapter;
import com.greymatter.brandke.Adapter.SliderAdapterExample;
import com.greymatter.brandke.Models.Categorylist;
import com.greymatter.brandke.Models.Slide;
import com.greymatter.brandke.helper.ApiConfig;
import com.greymatter.brandke.helper.Constant;
import com.greymatter.brandke.helper.Session;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class HomeFragment extends Fragment  {


    RecyclerView recyclerView;
    CategoryListAdapter categoryListAdapter;
    View root;
    TextView view_txt;
    SliderView sliderView;
    Activity activity;
    Session session;
    private SliderAdapterExample adapter;
    TextView tvTitle,tvCartCount;
    ImageView imgCart;
    EditText etSearch;

    public HomeFragment() {
        // Required empty public constructor
    }





    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home_fragment, container, false);

        activity = getActivity();
        adapter = new SliderAdapterExample(activity);
        session = new Session(activity);

        recyclerView = root.findViewById(R.id.categoryRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));

        view_txt = root.findViewById(R.id.view_all);
        imgCart = root.findViewById(R.id.imgcart);
        etSearch = root.findViewById(R.id.etSearch);
        etSearch = root.findViewById(R.id.etSearch);
      //  tvTitle = root.findViewById(R.id.tvTitle);

        sliderView = root.findViewById(R.id.image_slider);
      //  tvTitle.setText("Hi, "+session.getData(Constant.NAME));

        catgorylist();

        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),CartActivity.class);
                startActivity(intent);

            }
        });
        view_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CategoryActivity.class);
                intent.putExtra(Constant.CATEGORY_ID,"all");
                intent.putExtra(Constant.PRODUCT_NAME,"ALL Products");
                startActivity(intent);
//                Intent intent = new Intent(getActivity(),CategoryListActivity.class);
//                startActivity(intent);
            }
        });
        etSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent intent = new Intent(activity, SearchActivity.class);
                startActivity(intent);
                return false;
            }
        });

       slideslist();
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
            }
        });
        return root;
    }

    private void slideslist() {
        ArrayList<com.greymatter.brandke.Models.Slide> slides = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        ApiConfig.RequestToVolley((result, response) -> {

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            if (jsonObject1 != null) {
                                com.greymatter.brandke.Models.Slide group = g.fromJson(jsonObject1.toString(), Slide.class);
                                slides.add(group);
                            } else {
                                break;
                            }
                        }
                        adapter.renewItems(slides);
                    } else {
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.SLIDES_LIST, params, true);
    }

    private void catgorylist() {

        Map<String, String> params = new HashMap<>();
        ApiConfig.RequestToVolley((result, response) -> {

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();
                        ArrayList<Categorylist> categories = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            if (jsonObject1 != null) {
                                Categorylist group = g.fromJson(jsonObject1.toString(), Categorylist.class);
                                categories.add(group);
                            } else {
                                break;
                            }
                        }
                        categoryListAdapter = new CategoryListAdapter(getActivity(), categories);
                        recyclerView.setAdapter(categoryListAdapter);



                    } else {
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.CATEGORY_LIST_URL, params, true);




    }


}
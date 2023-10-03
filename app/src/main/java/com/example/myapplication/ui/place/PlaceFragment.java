package com.example.myapplication.ui.place;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.WeatherActivity;
import com.example.myapplication.logic.model.Place;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PlaceFragment extends Fragment {

    PlaceViewModel placeViewModel;
    RecyclerView recyclerViewZtm;
    PlaceAdapter placeAdapter;
    ImageView imageView;


    EditText inputAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewZtm);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_city, container, false);
        recyclerViewZtm = view.findViewById(R.id.recyclerViewZtm);
        inputAdd = view.findViewById(R.id.inputAdd);
        imageView = view.findViewById(R.id.image1);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyApplication.getContext());
        recyclerViewZtm.setLayoutManager(linearLayoutManager);
        placeViewModel = new ViewModelProvider(this).get(PlaceViewModel.class);
        placeAdapter = new PlaceAdapter(this,placeViewModel.placeArrayList,placeViewModel);
        recyclerViewZtm.setAdapter(placeAdapter);
        FragmentActivity activity = this.requireActivity();
        if (activity instanceof MainActivity && placeViewModel.existedPlace("saved_place")) {
            Place place = placeViewModel.GetPlace("saved_place");
            Intent intent = new Intent(MyApplication.getContext(), WeatherActivity.class);
            intent.putExtra("location_lng",place.getLocation().getLng());
            intent.putExtra("location_lat",place.getLocation().getLat());
            intent.putExtra("place_name",place.getName());
            this.startActivity(intent);
            this.getActivity().finish();
        }
        inputAdd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String content = editable.toString();
                if(StringUtils.isNotEmpty(content)) {
                    placeViewModel.searchPlaces(content.trim());
                }else{
                    recyclerViewZtm.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                    placeViewModel.placeArrayList.clear();
                    placeAdapter.notifyDataSetChanged();
                }
            }
        }) ;
        placeViewModel.postalCode.observe(this.getViewLifecycleOwner(), new Observer<ArrayList<Place>>() {
            @Override
            public void onChanged(ArrayList<Place> places) {
                if (places != null) {
                    recyclerViewZtm.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.GONE);
                    placeViewModel.placeArrayList.clear();
                    placeViewModel.placeArrayList.addAll(places);
                    placeAdapter.notifyDataSetChanged();
                }else {
                    Toast toast= Toast.makeText(MyApplication.getContext(), "未能查询到结果", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}
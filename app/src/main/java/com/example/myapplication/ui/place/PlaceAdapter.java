package com.example.myapplication.ui.place;



import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.WeatherActivity;
import com.example.myapplication.logic.model.Place;

import java.util.ArrayList;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder>{

    private  Fragment fragment;
    private ArrayList<Place> placeArrayList;

    private  PlaceViewModel placeViewModel;
    public  PlaceAdapter(Fragment fragment, ArrayList<Place> placeArrayList,PlaceViewModel placeViewModel) {
        this.fragment = fragment;
        this.placeArrayList =placeArrayList;
        this.placeViewModel = placeViewModel;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView quname;
        TextView quadress;
        public ViewHolder(View itemView) {
            super(itemView);
            quname = (TextView) itemView.findViewById(R.id.quname);
            quadress=(TextView) itemView.findViewById(R.id.quadress);
        }
    }

    @NonNull
    @Override
    public PlaceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View postView = inflater.inflate(R.layout.city_item, parent, false);

        PlaceAdapter.ViewHolder viewHolder = new ViewHolder(postView);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = fragment.requireActivity();
                Place place = placeArrayList.get(viewHolder.getAdapterPosition());

                if (activity instanceof  WeatherActivity) {
                    ((WeatherActivity) activity).drawerLayout.closeDrawers();
                    ((WeatherActivity) activity).weatherViewModel.placeName =place.getName();
                    //((WeatherActivity) activity).weatherViewModel.lat =place.getLocation().getLat();
                    //((WeatherActivity) activity).weatherViewModel.lng = place.getLocation().getLng();
                    ((WeatherActivity) activity).refreshWeather(place.getLocation().getLat(),place.getLocation().getLng());
                } else {
                    placeViewModel.SavePlace(place,"saved_place");
                    Intent intent = new Intent(MyApplication.getContext(), WeatherActivity.class);
                    intent.putExtra("location_lng",place.getLocation().getLng());
                    intent.putExtra("location_lat",place.getLocation().getLat());
                    intent.putExtra("place_name",place.getName());
                    fragment.startActivity(intent);
                    fragment.getActivity().finish();
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceAdapter.ViewHolder holder, int position) {
        Place place = placeArrayList.get(position);
        holder.quname.setText(place.getName());
        holder.quadress.setText(place.getFormatted_address());
    }

    @Override
    public int getItemCount() {
        return placeArrayList.size();
    }
}

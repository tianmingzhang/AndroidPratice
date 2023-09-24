package com.example.myapplication.ui.place;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.logic.model.Place;

import java.util.ArrayList;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder>{

    private  Fragment fragment;
    private ArrayList<Place> placeArrayList;
    public  PlaceAdapter(Fragment fragment, ArrayList<Place> placeArrayList) {
        this.fragment = fragment;
        this.placeArrayList =placeArrayList;
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

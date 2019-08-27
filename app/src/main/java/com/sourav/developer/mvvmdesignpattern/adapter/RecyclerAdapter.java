package com.sourav.developer.mvvmdesignpattern.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sourav.developer.mvvmdesignpattern.R;
import com.sourav.developer.mvvmdesignpattern.databinding.ItemListBinding;
import com.sourav.developer.mvvmdesignpattern.models.NicePlace;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    List<NicePlace> places;
    Context context;

    public RecyclerAdapter(Context context) {
        this.context = context;
        places = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.layout_listitem,
                parent,
                false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NicePlace place = places.get(position);
        holder.binding.imageName.setText(place.getTitle());

        Glide.with(context)
                .setDefaultRequestOptions(new RequestOptions().error(R.drawable.ic_person))
                .load(place.getImageUrl())
                .into(holder.binding.circleImage);
    }

    public void setPlaces(List<NicePlace> places){
        this.places = places;
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemListBinding binding;
        public ViewHolder(@NonNull ItemListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.executePendingBindings();
        }
    }
}

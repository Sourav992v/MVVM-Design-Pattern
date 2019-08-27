package com.sourav.developer.mvvmdesignpattern;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.sourav.developer.mvvmdesignpattern.adapter.RecyclerAdapter;
import com.sourav.developer.mvvmdesignpattern.databinding.MainActivityBinding;
import com.sourav.developer.mvvmdesignpattern.models.NicePlace;
import com.sourav.developer.mvvmdesignpattern.viewmodels.MainActivityViewModel;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MainActivityBinding activityBinding;
    RecyclerAdapter mAdapter;
    MainActivityViewModel activityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        activityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        activityViewModel.init();
        activityViewModel.getNicePlaces().observe(this, new Observer<List<NicePlace>>() {
            @Override
            public void onChanged(List<NicePlace> nicePlaces) {
                mAdapter.notifyDataSetChanged();
            }
        });
        activityViewModel.getIsUpdating().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    showProgressBar();
                }else {
                    hideProgressBar();
                    activityBinding.recycler.smoothScrollToPosition(activityViewModel.getNicePlaces().getValue().size()-1);
                }
            }
        });
        activityBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityViewModel.addNewValue(
                        new NicePlace(
                                "https://i.redd.it/qn7f9oqu7o501.jpg",
                                "Washington"
                        )
                );
            }
        });

        initRecyclerView();
    }

    private void initRecyclerView() {
        activityBinding.recycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecyclerAdapter(this);
        activityBinding.recycler.setAdapter(mAdapter);
        mAdapter.setPlaces(activityViewModel.getNicePlaces().getValue());
        activityBinding.recycler.setHasFixedSize(true);
    }

    private void showProgressBar(){
        activityBinding.progressbar.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar(){
        activityBinding.progressbar.setVisibility(View.GONE);
    }
}

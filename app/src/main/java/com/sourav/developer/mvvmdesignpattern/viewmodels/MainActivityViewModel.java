package com.sourav.developer.mvvmdesignpattern.viewmodels;


import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sourav.developer.mvvmdesignpattern.models.NicePlace;
import com.sourav.developer.mvvmdesignpattern.repository.NicePlaceRepository;

import java.util.List;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<List<NicePlace>> mNicePlace;
    private NicePlaceRepository mRepo;
    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();
    public void init(){
        if (mNicePlace != null){
            return;
        }
        mRepo = NicePlaceRepository.getInstance();
        mNicePlace = mRepo.getNicePlaces();
    }

    public void addNewValue(final NicePlace place){
        mIsUpdating.setValue(true);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                List<NicePlace> currentPlaces = mNicePlace.getValue();
                currentPlaces.add(place);
                mNicePlace.postValue(currentPlaces);
                mIsUpdating.setValue(false);
            }
        }.execute();
    }
    public LiveData<List<NicePlace>> getNicePlaces(){
        return mNicePlace;
    }
    public LiveData<Boolean> getIsUpdating(){
        return mIsUpdating;
    }
}

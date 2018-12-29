package com.example.ricoramars.bucketlistportofolio;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class CheckListViewModel extends AndroidViewModel {
    private CheckListRepository repository;
    private LiveData<List<CheckListItem>> allCheckListItems;


    public CheckListViewModel(@NonNull Application application) {
        super(application);
        repository = new CheckListRepository(application);
        allCheckListItems = repository.getAllNotes();
    }

    public void insert(CheckListItem checkListItem){
        repository.insert(checkListItem);
    }

    public void update(CheckListItem checkListItem){
        repository.update(checkListItem);
    }

    public void delete(CheckListItem checkListItem){
        repository.delete(checkListItem);
    }

    public LiveData<List<CheckListItem>> getAllCheckListItems() {
        return allCheckListItems;
    }
}

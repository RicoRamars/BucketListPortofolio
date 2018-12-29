package com.example.ricoramars.bucketlistportofolio;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CheckListDao {

    @Insert
    void insert(CheckListItem checkListItem);

    @Update
    void update(CheckListItem checkListItem);

    @Delete
    void delete(CheckListItem checkListItem);

    @Query("SELECT * FROM checklist_table")
    LiveData<List<CheckListItem>> getAllCheckListItems();
}

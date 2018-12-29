package com.example.ricoramars.bucketlistportofolio;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Checklist_table")
public class CheckListItem {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private Boolean completed;

    private String  name;

    private String  description;

    public CheckListItem(Boolean completed, String name, String description) {
        this.completed = completed;
        this.name = name;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

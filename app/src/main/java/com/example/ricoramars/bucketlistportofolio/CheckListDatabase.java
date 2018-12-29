package com.example.ricoramars.bucketlistportofolio;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = CheckListItem.class, version = 1)
public abstract class CheckListDatabase extends RoomDatabase {

    private static CheckListDatabase instance;

    public abstract CheckListDao checkListDao();

    public static synchronized CheckListDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CheckListDatabase.class, "Checklist_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static final class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private CheckListDao checkListDao;

        private PopulateDbAsyncTask(CheckListDatabase db) {
            checkListDao = db.checkListDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            checkListDao.insert(new CheckListItem(true, "Aap", "Twee stuks"));
            checkListDao.insert(new CheckListItem(false, "Kat", "Drie stuks"));
            checkListDao.insert(new CheckListItem(true, "Aap", "Tweehonderd stuks"));
            return null;
        }
    }
}

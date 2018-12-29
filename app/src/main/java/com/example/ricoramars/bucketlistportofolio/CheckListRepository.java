package com.example.ricoramars.bucketlistportofolio;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class CheckListRepository {
    private CheckListDao checkListDao;
    private LiveData<List<CheckListItem>> allCheckListItems;

    public CheckListRepository (Application application){
        CheckListDatabase database = CheckListDatabase.getInstance(application);
        checkListDao = database.checkListDao();
        allCheckListItems = checkListDao.getAllCheckListItems();
    }

    public void insert(CheckListItem checkListItem) {
        new InsertCheckListItemAsyncTask(checkListDao).execute(checkListItem);
    }

    public void update(CheckListItem checkListItem) {
        new UpdateCheckListItemAsyncTask(checkListDao).execute(checkListItem);
    }

    public void delete(CheckListItem checkListItem) {
        new DeleteCheckListItemAsyncTask(checkListDao).execute(checkListItem);
    }

    public LiveData<List<CheckListItem>> getAllNotes() {
        return allCheckListItems;
    }

    private static class InsertCheckListItemAsyncTask extends AsyncTask<CheckListItem, Void, Void> {
        private CheckListDao checkListDao;

        private InsertCheckListItemAsyncTask(CheckListDao checkListDao) {
            this.checkListDao = checkListDao;
        }

        @Override
        protected Void doInBackground(CheckListItem... checkListItems) {
            checkListDao.insert(checkListItems[0]);
            return null;
        }
    }

    private static class UpdateCheckListItemAsyncTask extends AsyncTask<CheckListItem, Void, Void> {
        private CheckListDao checkListDao;

        private UpdateCheckListItemAsyncTask(CheckListDao checkListDao) {
            this.checkListDao = checkListDao;
        }

        @Override
        protected Void doInBackground(CheckListItem... checkListItems) {
            checkListDao.update(checkListItems[0]);
            return null;
        }
    }

    private static class DeleteCheckListItemAsyncTask extends AsyncTask<CheckListItem, Void, Void> {
        private CheckListDao checkListDao;

        private DeleteCheckListItemAsyncTask(CheckListDao checkListDao) {
            this.checkListDao = checkListDao;
        }

        @Override
        protected Void doInBackground(CheckListItem... checkListItems) {
            checkListDao.delete(checkListItems[0]);
            return null;
        }
    }
}

package com.example.ricoramars.bucketlistportofolio;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_CHECKLIST_REQUEST = 1;
    public static final int EDIT_CHECKLIST_REQUEST = 2;

    private CheckListViewModel checkListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddCheckList = findViewById(R.id.button_add_checklist);
        buttonAddCheckList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewCheckList.class);
                startActivityForResult(intent, ADD_CHECKLIST_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final CheckListAdapter adapter = new CheckListAdapter();
        recyclerView.setAdapter(adapter);

        checkListViewModel = ViewModelProviders.of(this).get(CheckListViewModel.class);
        checkListViewModel.getAllCheckListItems().observe(this, new Observer<List<CheckListItem>>() {
            @Override
            public void onChanged(@Nullable List<CheckListItem> checkListItems) {
                adapter.setCheckListItems(checkListItems);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                checkListViewModel.delete(adapter.getCheckListItemAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Bucket Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new CheckListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CheckListItem checkListItem) {
                Intent intent = new Intent(MainActivity.this, AddNewCheckList.class);
                intent.putExtra(AddNewCheckList.EXTRA_ID, checkListItem.getId());
                intent.putExtra(AddNewCheckList.EXTRA_NAME, checkListItem.getName());
                intent.putExtra(AddNewCheckList.EXTRA_CHECKBOX, checkListItem.getCompleted());
                intent.putExtra(AddNewCheckList.EXTRA_DESCRIPTION, checkListItem.getDescription());
                startActivityForResult(intent, EDIT_CHECKLIST_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_CHECKLIST_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddNewCheckList.EXTRA_NAME);
            String description = data.getStringExtra(AddNewCheckList.EXTRA_DESCRIPTION);
            boolean completed = data.getBooleanExtra(AddNewCheckList.EXTRA_CHECKBOX, true);

            CheckListItem checkListItem = new CheckListItem(completed, name, description);
            checkListViewModel.insert(checkListItem);
            Toast.makeText(this, "Bucket Saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_CHECKLIST_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddNewCheckList.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Bucket can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = data.getStringExtra(AddNewCheckList.EXTRA_NAME);
            String description = data.getStringExtra(AddNewCheckList.EXTRA_DESCRIPTION);
            boolean completed = data.getBooleanExtra(AddNewCheckList.EXTRA_CHECKBOX, true);

            CheckListItem checkListItem = new CheckListItem(completed, name, description);
            checkListItem.setId(id);
            checkListViewModel.update(checkListItem);
            Toast.makeText(this, "Bucket updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Bucket not saved", Toast.LENGTH_SHORT).show();
        }
    }
}

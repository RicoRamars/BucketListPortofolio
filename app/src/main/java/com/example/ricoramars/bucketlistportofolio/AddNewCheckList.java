package com.example.ricoramars.bucketlistportofolio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewCheckList extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.example.ricoramars.bucketlistportofolio.EXTRA_ID";
    public static final String EXTRA_NAME =
            "com.example.ricoramars.bucketlistportofolio.EXTRA_NAME";
    public static final String EXTRA_DESCRIPTION =
            "com.example.ricoramars.bucketlistportofolio.EXTRA_DESCRIPTION";
    public static final String EXTRA_CHECKBOX =
            "com.example.ricoramars.bucketlistportofolio.EXTRA_CHECKBOX";

    private EditText editTextName;
    private EditText editTextDescription;
    private CheckBox checkBoxCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_check_list);

        editTextName = findViewById(R.id.edit_text_name);
        editTextDescription = findViewById(R.id.edit_text_description);
        checkBoxCompleted = findViewById(R.id.edit_checkbox);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Edit bucket");
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            editTextName.setText(intent.getStringExtra(EXTRA_NAME));
            checkBoxCompleted.setChecked(intent.getBooleanExtra(EXTRA_CHECKBOX, true));
        } else {
            setTitle("Add bucket");
        }
    }

    private void saveItem() {
        String name = editTextName.getText().toString();
        String description = editTextDescription.getText().toString();
        Boolean completed = checkBoxCompleted.isChecked();

        if (name.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Name or description is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_CHECKBOX, completed);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_checklist_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_item:
                saveItem();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

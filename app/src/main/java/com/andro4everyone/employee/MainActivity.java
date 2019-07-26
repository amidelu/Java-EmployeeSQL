package com.andro4everyone.employee;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etName, etAge, etDepartment, etDesignation;
    Button btnSave;

    //Reference creation to our custom db class
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        etName = findViewById(R.id.etNameId);
        etAge = findViewById(R.id.etAgeId);
        etDepartment = findViewById(R.id.etDepartId);
        etDesignation = findViewById(R.id.etDesignId);
        btnSave = findViewById(R.id.btnSaveId);

        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String name = etName.getText().toString();
        String age = etAge.getText().toString();
        String department = etDepartment.getText().toString();
        String designation = etDesignation.getText().toString();

        if (view.getId() == R.id.btnSaveId) {
            long rowId = databaseHelper.insertData(name, age, department, designation);

            //-1 will indicate that no row is inserted in our db
            if (rowId == -1) {
                Toast.makeText(getApplicationContext(), "Row isn't inserted successfully", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getApplicationContext(), "Row is inserted successfully", Toast.LENGTH_LONG).show();
            }
        }

    }
}

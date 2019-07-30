package com.andro4everyone.employee;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etId, etName, etAge, etDepartment, etDesignation;
    Button btnSave, btnView, btnUpdate, btnDelete;

    //Reference creation to our custom db class
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        etId = findViewById(R.id.etId);
        etName = findViewById(R.id.etNameId);
        etAge = findViewById(R.id.etAgeId);
        etDepartment = findViewById(R.id.etDepartId);
        etDesignation = findViewById(R.id.etDesignId);

        btnSave = findViewById(R.id.btnSaveId);
        btnView = findViewById(R.id.btnViewId);
        btnUpdate = findViewById(R.id.btnUpdateId);
        btnDelete = findViewById(R.id.btnDeleteId);

        btnSave.setOnClickListener(this);
        btnView.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String id = etId.getText().toString();
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

        //View data code goes here
        if (view.getId()==R.id.btnViewId) {
            Cursor cursor = databaseHelper.viewAllData();

            if (cursor.getCount() == 0 ) {
                viewData("Error", "Data Not Found");
            }

            StringBuilder stringBuilder = new StringBuilder();
            while (cursor.moveToNext()) {
                stringBuilder.append("ID: "+cursor.getString(0)+"\n")
                        .append("Name: "+cursor.getString(1)+"\n")
                        .append("Age: "+cursor.getString(2)+"\n")
                        .append("Department: "+cursor.getString(3)+"\n")
                        .append("Designation: "+cursor.getString(4)+"\n\n");
            }
            viewData("ResultSet", stringBuilder.toString());

        } else if (view.getId()==R.id.btnUpdateId) {
            boolean checkUpdate = databaseHelper.updateData(id, name, age, department, designation);

            if (checkUpdate) {
                Toast.makeText(getApplicationContext(), "Row is updated successfully", Toast.LENGTH_LONG).show();

            }else {
                Toast.makeText(getApplicationContext(), "Row isn't updated successfully", Toast.LENGTH_LONG).show();
            }
        }
    }

    //This method set the alert dialogue builder
    public void viewData (String title, String data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(data);
        builder.setCancelable(true);
        builder.show();
    }
}

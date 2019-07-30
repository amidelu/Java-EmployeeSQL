package com.andro4everyone.employee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {
    //Database variables created with all caps
    private static final String DATABASE_NAME = "employee.db";
    private static final String TABLE_NAME = "Employee";
    private static final String ID = "_id";
    private static final String NAME = "Name";
    private static final String AGE = "Age";
    private static final String DEPARTMENT = "Department";
    private static final String DESIGNATION = "Designation";
    private static final Integer VERSION_NO = 1;

    //Create table variable for Database create command
    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR (50) NOT NULL, " +
            ""+AGE+" INTEGER NOT NULL, "+DEPARTMENT+" VARCHAR (80) NOT NULL, "+DESIGNATION+" VARCHAR NOT NULL)";

    //Drop table variable if table already exist
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " +TABLE_NAME;
    private static final String VIEWALL_DATA = "SELECT * FROM "+TABLE_NAME;

    private Context context;

    //Default constructor and methods created after extending SQLiteOpenHelper
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NO);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(CREATE_TABLE);
            Toast.makeText(context, "On Create is Called", Toast.LENGTH_LONG).show();

        }catch (Exception e) {
            Toast.makeText(context, "Execption"+e, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try {
            sqLiteDatabase.execSQL(DROP_TABLE);
            Toast.makeText(context, "On Upgrade is Called", Toast.LENGTH_LONG).show();
        }catch (Exception e) {
            Toast.makeText(context, "Exception"+e, Toast.LENGTH_LONG).show();
        }
    }

    //This method will collect data from Main Activity save button and put it to DB
    public long insertData (String name, String age, String department, String designation) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(NAME, name);
        contentValues.put(AGE, age);
        contentValues.put(DEPARTMENT, department);
        contentValues.put(DESIGNATION, designation);

        long rowId = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return rowId;
    }

    //This method will show data in alert dialogue box
    public Cursor viewAllData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(VIEWALL_DATA, null);
        return cursor;
    }

    public boolean updateData(String id, String name, String age, String department, String designation) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ID, id);
        contentValues.put(NAME, name);
        contentValues.put(AGE, age);
        contentValues.put(DEPARTMENT, department);
        contentValues.put(DESIGNATION, designation);
        sqLiteDatabase.update(TABLE_NAME, contentValues, ID+" = ?", new String[]{id});
        return true;
    }
}

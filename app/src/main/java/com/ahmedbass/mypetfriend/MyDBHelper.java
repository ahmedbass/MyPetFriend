package com.ahmedbass.mypetfriend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MyDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyPetFriendDB.db";
    public static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;
    private Context context;

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // this method creates a new database if the required database is not present (by copying the ready database i put in the assets folder)
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override // this method is called when the database needs to be upgraded
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
        //In this example, for simplicity you simply drop the existing table and create a new one.
        //In real-life, you usually back up your existing table and then copy them over to the new table
    }

    //---open the database---
    public MyDBHelper open() throws SQLException{
        db = getWritableDatabase();
        return this;
    }

    //---close the database---
    public void close() {
        super.close();
    }

    //---insert a record into the database---
    public long insetRecord(String table, String[] columnNames, Object[] columnValues) {
        int i = (table.trim().equals(MyPetFriendContract.UsersEntry.TABLE_NAME) ? 0 : 1);
        //using a ContentValues object to store key/value pairs
        ContentValues values = new ContentValues();
        for (; i < columnNames.length; i++) {
            try {
                if (columnValues[i] instanceof String) {
                    values.put(columnNames[i], columnValues[i].toString());
                } else if (columnValues[i] instanceof Integer) {
                    values.put(columnNames[i], ((int) columnValues[i]));
                } else if (columnValues[i] instanceof Long) {
                    values.put(columnNames[i], ((long) columnValues[i]));
                } else if (columnValues[i] instanceof Double) {
                    values.put(columnNames[i], ((Double) columnValues[i]));
                } else if (columnValues[i] instanceof Boolean) {
                    values.put(columnNames[i], ((boolean) columnValues[i]));
                } else if (columnValues[i] instanceof Byte[] || columnValues[i].getClass().getName().equals("[B")) {
                    values.put(columnNames[i], ((byte[]) columnValues[i]));
                } else if (columnValues[i] instanceof Byte) {
                    values.put(columnNames[i], ((byte) columnValues[i]));
                }
            }
            catch (Exception e) {
                Toast.makeText(context, "Error: " + columnValues[i].toString(), Toast.LENGTH_SHORT).show();
            }
        }
        return db.insert(table, null, values);
    }

    //---update a record---
    public int updateRecord(String table, String[] columnsNames, Object[] columnsValues, String selection, String selectionArg) {
        int i = (table.trim().equals(MyPetFriendContract.UsersEntry.TABLE_NAME) ? 0 : 1);
        ContentValues values = new ContentValues();
        for(; i < columnsNames.length; i++) {
            if(columnsValues[i] instanceof String) {
                values.put(columnsNames[i], columnsValues[i].toString());
            } else if (columnsValues[i] instanceof Integer) {
                values.put(columnsNames[i], ((int) columnsValues[i]));
            } else if (columnsValues[i] instanceof Long) {
                values.put(columnsNames[i], ((long) columnsValues[i]));
            } else if (columnsValues[i] instanceof Double) {
                values.put(columnsNames[i], ((Double) columnsValues[i]));
            } else if (columnsValues[i] instanceof Boolean) {
                values.put(columnsNames[i], ((boolean) columnsValues[i]));
            } else if (columnsValues[i] instanceof Byte) {
                values.put(columnsNames[i], ((byte) columnsValues[i]));
            } else if (columnsValues[i] instanceof Byte[]) {
                values.put(columnsNames[i], ((byte[]) columnsValues[i]));
            }
//            Toast.makeText(context, values.getAsString(columnsNames[i]), Toast.LENGTH_SHORT).show();
        }
        return db.update(table, values, selection + "=?" , new String[]{selectionArg});
    }

    //---delete a particular record---
    public boolean deleteRecord(String table, String selection, String selectionArg) {
        return db.delete(table, selection + "=?", new String[]{selectionArg}) > 0;
    }

    //---retrieve all the records---
    public Cursor getAllRecords(String table, String[] columns){
        return db.query(table, columns, null, null, null, null, null);
    }

    //---retrieve a particular record(s)---
    public Cursor getRecord(String table, String[] columns, String[] selection, String[] selectionArg) {
        String selectionString = "";
        if(selection != null) {
            for (int i = 0; i < selection.length; i++) {
                selectionString += selection[i] + "=?";
                if (i < selection.length - 1) {
                    selectionString += " and ";
                }
            }
        }
        return db.query(true, table, columns, selectionString, selectionArg, null, null, null, null);
    }

    public String[] getColumnNames(String table) {
        Cursor cursor = db.query(table, null, null, null, null, null, null);
        String[] columnNames = cursor.getColumnNames();
        cursor.close();
        return columnNames;
    }

    public int[] getColumnTypes(String table) {
        Cursor cursor = db.query(table, null, null, null, null, null, null);
        int[] columnTypes = new int[cursor.getColumnCount()];
        for(int i = 0 ; i < cursor.getColumnCount(); i++) {
           columnTypes[i] = cursor.getType(i);
        }
        cursor.close();
        return columnTypes;
    }

    //---copy database file from the project(in assets folder) to android device---
    public static void copyDB(Context context, String destPath) throws IOException {

        // get destination path(passed), to copy database to it using OutputStream, by reading my pre-made database in assets using InputStream
        InputStream inputStream = context.getAssets().open(DATABASE_NAME);
        OutputStream outputStream = new FileOutputStream(destPath);

        //---copy 1K bytes at a time---
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }
}

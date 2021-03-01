package com.example.admin.mytripcart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 1/9/2016.
 */
public class PostpaidHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "operatorManager";

    // Contacts table name
    private static final String POST_OPERATOR = "operator";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_OPERATOR = "operator";
    private static final String KEY_OPCODE = "opcode";

    public PostpaidHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + POST_OPERATOR + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_OPERATOR + " TEXT,"
                + KEY_OPCODE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + POST_OPERATOR);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addPostOperator(PostOperator operator) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_OPERATOR, operator.getOperator()); // Contact Name
        values.put(KEY_OPCODE, operator.getOpcode()); // Contact Phone
        db.insert(POST_OPERATOR, null, values);
       // System.out.println("" + values);
        db.close(); // Closing database connection
    }

    public static void addPostOperator(String operator, String opcode) {
    }

    // Getting single contact
//    Contact getContact(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
//                        KEY_USERNAME, KEY_PASSWORD }, KEY_ID + "=?",
//                new String[] { String.valueOf(id) }, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
//                cursor.getString(1), cursor.getString(2));
//        // return contact
//        return contact;
//    }
//
    // Getting All Contacts
    public List<PostOperator> getAllOperator() {
        List<PostOperator> operatorList = new ArrayList<PostOperator>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + POST_OPERATOR;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PostOperator operator = new PostOperator();
                operator.setID(Integer.parseInt(cursor.getString(0)));
                operator.setOperator(cursor.getString(1));
                operator.setOpcode(cursor.getString(2));
                // Adding contact to list
                operatorList.add(operator);
            } while (cursor.moveToNext());
        }

        // return contact list
        return operatorList;
    }

//    // Updating single contact
//    public int updateContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_USERNAME, contact.get_username());
//        values.put(KEY_PASSWORD, contact.get_password());
//
//        // updating row
//        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
//                new String[] { String.valueOf(contact.getID()) });
//    }
//
//    // Deleting single contact
//    public void deleteContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
//                new String[] { String.valueOf(contact.getID()) });
//        db.close();
//    }
//
//
//    // Getting contacts Count
//    public int getContactsCount() {
//        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
//
//        // return count
//        return cursor.getCount();
//    }
}

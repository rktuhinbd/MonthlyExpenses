package com.rktuhinbd.smartmessmanager.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.rktuhinbd.smartmessmanager.Model.Members;
import com.rktuhinbd.smartmessmanager.Model.Rents;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "MessDB.db";
    private static final int VERSION = 1;

    private static final String MESS_MEMBER_TABLE = "mess_member_table";
    private static final String MEMBER_ID = "member_id";
    private static final String NAME = "name";
    private static final String PHONE = "phone";
    private static final String MAIL_ADDRESS = "mail_address";
    private static final String NATIONAL_ID = "national_id";
    private static final String OCCUPATION = "occupation";
    private static final String ORGANISATION = "organisation";
    private static final String HOME_ADDRESS = "home_address";
    private static final String PROFILE_PHOTO = "profile_photo_url";

    private static final String RENT_TABLE = "rent_table";
    private static final String RENT_ID = "rent_id";
    private static final String RENT_AMOUNT = "rent_amount";
    private static final String RENT_CATEGORY = "rent_category";
    private static final String RENT_DESCRIPTION = "rent_description";
    private static final String RENT_DATE = "rent_date";


    private static final String CREATE_MESS_TABLE = "CREATE TABLE " + MESS_MEMBER_TABLE + "("
            + MEMBER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME + " VARCHAR(100) , "
            + PHONE + " VARCHAR(100)  , "
            + MAIL_ADDRESS + " VARCHAR(100)  , "
            + HOME_ADDRESS + " VARCHAR(100)  , "
            + OCCUPATION + " VARCHAR(100) , "
            + ORGANISATION + " VARCHAR(100) , "
            + NATIONAL_ID + " VARCHAR(100) , "
            + PROFILE_PHOTO + " VARCHAR(250));";

    private static final String CREATE_RENT_TABLE = "CREATE TABLE " + RENT_TABLE + "("
            + RENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + RENT_AMOUNT + " INTEGER NOT NULL, "
            + RENT_DATE + " VARCHAR(100), "
            + RENT_CATEGORY + " VARCHAR(100) , "
            + RENT_DESCRIPTION + " VARCHAR(250));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_MESS_TABLE);
            db.execSQL(CREATE_RENT_TABLE);
        } catch (Exception e) {
            Toast.makeText(context, "Database Exception: " + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MESS_MEMBER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RENT_TABLE);
        onCreate(db);
    }

    //Add member to database
    public long addMember(String name, String phone, String mailAddress, String homeAddress, String nationalId, String occupation, String organisation,
                          String profilePhotoUrl) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(PHONE, phone);
        contentValues.put(MAIL_ADDRESS, mailAddress);
        contentValues.put(HOME_ADDRESS, homeAddress);
        contentValues.put(NATIONAL_ID, nationalId);
        contentValues.put(OCCUPATION, occupation);
        contentValues.put(ORGANISATION, organisation);
        contentValues.put(PROFILE_PHOTO, profilePhotoUrl);

        return sqLiteDatabase.insert(MESS_MEMBER_TABLE, null, contentValues);
    }

    //Update member information
    public void updateMemberInfo(String memberId, String name, String phone, String mailAddress, String homeAddress, String nationalId, String occupation,
                                 String organisation, String profilePhotoUrl) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + MESS_MEMBER_TABLE + " WHERE " + MEMBER_ID + " = '" + memberId + "'";
        Cursor c = db.rawQuery(query, null);

        if (c.getCount() != 0) {
            if (c.moveToFirst()) {
                do {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(NAME, name);
                    contentValues.put(PHONE, phone);
                    contentValues.put(MAIL_ADDRESS, mailAddress);
                    contentValues.put(HOME_ADDRESS, homeAddress);
                    contentValues.put(NATIONAL_ID, nationalId);
                    contentValues.put(OCCUPATION, occupation);
                    contentValues.put(ORGANISATION, organisation);
                    contentValues.put(PROFILE_PHOTO, profilePhotoUrl);
                    db.update(MESS_MEMBER_TABLE, contentValues, MEMBER_ID + "=?", new String[]{memberId});
                } while (c.moveToNext());
            }
        }
        c.close();
    }

    //Remove member from database
    public void removeMember(String memberId) {
        SQLiteDatabase database = this.getReadableDatabase();
        database.delete(MESS_MEMBER_TABLE, MEMBER_ID + "=? ", new String[]{memberId});
    }

    //Get member list from database
    public ArrayList<Members> getMemberList() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        final Cursor cursor;
        cursor = sqLiteDatabase.rawQuery("Select * from " + MESS_MEMBER_TABLE, null);

        ArrayList<Members> members = new ArrayList<>();

        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(cursor.getColumnIndex(MEMBER_ID));
                    String name = cursor.getString(cursor.getColumnIndex(NAME));
                    String phone = cursor.getString(cursor.getColumnIndex(PHONE));
                    String email = cursor.getString(cursor.getColumnIndex(MAIL_ADDRESS));
                    String homeAddress = cursor.getString(cursor.getColumnIndex(HOME_ADDRESS));
                    String nationalId = cursor.getString(cursor.getColumnIndex(NATIONAL_ID));
                    String occupation = cursor.getString(cursor.getColumnIndex(OCCUPATION));
                    String organisation = cursor.getString(cursor.getColumnIndex(ORGANISATION));
                    String profilePhotoUrl = cursor.getString(cursor.getColumnIndex(PROFILE_PHOTO));

                    members.add(new Members(id, name, phone, email, homeAddress, nationalId, occupation, organisation, profilePhotoUrl));
                } while (cursor.moveToNext());
            }
        }
        return members;
    }

    //Get single member information from database
    /*public ArrayList<Members> getMemberInformation(int memberId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        final Cursor cursor;
        cursor = sqLiteDatabase.rawQuery("Select * from " + MESS_MEMBER_TABLE + " WHERE " + MEMBER_ID + " = '" + memberId + "' ORDER BY " + MEMBER_ID
                + " ASC", null);

        ArrayList<Members> members = new ArrayList<>();

        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(cursor.getColumnIndex(MEMBER_ID));
                    String name = cursor.getString(cursor.getColumnIndex(NAME));
                    String phone = cursor.getString(cursor.getColumnIndex(PHONE));
                    String email = cursor.getString(cursor.getColumnIndex(MAIL_ADDRESS));
                    String homeAddress = cursor.getString(cursor.getColumnIndex(HOME_ADDRESS));
                    String nationalId = cursor.getString(cursor.getColumnIndex(NATIONAL_ID));
                    String occupation = cursor.getString(cursor.getColumnIndex(OCCUPATION));
                    String organisation = cursor.getString(cursor.getColumnIndex(PROFILE_PHOTO));

                    members.add(new Members(id, name, phone, email, homeAddress, nationalId, occupation, organisation, ""));
                } while (cursor.moveToNext());
            }
        }
        return members;
    }*/

    //Add rent to database
    public long addRent(int rentAmount, String rentCategory, String rentDescription, String rentMonth) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RENT_AMOUNT, rentAmount);
        contentValues.put(RENT_CATEGORY, rentCategory);
        contentValues.put(RENT_DESCRIPTION, rentDescription);
        contentValues.put(RENT_DATE, rentMonth);

        return sqLiteDatabase.insert(RENT_TABLE, null, contentValues);
    }

    //Get member list from database
    public ArrayList<Rents> getRents(String month) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        final Cursor cursor;
        cursor = sqLiteDatabase.rawQuery("Select * from " + RENT_TABLE + " WHERE " + RENT_DATE + " = '" + month + "'", null);

        ArrayList<Rents> rents = new ArrayList<>();

        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(cursor.getColumnIndex(RENT_ID));
                    int amount = cursor.getInt(cursor.getColumnIndex(RENT_AMOUNT));
                    String rentCategory = cursor.getString(cursor.getColumnIndex(RENT_CATEGORY));
                    String rentDescription = cursor.getString(cursor.getColumnIndex(RENT_DESCRIPTION));

                    rents.add(new Rents(id, amount, rentCategory, rentDescription, month));
                } while (cursor.moveToNext());
            }
        }
        return rents;
    }

    //Update member information
    public void updateRentInfo(String rentId, String rentCategory, int rentAmount, String rentDate, String rentDescription) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + RENT_TABLE + " WHERE " + RENT_ID + " = '" + rentId + "'";
        Cursor c = db.rawQuery(query, null);

        if (c.getCount() != 0) {
            if (c.moveToFirst()) {
                do {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(RENT_CATEGORY, rentCategory);
                    contentValues.put(RENT_AMOUNT, rentAmount);
                    contentValues.put(RENT_DATE, rentDate);
                    contentValues.put(RENT_DESCRIPTION, rentDescription);
                    db.update(RENT_TABLE, contentValues, RENT_ID + "=?", new String[]{rentId});
                } while (c.moveToNext());
            }
        }
        c.close();
    }

    //Remove rent information from database
    public void removeRentInformation(String rentId) {
        SQLiteDatabase database = this.getReadableDatabase();
        database.delete(RENT_TABLE, RENT_ID + "=? ", new String[]{rentId});
    }
}

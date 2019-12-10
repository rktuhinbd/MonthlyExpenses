package com.rktuhinbd.messmanager.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.rktuhinbd.messmanager.Model.MemberList;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "MessDB.db";
    private static final int VERSION = 1;

    private static final String MESS_MEMBER_TABLE = "mess_member_table";
    private static final String MEMBER_ID = "member_id";
    private static final String NAME = "name";
    private static final String PHONE = "phone";
    private static final String EMAIL = "email";
    private static final String NATIONAL_ID = "national_id";
    private static final String OCCUPATION = "occupation";
    private static final String ORGANISATION = "organisation";
    private static final String HOME_ADDRESS = "home_address";
    private static final String PROFILE_PHOTO = "profile_photo_url";

    private static final String MESS_DEPOSIT_TABLE = "mess_deposit_table";

    private static final String CREATE_MESS_TABLE = "CREATE TABLE " + MESS_MEMBER_TABLE + "("
            + MEMBER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME + " VARCHAR(100) , "
            + PHONE + " VARCHAR(100)  , "
            + EMAIL + " VARCHAR(100)  , "
            + HOME_ADDRESS + " VARCHAR(100)  , "
            + OCCUPATION + " VARCHAR(100) , "
            + ORGANISATION + " VARCHAR(100) , "
            + NATIONAL_ID + " VARCHAR(100) , "
            + PROFILE_PHOTO + " VARCHAR(250));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_MESS_TABLE);
        } catch (Exception e) {
            Toast.makeText(context, "Exception: " + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MESS_MEMBER_TABLE);
        onCreate(db);
    }

    //Add member to database
    public long addMember(String name, String phone, String email, String homeAddress, String nationalId, String occupation, String organisation, String profilePhotoUrl) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(PHONE, phone);
        contentValues.put(EMAIL, email);
        contentValues.put(HOME_ADDRESS, homeAddress);
        contentValues.put(NATIONAL_ID, nationalId);
        contentValues.put(OCCUPATION, occupation);
        contentValues.put(ORGANISATION, organisation);
        contentValues.put(PROFILE_PHOTO, profilePhotoUrl);

        return sqLiteDatabase.insert(MESS_MEMBER_TABLE, null, contentValues);
    }

    //Get member list from database
    public ArrayList<MemberList> getMemberList() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        final Cursor cursor;
        cursor = sqLiteDatabase.rawQuery("Select * from " + MESS_MEMBER_TABLE, null);

        ArrayList<MemberList> memberLists = new ArrayList<>();

        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(MEMBER_ID)));
                    String name = cursor.getString(cursor.getColumnIndex(NAME));
                    String phone = cursor.getString(cursor.getColumnIndex(PHONE));
                    String email = cursor.getString(cursor.getColumnIndex(EMAIL));
                    String homeAddress = cursor.getString(cursor.getColumnIndex(HOME_ADDRESS));
                    String nationalId = cursor.getString(cursor.getColumnIndex(NATIONAL_ID));
                    String occupation = cursor.getString(cursor.getColumnIndex(OCCUPATION));
                    String organisation = cursor.getString(cursor.getColumnIndex(PROFILE_PHOTO));

                    memberLists.add(new MemberList(id, name, phone, email, homeAddress, nationalId, occupation, organisation, ""));
                } while (cursor.moveToNext());
            }
        }
        return memberLists;
    }

    //Get single member information from database
    public ArrayList<MemberList> getMemberInformation(int memberId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        final Cursor cursor;
        cursor = sqLiteDatabase.rawQuery("Select * from " + MESS_MEMBER_TABLE + " WHERE " + MEMBER_ID + " = '" + memberId + "' ORDER BY " + MEMBER_ID + " ASC", null);

        ArrayList<MemberList> memberLists = new ArrayList<>();

        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(MEMBER_ID)));
                    String name = cursor.getString(cursor.getColumnIndex(NAME));
                    String phone = cursor.getString(cursor.getColumnIndex(PHONE));
                    String email = cursor.getString(cursor.getColumnIndex(EMAIL));
                    String homeAddress = cursor.getString(cursor.getColumnIndex(HOME_ADDRESS));
                    String nationalId = cursor.getString(cursor.getColumnIndex(NATIONAL_ID));
                    String occupation = cursor.getString(cursor.getColumnIndex(OCCUPATION));
                    String organisation = cursor.getString(cursor.getColumnIndex(PROFILE_PHOTO));

                    memberLists.add(new MemberList(id, name, phone, email, homeAddress, nationalId, occupation, organisation, ""));
                } while (cursor.moveToNext());
            }
        }
        return memberLists;
    }
}

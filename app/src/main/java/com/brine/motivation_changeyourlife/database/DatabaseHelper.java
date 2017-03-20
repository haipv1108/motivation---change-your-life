package com.brine.motivation_changeyourlife.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.brine.motivation_changeyourlife.model.MotivationCard;

import java.util.ArrayList;

/**
 * Created by phamhai on 17/03/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "motivation.db";
    public static final int DATA_VERSION = 1;

    public static final String TABLE_MOTIVATION_CARD = "tb_motivation_card";
    public static final String KEY_ID_MOTIVATION_CARD = "id";
    public static final String KEY_TITLE_MOTIVATION_CARD = "title";
    public static final String KEY_IMAGE_MOTIVATION_CARD = "image";
    public static final String KEY_DESCRIPTION_MOTIVATION_CARD = "description";
    public static final String KEY_STATUS_MOTIVATION_CARD = "status";
    public static final String KEY_IS_UNDERSTAND_MOTIVATION_CARD = "is_understand";
    public static final String KEY_IS_CAN_DO_MOTIVATION_CARD = "is_can_do";
    public static final String KEY_IS_DONE_MOTIVATION_CARD = "is_done";

    public static final String CREATE_TABLE_MOTIVATION_CARD =
            "CREATE TABLE " + TABLE_MOTIVATION_CARD + "(" +
                    KEY_ID_MOTIVATION_CARD + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL" +
                    ", " + KEY_TITLE_MOTIVATION_CARD + " TEXT NOT NULL" +
                    "," + KEY_IMAGE_MOTIVATION_CARD + " TEXT NOT NULL" +
                    ", " + KEY_DESCRIPTION_MOTIVATION_CARD + " TEXT NOT NULL" +
                    ", " + KEY_STATUS_MOTIVATION_CARD + " INTEGER NOT NULL" +
                    ", " + KEY_IS_UNDERSTAND_MOTIVATION_CARD + " INTEGER DEFAULT 0" +
                    ", " + KEY_IS_CAN_DO_MOTIVATION_CARD + " INTEGER DEFAULT 0" +
                    ", " + KEY_IS_DONE_MOTIVATION_CARD + " INTEGER DEFAULT 0" +
                    ")";

    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_MOTIVATION_CARD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void open() {
        try {
            db = getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (db != null && db.isOpen()) {
            try {
                db.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Cursor getAll(String sql) {
        open();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        close();
        return cursor;
    }

    public long insert(String table, ContentValues values) {
        open();
        long index = db.insert(table, null, values);
        close();
        return index;
    }

    public boolean update(String table, ContentValues values, String where) {
        open();
        long index = db.update(table, values, where, null);
        close();
        return index > 0;
    }

    public boolean delete(String table, String where) {
        open();
        long index = db.delete(table, where, null);
        close();
        return index > 0;
    }

    //++++++++++++++++++++MOTIVATION CARD++++++++++++++++++++++
    public MotivationCard getMotivationCard(String sql) {
        MotivationCard motivationCard = null;
        Cursor cursor = getAll(sql);
        if (cursor != null) {
            motivationCard = cursorToMotivationCard(cursor);
            cursor.close();
        }
        return motivationCard;
    }

    public ArrayList<MotivationCard> getAllMotivationCard(String sql) {
        ArrayList<MotivationCard> motivationCards = new ArrayList<>();
        Cursor cursor = getAll(sql);

        while (!cursor.isAfterLast()) {
            motivationCards.add(cursorToMotivationCard(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        return motivationCards;
    }

    public long insertMotivationCard(MotivationCard motivationCard) {
        return insert(TABLE_MOTIVATION_CARD, motivationCardToValues(motivationCard));
    }

    public boolean updateMotivationCard(MotivationCard motivationCard) {
        return update(TABLE_MOTIVATION_CARD, motivationCardToValues(motivationCard),
                KEY_ID_MOTIVATION_CARD + " = " + motivationCard.getId());
    }

    public boolean deleteMotivationCard(String where) {
        return delete(TABLE_MOTIVATION_CARD, where);
    }

    private ContentValues motivationCardToValues(MotivationCard motivationCard) {
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE_MOTIVATION_CARD, motivationCard.getTitle());
        values.put(KEY_IMAGE_MOTIVATION_CARD, motivationCard.getImage());
        values.put(KEY_DESCRIPTION_MOTIVATION_CARD, motivationCard.getDescription());
        values.put(KEY_STATUS_MOTIVATION_CARD, motivationCard.getStatus());
        values.put(KEY_IS_UNDERSTAND_MOTIVATION_CARD, motivationCard.getUnderstand());
        values.put(KEY_IS_CAN_DO_MOTIVATION_CARD, motivationCard.getCanDoIt());
        values.put(KEY_IS_DONE_MOTIVATION_CARD, motivationCard.getDoneIt());
        return values;
    }

    private MotivationCard cursorToMotivationCard(Cursor cursor) {
        MotivationCard motivationCard = new MotivationCard();
        motivationCard.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID_MOTIVATION_CARD)))
                .setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE_MOTIVATION_CARD)))
                .setImage(cursor.getString(cursor.getColumnIndex(KEY_IMAGE_MOTIVATION_CARD)))
                .setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION_MOTIVATION_CARD)))
                .setStatus(cursor.getInt(cursor.getColumnIndex(KEY_STATUS_MOTIVATION_CARD)))
                .setUnderstand(cursor.getInt(cursor.getInt(cursor.getColumnIndex(KEY_IS_UNDERSTAND_MOTIVATION_CARD))))
                .setCanDoIt(cursor.getInt(cursor.getInt(cursor.getColumnIndex(KEY_IS_CAN_DO_MOTIVATION_CARD))))
                .setDoneIt(cursor.getInt(cursor.getInt(cursor.getColumnIndex(KEY_IS_DONE_MOTIVATION_CARD))));
        return motivationCard;
    }
}

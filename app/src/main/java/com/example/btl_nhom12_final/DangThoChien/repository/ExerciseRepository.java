package com.example.btl_nhom12_final.DangThoChien.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.btl_nhom12_final.DangThoChien.entity.Exercise;
import com.example.btl_nhom12_final.SQLite;

import java.util.ArrayList;
import java.util.List;

public class ExerciseRepository {
    private final SQLite dbHelper;
    private SQLiteDatabase database;

    private static final String TABLE_NAME_BAI_TAP = "tbl_bt";
    private static final String KEY_ID3 = "id_baitap";
    private static final String KEY_TEN_BAI_TAP = "ten_bai_tap";
    private static final String KEY_THOI_GIAN_TAP = "thoi_gian_tap";
    private static final String KEY_MO_TA = "mo_ta";

    public ExerciseRepository(SQLite dbHelper) {
        this.dbHelper = dbHelper;
    }

    // Open database connection
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    // Close database connection
    public void close() {
        dbHelper.close();
    }

    // Add a new BaiTap (exercise)
    public long addBaiTap( Exercise baiTap) {
        ContentValues values = new ContentValues();
        values.put(KEY_TEN_BAI_TAP, baiTap.getTenBaiTap());
        values.put(KEY_THOI_GIAN_TAP, baiTap.getThoiGianTap());
        values.put(KEY_MO_TA, baiTap.getMoTa());

        return database.insert(TABLE_NAME_BAI_TAP, null, values);
    }

    // Get all BaiTap (exercises)
    public List<Exercise> getAllBaiTap() {
        List<Exercise> baiTapList = new ArrayList<>();
        Cursor cursor = null;

        try {
            // Query all exercises from tbl_bt
            cursor = database.query(TABLE_NAME_BAI_TAP, null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Exercise baiTap = new Exercise(
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_TEN_BAI_TAP)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_THOI_GIAN_TAP)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_MO_TA))
                    );
                    baiTapList.add(baiTap);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("BaiTapRepository", "Error fetching exercises", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return baiTapList;
    }

    // Update a BaiTap (exercise)
    public int updateBaiTap(int id, Exercise baiTap) {
        ContentValues values = new ContentValues();
        values.put(KEY_TEN_BAI_TAP, baiTap.getTenBaiTap());
        values.put(KEY_THOI_GIAN_TAP, baiTap.getThoiGianTap());
        values.put(KEY_MO_TA, baiTap.getMoTa());

        return database.update(TABLE_NAME_BAI_TAP, values, KEY_ID3 + "=?", new String[]{String.valueOf(id)});
    }

    // Delete a BaiTap (exercise)
    public int deleteBaiTap(int id) {
        return database.delete(TABLE_NAME_BAI_TAP, KEY_ID3 + "=?", new String[]{String.valueOf(id)});
    }
}

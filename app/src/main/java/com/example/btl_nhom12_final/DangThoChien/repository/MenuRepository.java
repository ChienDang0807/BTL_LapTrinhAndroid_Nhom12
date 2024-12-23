package com.example.btl_nhom12_final.DangThoChien.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.btl_nhom12_final.DangThoChien.entity.Food;
import com.example.btl_nhom12_final.DangThoChien.entity.Menu;
import com.example.btl_nhom12_final.SQLite;

import java.util.ArrayList;
import java.util.List;

public class MenuRepository {
    private final SQLite dbHelper;
    private SQLiteDatabase database;

    private static final String TABLE_NAME_THUC_DON = "tbl_td";
    private static final String KEY_ID = "id_thucdon";
    private static final String KEY_TEN_THUC_DON = "ten_thuc_don";

    private static final String TABLE_NAME_FOOD = "tblfood";  // Food table liên kết với ThucDon
    private static final String KEY_FOOD_NAME = "food_name";
    private static final String KEY_SERVING_SIZE = "serving_size";
    private static final String KEY_CALORIES = "calories";
    private static final String KEY_THUC_DON_ID = "thuc_don_id"; // Foreign key liên kết với ThucDon

    public MenuRepository(SQLite dbHelper) {
        this.dbHelper = dbHelper;
    }

    // Mở kết nối cơ sở dữ liệu
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    // Đóng kết nối cơ sở dữ liệu
    public void close() {
        dbHelper.close();
    }



    // Thêm một món ăn vào thực đơn
    private long addFoodToThucDon(Food food, int thucDonId) {
        ContentValues values = new ContentValues();
        values.put(KEY_FOOD_NAME, food.getFoodName());
        values.put(KEY_SERVING_SIZE, food.getServingSize());
        values.put(KEY_CALORIES, food.getCalories());
        values.put(KEY_THUC_DON_ID, thucDonId);  // Liên kết món ăn với thực đơn

        return database.insert(TABLE_NAME_FOOD, null, values);
    }

    // Lấy tất cả thực đơn cùng với món ăn từ cơ sở dữ liệu
    public List<Menu> getAllThucDon() {
        List<Menu> thucDonList = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = database.query(TABLE_NAME_THUC_DON, null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int thucDonId = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID));
                    String tenThucDon = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TEN_THUC_DON));

                    // Lấy danh sách món ăn của thực đơn
                    List<Food> foodList = getFoodListByThucDonId(thucDonId);

                    Menu thucDon = new Menu(tenThucDon, foodList);
                    thucDonList.add(thucDon);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("ThucDonRepository", "Error fetching thucdon", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return thucDonList;
    }

    // Lấy danh sách món ăn theo ID thực đơn
    private List<Food> getFoodListByThucDonId(int thucDonId) {
        List<Food> foodList = new ArrayList<>();
        Cursor cursor = null;

        try {
            String selection = KEY_THUC_DON_ID + "=?";
            String[] selectionArgs = {String.valueOf(thucDonId)};
            cursor = database.query(TABLE_NAME_FOOD, null, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String foodName = cursor.getString(cursor.getColumnIndexOrThrow(KEY_FOOD_NAME));
                    String servingSize = cursor.getString(cursor.getColumnIndexOrThrow(KEY_SERVING_SIZE));
                    int calories = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_CALORIES));

                    Food food = new Food(foodName, calories,servingSize);
                    foodList.add(food);
                } while (cursor.moveToNext());
                Log.d("MenuRepository", "" );
            }else {
                Log.d("MenuRepository", "");
            }

        } catch (Exception e) {
            Log.e("MenuRepository", "Error fetching food for ThucDon", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return foodList;
    }

    // Cập nhật thực đơn
    public int updateThucDon(int id, Menu thucDon) {
        ContentValues values = new ContentValues();
        values.put(KEY_TEN_THUC_DON, thucDon.getTenThucDon());

        // Cập nhật thực đơn
        return database.update(TABLE_NAME_THUC_DON, values, KEY_ID + "=?", new String[]{String.valueOf(id)});
    }

    // Xoá thực đơn
    public int deleteThucDon(int id) {
        // Xoá các món ăn liên quan trước khi xoá thực đơn
        deleteFoodByThucDonId(id);

        return database.delete(TABLE_NAME_THUC_DON, KEY_ID + "=?", new String[]{String.valueOf(id)});
    }

    // Xoá món ăn theo ID thực đơn
    private void deleteFoodByThucDonId(int thucDonId) {
        String selection = KEY_THUC_DON_ID + "=?";
        String[] selectionArgs = {String.valueOf(thucDonId)};
        database.delete(TABLE_NAME_FOOD, selection, selectionArgs);
    }
}

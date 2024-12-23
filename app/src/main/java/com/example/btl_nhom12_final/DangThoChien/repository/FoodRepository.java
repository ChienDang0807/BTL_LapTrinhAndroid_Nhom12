package com.example.btl_nhom12_final.DangThoChien.repository;

import android.content.ContentValues;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.btl_nhom12_final.DangThoChien.entity.Food;
import com.example.btl_nhom12_final.SQLite;

import java.util.ArrayList;
import java.util.List;

public class FoodRepository {
    private final SQLite dbHelper;
    private SQLiteDatabase database;

    private static final String TABLE_NAME_FOOD = "tblfood";
    private static final String KEY_ID1 = "id";
    private static final String KEY_FOOD_NAME = "food_name";
    private static final String KEY_SERVING_SIZE = "serving_size";
    private static final String KEY_CALORIES = "calories";
    private static final String KEY_KJ = "kj";
    private static final String KEY_THUC_DON_ID = "thuc_don_id";

    public FoodRepository(SQLite dbHelper) {
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

    // Add a new food item
    public long addFoodCalories(Food food, int thucDonId) {
        ContentValues values = new ContentValues();
        values.put(KEY_FOOD_NAME, food.getFoodName());
        values.put(KEY_SERVING_SIZE, food.getServingSize());
        values.put(KEY_CALORIES, food.getCalories());
        values.put(KEY_KJ, food.getKj());
        values.put(KEY_THUC_DON_ID, thucDonId);

        return database.insert(TABLE_NAME_FOOD, null, values);
    }

    // Get all food items
    public List<Food> getAllFoods() {
        List<Food> foodList = new ArrayList<>();
        Cursor cursor = null;

        try {
            // Truy vấn toàn bộ dữ liệu từ bảng TABLE_NAME_FOOD
            cursor = database.query(TABLE_NAME_FOOD, null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Tạo đối tượng Food và gán giá trị từ cursor
                    Food foodItem = new Food(
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_FOOD_NAME)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_SERVING_SIZE)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(KEY_CALORIES)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(KEY_KJ)),
                            cursor.isNull(cursor.getColumnIndexOrThrow(KEY_THUC_DON_ID)) ? null : cursor.getInt(cursor.getColumnIndexOrThrow(KEY_THUC_DON_ID))
                    );
                    // Thêm vào danh sách
                    foodList.add(foodItem);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("FoodRepository", "Error fetching foods", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return foodList;
    }


    // Update a food item
    public int updateFoodCalories(int id, Food foodCalories) {
        ContentValues values = new ContentValues();
        values.put(KEY_FOOD_NAME, foodCalories.getFoodName());
        values.put(KEY_SERVING_SIZE, foodCalories.getServingSize());
        values.put(KEY_CALORIES, foodCalories.getCalories());
        values.put(KEY_KJ, foodCalories.getKj());

        return database.update(TABLE_NAME_FOOD, values, KEY_ID1 + "=?", new String[]{String.valueOf(id)});
    }

    // Delete a food item
    public int deleteFoodCalories(int id) {
        return database.delete(TABLE_NAME_FOOD, KEY_ID1 + "=?", new String[]{String.valueOf(id)});
    }

}


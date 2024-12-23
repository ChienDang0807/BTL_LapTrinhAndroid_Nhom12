package com.example.btl_nhom12_final;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.btl_nhom12_final.LyThiThanhHuyen.entity.HealthIndex;
import com.example.btl_nhom12_final.LyThiThanhHuyen.entity.User;
import com.example.btl_nhom12_final.ThiVanHoc.entity.Contact;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SQLite extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TTCN.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "tblnd";
    private static final String KEY_MA_USER = "mangdung";
    private static final String KEY_NAME = "hoten";
    private static final String KEY_DOB = "dob";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_SDT = "dienthoai";
    private static final String KEY_MK = "matkhau";


    private static final String TABLE_NAME_HEALTH = "tblsk";
    private static final String KEY_ID = "id";
    private static final String KEY_chieucao = "chieucao";
    private static final String KEY_cannang = "cannang";
    private static final String KEY_bmi = "bmi";
    private static final String KEY_sdt = "id_user";
    private static final String KEY_ngaythang = "ngaythangdo";

    private static final String TABLE_NAME_FOOD = "tblfood";
    private static final String KEY_ID1 = "id";
    private static final String KEY_FOOD_NAME = "food_name";
    private static final String KEY_SERVING_SIZE = "serving_size";
    private static final String KEY_CALORIES = "calories";
    private static final String KEY_KJ = "kj";
    private static final String KEY_THUC_DON_ID = "thuc_don_id";

    private static final String TABLE_NAME_THUC_DON = "tbl_td";
    private static final String KEY_ID2 = "id_thucdon";
    private static final String KEY_TEN_THUC_DON = "ten_thuc_don";

    private static final String TABLE_NAME_BAI_TAP = "tbl_bt";
    private static final String KEY_ID3 = "id_baitap";
    private static final String KEY_TEN_BAI_TAP = "ten_bai_tap";
    private static final String KEY_THOI_GIAN_TAP = "thoi_gian_tap";
    private static final String KEY_MO_TA = "mo_ta";

    private static final String TABLE_NAME_CHUYENGIA = "tblcg";
    private static final String KEY_ID4 = "id_chuyengia";
    private static final String KEY_TEN_CHUYEN_GIA = "ten_chuyen_gia";
    private static final String KEY_TUOI = "tuoi";
    private static final String KEY_SODIENTHOAI = "sdt";
    private static final String KEY_MAIL= "mail";
    private static final String KEY_ADDRESS= "diachi";
    private static final String KEY_GENDER= "gioitinh";
    private static final String KEY_NOTE= "note";
    private static final String KEY_CHUYENMON = "chuyenmon";
    private static final String KEY_TRINHDO = "trinhdo";



    public SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String create_table = String.format(
                "CREATE TABLE IF NOT EXISTS %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                TABLE_NAME, KEY_MA_USER, KEY_NAME, KEY_EMAIL, KEY_DOB, KEY_SDT, KEY_MK);
        sqLiteDatabase.execSQL(create_table);

        String createHealthTable = String.format(
                "CREATE TABLE IF NOT EXISTS %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s REAL NOT NULL, " +
                        "%s REAL NOT NULL, " +
                        "%s REAL NOT NULL, " +
                        "%s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL)",
                TABLE_NAME_HEALTH, KEY_ID, KEY_chieucao, KEY_cannang, KEY_bmi, KEY_sdt, KEY_ngaythang
        );
        sqLiteDatabase.execSQL(createHealthTable);

        String createFoodTable = String.format(
                "CREATE TABLE IF NOT EXISTS %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL, " +
                        "%s INTEGER NOT NULL, " +
                        "%s INTEGER NOT NULL, " +
                        "%s INTEGER)",
                TABLE_NAME_FOOD, KEY_ID1, KEY_FOOD_NAME, KEY_SERVING_SIZE, KEY_CALORIES, KEY_KJ, KEY_THUC_DON_ID
        );
        sqLiteDatabase.execSQL(createFoodTable);
        DataFood(sqLiteDatabase);

        String createThucDonTable = String.format(
                "CREATE TABLE IF NOT EXISTS %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT NOT NULL)",
                TABLE_NAME_THUC_DON, KEY_ID2, KEY_TEN_THUC_DON
        );
        sqLiteDatabase.execSQL(createThucDonTable);
        DataTD(sqLiteDatabase);

        String createBaiTapTable = String.format(
                "CREATE TABLE IF NOT EXISTS %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL, " +
                        "%s TEXT)",
                TABLE_NAME_BAI_TAP, KEY_ID3, KEY_TEN_BAI_TAP, KEY_THOI_GIAN_TAP, KEY_MO_TA
        );
        sqLiteDatabase.execSQL(createBaiTapTable);
        DataBT(sqLiteDatabase);

        String createChuyenGiaTable = String.format(
                "CREATE TABLE IF NOT EXISTS %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT NOT NULL, " +
                        "%s INTEGER NOT NULL, " +
                        "%s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL) ",
                TABLE_NAME_CHUYENGIA,
                KEY_ID4,
                KEY_TEN_CHUYEN_GIA,
                KEY_TUOI,
                KEY_SODIENTHOAI,
                KEY_MAIL,
                KEY_ADDRESS,
                KEY_GENDER,
                KEY_NOTE,
                KEY_CHUYENMON,
                KEY_TRINHDO
        );
        sqLiteDatabase.execSQL(createChuyenGiaTable);
        DataChuyenGia(sqLiteDatabase);
    }

    private void DataFood(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_NAME_FOOD);

// Danh sách thực phẩm mẫu
        Object[][] foodList = {
                {"Chuối", "1 (170g)", 151, 632, 1},
                {"Nho", "1 cốc", 100, 419, 2},
                {"Cam", "1 (113g)", 53, 222, 3},
                {"Lê", "1 (142g)", 82, 343, 2},
                {"Đào", "1 (170g)", 67, 281, 3},
                {"Dứa", "1 cốc", 82, 343, 1},
                {"Dâu tây", "1 cốc", 53, 222, 5},
                {"Dưa hấu", "1 cốc", 50, 209, 2},
                {"Măng tây", "1 cốc", 27, 113, 2},
                {"Bông cải xanh", "1 cốc", 45, 188, 3},
                {"Cà rốt", "1 cốc", 50, 209, 1},
                {"Dưa chuột", "113g", 17, 71, 2},
                {"Cà tím", "1 cốc", 35, 147, 4},
                {"Xà lách", "1 cốc", 5, 21, 7},
                {"Cà chua", "1 cốc", 22, 92, 6},
                {"Thịt bò (loại thường, chín)", "57g", 142, 595, 5},
                {"Thịt gà (chín)", "57g", 136, 569, 4},
                {"Đậu phụ", "113g", 86, 360, 7},
                {"Trứng", "1 quả lớn", 78, 327, 9},
                {"Cá da trơn (chín)", "57g", 136, 569, 4},
                {"Thịt lợn (chín)", "57g", 137, 574, 3},
                {"Tôm (chín)", "57g", 56, 234, 2},
                {"Bánh mì trắng", "1 lát (28g)", 75, 314, 6},
                {"Bơ", "1 muỗng canh", 102, 427, 8},
                {"Salad Caesar", "3 cốc", 481, 2014, 5},
                {"Bánh mì kẹp phô mai", "1 cái", 285, 1193, 4},
                {"Bánh mì kẹp thịt", "1 cái", 250, 1047, 3},
                {"Socola đen", "28g", 155, 649, 4},
                {"Ngô", "1 cốc", 132, 553, 5},
                {"Khoai tây", "170g", 130, 544, 5},
                {"Gạo", "1 cốc (nấu chín)", 206, 862, 4},
                {"Bia", "1 lon", 154, 645, 8},
                {"Coca-Cola", "1 lon", 150, 628, 7},
                {"Coca-Cola Diet", "1 lon", 0, 0, 5},
                {"Sữa (1%)", "1 cốc", 102, 427, 2},
                {"Sữa (2%)", "1 cốc", 122, 511, 3},
                {"Sữa nguyên kem", "1 cốc", 146, 611, 2},
                {"Nước cam", "1 cốc", 111, 465, 3},
                {"Nước táo", "1 cốc", 117, 490, 1},
                {"Sữa chua (ít béo)", "1 cốc", 154, 645, 1},
                {"Sữa chua (không béo)", "1 cốc", 110, 461, 1}
        };
        for (Object[] food : foodList) {
            ContentValues values = new ContentValues();
            values.put(KEY_FOOD_NAME, (String) food[0]);
            values.put(KEY_SERVING_SIZE, (String) food[1]);
            values.put(KEY_CALORIES, (int) food[2]);
            values.put(KEY_KJ, (int) food[3]);
            values.put(KEY_THUC_DON_ID, (Integer) food[4]);
            sqLiteDatabase.insert(TABLE_NAME_FOOD, null, values);
        }
    }

    private void DataBT(SQLiteDatabase sqLiteDatabase) {
        // Xóa dữ liệu cũ nếu cần
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_NAME_BAI_TAP);

// Danh sách bài tập
        String[][] baiTapList = {
                {"Nhảy dây", "15 phút", "Bài tập cardio nhanh và hiệu quả."},
                {"Hít đất", "30 phút", "Bài tập giúp tăng cường cơ bắp và sức bền."},
                {"Chạy bộ", "60 phút", "Hoạt động thể dục toàn thân, tốt cho tim mạch."},
                {"Gập bụng", "20 phút", "Bài tập tập trung vào cơ bụng."},
                {"Yoga cơ bản", "45 phút", "Bài tập giúp thư giãn và cải thiện sự dẻo dai."},
                {"Đạp xe", "40 phút", "Bài tập giúp tăng cường sức khỏe tim mạch."},
                {"Bơi lội", "60 phút", "Tốt cho toàn bộ cơ thể và cải thiện sức bền."},
                {"Squat", "25 phút", "Bài tập giúp tăng cường cơ đùi và mông."},
                {"Bài tập plank", "5 phút", "Giúp cải thiện cơ bụng và lưng."},
                {"Lunge", "15 phút", "Bài tập giúp tăng cường cơ mông và đùi."},
                {"Chạy bền", "30 phút", "Chạy với tốc độ ổn định để tăng cường sức bền."},
                {"Chèo thuyền", "40 phút", "Bài tập giúp phát triển cơ tay và vai."},
                {"Tập với dây kháng lực", "25 phút", "Tập trung vào các cơ bắp lớn."},
                {"Tập bắp tay", "20 phút", "Bài tập giúp cơ bắp tay săn chắc."},
                {"Tập bụng dưới", "15 phút", "Bài tập dành riêng cho cơ bụng dưới."},
                {"Bài tập thở", "10 phút", "Giúp thư giãn cơ thể và giảm stress."},
                {"Đấm bao cát", "30 phút", "Bài tập cardio giúp giảm căng thẳng."},
                {"Jumping jacks", "10 phút", "Bài tập giúp tăng cường sức khỏe tim mạch."},
                {"Căng cơ", "20 phút", "Bài tập giúp kéo giãn cơ và cải thiện sự linh hoạt."}
        };

// Thêm từng bài tập vào bảng
        for (String[] baiTap : baiTapList) {
            ContentValues values = new ContentValues();
            values.put(KEY_TEN_BAI_TAP, baiTap[0]);
            values.put(KEY_THOI_GIAN_TAP, baiTap[1]);
            values.put(KEY_MO_TA, baiTap[2]);
            sqLiteDatabase.insert(TABLE_NAME_BAI_TAP, null, values);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
        sqLiteDatabase.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME_HEALTH));
        sqLiteDatabase.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME_FOOD));
        sqLiteDatabase.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME_THUC_DON));
        sqLiteDatabase.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME_BAI_TAP));
        sqLiteDatabase.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME_CHUYENGIA));
        onCreate(sqLiteDatabase);
    }
    private void DataChuyenGia(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_NAME_CHUYENGIA);
        ContentValues values = new ContentValues();
        values.put(KEY_TEN_CHUYEN_GIA, "Nguyễn Văn An");
        values.put(KEY_TUOI, 35);
        values.put(KEY_SODIENTHOAI, "0912345678");
        values.put(KEY_MAIL, "nguyenvanan@gmail.com");
        values.put(KEY_ADDRESS, "Hà Nội");
        values.put(KEY_GENDER, "Nam");
        values.put(KEY_NOTE, "Chuyên gia dinh dưỡng");
        values.put(KEY_CHUYENMON, "Dinh dưỡng");
        values.put(KEY_TRINHDO, "Thạc sĩ");
        sqLiteDatabase.insert(TABLE_NAME_CHUYENGIA, null, values);

        values.clear();
        values.put(KEY_TEN_CHUYEN_GIA, "Lê Thị Bình");
        values.put(KEY_TUOI, 28);
        values.put(KEY_SODIENTHOAI, "0987654321");
        values.put(KEY_MAIL, "lethibinh@gmail.com");
        values.put(KEY_ADDRESS, "TP Hồ Chí Minh");
        values.put(KEY_GENDER, "Nữ");
        values.put(KEY_NOTE, "Chuyên gia tâm lý");
        values.put(KEY_CHUYENMON, "Tâm lý học");
        values.put(KEY_TRINHDO, "Tiến sĩ");
        sqLiteDatabase.insert(TABLE_NAME_CHUYENGIA, null, values);

        values.clear();
        values.put(KEY_TEN_CHUYEN_GIA, "Trần Quốc Hưng");
        values.put(KEY_TUOI, 40);
        values.put(KEY_SODIENTHOAI, "0901234567");
        values.put(KEY_MAIL, "tranquochung@outlook.com");
        values.put(KEY_ADDRESS, "Đà Nẵng");
        values.put(KEY_GENDER, "Nam");
        values.put(KEY_NOTE, "Chuyên gia thể thao");
        values.put(KEY_CHUYENMON, "Huấn luyện thể thao");
        values.put(KEY_TRINHDO, "Thạc sĩ");
        sqLiteDatabase.insert(TABLE_NAME_CHUYENGIA, null, values);

        values.clear();
        values.put(KEY_TEN_CHUYEN_GIA, "Phạm Thị Hạnh");
        values.put(KEY_TUOI, 44);
        values.put(KEY_SODIENTHOAI, "0911223344");
        values.put(KEY_MAIL, "phamhanh@gmail.com");
        values.put(KEY_ADDRESS, "TP Hà Nội");
        values.put(KEY_GENDER, "Nữ");
        values.put(KEY_NOTE, "Bác sĩ tâm lý khoa nhi");
        values.put(KEY_CHUYENMON, "Khoa nhi");
        values.put(KEY_TRINHDO, "Tiến sĩ");
        sqLiteDatabase.insert(TABLE_NAME_CHUYENGIA, null, values);

        values.clear();
        values.put(KEY_TEN_CHUYEN_GIA, "Đỗ Minh Đức");
        values.put(KEY_TUOI, 50);
        values.put(KEY_SODIENTHOAI, "0945566778");
        values.put(KEY_MAIL, "dominhduc@hospital.com");
        values.put(KEY_ADDRESS, "TP Hà Nội");
        values.put(KEY_GENDER, "Nam");
        values.put(KEY_NOTE, "Bác sĩ chuyên tim mạch bệnh viên tim Hà Nội ");
        values.put(KEY_CHUYENMON, "Khoa tim");
        values.put(KEY_TRINHDO, "Giáo sư");
        sqLiteDatabase.insert(TABLE_NAME_CHUYENGIA, null, values);

        values.clear();
        values.put(KEY_TEN_CHUYEN_GIA, "Vũ Hoài Nam");
        values.put(KEY_TUOI, 38);
        values.put(KEY_SODIENTHOAI, "0977889900");
        values.put(KEY_MAIL, "vuhoainam@gmail.com");
        values.put(KEY_ADDRESS, "TP Hà Nội");
        values.put(KEY_GENDER, "Nam");
        values.put(KEY_NOTE, "Bác sĩ da liễu bệnh viện Bạch Mai");
        values.put(KEY_CHUYENMON, "Da liễu");
        values.put(KEY_TRINHDO, "Bác sĩ chuyên khoa I");
        sqLiteDatabase.insert(TABLE_NAME_CHUYENGIA, null, values);

        values.clear();
        values.put(KEY_TEN_CHUYEN_GIA, "Hoàng Văn Dũng");
        values.put(KEY_TUOI, 42);
        values.put(KEY_SODIENTHOAI, "0966677888");
        values.put(KEY_MAIL, "hoangdung@gmail.com");
        values.put(KEY_ADDRESS, "TP Hà Nam");
        values.put(KEY_GENDER, "Nam");
        values.put(KEY_NOTE, "Bác sĩ gây mê");
        values.put(KEY_CHUYENMON, "Gây mê hồi sức");
        values.put(KEY_TRINHDO, "Tiến sĩ");
        sqLiteDatabase.insert(TABLE_NAME_CHUYENGIA, null, values);

        values.clear();
        values.put(KEY_TEN_CHUYEN_GIA, "Lê Minh Tú");
        values.put(KEY_TUOI, 47);
        values.put(KEY_SODIENTHOAI, "0933445566");
        values.put(KEY_MAIL, "leminhtu@hospital.com");
        values.put(KEY_ADDRESS, "TP Hà Nội");
        values.put(KEY_GENDER, "Nam");
        values.put(KEY_NOTE, "Chuyên gia bệnh về mắt, võng mạc");
        values.put(KEY_CHUYENMON, "Mắt");
        values.put(KEY_TRINHDO, "Thạc sĩ");
        sqLiteDatabase.insert(TABLE_NAME_CHUYENGIA, null, values);

        values.clear();
        values.put(KEY_TEN_CHUYEN_GIA, "Trần Thị Hồng");
        values.put(KEY_TUOI, 48);
        values.put(KEY_SODIENTHOAI, "0911223345");
        values.put(KEY_MAIL, "ranthihong@gmail.com");
        values.put(KEY_ADDRESS, "TP Hồ Chí Minh");
        values.put(KEY_GENDER, "Nữ");
        values.put(KEY_NOTE, "Chuyên môn Tai - Mũi - Họng");
        values.put(KEY_CHUYENMON, "Tai - Mũi - Họng");
        values.put(KEY_TRINHDO, "Tiến sĩ");
        sqLiteDatabase.insert(TABLE_NAME_CHUYENGIA, null, values);

        values.clear();

    }
    private void DataTD(SQLiteDatabase sqLiteDatabase) {
        // Thêm dữ liệu mẫu
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_NAME_THUC_DON); // Xóa dữ liệu cũ
        ContentValues values = new ContentValues();
        values.put(KEY_TEN_THUC_DON, "Thực Đơn Buổi Sáng Lành Mạnh");
        sqLiteDatabase.insert(TABLE_NAME_THUC_DON, null, values);

        values.clear();
        values.put(KEY_TEN_THUC_DON, "Thực Đơn Năng Lượng Trưa");
        sqLiteDatabase.insert(TABLE_NAME_THUC_DON, null, values);

        values.clear();
        values.put(KEY_TEN_THUC_DON, "Thực Đơn Tối Nhẹ Nhàng");
        sqLiteDatabase.insert(TABLE_NAME_THUC_DON, null, values);

        values.clear();
        values.put(KEY_TEN_THUC_DON, "Thực Đơn Rau Củ Xanh");
        sqLiteDatabase.insert(TABLE_NAME_THUC_DON, null, values);

        values.clear();
        values.put(KEY_TEN_THUC_DON, "Thực Đơn Trái Cây Tươi Mát");
        sqLiteDatabase.insert(TABLE_NAME_THUC_DON, null, values);
        values.clear();
        values.put(KEY_TEN_THUC_DON, "Thực Đơn Dinh Dưỡng Sáng");
        sqLiteDatabase.insert(TABLE_NAME_THUC_DON, null, values);
        values.clear();
        values.put(KEY_TEN_THUC_DON, "Thực Đơn Chay Đầy Đủ Chất");
        sqLiteDatabase.insert(TABLE_NAME_THUC_DON, null, values);
        values.clear();
        values.put(KEY_TEN_THUC_DON, "Thực Đơn Protein Buổi Trưa");
        sqLiteDatabase.insert(TABLE_NAME_THUC_DON, null, values);
        values.clear();
        values.put(KEY_TEN_THUC_DON, "Thực Đơn Detox Buổi Tối");
        sqLiteDatabase.insert(TABLE_NAME_THUC_DON, null, values);
    }
    public boolean addUser(String name, String email, String dob, String phone, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Đặt các giá trị cho cột
        values.put(KEY_NAME, name);
        values.put(KEY_EMAIL, email);
        values.put(KEY_DOB, dob);
        values.put(KEY_SDT, phone);
        values.put(KEY_MK, password);

        long result = -1;
        try {
            // Thực hiện chèn dữ liệu
            result = db.insert(TABLE_NAME, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối với cơ sở dữ liệu
            db.close();
        }

        // Kiểm tra kết quả chèn, trả về true nếu thành công
        return result != -1;
    }

    public boolean checkUser(String dienthoai, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM tblnd WHERE dienthoai = ? AND matkhau = ?";
        Cursor cursor = db.rawQuery(query, new String[]{dienthoai, password});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();
        return exists;
    }
    public User getUserByid(String id ){
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                KEY_SDT + " = ?",
                new String[]{id},
                null,
                null,
                null
        );
        if (cursor != null && cursor.moveToFirst()) {
            user = new User(
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_DOB)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_SDT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_MK))
            );
            cursor.close();
        }
        db.close();
        return user;
    }
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_DOB, user.getDob());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_MK, user.getPass());
        int rowsAffected = db.update(TABLE_NAME, values, KEY_SDT + " = ?", new String[]{user.getPhone()});
        db.close();

        if (rowsAffected == 0) {
            Log.d("Update Error", "Không tìm thấy người dùng với số điện thoại: " + user.getPhone());
        } else {
            Log.d("Update Success", "Cập nhật thành công.");
        }
    }
    public boolean addHealthRecord(String sdt, Double chieucao, Double cannang, Double bmi, String ngaythang) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Thêm dữ liệu vào ContentValues
        values.put(KEY_chieucao, chieucao);
        values.put(KEY_cannang, cannang);
        values.put(KEY_bmi, bmi);
        values.put(KEY_sdt, sdt);
        values.put(KEY_ngaythang, ngaythang);

        long result = -1;
        try {
            // Chèn dữ liệu vào cơ sở dữ liệu
            result = db.insert(TABLE_NAME_HEALTH, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng cơ sở dữ liệu
            db.close();
        }

        // Trả về true nếu chèn thành công, ngược lại trả về false
        return result != -1;
    }

    public void updateHealthRecordById(String id, Double newChieucao, Double newCannang, Double newBmi, String newSdt, String newNgaythang) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Cập nhật dữ liệu mới
        values.put(KEY_chieucao, newChieucao);
        values.put(KEY_cannang, newCannang);
        values.put(KEY_bmi, newBmi);
        values.put(KEY_sdt, newSdt);
        values.put(KEY_ngaythang, newNgaythang);

        // Chuẩn bị mệnh đề WHERE và tham số
        String whereClause = KEY_ID + " = ?";
        String[] whereArgs = new String[]{id};

        // Thực hiện cập nhật
        try {
            int rows = db.update(TABLE_NAME_HEALTH, values, whereClause, whereArgs);
            if (rows > 0) {
                Log.d("SQLite", "Cập nhật thành công bản ghi với id: " + id);
            } else {
                Log.d("SQLite", "Không tìm thấy bản ghi với id: " + id);
            }
        } catch (Exception e) {
            Log.e("SQLite", "Lỗi khi cập nhật bản ghi với id: " + id, e);
        } finally {
            db.close();
        }
    }
    public void deleteHealthRecordById(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            int rows = db.delete(TABLE_NAME_HEALTH,
                    KEY_ID + " = ?",
                    new String[]{id});

            if (rows > 0) {
                Log.d("SQLite", "Xóa thành công bản ghi với id: " + id);
            } else {
                Log.d("SQLite", "Không tìm thấy bản ghi với id: " + id);
            }
        } catch (Exception e) {
            Log.e("SQLite", "Lỗi khi xóa bản ghi với id: " + id, e);
        } finally {
            db.close();
        }
    }
    public ArrayList<HealthIndex> getHealthRecordsByPhone(String id) {
        ArrayList<HealthIndex> healthList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME_HEALTH + " WHERE " + KEY_sdt + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{id});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                HealthIndex healthRecord = new HealthIndex(
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_sdt)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_ngaythang)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_cannang)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_chieucao)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_bmi))
                );
                healthList.add(healthRecord);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        Collections.sort(healthList, new Comparator<HealthIndex>() {
            @Override
            public int compare(HealthIndex record1, HealthIndex record2) {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    Date date1 = dateFormat.parse(record1.getDate());
                    Date date2 = dateFormat.parse(record2.getDate());
                    return date2.compareTo(date1);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });

        return healthList;
    }
    public List<Contact> getAllContact() {
        List<Contact> chuyenGiaList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_CHUYENGIA, null);

        if (cursor.moveToFirst()) {
            do {
                Contact chuyenGia = new Contact();
                chuyenGia.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID4)));
                chuyenGia.setName(cursor.getString(cursor.getColumnIndexOrThrow(KEY_TEN_CHUYEN_GIA)));
                chuyenGia.setAge(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_TUOI)));
                chuyenGia.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(KEY_SODIENTHOAI)));
                chuyenGia.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(KEY_MAIL)));
                chuyenGia.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(KEY_ADDRESS)));
                chuyenGia.setGender(cursor.getString(cursor.getColumnIndexOrThrow(KEY_GENDER)));
                chuyenGia.setNote(cursor.getString(cursor.getColumnIndexOrThrow(KEY_NOTE)));
                chuyenGia.setExpertise(cursor.getString(cursor.getColumnIndexOrThrow(KEY_CHUYENMON)));
                chuyenGia.setLevel(cursor.getString(cursor.getColumnIndexOrThrow(KEY_TRINHDO)));

                chuyenGiaList.add(chuyenGia);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return chuyenGiaList;
    }
    public Contact getContatcById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Contact chuyenGia = null;
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_CHUYENGIA + " WHERE " + KEY_ID4 + " = ?", new String[]{String.valueOf(id)});

        if (cursor != null && cursor.moveToFirst()) {
            chuyenGia = new Contact();
            chuyenGia.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID4)));
            chuyenGia.setName(cursor.getString(cursor.getColumnIndexOrThrow(KEY_TEN_CHUYEN_GIA)));
            chuyenGia.setAge(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_TUOI)));
            chuyenGia.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(KEY_SODIENTHOAI)));
            chuyenGia.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(KEY_MAIL)));
            chuyenGia.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(KEY_ADDRESS)));
            chuyenGia.setGender(cursor.getString(cursor.getColumnIndexOrThrow(KEY_GENDER)));
            chuyenGia.setNote(cursor.getString(cursor.getColumnIndexOrThrow(KEY_NOTE)));
            chuyenGia.setExpertise(cursor.getString(cursor.getColumnIndexOrThrow(KEY_CHUYENMON)));
            chuyenGia.setLevel(cursor.getString(cursor.getColumnIndexOrThrow(KEY_TRINHDO)));
            cursor.close();
        }
        db.close();
        return chuyenGia; // Trả về đối tượng chuyên gia hoặc null nếu không tìm thấy
    }
}
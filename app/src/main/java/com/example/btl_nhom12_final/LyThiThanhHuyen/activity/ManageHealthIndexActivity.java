package com.example.btl_nhom12_final.LyThiThanhHuyen.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btl_nhom12_final.SQLite;
import com.example.btl_nhom12_final.LyThiThanhHuyen.entity.User;
import com.example.btl_nhom12_final.R;

import java.util.Calendar;

public class ManageHealthIndexActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView btnngaydo, btnhome, btnlogout;
    Button btncapnhat, btnxoa;
    private SQLite databaseHelper;
    private User currentUser;
    EditText txtngaydo, txtcannang, txtchieucao;
    TextView txtuser;
    String ngaythang, sdt, id;
    Double cannang, chieucao, bmi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_health_index);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWidget();
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(ManageHealthIndexActivity.this, HealthMonitorActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });

        databaseHelper = new SQLite(this);

        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String phoneNumber = sharedPreferences.getString("user_sdt", null);
        currentUser = databaseHelper.getUserByid(phoneNumber);
        if (currentUser != null) {
            txtuser.setText(currentUser.getName());
        } else {
            Toast.makeText(this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
        }

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            sdt = intent.getStringExtra("sdt");
            id = intent.getStringExtra("idhealth");
            bmi = intent.getDoubleExtra("bmi", 0.0); // 0.0 là giá trị mặc định nếu không có dữ liệu
            ngaythang = intent.getStringExtra("ngaythang");
            chieucao = intent.getDoubleExtra("chieucao", 0.0);
            cannang = intent.getDoubleExtra("cannang", 0.0);
        }


        // Hiển thị dữ liệu lên giao diện
        txtngaydo.setText(ngaythang);
        txtcannang.setText(cannang.toString());
        txtchieucao.setText(chieucao.toString());

    }
    private void getWidget() {
        btnngaydo = findViewById(R.id.calngaydo);
        btnhome = findViewById(R.id.home);
        btnlogout = findViewById(R.id.logout);
        btncapnhat = findViewById(R.id.btncapnhat);
        btnxoa = findViewById(R.id.btnxoa);
        txtngaydo = findViewById(R.id.txtngaydo);
        txtcannang = findViewById(R.id.txtcannang);
        txtchieucao = findViewById(R.id.txtchieucao);
        txtuser = findViewById(R.id.tennguoidung);
        btncapnhat.setOnClickListener(this);
        btnxoa.setOnClickListener(this);
        btnhome.setOnClickListener(this);
        btnlogout.setOnClickListener(this);
        btnngaydo.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (btnngaydo == view) {
            // Lấy ngày tháng năm từ TextView txtngaysinh
            String currentDate = txtngaydo.getText().toString().trim();
            int dd, mm, yy;

            if (!TextUtils.isEmpty(currentDate) && currentDate.contains("/")) {
                // Phân tách ngày/tháng/năm từ chuỗi
                String[] dateParts = currentDate.split("/");
                dd = Integer.parseInt(dateParts[0]);
                mm = Integer.parseInt(dateParts[1]) - 1; // Tháng bắt đầu từ 0 trong Calendar
                yy = Integer.parseInt(dateParts[2]);
            } else {
                // Nếu TextView không có giá trị, lấy ngày tháng hiện tại
                Calendar c = Calendar.getInstance();
                yy = c.get(Calendar.YEAR);
                mm = c.get(Calendar.MONTH);
                dd = c.get(Calendar.DAY_OF_MONTH);
            }

            // Tạo DatePickerDialog với ngày, tháng, năm lấy từ TextView
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view1, t, m, d) -> {
                txtngaydo.setText(d + "/" + (m + 1) + "/" + t); // Cập nhật TextView với ngày được chọn
            }, yy, mm, dd);

            datePickerDialog.show();
        }

        if (btnhome == view) {
            Intent intenthome = new Intent(this, MainActivity.class);
            startActivity(intenthome);
        }
        if (view == btnlogout) {
            new AlertDialog.Builder(ManageHealthIndexActivity.this)
                    .setTitle("Đăng xuất")
                    .setMessage("Bạn có chắc chắn muốn đăng xuất?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        // Thực hiện đăng xuất nếu người dùng chọn "Có"
                        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear(); // Xóa toàn bộ dữ liệu
                        editor.apply();

                        Intent intent = new Intent(ManageHealthIndexActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish(); // Đóng Activity hiện tại
                    })
                    .setNegativeButton("Không", (dialog, which) -> {
                        // Đóng hộp thoại nếu người dùng chọn "Không"
                        dialog.dismiss();
                    })
                    .show();
        }
        if (view == btncapnhat) {
            updateHealthRecord();

        }
        if (view == btnxoa) {
            deleteRecord(id);
        }

    }

    private void deleteRecord(String id) {
        // Tạo đối tượng trợ giúp cơ sở dữ liệu
        SQLite databaseHelper = new SQLite(this);

        // Tạo hộp thoại xác nhận
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa bản ghi này không?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    // Xóa bản ghi trong cơ sở dữ liệu
                    try {
                        databaseHelper.deleteHealthRecordById(id);
                        Toast.makeText(ManageHealthIndexActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();

                        // Làm mới giao diện hoặc quay về màn hình trước
                        Intent intent = new Intent(ManageHealthIndexActivity.this, HealthMonitorActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                        Log.e("DeleteRecord", "Lỗi khi xóa bản ghi: ", e);
                        Toast.makeText(ManageHealthIndexActivity.this, "Xóa thất bại. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", (dialog, which) -> {
                    // Không làm gì khi chọn "Hủy"
                    Toast.makeText(ManageHealthIndexActivity.this, "Đã hủy xóa", Toast.LENGTH_SHORT).show();
                })
                .show();
    }

    public void updateHealthRecord() {
        // Lấy dữ liệu từ các trường nhập liệu hoặc các giá trị hiện tại
        Double chieucaomoi = Double.parseDouble(txtchieucao.getText().toString().trim());
        Double cannangmoi = Double.parseDouble(txtcannang.getText().toString().trim());
        String ngaythangmoi = txtngaydo.getText().toString().trim();
        String sdtmoi = sdt;

        double bmi1 = cannangmoi / (chieucaomoi * chieucaomoi);
        double roundedBmi = Math.round(bmi1 * 10000.0) / 10000.0;
      //  String bmimoi = String.valueOf(roundedBmi);

        // Kiểm tra các trường không rỗng
        if (txtchieucao.getText().toString().isEmpty() || txtcannang.getText().toString().isEmpty() || txtngaydo.getText().toString().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo đối tượng SQLite
        SQLite databaseHelper = new SQLite(this);

        // Gọi phương thức cập nhật
        try {
            databaseHelper.updateHealthRecordById(id, chieucaomoi, cannangmoi, roundedBmi, sdtmoi, ngaythangmoi);
            Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, HealthMonitorActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Không tìm thấy bản ghi để cập nhật!", Toast.LENGTH_SHORT).show();

        }
    }


}
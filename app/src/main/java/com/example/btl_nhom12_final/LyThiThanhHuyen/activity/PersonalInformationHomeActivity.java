package com.example.btl_nhom12_final.LyThiThanhHuyen.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btl_nhom12_final.SQLite;
import com.example.btl_nhom12_final.LyThiThanhHuyen.entity.User;
import com.example.btl_nhom12_final.R;

public class PersonalInformationHomeActivity extends AppCompatActivity implements View.OnClickListener {
    CardView btncapnhat, btnkiemtra;
    ImageView btnhome, btnlogout;
    private SQLite databaseHelper;
    TextView txtuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_personal_information_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWidget();
        databaseHelper = new SQLite(this);
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String phoneNumber = sharedPreferences.getString("user_sdt", null);
        User updatedUser = databaseHelper.getUserByid(phoneNumber);
        if (updatedUser != null) {
            txtuser.setText(updatedUser.getName());
        }
    }

    private void getWidget() {
        btncapnhat = findViewById(R.id.capnhatthongtin);
        btnhome = findViewById(R.id.home1);
        btnkiemtra = findViewById(R.id.kiemtrachiso);
        btncapnhat.setOnClickListener(this);
        btnkiemtra.setOnClickListener(this);
        btnhome.setOnClickListener(this);
        txtuser = findViewById(R.id.user);
        btnlogout = findViewById(R.id.logout);
        btnlogout.setOnClickListener(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String userPhone = sharedPreferences.getString("user_sdt", null);
        User updatedUser = databaseHelper.getUserByid(userPhone);
        if (updatedUser != null) {
            txtuser.setText(updatedUser.getName());
        }
    }

    @Override
    public void onClick(View view) {
        if(view == btnlogout)
        {
            new AlertDialog.Builder(PersonalInformationHomeActivity.this)
                    .setTitle("Đăng xuất")
                    .setMessage("Bạn có chắc chắn muốn đăng xuất?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        // Thực hiện đăng xuất nếu người dùng chọn "Có"
                        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear(); // Xóa toàn bộ dữ liệu
                        editor.apply();

                        Intent intent = new Intent(PersonalInformationHomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish(); // Đóng Activity hiện tại
                    })
                    .setNegativeButton("Không", (dialog, which) -> {
                        // Đóng hộp thoại nếu người dùng chọn "Không"
                        dialog.dismiss();
                    })
                    .show();
        }
        if(btncapnhat == view)
        {
            Intent intentcapnhat = new Intent(this, PersonalInformationActivity.class);
            startActivity(intentcapnhat);
        }
        if(btnkiemtra == view)
        {
            Intent intentkiemtra = new Intent(this, HealthCheckActivity.class);
            startActivity(intentkiemtra);
        }
        if(btnhome == view)
        {
            Intent intenthome = new Intent(this, MainActivity.class);
            startActivity(intenthome);
        }
    }
}
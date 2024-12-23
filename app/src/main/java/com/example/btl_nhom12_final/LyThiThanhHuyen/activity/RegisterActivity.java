package com.example.btl_nhom12_final.LyThiThanhHuyen.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_nhom12_final.SQLite;
import com.example.btl_nhom12_final.R;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etName, etEmail, etDOB, etPhone, etPassword;
    private Button btnRegister, btnlogin;
    private SQLite databaseHelper;
    ImageView btnngaythang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        databaseHelper = new SQLite(this);

        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etDOB = findViewById(R.id.dob);
        etPhone = findViewById(R.id.et_phone);
        etPassword = findViewById(R.id.et_password);
        btnRegister = findViewById(R.id.btn_register);
        btnlogin = findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(this);
        btnngaythang = findViewById(R.id.calngaysinh);
        btnngaythang.setOnClickListener(this);

        getDateNow();


        btnRegister.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String dob = etDOB.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(dob)
                    || TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
                Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            } else {
                if (databaseHelper.addUser(name, email, dob, phone, password)) {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void getDateNow() {
        Calendar c = Calendar.getInstance();
        int yy = c.get(Calendar.YEAR);
        int mm = c.get(Calendar.MONTH);
        int dd = c.get(Calendar.DAY_OF_MONTH);
        etDOB.setText(dd+"/"+(mm + 1)+"/"+yy);
    }

    @Override
    public void onClick(View view) {
        if(view == btnlogin)
        {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        if(view == btnngaythang)
        {
            Calendar c = Calendar.getInstance();
            int dd, mm, yy;
            yy = c.get(Calendar.YEAR);
            mm = c.get(Calendar.MONTH);
            dd = c.get(Calendar.DAY_OF_MONTH);
            // Tạo DatePickerDialog với ngày, tháng, năm lấy từ TextView
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view1, t, m, d) -> {
                etDOB.setText(d + "/" + (m + 1) + "/" + t); // Cập nhật TextView với ngày được chọn
            }, yy, mm, dd);

            datePickerDialog.show();
        }
    }
}
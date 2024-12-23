package com.example.btl_nhom12_final.LyThiThanhHuyen.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_nhom12_final.SQLite;
import com.example.btl_nhom12_final.R;

public class LoginActivity extends AppCompatActivity implements  View.OnClickListener{
    EditText etdt, etPassword;
    Button btnLogin, btnRegister;
    private SQLite databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper = new SQLite(this);

        etdt = findViewById(R.id.phone);
        etPassword = findViewById(R.id.pass);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);

        btnLogin.setOnClickListener(v -> {
            String tdn = etdt.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(tdn) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            } else if (databaseHelper.checkUser(tdn, password)) {
                saveLoginState(tdn);
                Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("user_sdt", tdn);
                editor.apply();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Thông tin không chính xác!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveLoginState(String sdt) {
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("is_logged_in", true);
        editor.putString("user_sdt", sdt);
        editor.apply();
    }

    @Override
    public void onClick(View view) {
        if(view == btnRegister)
        {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
    }
}
package com.example.btl_nhom12_final.LyThiThanhHuyen.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_nhom12_final.SQLite;
import com.example.btl_nhom12_final.LyThiThanhHuyen.entity.User;
import com.example.btl_nhom12_final.R;

import java.util.Calendar;
import java.util.Locale;

public class HealthCheckActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView btnhome, btnngaythang, btnlogout;
    EditText txtngaythang, txtcannang, txtchieucao;
    private int style = AlertDialog.THEME_HOLO_DARK;
    Button btnbmi, btnthem;
    private User currentUser;
    private SQLite databaseHelper;
    TextView txtuser, txtbmi;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_heath_check);
        getWidget();
        getDateNow();
        databaseHelper = new SQLite(this);

        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        phoneNumber = sharedPreferences.getString("user_sdt", null);
        currentUser = databaseHelper.getUserByid(phoneNumber);
        if(currentUser != null)
        {
            txtuser.setText(currentUser.getName());
        }
        else
        {
            Toast.makeText(this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDateNow() {
        Calendar c = Calendar.getInstance();
        int yy = c.get(Calendar.YEAR);
        int mm = c.get(Calendar.MONTH);
        int dd = c.get(Calendar.DAY_OF_MONTH);
        txtngaythang.setText(dd+"/"+(mm + 1)+"/"+yy);
    }

    private void getWidget() {
        btnhome = findViewById(R.id.home1);
        btnhome.setOnClickListener(this);
        btnngaythang = findViewById(R.id.calngaythang);
        btnngaythang.setOnClickListener(this);
        txtngaythang = findViewById(R.id.txtngaythang);
        btnthem = findViewById(R.id.btnthem);
        btnthem.setOnClickListener(this);
        btnbmi = findViewById(R.id.btnbmi);
        btnbmi.setOnClickListener(this);
        txtuser = findViewById(R.id.user);
        txtcannang = findViewById(R.id.txtcannang);
        txtchieucao = findViewById(R.id.txtchieucao);
        txtbmi = findViewById(R.id.txtbmi);
        btnlogout = findViewById(R.id.logout);
        btnlogout.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(btnhome == view)
        {
            Intent intenthome = new Intent(this, MainActivity.class);
            startActivity(intenthome);
        }
        if( btnngaythang == view)
        {
            Calendar c = Calendar.getInstance();
            int yy = c.get(Calendar.YEAR);
            int mm = c.get(Calendar.MONTH);
            int dd = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog;
            datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int t, int m, int d) {
                    txtngaythang.setText(d+"/"+(m+1)+"/"+ t);
                }
            },yy,mm,dd);
            datePickerDialog.show();
        }
        if(btnthem == view)
        {
            String date = txtngaythang.getText().toString().trim();
            String edcannang = txtcannang.getText().toString().trim();
            String edchieucao = txtchieucao.getText().toString().trim();

            if(date.isEmpty() || edcannang.isEmpty() || edchieucao.isEmpty())
            {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(this, HealthMonitorActivity.class);
                startActivity(intent);
                finish();
            }
        }
        if (btnbmi == view) {
            String date = txtngaythang.getText().toString().trim();
            String edCannang = txtcannang.getText().toString().trim();
            String edChieucao = txtchieucao.getText().toString().trim();
            if (date.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập ngày tháng!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (edCannang.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập cân nặng!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (edChieucao.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập chiều cao!", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                double cannang = Double.parseDouble(edCannang);
                double chieucao = Double.parseDouble(edChieucao);
                if (cannang <= 0) {
                    Toast.makeText(this, "Cân nặng phải lớn hơn 0!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (chieucao <= 0) {
                    Toast.makeText(this, "Chiều cao phải lớn hơn 0!", Toast.LENGTH_SHORT).show();
                    return;
                }
                double bmi = cannang / (chieucao * chieucao);
                double roundedBmi = Math.round(bmi * 10000.0) / 10000.0;
                txtbmi.setText(String.valueOf(roundedBmi));
                boolean isAdded = databaseHelper.addHealthRecord(phoneNumber, chieucao, cannang, roundedBmi, date);
                if (isAdded) {
                    Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Thêm thất bại! Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                }

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Cân nặng và chiều cao phải là số hợp lệ!", Toast.LENGTH_SHORT).show();
            }
        }


        if(btnlogout == view)
        {
            new AlertDialog.Builder(HealthCheckActivity.this)
                    .setTitle("Đăng xuất")
                    .setMessage("Bạn có chắc chắn muốn đăng xuất?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();

                        Intent intent = new Intent(HealthCheckActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("Không", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .show();
        }
    }
}
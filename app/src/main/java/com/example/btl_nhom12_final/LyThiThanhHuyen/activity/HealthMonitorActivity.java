package com.example.btl_nhom12_final.LyThiThanhHuyen.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btl_nhom12_final.SQLite;
import com.example.btl_nhom12_final.LyThiThanhHuyen.adapter.AdapterIndex;
import com.example.btl_nhom12_final.LyThiThanhHuyen.entity.HealthIndex;
import com.example.btl_nhom12_final.LyThiThanhHuyen.entity.User;
import com.example.btl_nhom12_final.R;

import java.util.ArrayList;

public class HealthMonitorActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView btnthemtheodoi, btnlogout, btnhome;
    private SQLite databaseHelper;
    TextView txtuser;
    User currentUser;
    ListView listView;
    ArrayList<HealthIndex> list;
    ArrayAdapter<HealthIndex> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_health_monitoring);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWidget();
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(HealthMonitorActivity.this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
        databaseHelper = new SQLite(this);

        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String phoneNumber = sharedPreferences.getString("user_sdt", null);
        currentUser = databaseHelper.getUserByid(phoneNumber);
        if(currentUser != null)
        {
            txtuser.setText(currentUser.getName());
        }
        else
        {
            Toast.makeText(this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
        }

        list = new ArrayList<>();
        list =  databaseHelper.getHealthRecordsByPhone(phoneNumber);
        adapter = new AdapterIndex(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                HealthIndex selectedRecord = list.get(position);
//                Intent intent = new Intent(HealthMonitorActivity.this, ManageHealthIndexActivity.class);
//                intent.putExtra("ngaythang", selectedRecord.getDate());
//                intent.putExtra("idhealth", selectedRecord.getId());
//                intent.putExtra("sdt", selectedRecord.getPhone());
//                intent.putExtra("bmi", selectedRecord.getBmi());
//                intent.putExtra("chieucao", selectedRecord.getHeight());
//                intent.putExtra("cannang", selectedRecord.getWeight());
//                startActivity(intent);
//                finish();
//                return true;
//            }
//        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy đối tượng HealthIndex được chọn
                HealthIndex selectedRecord = list.get(position);

                // Tạo Intent để chuyển đến ManageHealthIndexActivity
                Intent intent = new Intent(HealthMonitorActivity.this, ManageHealthIndexActivity.class);
                intent.putExtra("ngaythang", selectedRecord.getDate());
                intent.putExtra("idhealth", selectedRecord.getId());
                intent.putExtra("sdt", selectedRecord.getPhone());
                intent.putExtra("bmi", selectedRecord.getBmi());
                intent.putExtra("chieucao", selectedRecord.getHeight());
                intent.putExtra("cannang", selectedRecord.getWeight());

                // Bắt đầu Activity
                startActivity(intent);
                finish(); // Kết thúc Activity hiện tại nếu cần
            }
        });

    }

    private void getWidget() {
        btnthemtheodoi = findViewById(R.id.themtheodoi);
        btnthemtheodoi.setOnClickListener(this);
        txtuser = findViewById(R.id.user);
        btnlogout = findViewById(R.id.logout);
        btnlogout.setOnClickListener(this);
        listView = findViewById(R.id.danhsachchiso);
        btnhome = findViewById(R.id.home1);
        btnhome.setOnClickListener(this);
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
        if(btnhome == view)
        {
            Intent intenthome = new Intent(this, MainActivity.class);
            startActivity(intenthome);
        }
        if(view == btnthemtheodoi)
        {
            Intent intent = new Intent(this,HealthCheckActivity.class);
            startActivity(intent);
        }
        if(view == btnlogout)
        {
            new AlertDialog.Builder(HealthMonitorActivity.this)
                    .setTitle("Đăng xuất")
                    .setMessage("Bạn có chắc chắn muốn đăng xuất?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        // Thực hiện đăng xuất nếu người dùng chọn "Có"
                        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        Intent intent = new Intent(HealthMonitorActivity.this, LoginActivity.class);
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
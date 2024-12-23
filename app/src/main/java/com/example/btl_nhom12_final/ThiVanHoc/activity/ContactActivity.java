package com.example.btl_nhom12_final.ThiVanHoc.activity;

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

import com.example.btl_nhom12_final.LyThiThanhHuyen.activity.HealthMonitorActivity;
import com.example.btl_nhom12_final.LyThiThanhHuyen.activity.LoginActivity;
import com.example.btl_nhom12_final.LyThiThanhHuyen.activity.MainActivity;
import com.example.btl_nhom12_final.LyThiThanhHuyen.adapter.AdapterIndex;
import com.example.btl_nhom12_final.LyThiThanhHuyen.entity.User;
import com.example.btl_nhom12_final.R;
import com.example.btl_nhom12_final.SQLite;
import com.example.btl_nhom12_final.ThiVanHoc.adapter.AdapterContact;
import com.example.btl_nhom12_final.ThiVanHoc.entity.Contact;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txtuser;
    ImageView btnhome, btnlogout;
    ListView listview;
    ArrayList<Contact> list;
    ArrayAdapter<Contact> adapter;
    private SQLite databaseHelper;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact);
        getwidget();
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(ContactActivity.this, MainActivity.class);
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
        list = (ArrayList<Contact>) databaseHelper.getAllContact();
        adapter = new AdapterContact(this, android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy đối tượng Contact tại vị trí được click
                Contact selectedContact = list.get(position);

                // Chuyển sang màn hình chi tiết và gửi ID của Contact
                Intent intent = new Intent(ContactActivity.this, DetailContactActivity.class);
                intent.putExtra("CONTACT_ID", selectedContact.getId());
                startActivity(intent);
            }
        });

    }

    private void getwidget() {
        txtuser = findViewById(R.id.user);
        btnhome = findViewById(R.id.home1);
        btnlogout = findViewById(R.id.logout);
        listview = findViewById(R.id.danhsachlienhe);
        btnhome.setOnClickListener(this);
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
        if(btnhome == view)
        {
            Intent intenthome = new Intent(this, MainActivity.class);
            startActivity(intenthome);
        }
        if(view == btnlogout)
        {
            new AlertDialog.Builder(ContactActivity.this)
                    .setTitle("Đăng xuất")
                    .setMessage("Bạn có chắc chắn muốn đăng xuất?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        Intent intent = new Intent(ContactActivity.this, LoginActivity.class);
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
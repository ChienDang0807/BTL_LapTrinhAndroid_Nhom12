package com.example.btl_nhom12_final.LyThiThanhHuyen.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.btl_nhom12_final.DangThoChien.activity.RecommendActivity;
import com.example.btl_nhom12_final.SQLite;
import com.example.btl_nhom12_final.LyThiThanhHuyen.entity.User;
import com.example.btl_nhom12_final.R;
import com.example.btl_nhom12_final.ThiVanHoc.activity.ContactActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        checkLoginState();
        setContentView(R.layout.activity_main);
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finishAffinity();
            }
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
    private void checkLoginState() {
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("is_logged_in", false);
        if (!isLoggedIn) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
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
    private void getWidget() {
        cardthongtin = findViewById(R.id.thongtincanhan);
        cardthongtin.setOnClickListener(this);
        cardtheodoi = findViewById(R.id.theodoisk);
        cardtheodoi.setOnClickListener(this);
        carddexuat = findViewById(R.id.dexuat);
        carddexuat.setOnClickListener(this);
        cardtrogiup = findViewById(R.id.trogiup);
        cardtrogiup.setOnClickListener(this);
        btnlogout = findViewById(R.id.logout);
        btnlogout.setOnClickListener(this);
        txtuser = findViewById(R.id.user);
    }
    CardView cardthongtin, cardtheodoi, carddexuat, cardtrogiup;
    ImageView btnlogout;
    TextView txtuser;
    private SQLite databaseHelper;
    @Override
    public void onClick(View view) {
        if(cardthongtin == view)
        {
            Intent intent = new Intent(this, PersonalInformationHomeActivity.class);
            startActivity(intent);
        }
        if(cardtheodoi == view)
        {
            Intent intent = new Intent(this,HealthMonitorActivity.class);
            startActivity(intent);
        }
        if(carddexuat == view)
        {
            Intent intent = new Intent(this, RecommendActivity.class);
            startActivity(intent);
        }
        if(cardtrogiup == view)
        {
            Intent intent = new Intent(this, ContactActivity.class);
            startActivity(intent);
        }
        if(view == btnlogout)
        {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Đăng xuất")
                    .setMessage("Bạn có chắc chắn muốn đăng xuất?")
                    .setPositiveButton("Có", (dialog, which) -> {

                        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();

                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
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
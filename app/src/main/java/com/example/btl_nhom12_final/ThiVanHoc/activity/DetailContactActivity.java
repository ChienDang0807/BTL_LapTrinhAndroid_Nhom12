package com.example.btl_nhom12_final.ThiVanHoc.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.net.Uri;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btl_nhom12_final.LyThiThanhHuyen.activity.LoginActivity;
import com.example.btl_nhom12_final.LyThiThanhHuyen.activity.MainActivity;
import com.example.btl_nhom12_final.LyThiThanhHuyen.entity.User;
import android.Manifest;
import com.example.btl_nhom12_final.R;
import com.example.btl_nhom12_final.SQLite;
import com.example.btl_nhom12_final.ThiVanHoc.entity.Contact;

public class DetailContactActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txtuser, txthoten, txttuoi, txtsodt, txtemail, txtdiachi, txtgioitinh, txtchuyenmon, txttrinhdo, txtghichu;
    Button btncall, btnmess, btnmail;
    ImageView btnhome, btnlogout;
    private SQLite databaseHelper;
    User currentUser;
    Contact selectedContact;
    int contactId;
    Contact contact;
    private static final int SMS_PERMISSION_CODE = 101;
    public static final int REQUEST_CODE_PERMISSION_SEND_SMS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_contact);
        getwidget();
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
        databaseHelper = new SQLite(this);
        contactId = getIntent().getIntExtra("CONTACT_ID", -1);

        if (contactId != -1) {
            // Lấy thông tin Contact từ database qua ID
            contact = databaseHelper.getContatcById(contactId);
            if (contact != null) {
                txthoten.setText(contact.getName());
               txttuoi.setText(contact.getAge().toString());
                txtdiachi.setText(contact.getAddress());
                txtemail.setText(contact.getEmail());
                txtsodt.setText(contact.getPhone());
                txttrinhdo.setText(contact.getLevel());
                txtchuyenmon.setText(contact.getExpertise());
                txtghichu.setText(contact.getNote());
                txtgioitinh.setText(contact.getGender());
            }
        }
    }

    private void getwidget() {
        txthoten = findViewById(R.id.txthoten);
        txttuoi = findViewById(R.id.txttuoi);
        txtsodt = findViewById(R.id.txtsodt);
        txtemail = findViewById(R.id.txtemail);
        txtdiachi = findViewById(R.id.txtdiachi);
        txtgioitinh = findViewById(R.id.txtgioitinh);
        txtchuyenmon = findViewById(R.id.txtchuyenmon);
        txttrinhdo = findViewById(R.id.txttrinhdo);
        txtghichu = findViewById(R.id.txtghichu);
        btncall = findViewById(R.id.call);
        btnmess = findViewById(R.id.mess);
        btnhome = findViewById(R.id.home);
        btnlogout = findViewById(R.id.logout);
        btnmail = findViewById(R.id.mail);
        btncall.setOnClickListener(this);
        btnmess.setOnClickListener(this);
        btnhome.setOnClickListener(this);
        btnlogout.setOnClickListener(this);
        btnmail.setOnClickListener(this);
        txtuser = findViewById(R.id.tennguoidung);
    }

    @Override
    public void onClick(View view) {
        if (btnhome == view) {
            Intent intenthome = new Intent(this, MainActivity.class);
            startActivity(intenthome);
        }
        if (view == btnlogout) {
            new AlertDialog.Builder(DetailContactActivity.this)
                    .setTitle("Đăng xuất")
                    .setMessage("Bạn có chắc chắn muốn đăng xuất?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        Intent intent = new Intent(DetailContactActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("Không", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .show();
        }
        if (view == btncall) {
            // Lấy số điện thoại từ TextView hoặc EditText
            String phoneNumber = txtsodt.getText().toString().trim();

            if (!phoneNumber.isEmpty()) {
                // Tạo Intent để gọi điện thoại
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber));

                // Kiểm tra và khởi chạy Intent
                if (callIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(callIntent);
                } else {
                    Toast.makeText(this, "Không thể thực hiện cuộc gọi", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
            }
        }
        if (view == btnmess) {
            String phoneNumber = txtsodt.getText().toString().trim();
            String message = "Xin chào chuyên gia, tôi là " + currentUser.getName()
                    + ".\nTôi muốn hỗ trợ tư vấn về vấn đề " + contact.getExpertise() + ".";

            int permissionCheck = ContextCompat.checkSelfPermission(
                    DetailContactActivity.this, Manifest.permission.SEND_SMS);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        DetailContactActivity.this,
                        new String[]{Manifest.permission.SEND_SMS},
                        REQUEST_CODE_PERMISSION_SEND_SMS);
            } else {
                // Tạo Intent để gửi tin nhắn
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:" + phoneNumber)); // Sử dụng "smsto:" để chỉ định số điện thoại
                intent.putExtra("sms_body", message); // Thêm nội dung tin nhắn

                // Mở hộp thoại cho phép người dùng chọn ứng dụng nhắn tin
                startActivity(Intent.createChooser(intent, "Chọn ứng dụng nhắn tin"));
            }
        }
        if (view == btnmail) {
            String emailAddress = txtemail.getText().toString().trim();
            String subject = "Yêu cầu tư vấn sức khỏe";  // Tiêu đề email
            String message = "Xin chào,\nTôi là " + currentUser.getName() + ".\nTôi muốn hỗ trợ tư vấn về vấn đề " + contact.getExpertise() + ".";  // Nội dung email

            // Tạo Intent gửi email
            Intent email = new Intent(Intent.ACTION_SEND);

            // Thêm các thông tin vào Intent
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddress});  // Địa chỉ email người nhận (dùng mảng)
            email.putExtra(Intent.EXTRA_SUBJECT, subject);  // Tiêu đề email
            email.putExtra(Intent.EXTRA_TEXT, message);  // Nội dung email

            // Đặt loại cho intent để mở ứng dụng email
            email.setType("message/rfc822");

            // Mở hộp thoại chọn ứng dụng gửi email
            startActivity(Intent.createChooser(email, "Chọn ứng dụng email:"));
        }


    }
}
package com.example.btl_nhom12_final.ThiVanHoc.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.btl_nhom12_final.R;
import com.example.btl_nhom12_final.ThiVanHoc.entity.Contact;

import java.util.ArrayList;

public class AdapterContact extends ArrayAdapter<Contact> {
    private Context context;
    private ArrayList<Contact> list;

    public AdapterContact(@NonNull Context context, int itemlv, ArrayList<Contact> list) {
        super(context, R.layout.activity_adapter_contact, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_adapter_contact, null, true);
        Contact lh = list.get(position);

        TextView name = view.findViewById(R.id.txthoten);
        name.setText(lh.getName());

        TextView chuyenmon = view.findViewById(R.id.txtchuyenmon);
        chuyenmon.setText(lh.getExpertise());

        TextView trinhdo = view.findViewById(R.id.txttrinhdo);
        trinhdo.setText(lh.getLevel());

        TextView diachi = view.findViewById(R.id.txtdiachi);
        diachi.setText(lh.getAddress());

        return view;
    }

}

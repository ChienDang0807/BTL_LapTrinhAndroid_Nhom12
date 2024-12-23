package com.example.btl_nhom12_final.LyThiThanhHuyen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.btl_nhom12_final.LyThiThanhHuyen.entity.HealthIndex;
import com.example.btl_nhom12_final.R;

import java.util.ArrayList;

public class AdapterIndex extends ArrayAdapter<HealthIndex> {
    private Context context;
    private ArrayList<HealthIndex> list;

    public AdapterIndex(@NonNull Context context, int itemlv, ArrayList<HealthIndex> list) {
        super(context, R.layout.listview_index, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.listview_index, null, true);
        HealthIndex chiso = list.get(position);

        TextView date = view.findViewById(R.id.txtngaythangdo);
        date.setText(chiso.getDate());

        TextView cannang = view.findViewById(R.id.txtcannang);
        cannang.setText(chiso.getWeight().toString());

        TextView chieucao = view.findViewById(R.id.txtchieucao);
        chieucao.setText(chiso.getHeight().toString());

        TextView bmi = view.findViewById(R.id.txtbmi);
        bmi.setText(chiso.getBmi().toString());

        return view;
    }

}

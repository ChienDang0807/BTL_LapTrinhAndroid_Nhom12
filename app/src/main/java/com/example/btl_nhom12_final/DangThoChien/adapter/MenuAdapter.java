package com.example.btl_nhom12_final.DangThoChien.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.btl_nhom12_final.R;
import com.example.btl_nhom12_final.DangThoChien.entity.Menu;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ThucDonViewHolder> {


    private List<Menu> thucDonList;
    private Context context;

    public MenuAdapter(Context context, List<Menu> thucDonList) {
        this.context = context;
        this.thucDonList = thucDonList;
    }

    @NonNull
    @Override
    public ThucDonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_thucdon, parent, false);
        return new ThucDonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThucDonViewHolder holder, int position) {
        Menu thucDon = thucDonList.get(position);

        holder.tvTenThucDon.setText(thucDon.getTenThucDon());
        holder.tvTotalCalories.setText("Tổng calo: " + thucDon.getTotalCalories());

        // Cài đặt RecyclerView cho danh sách món ăn, nhưng tránh tạo lại adapter mỗi lần
        if (holder.rvDanhSachMonAn.getAdapter() == null) {
            FoodAdapterForMenu foodAdapter = new FoodAdapterForMenu(thucDon.getDanhSachMonAn());
            holder.rvDanhSachMonAn.setLayoutManager(new LinearLayoutManager(context));
            holder.rvDanhSachMonAn.setAdapter(foodAdapter);
        }
    }

    @Override
    public int getItemCount() {
        return thucDonList.size();
    }

    static class ThucDonViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenThucDon, tvTotalCalories;
        RecyclerView rvDanhSachMonAn;

        public ThucDonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenThucDon = itemView.findViewById(R.id.tvTenThucDon);
            tvTotalCalories = itemView.findViewById(R.id.tvTotalCalories);
            rvDanhSachMonAn = itemView.findViewById(R.id.rvDanhSachMonAn);
        }
    }
}
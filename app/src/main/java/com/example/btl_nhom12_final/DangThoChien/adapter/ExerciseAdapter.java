package com.example.btl_nhom12_final.DangThoChien.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.btl_nhom12_final.R;
import com.example.btl_nhom12_final.DangThoChien.entity.Exercise;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.BaiTapViewHolder> {
    private final List<Exercise> baiTapList;

    public ExerciseAdapter(List<Exercise> baiTapList) {
        this.baiTapList = baiTapList;
    }

    @NonNull
    @Override
    public BaiTapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_baitap, parent, false);
        return new BaiTapViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaiTapViewHolder holder, int position) {
        Exercise baiTap = baiTapList.get(position);
        holder.tvTenBaiTap.setText(baiTap.getTenBaiTap());
        holder.tvThoiGianTap.setText(baiTap.getThoiGianTap());
        holder.tvMoTa.setText(baiTap.getMoTa());

        // Bắt sự kiện click để hiển thị mô tả
        holder.itemView.setOnClickListener(v -> {
            if (holder.tvMoTa.getVisibility() == View.GONE) {
                holder.tvMoTa.setVisibility(View.VISIBLE); // Hiển thị mô tả
            } else {
                holder.tvMoTa.setVisibility(View.GONE); // Ẩn mô tả
            }
        });
    }

    @Override
    public int getItemCount() {
        return baiTapList.size();
    }

    static class BaiTapViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenBaiTap, tvThoiGianTap, tvMoTa;

        public BaiTapViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenBaiTap = itemView.findViewById(R.id.tvTenBaiTap);
            tvThoiGianTap = itemView.findViewById(R.id.tvThoiGianTap);
            tvMoTa = itemView.findViewById(R.id.tvMoTa);
        }
    }
}

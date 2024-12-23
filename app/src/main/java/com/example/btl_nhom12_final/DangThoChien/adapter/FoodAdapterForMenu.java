package com.example.btl_nhom12_final.DangThoChien.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.btl_nhom12_final.DangThoChien.entity.Food;
import com.example.btl_nhom12_final.R;

import java.util.List;

public class FoodAdapterForMenu extends RecyclerView.Adapter<FoodAdapterForMenu.ViewHolder> {

    private List<Food> foodList;

    public FoodAdapterForMenu(List<Food> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.tvFoodName.setText(food.getFoodName());
        holder.tvCalories.setText("Calories: " + food.getCalories());
        holder.tvServingSize.setText("Serving size: " + food.getServingSize());
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFoodName, tvCalories , tvServingSize;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvCalories = itemView.findViewById(R.id.tvCalories);
            tvServingSize=itemView.findViewById(R.id.tvServingSize);
        }
    }
}

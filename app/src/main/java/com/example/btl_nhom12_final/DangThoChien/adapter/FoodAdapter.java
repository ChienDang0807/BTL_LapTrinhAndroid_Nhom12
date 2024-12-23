package com.example.btl_nhom12_final.DangThoChien.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.btl_nhom12_final.DangThoChien.entity.Food;
import com.example.btl_nhom12_final.R;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodCaloriesViewHolder> {

    private List<Food> foodList;

    public FoodAdapter(List<Food> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodCaloriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_calories, parent, false);
        return new FoodCaloriesViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FoodCaloriesViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.foodName.setText(food.getFoodName());
        holder.servingSize.setText("Serving Size: " + food.getServingSize());
        holder.calories.setText("Calories: " + food.getCalories());
        holder.kj.setText("kJ: " + food.getKj());
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    static class FoodCaloriesViewHolder extends RecyclerView.ViewHolder {
        TextView foodName, servingSize, calories, kj;

        public FoodCaloriesViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.foodName);
            servingSize = itemView.findViewById(R.id.servingSize);
            calories = itemView.findViewById(R.id.calories);
            kj = itemView.findViewById(R.id.kj);
        }
    }
}

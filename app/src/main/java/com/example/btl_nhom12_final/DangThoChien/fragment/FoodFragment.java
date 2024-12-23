package com.example.btl_nhom12_final.DangThoChien.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_nhom12_final.DangThoChien.adapter.FoodAdapter;
import com.example.btl_nhom12_final.DangThoChien.entity.Food;
import com.example.btl_nhom12_final.DangThoChien.repository.FoodRepository;
import com.example.btl_nhom12_final.R;
import com.example.btl_nhom12_final.SQLite;

import java.util.ArrayList;
import java.util.List;

public class FoodFragment extends Fragment {

    private RecyclerView recyclerView;
    private FoodAdapter adapter;
    private FoodRepository foodRepository;
    private List<Food> foodList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chiso, container, false);

        // Khởi tạo RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Khởi tạo cơ sở dữ liệu và repository
        SQLite dbHelper = new SQLite(requireContext());
        foodRepository = new FoodRepository(dbHelper);
        foodList = new ArrayList<>(); // Đảm bảo không null

        try {
            // Mở kết nối cơ sở dữ liệu
            foodRepository.open();

            // Lấy danh sách món ăn
            foodList = foodRepository.getAllFoods();

            if (foodList == null || foodList.isEmpty()) {
              //  Log.d("FragmentChiso", "Danh sách món ăn trống");
            }
        } catch (Exception e) {
            Log.e("FragmentChiso", "Lỗi khi lấy danh sách món ăn", e);
        } finally {
            // Đóng kết nối cơ sở dữ liệu
            foodRepository.close();
        }

        // Khởi tạo Adapter và gán vào RecyclerView
        adapter = new FoodAdapter(foodList);
        recyclerView.setAdapter(adapter);

        return view;
    }

}
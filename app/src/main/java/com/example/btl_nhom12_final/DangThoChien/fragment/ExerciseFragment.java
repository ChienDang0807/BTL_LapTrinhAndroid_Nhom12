package com.example.btl_nhom12_final.DangThoChien.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.btl_nhom12_final.DangThoChien.adapter.ExerciseAdapter;
import com.example.btl_nhom12_final.DangThoChien.entity.Exercise;
import com.example.btl_nhom12_final.DangThoChien.repository.ExerciseRepository;
import com.example.btl_nhom12_final.R;
import com.example.btl_nhom12_final.SQLite;

import java.util.List;

public class ExerciseFragment extends Fragment {

    private RecyclerView recyclerView;
    private ExerciseAdapter adapter;
    private List<Exercise> baiTapList;
    private ExerciseRepository baiTapRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_baitap, container, false);

        // Khởi tạo RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Khởi tạo BaiTapRepository và mở kết nối cơ sở dữ liệu
        baiTapRepository = new ExerciseRepository(new SQLite(getContext()));
        baiTapRepository.open();

        // Lấy danh sách bài tập từ cơ sở dữ liệu
        baiTapList = baiTapRepository.getAllBaiTap();

        // Khởi tạo Adapter và thiết lập cho RecyclerView
        adapter = new ExerciseAdapter(baiTapList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Đóng kết nối cơ sở dữ liệu khi Fragment bị huỷ
        baiTapRepository.close();
    }
}

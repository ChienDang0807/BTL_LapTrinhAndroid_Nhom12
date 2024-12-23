package com.example.btl_nhom12_final.DangThoChien.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.btl_nhom12_final.DangThoChien.adapter.MenuAdapter;
import com.example.btl_nhom12_final.DangThoChien.entity.Menu;
import com.example.btl_nhom12_final.DangThoChien.repository.MenuRepository;
import com.example.btl_nhom12_final.R;
import com.example.btl_nhom12_final.SQLite;

import java.util.List;

public class MenuFragment extends Fragment {
    private RecyclerView rvDanhSachThucDon;
    private MenuAdapter thucDonAdapter;
    private MenuRepository thucDonRepository;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thucdon, container, false);

        // Khởi tạo RecyclerView
        rvDanhSachThucDon = view.findViewById(R.id.rvDanhSachThucDon);
        rvDanhSachThucDon.setLayoutManager(new LinearLayoutManager(getContext()));

        // Khởi tạo ThucDonRepository
        SQLite dbHelper = new SQLite(getContext());
        thucDonRepository = new MenuRepository(dbHelper);

        // Mở kết nối cơ sở dữ liệu
        thucDonRepository.open();

        // Lấy danh sách thực đơn từ cơ sở dữ liệu
        List<Menu> danhSachThucDon = thucDonRepository.getAllThucDon();

        // Log kiểm tra số lượng thực đơn
        Log.d("ThucDonFragment", "Số lượng thực đơn: " + danhSachThucDon.size());

        if (danhSachThucDon.isEmpty()) {
            Log.d("ThucDonFragment", "Không có thực đơn nào.");
        }

        // Cài đặt adapter
        thucDonAdapter = new MenuAdapter(getContext(), danhSachThucDon);
        rvDanhSachThucDon.setAdapter(thucDonAdapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Đóng kết nối cơ sở dữ liệu khi fragment bị hủy
        if (thucDonRepository != null) {
            thucDonRepository.close();
        }
    }
}


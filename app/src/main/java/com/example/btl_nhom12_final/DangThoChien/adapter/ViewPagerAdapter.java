package com.example.btl_nhom12_final.DangThoChien.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.btl_nhom12_final.DangThoChien.fragment.ExerciseFragment;
import com.example.btl_nhom12_final.DangThoChien.fragment.FoodFragment;
import com.example.btl_nhom12_final.DangThoChien.fragment.MenuFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {



    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm,behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new MenuFragment();
            case 1: return new ExerciseFragment();
            case 2:  return new FoodFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}

package com.example.btl_nhom12_final.DangThoChien.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.btl_nhom12_final.DangThoChien.adapter.ViewPagerAdapter;
import com.example.btl_nhom12_final.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RecommendActivity extends AppCompatActivity {
    private BottomNavigationView navigationView;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dexuat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        navigationView=findViewById(R.id.bottom_nav);
        viewPager=findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT); // goi resume
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                   case 0: navigationView.getMenu().findItem(R.id.mthucdon).setChecked(true);
                        break;
                   case 1: navigationView.getMenu().findItem(R.id.mbaitap).setChecked(true);
                        break;
                   case 2: navigationView.getMenu().findItem(R.id.monan).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.mthucdon) {
                viewPager.setCurrentItem(0);
            } else if (id == R.id.mbaitap) {
                viewPager.setCurrentItem(1);
            } else if (id == R.id.monan) {
                viewPager.setCurrentItem(2);
            }
            return true;
        }
    });
    }
}
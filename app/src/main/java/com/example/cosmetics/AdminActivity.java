package com.example.cosmetics;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AdminActivity extends AppCompatActivity {

    // khai bao
    private BottomNavigationView bottom_nav;
    private ViewPager view_pager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        //anh xa
        bottom_nav = findViewById(R.id.bottom_nav);
        view_pager = findViewById(R.id.view_pager);


        setUpViewPage();

        bottom_nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_home) {
                    view_pager.setCurrentItem(0);
                    return true;
                } else if (item.getItemId() == R.id.action_product) {
                    view_pager.setCurrentItem(1);
                    return true;
                } else if (item.getItemId() == R.id.action_customer) {
                    view_pager.setCurrentItem(2);
                    return true;
                }
                return false;
            }
        });



    }

    private void setUpViewPage(){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        view_pager.setAdapter(viewPagerAdapter);

        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottom_nav.getMenu().findItem(R.id.action_home).setChecked(true);
                        break;
                    case 1:
                        bottom_nav.getMenu().findItem(R.id.action_product).setChecked(true);
                        break;
                    case 2:
                        bottom_nav.getMenu().findItem(R.id.action_customer).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
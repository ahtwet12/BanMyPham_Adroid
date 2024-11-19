
package com.example.cosmetics;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.jetbrains.annotations.NotNull;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NotNull
    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new ProductFragment();
            case 2:
                return new CustomerFragment();
            default:
                return new HomeFragment();
        }

    }
    @Override
    public int getCount(){
        return 3;
    }
}

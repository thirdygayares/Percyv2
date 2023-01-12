package com.firetera.percyv2.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.firetera.percyv2.LogIn.LogInFragment.EmailFragment;
import com.firetera.percyv2.LogIn.LogInFragment.MobileFragment;

public class MyViewPagerAdapter extends FragmentStateAdapter {
    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new EmailFragment();
            case 1:
                return  new MobileFragment();
            default:
                return new EmailFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

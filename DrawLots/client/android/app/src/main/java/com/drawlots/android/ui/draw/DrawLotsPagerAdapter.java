package com.drawlots.android.ui.draw;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.drawlots.android.model.Building;

import java.util.List;

public class DrawLotsPagerAdapter extends FragmentStateAdapter {
    
    private List<Building> availableBuildings;
    private List<Building> selectedBuildings;
    
    public DrawLotsPagerAdapter(@NonNull FragmentActivity fragmentActivity,
                                List<Building> availableBuildings,
                                List<Building> selectedBuildings) {
        super(fragmentActivity);
        this.availableBuildings = availableBuildings;
        this.selectedBuildings = selectedBuildings;
    }
    
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            // 可用楼层页面
            return BuildingListFragment.newInstance(availableBuildings, true);
        } else {
            // 已选楼层页面
            return BuildingListFragment.newInstance(selectedBuildings, false);
        }
    }
    
    @Override
    public int getItemCount() {
        return 2; // 两个页面：可用楼层和已选楼层
    }
} 
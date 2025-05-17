package com.drawlots.android.ui.draw;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.drawlots.android.R;
import com.drawlots.android.model.Building;

import java.util.List;

public class BuildingAdapter extends RecyclerView.Adapter<BuildingAdapter.BuildingViewHolder> {
    
    private List<Building> buildings;
    private boolean selectable;
    private OnBuildingSelectListener listener;
    
    public interface OnBuildingSelectListener {
        void onBuildingSelect(Building building);
    }
    
    public BuildingAdapter(List<Building> buildings, boolean selectable, OnBuildingSelectListener listener) {
        this.buildings = buildings;
        this.selectable = selectable;
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public BuildingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_building, parent, false);
        return new BuildingViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull BuildingViewHolder holder, int position) {
        Building building = buildings.get(position);
        holder.bind(building, selectable, listener);
    }
    
    @Override
    public int getItemCount() {
        return buildings.size();
    }
    
    static class BuildingViewHolder extends RecyclerView.ViewHolder {
        
        private TextView tvBuildingName;
        private TextView tvStatus;
        private Button btnSelect;
        
        public BuildingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBuildingName = itemView.findViewById(R.id.tv_building_name);
            tvStatus = itemView.findViewById(R.id.tv_status);
            btnSelect = itemView.findViewById(R.id.btn_select);
        }
        
        public void bind(final Building building, boolean selectable, final OnBuildingSelectListener listener) {
            tvBuildingName.setText(building.getName());
            
            if (building.getIsSelected()) {
                // 已被选择的楼层
                tvStatus.setText("已被选择");
                btnSelect.setVisibility(View.GONE);
            } else {
                // 可选择的楼层
                tvStatus.setText("可选择");
                btnSelect.setVisibility(selectable ? View.VISIBLE : View.GONE);
                
                btnSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onBuildingSelect(building);
                        }
                    }
                });
            }
        }
    }
} 
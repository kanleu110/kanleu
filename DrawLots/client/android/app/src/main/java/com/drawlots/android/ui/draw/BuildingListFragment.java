package com.drawlots.android.ui.draw;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drawlots.android.R;
import com.drawlots.android.api.ApiClient;
import com.drawlots.android.model.Building;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuildingListFragment extends Fragment implements BuildingAdapter.OnBuildingSelectListener {
    
    private static final String ARG_BUILDINGS = "buildings";
    private static final String ARG_SELECTABLE = "selectable";
    
    private List<Building> buildings;
    private boolean selectable;
    
    private RecyclerView recyclerView;
    private TextView tvEmpty;
    private BuildingAdapter adapter;
    
    public BuildingListFragment() {
        // Required empty public constructor
    }
    
    public static BuildingListFragment newInstance(List<Building> buildings, boolean selectable) {
        BuildingListFragment fragment = new BuildingListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_BUILDINGS, (Serializable) buildings);
        args.putBoolean(ARG_SELECTABLE, selectable);
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            buildings = (List<Building>) getArguments().getSerializable(ARG_BUILDINGS);
            selectable = getArguments().getBoolean(ARG_SELECTABLE);
        }
        
        if (buildings == null) {
            buildings = new ArrayList<>();
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_building_list, container, false);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        recyclerView = view.findViewById(R.id.recycler_view);
        tvEmpty = view.findViewById(R.id.tv_empty);
        
        setupRecyclerView();
        updateEmptyView();
    }
    
    private void setupRecyclerView() {
        adapter = new BuildingAdapter(buildings, selectable, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
    
    private void updateEmptyView() {
        if (buildings.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
        }
    }
    
    @Override
    public void onBuildingSelect(Building building) {
        // 调用API进行抽签
        ApiClient.getApiService().selectBuilding(building.getId()).enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    boolean success = (boolean) response.body().get("success");
                    String message = (String) response.body().get("message");
                    
                    if (success) {
                        Toast.makeText(getContext(), R.string.draw_success, Toast.LENGTH_SHORT).show();
                        // 刷新数据
                        buildings.remove(building);
                        adapter.notifyDataSetChanged();
                        updateEmptyView();
                    } else {
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), R.string.draw_failed, Toast.LENGTH_SHORT).show();
                }
            }
            
            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Toast.makeText(getContext(), R.string.error_message, Toast.LENGTH_SHORT).show();
            }
        });
    }
} 
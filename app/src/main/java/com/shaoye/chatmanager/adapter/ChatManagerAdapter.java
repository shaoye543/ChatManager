package com.shaoye.chatmanager.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.shaoye.chatmanager.R;
import com.shaoye.chatmanager.databinding.ItemManagedAppBinding;
import com.shaoye.chatmanager.model.AppInfo;

import java.util.List;

public class ChatManagerAdapter extends RecyclerView.Adapter<ChatManagerAdapter.ChatManagerViewHolder> {
    private static final String TAG = "ChatManagerAdapter";


    private List<AppInfo> mAppInfos;
    private Context mContext;
    ItemManagedAppBinding mItemBinding;

    public ChatManagerAdapter(Context context, List<AppInfo> apps) {
        mContext = context;
        mAppInfos = apps;
    }

    @NonNull
    @Override
    public ChatManagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemManagedAppBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_managed_app, parent, false);
        return new ChatManagerViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ChatManagerViewHolder holder, int position) {
        AppInfo info = mAppInfos.get(position);
        ItemManagedAppBinding binding = DataBindingUtil.getBinding(holder.itemView);
        binding.managedAppIcon.setImageResource(R.mipmap.ic_launcher_round);
        binding.managedAppIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick " + info);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAppInfos.size();
    }

    public void setAppInfos(List<AppInfo> appInfos) {
        mAppInfos = appInfos;
    }

    static class ChatManagerViewHolder extends RecyclerView.ViewHolder {

        public ChatManagerViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

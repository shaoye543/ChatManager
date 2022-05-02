package com.shaoye.chatmanager.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shaoye.chatmanager.R;
import com.shaoye.chatmanager.model.AppInfo;

import java.util.List;

public class ChatManagerAdapter extends RecyclerView.Adapter<ChatManagerAdapter.ChatManagerViewHolder> {
    private static final String TAG = "ChatManagerAdapter";

    private static final int APP_ICON = 0;
    private static final int APP_ADD = 1;

    private List<AppInfo> mAppInfos;
    private Context mContext;

    public ChatManagerAdapter(Context context, List<AppInfo> apps) {
        mContext = context;
        mAppInfos = apps;
    }

    @NonNull
    @Override
    public ChatManagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_app_info, parent, false);
        return new ChatManagerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatManagerViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == APP_ICON) {
            AppInfo info = mAppInfos.get(position);
            holder.mIcon.setImageDrawable(info.getIcon());
//            holder.mAppName.setText(info.getName());
            holder.mIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(TAG, "onClick " + info);
                }
            });
        } else if (viewType == APP_ADD) {
            holder.mIcon.setImageResource(R.drawable.round_add);
//            holder.mAppName.setText("添加应用");
        }
    }

    @Override
    public int getItemCount() {
        return mAppInfos.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mAppInfos.size()) {
            return APP_ICON;
        }
        return APP_ADD;
    }

    public void setAppInfos(List<AppInfo> appInfos) {
        mAppInfos = appInfos;
    }

    static class ChatManagerViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIcon;
        private TextView mAppName;

        public ChatManagerViewHolder(@NonNull View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.app_icon);
//            mAppName = itemView.findViewById(R.id.app_name);
        }
    }
}

package com.shaoye.chatmanager.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shaoye.chatmanager.R;

/**
 * 给管理的应用列表添加点间距
 */
public class ChatItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "ItemDecoration";

    private Context mContext;

    public ChatItemDecoration(Context context) {
        mContext = context;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int margin = mContext.getResources().getDimensionPixelSize(R.dimen.chat_list_item_margin);
        int position = parent.getChildAdapterPosition(view);
        LinearLayoutManager manager = (LinearLayoutManager) parent.getLayoutManager();
        if (manager != null) {
            int orientation = manager.getOrientation();
            if (position != 0) {
                if (orientation == RecyclerView.VERTICAL) {
                    outRect.top = margin;
                } else if (orientation == RecyclerView.HORIZONTAL) {
                    outRect.left = margin;
                }
            }
        }
    }

}

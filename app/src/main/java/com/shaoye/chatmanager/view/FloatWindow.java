package com.shaoye.chatmanager.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shaoye.chatmanager.R;
import com.shaoye.chatmanager.adapter.ChatItemDecoration;
import com.shaoye.chatmanager.adapter.ChatManagerAdapter;
import com.shaoye.chatmanager.model.AppInfo;

import java.util.ArrayList;
import java.util.List;

public class FloatWindow {
    private static final String TAG = "FloatWindowManager";

    private static FloatWindow sInstance;

    private List<AppInfo> mAppInfos = new ArrayList<>();
    private boolean mShowing = false;

    private Context mContext;
    private WindowManager mWindowManager;
    private ChatManagerAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private WindowManager.LayoutParams mParams;
    
    private View mFloatView;
    private RecyclerView mAppList;

    private FloatWindow(Context context) {
        mContext = context;
    }

    public static FloatWindow getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new FloatWindow(context);
        }
        return sInstance;
    }

    public List<AppInfo> getAppInfos() {
        return mAppInfos;
    }

    public void setOrientation(int orientation) {
        mLayoutManager.setOrientation(orientation);
        if (mWindowManager != null) {
            mWindowManager.updateViewLayout(mFloatView, mParams);
        }
    }

    public void createFloatView(List<AppInfo> appInfos) {
        if (mFloatView != null) {
            return;
        }
        mAppInfos = appInfos;
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mFloatView = LayoutInflater.from(mContext).inflate(R.layout.float_window_chat_manager_bar, null, false);
        mAppList = mFloatView.findViewById(R.id.apps_list);
        mLayoutManager = new LinearLayoutManager(mContext);
        mAdapter = new ChatManagerAdapter(mContext, mAppInfos);
        initWindowParams();

        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mAppList.setAdapter(mAdapter);
        mAppList.addItemDecoration(new ChatItemDecoration(mContext));
        mAppList.setLayoutManager(mLayoutManager);
    }

    private void initWindowParams() {
        mParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            mParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        //设置背景透明  注意不是RGBX_8888   痛、非常痛
        suitWindowSize(LinearLayoutManager.HORIZONTAL);
        mParams.format = PixelFormat.RGBA_8888;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
    }

    private void suitWindowSize(int orientation) {
        if (mParams == null) {
            return;
        }
        DisplayMetrics metrics = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(metrics);
        int margin = mContext.getResources().getDimensionPixelSize(R.dimen.chat_list_item_margin);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mAppList.getLayoutParams();
        if (orientation == LinearLayoutManager.VERTICAL) {
            params.topMargin = margin;
            params.bottomMargin = margin;
            mParams.width = mContext.getResources().getDimensionPixelSize(R.dimen.float_window_width);
            mParams.height = (int) (metrics.heightPixels * 0.6f);
        } else if (orientation == LinearLayoutManager.HORIZONTAL) {
            params.leftMargin = margin;
            params.rightMargin = margin;
            mParams.width = (int) (metrics.widthPixels * 0.8f);
            mParams.height = mContext.getResources().getDimensionPixelSize(R.dimen.float_window_width);
        }
    }

    public void updateView() {
        Log.e(TAG, "updateView: " + isCreated());
        if (isCreated()) {
            Log.e(TAG, "updateView: " + mAppInfos.size());
//            mWindowManager.updateViewLayout(mFloatView, mParams);
            mAdapter.notifyDataSetChanged();
        }
    }

    private boolean isCreated() {
        return mAppList != null && mParams != null;
    }

    public void showWindow() {
        if (!mShowing && mAppList != null) {
            mShowing = true;
            mWindowManager.addView(mFloatView, mParams);
        }
    }

    public void dismiss() {
        if (mShowing) {
            mWindowManager.removeViewImmediate(mFloatView);
        }
    }

}

package com.shaoye.chatmanager.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.shaoye.chatmanager.R;
import com.shaoye.chatmanager.adapter.ChatItemDecoration;
import com.shaoye.chatmanager.adapter.ChatManagerAdapter;
import com.shaoye.chatmanager.databinding.FloatWindowChatManagerBarBinding;
import com.shaoye.chatmanager.model.AppInfo;
import com.shaoye.chatmanager.model.ChatBarInfo;

import java.util.ArrayList;
import java.util.List;

public class FloatWindow {
    private static final String TAG = "FloatWindowManager";

    private static FloatWindow sInstance;

//    private ChatBarInfo mChatBarInfo = new ChatBarInfo();
    private List<AppInfo> mAppInfos = new ArrayList<>();

    private Context mContext;

    private WindowManager mWindowManager;
    private ChatManagerAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private FloatWindowChatManagerBarBinding mFloatBinding;
    private WindowManager.LayoutParams mParams;

    private FloatWindow(Context context) {
        mContext = context;
    }

    public static FloatWindow getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new FloatWindow(context);
        }
        return sInstance;
    }

//    public void setAppInfos(List<AppInfo> appInfos) {
//        mAppInfos = appInfos;
//        mAdapter.setAppInfos(appInfos);
//    }

    public void setOrientation(int orientation) {
        mLayoutManager.setOrientation(orientation);
        if (mWindowManager != null) {
            mWindowManager.updateViewLayout(mFloatBinding.getRoot(), mParams);
        }
    }

    public void createFloatView(List<AppInfo> appInfos) {
        mAppInfos = appInfos;
        if (mFloatBinding != null) {
            return;
        }
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mLayoutManager = new LinearLayoutManager(mContext);
        mFloatBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.float_window_chat_manager_bar, null, false);
        mAdapter = new ChatManagerAdapter(mContext, mAppInfos);
        initWindowParams();
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mFloatBinding.managedAppsList.setAdapter(mAdapter);
        mFloatBinding.managedAppsList.addItemDecoration(new ChatItemDecoration(mContext));
        mFloatBinding.managedAppsList.setLayoutManager(mLayoutManager);
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
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mFloatBinding.managedAppsList.getLayoutParams();
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

    public void showWindow() {
        if (mFloatBinding != null) {
            mWindowManager.addView(mFloatBinding.getRoot(), mParams);
        }
    }

}

package com.shaoye.chatmanager.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.shaoye.chatmanager.R;
import com.shaoye.chatmanager.model.AppInfo;
import com.shaoye.chatmanager.view.FloatWindow;

import java.util.ArrayList;
import java.util.List;

public class ChatManagerService extends Service {
    private static final String TAG = "ChatService";

    private FloatWindow mFloatWindow;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: ");
        mFloatWindow = FloatWindow.getInstance(getApplicationContext());
        List<AppInfo> appInfos = new ArrayList<>();
        mFloatWindow.createFloatView(appInfos);
        mFloatWindow.showWindow();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFloatWindow.dismiss();
    }
}

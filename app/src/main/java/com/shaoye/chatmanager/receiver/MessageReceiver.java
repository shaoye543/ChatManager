package com.shaoye.chatmanager.receiver;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.shaoye.chatmanager.constant.MessageConstant;
import com.shaoye.chatmanager.model.AppInfo;
import com.shaoye.chatmanager.view.FloatWindow;

import java.util.Collections;
import java.util.List;

public class MessageReceiver extends BroadcastReceiver {
    private static final String TAG = "MessageReceiver";

    private FloatWindow mFloatWindow;

    @Override
    public void onReceive(Context context, Intent intent) {
        mFloatWindow = FloatWindow.getInstance(context);
        String packageName = intent.getStringExtra(MessageConstant.PACKAGE_NAME);
        Log.e(TAG, "onReceive: " + intent.getAction() + "  " + intent.getIntExtra("notificationNum", 0)
                + "  " + packageName + "  " + intent.getStringExtra(MessageConstant.CLASS_NAME));
        PackageManager manager = context.getPackageManager();
        try {
            ApplicationInfo info = manager.getApplicationInfo(packageName, 0);

            String appName = info.loadLabel(manager).toString();
            Drawable icon = info.loadIcon(manager);
            int messages = intent.getIntExtra(MessageConstant.APPLICATION_NOTIFICATION_NUMBER, 0);

            AppInfo appInfo = getAppInfo(mFloatWindow.getAppInfos(), appName);
            if (appInfo == null) {
                appInfo = new AppInfo(appName, icon, messages);
                Log.e(TAG, "onReceive: " + appInfo);
                mFloatWindow.getAppInfos().add(appInfo);
                mFloatWindow.updateView();
            } else {
                appInfo.setMessages(intent.getIntExtra(MessageConstant.APPLICATION_NOTIFICATION_NUMBER, 0));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "onReceive: ", e);
        }
    }

    private void updateList(PackageManager manager, Intent intent) {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.setPackage(intent.getStringExtra(MessageConstant.PACKAGE_NAME));
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        mainIntent.setComponent(new ComponentName(intent.getStringExtra(MessageConstant.PACKAGE_NAME), intent.getStringExtra(MessageConstant.CLASS_NAME)));
        ResolveInfo resolveInfo = manager.resolveActivity(mainIntent, 0);
        String appName = resolveInfo.loadLabel(manager).toString();
        AppInfo appInfo = getAppInfo(mFloatWindow.getAppInfos(), appName);
        Log.e(TAG, "onReceive: " + mFloatWindow.getAppInfos().size() + "  " + (appInfo == null));

        if (appInfo == null) {
            appInfo = new AppInfo(appName, resolveInfo.loadIcon(manager), intent.getIntExtra(MessageConstant.APPLICATION_NOTIFICATION_NUMBER, 0));
            Log.e(TAG, "onReceive: " + appInfo);
            mFloatWindow.getAppInfos().add(appInfo);
            mFloatWindow.updateView();
        } else {
            appInfo.setMessages(intent.getIntExtra(MessageConstant.APPLICATION_NOTIFICATION_NUMBER, 0));
        }
    }

    private AppInfo getAppInfo(List<AppInfo> infos, String name) {
        for (AppInfo info : infos) {
            if (name.equals(info.getName())) {
                return info;
            }
        }
        return null;
    }

}

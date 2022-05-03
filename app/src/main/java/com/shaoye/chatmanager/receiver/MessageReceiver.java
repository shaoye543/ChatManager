package com.shaoye.chatmanager.receiver;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.shaoye.chatmanager.constant.MessageConstant;
import com.shaoye.chatmanager.model.AppInfo;
import com.shaoye.chatmanager.view.FloatWindow;

import java.util.List;

/**
 * @date: 开元4719年5月03
 * @author: shaoye
 * @description: 可以监听发送角标信息的广播
 */
public class MessageReceiver extends BroadcastReceiver {
    private static final String TAG = "MessageReceiver";

    private FloatWindow mFloatWindow;

    @Override
    public void onReceive(Context context, Intent intent) {
        mFloatWindow = FloatWindow.getInstance(context);
        String packageName = intent.getStringExtra(MessageConstant.PACKAGE_NAME);
        int messages = getMessageCount(intent);
        Log.e(TAG, "onReceive: " + intent.getAction() + "  " + messages + "  " + packageName
                + "  " + intent.getStringExtra(MessageConstant.CLASS_NAME));
        PackageManager manager = context.getPackageManager();
        try {
            ApplicationInfo info = manager.getApplicationInfo(packageName, 0);

            String appName = info.loadLabel(manager).toString();
            Drawable icon = info.loadIcon(manager);

            AppInfo appInfo = getAppInfo(mFloatWindow.getAppInfos(), appName);
            if (appInfo == null && messages > 0) {
                appInfo = new AppInfo(appName, icon, messages);
                Log.e(TAG, "onReceive: " + appInfo);
                mFloatWindow.getAppInfos().add(appInfo);
            } else if (appInfo != null) {
                appInfo.setMessages(messages);
            }
            mFloatWindow.updateView();
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "onReceive: ", e);
        }
    }

    /**
     * 目前只有vivo手机
     */
    private int getMessageCount(Intent intent) {
        return intent.getIntExtra(MessageConstant.APPLICATION_NOTIFICATION_NUMBER, 0);
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

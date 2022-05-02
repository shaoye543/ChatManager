package com.shaoye.chatmanager;

import android.app.Application;
import android.content.Intent;

import com.shaoye.chatmanager.service.ChatManagerService;

public class ChatManagerApplication extends Application {
    private static final String TAG = "ChatManagerApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        Intent intent = new Intent(this, ChatManagerService.class);
//        startService(intent);

    }
}

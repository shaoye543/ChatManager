package com.shaoye.chatmanager;

import android.app.Application;
import android.app.Service;
import android.content.Intent;

import com.shaoye.chatmanager.service.ChatService;

public class ChatManagerApplication extends Application {
    private static final String TAG = "ChatManagerApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        Intent intent = new Intent(this, ChatService.class);
        startService(intent);

    }
}

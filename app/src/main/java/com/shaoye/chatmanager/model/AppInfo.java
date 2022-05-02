package com.shaoye.chatmanager.model;

import android.graphics.drawable.Drawable;

public class AppInfo {
    private static final String TAG = "AppInfo";

    private String name;
    private Drawable icon;
    private int messages;

    public AppInfo() {
    }

    public AppInfo(String name, Drawable icon, int messages) {
        this.name = name;
        this.icon = icon;
        this.messages = messages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public int getMessages() {
        return messages;
    }

    public void setMessages(int messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "name='" + name + '\'' +
                ", message count=" + messages +
                '}';
    }
}

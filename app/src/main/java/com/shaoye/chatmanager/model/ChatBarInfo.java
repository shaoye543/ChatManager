package com.shaoye.chatmanager.model;

import androidx.recyclerview.widget.RecyclerView;

public class ChatBarInfo {

    private int orientation;
    private int verticalMargin;
    private int horizontalMargin;

    public ChatBarInfo() {
        this.orientation = RecyclerView.HORIZONTAL;
        this.verticalMargin = 0;
        this.horizontalMargin = 0;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getVerticalMargin() {
        return verticalMargin;
    }

    public void setVerticalMargin(int verticalMargin) {
        this.verticalMargin = verticalMargin;
    }

    public int getHorizontalMargin() {
        return horizontalMargin;
    }

    public void setHorizontalMargin(int horizontalMargin) {
        this.horizontalMargin = horizontalMargin;
    }
}

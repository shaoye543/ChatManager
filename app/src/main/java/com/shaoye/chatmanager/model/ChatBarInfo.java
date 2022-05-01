package com.shaoye.chatmanager.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

public class ChatBarInfo extends BaseObservable {

    private int orientation;
    private int verticalMargin;
    private int horizontalMargin;

    public ChatBarInfo() {
        this.orientation = RecyclerView.HORIZONTAL;
        this.verticalMargin = 0;
        this.horizontalMargin = 0;
    }

    @Bindable
    public String name = "dasd";

    @Bindable
    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
        notifyPropertyChanged(BR.orientation);
    }

    @Bindable
    public int getVerticalMargin() {
        return verticalMargin;
    }

    public void setVerticalMargin(int verticalMargin) {
        this.verticalMargin = verticalMargin;
        notifyPropertyChanged(BR.verticalMargin);
    }

    @Bindable
    public int getHorizontalMargin() {
        return horizontalMargin;
    }

    public void setHorizontalMargin(int horizontalMargin) {
        this.horizontalMargin = horizontalMargin;
        notifyPropertyChanged(BR.horizontalMargin);
    }
}

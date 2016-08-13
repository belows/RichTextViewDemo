package com.example.mingjing.richtextviewdemo;

import android.text.Spannable;

/**
 * Created by mingjing on 16-8-12.
 */
public abstract class RichTextItem {
    protected int startIndex;
    protected int endIndex;
    protected Spannable showedText;
    public RichTextItem(int startIndex, int endIndex,Spannable showedText) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.showedText = showedText;
    }
}

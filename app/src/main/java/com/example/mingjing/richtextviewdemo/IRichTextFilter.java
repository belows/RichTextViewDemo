package com.example.mingjing.richtextviewdemo;

import android.text.Spannable;

/**
 * Created by mingjing on 16-8-12.
 */
public interface IRichTextFilter {
    Spannable filter(CharSequence contentText);
}

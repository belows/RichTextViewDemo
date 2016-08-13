package com.example.mingjing.richtextviewdemo;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.TextAppearanceSpan;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mingjing on 16-8-12.
 */
public class HighLightFilter implements IRichTextFilter {

    private String mFirstFlag;
    private String mMatchFlag;
    private String mFirstReplaceFlag;
    private String mMatchReplaceFlag;

    private List<HighLightItem> mHighLightItems = new ArrayList<>();

    private Context mContext;

    private IRichTextClickListener mClickListener;

    public HighLightFilter(Context context, String firstFlag, String matchFlag, IRichTextClickListener clickListener) {
        this(context, firstFlag, firstFlag, matchFlag, matchFlag, clickListener);
    }

    public HighLightFilter(Context context, String firstFlag, String fistReplaceFlag, String matchFlag, String matchReplaceFlag, IRichTextClickListener clickListener) {
        mContext = context;
        mFirstFlag = firstFlag;
        mFirstReplaceFlag = fistReplaceFlag;
        mMatchFlag = matchFlag;
        mMatchReplaceFlag = matchReplaceFlag;
        mClickListener = clickListener;
    }

    private static class HighLightItem extends RichTextItem {
        public HighLightItem(int startIndex, int endIndex, Spannable showedText) {
            super(startIndex, endIndex, showedText);
        }
    }

    @Override
    public Spannable filter(CharSequence contentText) {
        mHighLightItems.clear();
        StringBuilder targetStr = new StringBuilder(contentText);
        int firstFlagLength = mFirstFlag.length();
        int matchFlagLength = mMatchFlag.length();
        int firstFlagReplaceLength = mFirstReplaceFlag.length();
        int matchFlagReplaceLength = mMatchReplaceFlag.length();
        int matchFlagIndex = -matchFlagReplaceLength;

        while (true) {
            int firstFlagIndex = targetStr.indexOf(mFirstFlag, matchFlagIndex + matchFlagReplaceLength);
            if (firstFlagIndex == -1) {
                break;
            }
            matchFlagIndex = targetStr.indexOf(mMatchFlag, firstFlagIndex + firstFlagLength);
            if (matchFlagIndex == -1) {
                break;
            }

            targetStr = targetStr.replace(firstFlagIndex, firstFlagLength + firstFlagIndex, mFirstReplaceFlag);
            matchFlagIndex = matchFlagIndex - (firstFlagLength - firstFlagReplaceLength);
            targetStr = targetStr.replace(matchFlagIndex, matchFlagLength + matchFlagIndex, mMatchReplaceFlag);
            SpannableString highLightStr = new SpannableString(targetStr.substring(firstFlagIndex, matchFlagIndex + matchFlagReplaceLength));
            HighLightItem item = new HighLightItem(firstFlagIndex, matchFlagIndex + matchFlagReplaceLength, highLightStr);
            item.startIndex = firstFlagIndex;
            item.endIndex = matchFlagIndex + matchFlagReplaceLength;
            mHighLightItems.add(item);
        }
        ColorStateList colorList = ColorStateList.valueOf(mContext.getResources().getColor(android.R.color.holo_blue_bright));
        Spannable sp = new SpannableString(targetStr);
        for (final HighLightItem item : mHighLightItems) {
            TextAppearanceSpan span = new TextAppearanceSpan("sans-serif", Typeface.NORMAL, (int) 32, colorList, colorList);
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    if(mClickListener != null) {
                        mClickListener.onRichTextClick(item);
                    }
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                }
            };
            sp.setSpan(span, item.startIndex, item.endIndex, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            sp.setSpan(clickableSpan, item.startIndex, item.endIndex, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        }
        return sp;
    }
}

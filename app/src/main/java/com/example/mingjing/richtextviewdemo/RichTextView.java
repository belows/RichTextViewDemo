package com.example.mingjing.richtextviewdemo;

import android.content.Context;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.Touch;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by mingjing on 16-8-12.
 */
public class RichTextView extends TextView {

    private boolean linkHit;

    private IRichTextFilter mFilter;

    public RichTextView(Context context) {
        this(context, null);
    }

    public RichTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMovementMethod(new RichTextMoveMethod());
    }

    public void setFilter(IRichTextFilter filter) {
        mFilter = filter;
    }

    public void setRichText(CharSequence contentText) {
        if (mFilter != null) {
            Spannable richText = mFilter.filter(contentText);
            setText(richText);
        } else {
            setText(contentText);
        }
    }

    @Override
    public boolean performClick() {
        if(linkHit){
            return true;
        }
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        linkHit = false;
        return super.onTouchEvent(event);
    }

    private static class RichTextMoveMethod extends LinkMovementMethod {
        @Override

        public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {

            int action = event.getAction();


            if (action == MotionEvent.ACTION_UP ||

                    action == MotionEvent.ACTION_DOWN) {

                int x = (int) event.getX();

                int y = (int) event.getY();


                x -= widget.getTotalPaddingLeft();

                y -= widget.getTotalPaddingTop();


                x += widget.getScrollX();

                y += widget.getScrollY();


                Layout layout = widget.getLayout();

                int line = layout.getLineForVertical(y);

                int off = layout.getOffsetForHorizontal(line, x);


                ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);


                if (link.length != 0) {

                    if (action == MotionEvent.ACTION_UP) {

                        link[0].onClick(widget);

                    } else if (action == MotionEvent.ACTION_DOWN) {

                        Selection.setSelection(buffer,

                                buffer.getSpanStart(link[0]),

                                buffer.getSpanEnd(link[0]));

                    }


                    if (widget instanceof RichTextView) {

                        ((RichTextView) widget).linkHit = true;

                    }

                    return true;

                } else {

                    Selection.removeSelection(buffer);

                    Touch.onTouchEvent(widget, buffer, event);

                    return false;

                }

            }

            return Touch.onTouchEvent(widget, buffer, event);

        }
    }
}

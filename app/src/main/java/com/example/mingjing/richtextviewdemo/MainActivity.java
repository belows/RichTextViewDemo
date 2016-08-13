package com.example.mingjing.richtextviewdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RichTextView richTextView = (RichTextView) findViewById(R.id.rtv_test);
//        richTextView.setFilter(new IRichTextFilter() {
//            @Override
//            public Spannable filter(CharSequence contentText) {
//                String targetString = contentText.toString();
//                targetString = targetString.replace("\r", "\n");
//                String mFirstFlag = "$";
//                int changeIndex = 0;
//                int mHighLightColor = getResources().getColor(android.R.color.holo_blue_bright);
//                String mMatchFlag = "$";
//                int firstFlagIndex = targetString.indexOf(mFirstFlag, changeIndex);
//
//                int matchFlagIndex = changeIndex - 1;
//                ColorStateList colorList = ColorStateList.valueOf(mHighLightColor);
//                final int firstFlagLength = mFirstFlag.length();
//                final int matchFlagLength = mMatchFlag.length();
//
//                Spannable sp = new SpannableString(contentText);
//                while (true) {
//                    if (firstFlagIndex == -1) {
//                        break;
//                    }
//                    matchFlagIndex = targetString.indexOf(mMatchFlag, firstFlagIndex + firstFlagLength);
//                    if (matchFlagIndex == -1) {
//                        break;
//                    }
//
//                    int endIndex = matchFlagIndex + matchFlagLength - 1;
//                    final String highLightStr = targetString.substring(firstFlagIndex + firstFlagLength, endIndex - (matchFlagLength - 1));
//                    TextAppearanceSpan span = new TextAppearanceSpan("sans-serif", Typeface.NORMAL, (int) richTextView.getTextSize(), colorList, colorList);
//                    sp.setSpan(span, firstFlagIndex, endIndex + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    sp.setSpan(new ClickableSpan() {
//                        @Override
//                        public void onClick(View view) {
////                            if (mHighLightClickListener != null) {
////                                mHighLightClickListener.onHighLightClick(mFirstFlag, mMatchFlag, highLightStr);
////                            }
//                        }
//
//                        @Override
//                        public void updateDrawState(TextPaint ds) {
//                            super.updateDrawState(ds);
//                            ds.setUnderlineText(false);
//                        }
//                    }, firstFlagIndex, endIndex, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//
//                    firstFlagIndex = targetString.indexOf(mFirstFlag, endIndex + 1);
//                }
//                return sp;
//            }
//        });
        HighLightFilter filter = new HighLightFilter(this, "$", "{%%", "$", "}", new IRichTextClickListener() {
            @Override
            public void onRichTextClick(RichTextItem item) {
                Toast.makeText(getApplicationContext(),item.showedText,Toast.LENGTH_LONG).show();
            }
        });
        richTextView.setFilter(filter);
        int i = 0;
        richTextView.setRichText("212f$testdfosf$sfwfwe$testssssssdfsdfvsadfsfdafsfdsfsdfsfsdfsfdsfdsfdsdfsdfsdfd$sss");
        richTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TestActivity.class);
                startActivity(intent);
            }
        });

        TextView textView = (TextView) findViewById(R.id.tv_test);
        textView.setMovementMethod(new LinkMovementMethod());
        textView.setText(filter.filter("212f$testdfosf$sfwfwe$testssssssdfsdfvsadfsfdafsfdsfsdfsfsdfsfdsfdsfdsdfsdfsdfd$sss"));
    }
}

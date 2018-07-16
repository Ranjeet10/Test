package com.envent.bottlesup.mvp.view.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by ronem on 3/15/18.
 */

public class BottlesUpBoldTextView extends AppCompatTextView {

    public BottlesUpBoldTextView(Context context) {
        super(context);
        this.setTypeface(getTypeFace(context));
    }

    public BottlesUpBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(getTypeFace(context));
    }

    public BottlesUpBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setTypeface(getTypeFace(context));
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }

    public Typeface getTypeFace(Context context) {
        Typeface face = Typeface.createFromAsset(context.getAssets(), "century_gothic_bold.otf");
        return face;
    }

}
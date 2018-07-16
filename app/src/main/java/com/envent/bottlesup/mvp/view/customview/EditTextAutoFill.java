package com.envent.bottlesup.mvp.view.customview;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

/**
 * Created by ronem on 4/3/18.
 */

public class EditTextAutoFill extends AppCompatEditText implements TextWatcher {

    private boolean isAutoFill = false;

    public EditTextAutoFill(Context context) {
        super(context);
        init();
    }

    public EditTextAutoFill(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextAutoFill(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        addTextChangedListener(this);
    }

    public void setIsAutoFill(boolean isAutoFill) {
        this.isAutoFill = isAutoFill;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (!isAutoFill && getText().toString().trim().length() > 0) {
            isAutoFill = false;
            onEditorAction(getImeActionId());
        }
    }
}


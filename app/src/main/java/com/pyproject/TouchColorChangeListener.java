package com.pyproject;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class TouchColorChangeListener implements View.OnTouchListener {
    private TextView textView;

    public TouchColorChangeListener(TextView textView) {
        this.textView = textView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Change the text color to blue when pressed
                textView.setTextColor(Color.BLUE);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // Revert back to the original color when released or canceled
                textView.setTextColor(Color.BLACK);
                break;
        }
        return false;
    }
}

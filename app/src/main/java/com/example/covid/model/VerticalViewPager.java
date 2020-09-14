package com.example.covid.model;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class VerticalViewPager extends ViewPager {

    public VerticalViewPager(@NonNull Context context) {
        super(context);
        init();
    }

    public VerticalViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        setPageTransformer(true, new VerticalPage());
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    private MotionEvent getIntercambioXY(MotionEvent ev){
        float width = getWidth();
        float height = getHeight();

        float numX = (ev.getY() / height) * width;
        float numY = (ev.getX() / width) * height;

        ev.setLocation(numX, numY);
        return ev;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev){
        boolean intercept = super.onInterceptTouchEvent(getIntercambioXY(ev));
        getIntercambioXY(ev);
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(getIntercambioXY(ev));
    }

    private  class VerticalPage implements ViewPager.PageTransformer{

        @Override
        public void transformPage(@NonNull View view, float position) {
            if (position < -1){
                view.setAlpha(0);
            }else if (position <= 1){
                view.setAlpha(1);
                view.setTranslationX(view.getWidth() * -position);
                float yPosition = position * view.getHeight();
                view.setTranslationY(yPosition);
            }else {
                view.setAlpha(0);
            }
        }
    }
}

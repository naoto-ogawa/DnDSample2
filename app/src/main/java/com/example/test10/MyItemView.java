package com.example.test10;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Map;

public class MyItemView extends LinearLayout {

    private View vU;
    private View vD;
    private TextView tv;
    private Animation inAni;

    public MyItemView(Context context) {
        super(context);
    }

    public MyItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void bind(int pos, Map<String, Object> map, boolean dragover, boolean down) {

        vU = vU == null ? this.findViewById(R.id.indicatorU) : vU;
        vD = vD == null ? this.findViewById(R.id.indicatorD) : vD;
        inAni = inAni == null ?
                AnimationUtils.loadAnimation(this.getContext(), R.anim.in_anim) :
                inAni;
        tv = tv == null ? (TextView) this.findViewById(R.id.text) : tv;

        if (dragover) {
            if (down) {
                vU.setVisibility(View.GONE);
                vD.setVisibility(View.INVISIBLE);
                vD.startAnimation(inAni);
                vD.setVisibility(View.VISIBLE);
            } else {
                vU.setVisibility(View.VISIBLE);
                vD.setVisibility(View.GONE);
            }
        } else {
            vU.setVisibility(View.GONE);
            vD.setVisibility(View.GONE);
        }

        tv.setText(String.format(
                "[%d] (uid:%s) : %s" , pos, map.get("id"), map.get("value")
        ));
    }
}

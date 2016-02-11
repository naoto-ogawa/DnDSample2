package com.example.test10;

import android.content.ClipData;
import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import static android.view.DragEvent.ACTION_DRAG_ENDED;
import static android.view.DragEvent.ACTION_DRAG_EXITED;
import static android.view.DragEvent.ACTION_DRAG_LOCATION;
import static android.view.DragEvent.ACTION_DRAG_STARTED;
import static android.view.DragEvent.ACTION_DROP;

public class MyListView
        extends ListView
        implements AdapterView.OnItemLongClickListener, View.OnDragListener {

    public MyListView(Context context) {
        super(context);
        addListener();
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addListener();
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addListener();
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        // event location
        float x = event.getX();
        float y = event.getY();

        // current position
        int pos = pointToPosition((int) x, (int) y);

        int posF = this.getFirstVisiblePosition();
        int posL = this.getLastVisiblePosition();

        MyAdapter adapter = ((MyAdapter) getAdapter());
        switch (event.getAction()) {
            case ACTION_DRAG_STARTED:
                ((DragInfo) event.getLocalState()).isDragDown(x, y);
                break;
            case ACTION_DRAG_LOCATION:
                boolean down = ((DragInfo) event.getLocalState()).isDragDown(x, y);
                adapter.setDragPosition(pos, down);
                break;
            case ACTION_DROP:
                String ori = event.getClipData().getItemAt(0).getText().toString();
                boolean down2 = ((DragInfo) event.getLocalState()).isDragDown(x, y);
                adapter.swap(Integer.parseInt(ori), down2 ? pos + 1 : pos);
                break;
            case ACTION_DRAG_EXITED:
                adapter.clearDragPosition();
                break;
            case ACTION_DRAG_ENDED:
                adapter.clearDragPosition();
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        ClipData data = ClipData.newPlainText("dragging item no", "" + position);
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
        view.startDrag(data, shadowBuilder, new DragInfo(), 0);
        return true;
    }

    private void addListener() {
        setOnItemLongClickListener(this);
        setOnDragListener(this);
    }

    private class DragInfo {
        private float x0;
        private float y0;

        boolean isDragDown(float x1, float y1) {
            boolean ret = y1 > y0;
            x0 = x1;
            y0 = y1;
            return ret;
        }
    }
}

package com.example.test10;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;
import java.util.Map;

public class MyAdapter extends BaseAdapter {

    private int focus = -10;
    private boolean down = false;
    private final Context context;
    private final List<Map<String, Object>> data;

    public MyAdapter(Context context, List<Map<String, Object>> data) {
        super();
        this.context = context;
        this.data = data;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (Long) data.get(position).get("id");
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MyItemView itemView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = (MyItemView) inflater.inflate(R.layout.item, parent, false);
        } else {
            itemView = (MyItemView) convertView;
        }
        itemView.bind(position, data.get(position), position == focus, this.down);
        return itemView;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public void clearDragPosition() {
        focus = -10;
        down = false;
    }

    public void setDragPosition(int pos, boolean down) {
        this.focus = pos;
        this.down = down;
        Log.d("xxx", "setDragPosition pos=" + pos + ", down=" + down);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });

    }

    public void swap(int ori, int pos) {

        Log.d("xxx", String.format("ori=%d, pos=%d", ori, pos));

        if (ori < 0 || data.size() <= ori) {
            return;
        }
        if (pos < 0 || data.size() <= pos) {
            return;
        }

        Map<String, Object> item = data.remove(ori);
        data.add(pos, item);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });


    }
}

package com.example.test10;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    public static final int DATA_SIZE = 100;
    public static final int DATA_LEN = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new MyAdapter(this, getData()));
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> data = new ArrayList<>();

        for (int i = 0; i < DATA_SIZE; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", Long.valueOf(i));
            map.put("value", getRandomString(DATA_LEN));
            data.add(map);
        }


        return data;
    }

    private static String getRandomString(int maxlength) {
        //http://stackoverflow.com/a/25447172
        String result = "";
        int i = 0;
        int min = 33;
        int max = 122;
        while (i < maxlength) {
            int n = (int) (Math.random() * (max - min) + min);
            if (n >= 33 && n < 123) {
                result += (char) n;
                ++i;
            }
        }
        return result;
    }
}

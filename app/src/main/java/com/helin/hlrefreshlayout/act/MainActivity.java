package com.helin.hlrefreshlayout.act;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.helin.hlrefreshlayout.R;
import com.helin.hlrefreshlayout.view.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private RefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        ListView listview = (ListView) findViewById(R.id.listview);
        String data[]={"1","2","3"};
        String[] strings = {"text"};//Map的key集合数组
        int[] ids = {R.id.text1};//对应布局文件的id
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,
                getData(), R.layout.item_listvieww, strings, ids);
        listview.setAdapter(simpleAdapter);



    }

    private List<HashMap<String, Object>> getData() {
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = null;
        for (int i = 1; i <= 40; i++) {
            map = new HashMap<String, Object>();
            map.put("text", "人物" + i);
            list.add(map);
        }
        return list;
    }
}

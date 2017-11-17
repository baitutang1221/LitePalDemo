package com.example.administrator.litepaldemo.tools;

import com.example.administrator.litepaldemo.adapter.MyBaseAdapter;

/**
 * Created by xiazhenjie on 2017/9/13.
 */

public class CacheManager {

    public MyBaseAdapter adapter;

    private static final CacheManager ourInstance = new CacheManager();

    public static CacheManager getInstance() {
        return ourInstance;
    }

    public MyBaseAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(MyBaseAdapter adapter) {
        this.adapter = adapter;
    }
}

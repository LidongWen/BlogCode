package com.wenld.coustomlayoutmanager;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import com.wenld.multitypeadapter.MultiTypeAdapter;
import com.wenld.multitypeadapter.base.MultiItemView;
import com.wenld.multitypeadapter.base.ViewHolder;

/**
 * <p/>
 * Author: wenld on 2017/8/10 17:35.
 * blog: http://www.jianshu.com/u/99f514ea81b3
 * github: https://github.com/LidongWen
 */



public abstract class CommonAdapter<T> extends MultiTypeAdapter {
    Context mContext;
    @LayoutRes
    int layoutId;

    public CommonAdapter(Context context, Class<? extends T> clazz, @LayoutRes final int layoutId) {
        this.mContext = context;
        this.layoutId = layoutId;
        this.register(clazz, new MultiItemView<T>() {
            @NonNull
            public int getLayoutId() {
                return layoutId;
            }



            public void onBindViewHolder(@NonNull ViewHolder holder, @NonNull T item, int position) {
                CommonAdapter.this.convert(holder, item, position);
            }
            public int getMaxRecycleCount() {
                return 10;
            }
        });
    }

    protected abstract void convert(ViewHolder var1, T var2, int var3);

    public int getItemCount() {
        return this.getItems().size();
    }
}


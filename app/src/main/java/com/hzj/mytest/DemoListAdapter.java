package com.hzj.mytest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hzj.view.raiflat.RaiflatButton;

/**
 * Created by hzj on 2016/10/24.
 */
public class DemoListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private DemoInfo[] demos;

    private OnItemClickListener onItemClickListener;

    DemoListAdapter(Context context, DemoInfo[] demos) {
        super();
        this.inflater = LayoutInflater.from(context);
        this.demos = demos;
    }

    @Override
    public View getView(final int index, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.demo_info_item, parent, false);

            holder.title = (RaiflatButton) convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(demos[index].title);
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(index);
            }
        });
        return convertView;
    }

    @Override
    public int getCount() {
        return demos.length;
    }

    @Override
    public Object getItem(int index) {
        return demos[index];
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    private static class ViewHolder {
        RaiflatButton title;
    }

    interface OnItemClickListener {
        void onItemClick(int position);
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

}

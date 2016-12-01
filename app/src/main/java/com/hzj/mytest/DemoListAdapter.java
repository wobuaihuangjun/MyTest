package com.hzj.mytest;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by hzj on 2016/10/24.
 */
public class DemoListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private DemoInfo[] demos;

    public DemoListAdapter(Context context, DemoInfo[] demos) {
        super();
        this.inflater = LayoutInflater.from(context);
        this.demos = demos;
    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.demo_info_item, parent, false);

            holder.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(demos[index].title);
        if (index >= 16) {
            holder.title.setTextColor(Color.YELLOW);
        }
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
        TextView title;
    }


}

package com.example.a3shwa2y;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class customlistview extends BaseAdapter {
    private Context context;
    private String given_id[];
    private String taken_id[];
    private String numOfpoints[];

    public customlistview(Context context) {
        this.context = context;

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(context).inflate(R.layout.cusstomlistview,parent,false);
        TextView given_id=convertView.findViewById(R.id.given_id);
        TextView taken_id=convertView.findViewById(R.id.taken_id);
        TextView numOfpoints=convertView.findViewById(R.id.numOfpoints);
        return convertView;
    }
}

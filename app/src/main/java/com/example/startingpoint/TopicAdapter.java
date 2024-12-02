package com.example.startingpoint;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.gridlayout.widget.GridLayout;

import java.util.List;

public class TopicAdapter extends android.widget.BaseAdapter {
    private final Context context;
    private final List<String> topics;
    private final LayoutInflater inflater;

    public TopicAdapter(Context context, List<String> topics) {
        this.context = context;
        this.topics = topics;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return topics.size();
    }

    @Override
    public Object getItem(int position) {
        return topics.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        textView.setText(topics.get(position));
        textView.setTextColor(ContextCompat.getColor(context, android.R.color.black));
        textView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setPadding(20, 20, 20, 20);

        return convertView;
    }
}

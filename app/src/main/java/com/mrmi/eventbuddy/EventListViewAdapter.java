package com.mrmi.eventbuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class EventListViewAdapter extends BaseAdapter {

    private final Context context;
    private final List<Event> events;
    public EventListViewAdapter(Context context, List<Event> events) {
        this.context = context;
        this.events = events;
    }

    @Override
    public int getCount() {
        return Event.counter;
    }

    @Override
    public Event getItem(int i) {
        return this.events.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.event_list_view_item, container, false);
        }

        ((TextView) convertView.findViewById(R.id.eventTitle)).setText(getItem(position).geteventTitle());
        return convertView;
    }
}

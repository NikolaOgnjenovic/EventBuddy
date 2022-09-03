package com.mrmi.eventbuddy;

import android.content.Context;
import android.content.Intent;
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
        return events.size();
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

        ((TextView) convertView.findViewById(R.id.eventTitle)).setText(getItem(position).getTitle());

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EventActivity.class);
            Event event = events.get(position);
            System.out.println(event.toString());
            intent.putExtra("author", event.getAuthor());
            intent.putExtra("date", event.getDate());
            intent.putExtra("time", event.getTime());
            intent.putExtra("type", event.getType());
            intent.putExtra("description", event.getDescription());
            intent.putExtra("title", event.getTitle());
            intent.putExtra("city", event.getCity());
            intent.putExtra("address", event.getAddress());
            intent.putExtra("interestedCount", event.getInterestedCount());
            intent.putExtra("goingCount", event.getGoingCount());
            intent.putExtra("eventID", event.getId());
            context.startActivity(intent);
        });
        return convertView;
    }
}

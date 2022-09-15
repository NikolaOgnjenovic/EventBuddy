package com.mrmi.eventbuddy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EventListViewAdapter extends ArrayAdapter<Event> {

    private final Context context;

    public EventListViewAdapter(Context context, int resource, List<Event> eventList) {
        super(context, resource, eventList);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.event_list_view_item, container, false);
        }
        Event event = getItem(position);
        ((TextView) convertView.findViewById(R.id.eventTitle)).setText(getItem(position).getTitle());

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EventActivity.class);
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
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
        return convertView;
    }
}

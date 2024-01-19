package com.example.thegrimpeurscyclingclub.data;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.thegrimpeurscyclingclub.R;
import com.example.thegrimpeurscyclingclub.data.event_relative.EventType;

import java.util.List;

public class EventTypeListAdapter extends ArrayAdapter {
    private Activity context;
    List<String> eventTypeList;
    public EventTypeListAdapter(Activity context, List<String> eventTypeList ) {
        super(context, R.layout.event_type_list,eventTypeList);
        this.context=context;
        this.eventTypeList=eventTypeList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.event_type_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.EventTypeName);
        String eventType=eventTypeList.get(position);
        textViewName.setText(eventType);
        return listViewItem;
    }
}

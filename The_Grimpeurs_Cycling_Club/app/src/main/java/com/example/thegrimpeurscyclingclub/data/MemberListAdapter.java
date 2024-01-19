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

public class MemberListAdapter extends ArrayAdapter {
    private Activity context;
    List<String[]> memberList;
    public MemberListAdapter(Activity context, List<String[]> memberList ) {
        super(context, R.layout.member_list,memberList);
        this.context=context;
        this.memberList=memberList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.member_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.memberName);
        TextView textViewRole = (TextView) listViewItem.findViewById(R.id.memberRole);
        String[] member=memberList.get(position);
        String memberName=member[0];
        String memberRole=member[1];
        textViewName.setText("Member Name: "+memberName);
        textViewRole.setText("Role: "+memberRole);
        return listViewItem;
    }
}

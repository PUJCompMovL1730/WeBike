package webike.webike.adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import webike.webike.R;
import webike.webike.logic.Group;

public class GroupArrayAdapter extends ArrayAdapter<Group> {

    public GroupArrayAdapter(@NonNull Context context, ArrayList<Group> info ) {
        super(context, 0 , info);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if( v == null ){
            LayoutInflater li = LayoutInflater.from(getContext());
            v = li.inflate(R.layout.group_list_item,null);
        }

        Group g = getItem(position);
        if( g != null ){
            TextView route_name = (TextView) v.findViewById(R.id.route_name_list);
            TextView route_start = (TextView) v.findViewById(R.id.route_start_list);
            TextView route_end = (TextView) v.findViewById(R.id.route_end_list);

            route_name.setText(g.getName());
            route_start.setText(g.getStart());
            route_end.setText(g.getFinish());
        }
        return v;
    }
}

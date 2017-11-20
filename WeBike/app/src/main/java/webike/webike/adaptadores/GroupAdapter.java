package webike.webike.adaptadores;

import android.content.Context;
import android.support.annotation.LayoutRes;
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
import webike.webike.logic.User;

/**
 * Created by COLMENARES on 19/11/2017.
 */

public class GroupAdapter extends ArrayAdapter<Group> {

    public GroupAdapter(@NonNull Context context, ArrayList<Group> info) {
        super(context,0, info);
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if( v == null ){
            LayoutInflater li = LayoutInflater.from(getContext());
            v = li.inflate(R.layout.adapter_group,null);
        }

        Group g = getItem(position);
        if( g != null ){
            TextView text = (TextView) v.findViewById(R.id.group_name);
            text.setText(g.getName());
        }
        return v;
    }
}


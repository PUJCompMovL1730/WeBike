package webike.webike;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import logic.User;

/**
 * Created by Juan on 10/29/2017.
 */

public class UserArrayAdapter extends ArrayAdapter<User> {

    public UserArrayAdapter(@NonNull Context context, ArrayList<User> info ) {
        super(context, 0 , info);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if( v == null ){
            LayoutInflater li = LayoutInflater.from(getContext());
            v = li.inflate(R.layout.user_list_item,null);
        }

        User u = getItem(position);
        if( u != null ){
            TextView text = (TextView) v.findViewById(R.id.user_name);
            text.setText(u.getFirstName()+" "+u.getLastName());
        }
        return v;
    }
}

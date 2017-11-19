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

/**
 * Created by COLMENARES on 19/11/2017.
 */

public class FriendsAdapter extends ArrayAdapter<String>{


    public FriendsAdapter(@NonNull Context context, ArrayList<String> friends) {
        super(context,0,friends);
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View v = convertView;
        if( v == null ){
            LayoutInflater li = LayoutInflater.from(getContext());
            v = li.inflate(R.layout.adapter_friends,null);
        }
        String friend = getItem(position);
        if(friend != null){
            TextView name = (TextView)v.findViewById(R.id.friend_name);

        }
        return v;
    }


}

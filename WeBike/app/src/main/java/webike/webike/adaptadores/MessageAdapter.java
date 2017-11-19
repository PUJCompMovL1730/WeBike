package webike.webike.adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import webike.webike.R;
import webike.webike.logic.Message;

/**
 * Created by Carlos on 29/10/2017.
 */

public class MessageAdapter extends ArrayAdapter<Message> {
    public MessageAdapter(Context context , ArrayList<Message> resource){
        super(context , 0 , resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v= convertView;
        if(v == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.adapter_home_mensajes, null);
        }

        Message r = getItem(position);
        if (r!= null){
            TextView txdestino= (TextView) v.findViewById(R.id.receptor);
            TextView txorigen = (TextView) v.findViewById(R.id.emisor);
            TextView txasunto = (TextView) v.findViewById(R.id.asunto);
            txorigen.setText( r.getSender() );
            txdestino.setText( r.getReceiver() );
            txasunto.setText( r.getSubject() );
        }
        return v;

    }
}

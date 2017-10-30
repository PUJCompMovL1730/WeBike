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
import webike.webike.logic.Message;

/**
 * Created by Carlos on 29/10/2017.
 */

public class adaptador_home_mensaje extends ArrayAdapter<Message> {
    public adaptador_home_mensaje(Context context , ArrayList<Message> resource){
        super(context , 0 , resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v= convertView;
        if(v == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.adapterhome_notificacion, null);
        }

        Message r = getItem(position);
        if (r!= null){
            TextView txdestino= (TextView) v.findViewById(R.id.receptor);
            TextView txorigen = (TextView) v.findViewById(R.id.remitente);
            TextView txmensaje = (TextView) v.findViewById(R.id.mensaje);
            txdestino.setText((CharSequence) r.getSender());
            txorigen.setText((CharSequence) r.getReceiver());
            txmensaje.setText(r.getMsg());
        }
        return v;

    }
}

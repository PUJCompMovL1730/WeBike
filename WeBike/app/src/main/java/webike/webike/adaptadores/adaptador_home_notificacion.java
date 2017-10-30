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
import webike.webike.logic.Publicacion;

/**
 * Created by Carlos on 29/10/2017.
 */

public class adaptador_home_notificacion extends ArrayAdapter<Publicacion>{

    public adaptador_home_notificacion(Context context , ArrayList<Publicacion> resource){
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

        Publicacion r = getItem(position);
        if (r!= null){
            TextView txDestino = (TextView) v.findViewById(R.id.destino);
            TextView txorigen = (TextView) v.findViewById(R.id.origen);
            TextView txhora = (TextView) v.findViewById(R.id.hora);

            txDestino.setText(r.getDestino());
            txorigen.setText(r.getOrigen());
            txhora.setText(r.getHora());
        }
        return v;

    }
}

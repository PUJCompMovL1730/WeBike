package webike.webike.adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import webike.webike.R;
import webike.webike.logic.Publicacion;

public class adapter_notification extends ArrayAdapter<Publicacion> {

    public adapter_notification(Context context , ArrayList<Publicacion> resource){
        super(context , 0 , resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v= convertView;
        if(v == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.adapter_notification, null);
        }

        Publicacion r = getItem(position);
        if (r!= null){
            TextView tv_nombre = (TextView) v.findViewById(R.id.tv_nombre);
            TextView tv_inicio = (TextView) v.findViewById(R.id.tv_inicio);
            TextView tv_fin = (TextView) v.findViewById(R.id.tv_fin);

            tv_nombre.setText(r.getNombre());
            tv_inicio.setText(r.getOrigen());
            tv_fin.setText(r.getDestino());
        }
        return v;

    }
}

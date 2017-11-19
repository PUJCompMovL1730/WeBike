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
import webike.webike.logic.Route;

public class adapter_route extends ArrayAdapter<Route> {

    public adapter_route(Context context , ArrayList<Route> resource){
        super(context , 0 , resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.adapter_route, null);
        }

        Route r = getItem(position);
        if (r!= null){
            TextView tv_fecha = (TextView) v.findViewById(R.id.tv_fecha);
            TextView tv_inicio = (TextView) v.findViewById(R.id.tv_inicio);
            TextView tv_fin = (TextView) v.findViewById(R.id.tv_fin);

            tv_fecha.setText(r.getDestino());
            tv_inicio.setText(r.getOrigen());
            tv_fin.setText(r.getDestino());
        }
        return v;

    }
}

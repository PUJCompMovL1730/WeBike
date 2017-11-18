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
import webike.webike.logic.AbstractPublication;
import webike.webike.logic.PlacePromotion;
import webike.webike.logic.PlannedRoute;
import webike.webike.logic.Publicacion;
import webike.webike.logic.SpecialPublication;

/**
 * Created by Camila on 18/11/2017.
 */

public class adapter_all_publication extends ArrayAdapter<AbstractPublication> {

    public adapter_all_publication(Context context , ArrayList<AbstractPublication> resource){
        super(context , 0 , resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        AbstractPublication r = getItem(position);
        View v = convertView;
        LayoutInflater vi = LayoutInflater.from(getContext());;

        if (r!= null){
            if(r instanceof PlacePromotion){
                if(v == null) {
                    v = vi.inflate(R.layout.adapter_place_promotion, null);
                    TextView tv_nombre = (TextView) v.findViewById(R.id.tv_nombre);
                    TextView tv_lugar = (TextView) v.findViewById(R.id.tv_lugar);
                    tv_nombre.setText(((PlacePromotion) r).getNombre());
                    tv_lugar.setText(((PlacePromotion) r).getLugar());
                }
            }
            if(r instanceof PlannedRoute){
                if(v == null) {
                    v = vi.inflate(R.layout.adapter_planned_route, null);
                    TextView tv_nombre = (TextView) v.findViewById(R.id.tv_nombre);
                    TextView tv_inicio = (TextView) v.findViewById(R.id.tv_inicio);
                    TextView tv_fin = (TextView) v.findViewById(R.id.tv_fin);
                    tv_nombre.setText(((PlannedRoute) r).getNombre());
                    tv_inicio.setText(((PlannedRoute) r).getOrigen());
                    tv_fin.setText(((PlannedRoute) r).getDestino());
                }
            }
            if(r instanceof Publicacion){
                if(v == null) {
                    v = vi.inflate(R.layout.adapter_notification, null);
                    TextView tv_nombre = (TextView) v.findViewById(R.id.tv_nombre);
                    TextView tv_inicio = (TextView) v.findViewById(R.id.tv_inicio);
                    TextView tv_fin = (TextView) v.findViewById(R.id.tv_fin);
                    tv_nombre.setText(((Publicacion) r).getNombre());
                    tv_inicio.setText(((Publicacion) r).getOrigen());
                    tv_fin.setText(((Publicacion) r).getDestino());
                }
            }
        }
        return v;

    }

}

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
import webike.webike.logic.Tip;

public class adapter_manual extends ArrayAdapter<Tip> {

    public adapter_manual(Context context , ArrayList<Tip> resource){
        super(context , 0 , resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v= convertView;
        if(v == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.adapter_manual, null);
        }

        Tip r = getItem(position);
        if (r!= null){
            TextView tv_numero = (TextView) v.findViewById(R.id.tv_numero);
            TextView tv_descripcion = (TextView) v.findViewById(R.id.tv_titulo);

            tv_numero.setText(r.getNumero());
            tv_descripcion.setText(r.getTitulo());
        }
        return v;

    }
}

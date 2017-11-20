package webike.webike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import webike.webike.logic.Tip;

public class TipActivity extends AppCompatActivity {

    TextView tv_numero_titulo;
    TextView tv_descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        tv_numero_titulo = (TextView) findViewById(R.id.tv_numero_titulo);
        tv_descripcion = (TextView) findViewById(R.id.tv_descripcion);

        Intent tempIntent = getIntent();
        Bundle bundle = tempIntent.getExtras();
        Tip tip = new Tip();
        if(bundle != null){
            tip = (Tip) bundle.get("pub");
        }

        tv_numero_titulo.setText(tip.getNumero() + ": "+ tip.getTitulo());
        tv_descripcion.setText(tip.getDescripcion());
    }
}

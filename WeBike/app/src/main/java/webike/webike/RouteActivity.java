package webike.webike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import webike.webike.logic.Route;

public class RouteActivity extends AppCompatActivity {

    FirebaseAuth fbAuth;
    DatabaseReference dbReference;
    FirebaseDatabase fbDatabase;

    TextView tv_fecha;
    TextView tv_inicio;
    TextView tv_fin;
    TextView tv_tiempo;
    TextView tv_distancia;
    TextView tv_clima;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        fbDatabase = FirebaseDatabase.getInstance();
        fbAuth = FirebaseAuth.getInstance();

        tv_fecha = (TextView) findViewById(R.id.tv_fecha);
        tv_inicio = (TextView) findViewById(R.id.tv_inicio);
        tv_fin = (TextView) findViewById(R.id.tv_fin);
        tv_tiempo = (TextView) findViewById(R.id.tv_tiempo);
        tv_distancia = (TextView) findViewById(R.id.tv_distancia);
        tv_clima = (TextView) findViewById(R.id.tv_clima);

        Intent tempIntent = getIntent();
        Bundle bundle = tempIntent.getExtras();

        Route route = new Route();
        if(bundle != null){
            route = (Route) bundle.get("pub");
        }

        tv_fecha.setText(route.getFecha());
        tv_inicio.setText(route.getOrigen());
        tv_fin.setText(route.getDestino());
        tv_tiempo.setText(route.getDuracion());
        tv_distancia.setText(route.getDistancia());
        tv_clima.setText(route.getClima());
    }
}

package webike.webike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import webike.webike.logic.PlannedRoute;
import webike.webike.logic.SpecialPublication;
import webike.webike.logic.User;
import webike.webike.utils.FData;
import webike.webike.utils.SingleValueActions;

public class PlannedRouteActivity extends AppCompatActivity {

    FirebaseAuth fbAuth;
    DatabaseReference dbReference;
    FirebaseDatabase fbDatabase;

    TextView tv_nombre;
    TextView tv_organiza;
    TextView tv_inicio;
    TextView tv_fin;
    TextView tv_fecha;
    TextView tv_descripcion;
    Button b_participar;

    PlannedRoute plannedRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planned_route);

        fbDatabase = FirebaseDatabase.getInstance();
        fbAuth = FirebaseAuth.getInstance();

        tv_nombre = (TextView)findViewById(R.id.tv_nombre);
        tv_organiza = (TextView)findViewById(R.id.tv_organiza);
        tv_inicio = (TextView)findViewById(R.id.tv_inicio);
        tv_fin = (TextView)findViewById(R.id.tv_fin);
        tv_fecha = (TextView)findViewById(R.id.tv_fecha);
        tv_descripcion = (TextView)findViewById(R.id.tv_descripcion);

        Intent tempIntent = getIntent();
        Bundle bundle = tempIntent.getExtras();

        plannedRoute = new PlannedRoute();
        if(bundle != null){
            plannedRoute = (PlannedRoute) bundle.get("pub");
        }

        tv_nombre.setText(plannedRoute.getNombre());
        tv_organiza.setText(plannedRoute.getOrganiza());
        tv_inicio.setText(plannedRoute.getOrigen());
        tv_fin.setText(plannedRoute.getDestino());
        tv_fecha.setText(plannedRoute.getFecha());
        tv_descripcion.setText(plannedRoute.getDescripcion());

        b_participar = (Button)findViewById(R.id.b_participar);
        b_participar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    writeDatabase();
                    Toast.makeText(getBaseContext(),"Se ha añadido la publicación exitosamente",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PlannedRouteActivity.this, HomeActivity.class);
                    intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                    startActivity( intent );
                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(),"Error al añadir publiación",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void writeDatabase(){

        FData.getUserFromId(fbDatabase, fbAuth.getCurrentUser().getUid(), new SingleValueActions<User>() {
            @Override
            public void onReceiveSingleValue(User data, DatabaseReference reference) {
                if( data.getHistoryPublications() == null ){
                    data.setHistoryPublications(new ArrayList<SpecialPublication>());
                }
                data.getHistoryPublications().add(plannedRoute);
                reference.setValue(data);
            }

            @Override
            public void onCancel( DatabaseError databaseError ) {

            }
        });
    }

}

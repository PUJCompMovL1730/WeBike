package webike.webike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import webike.webike.logic.PlannedRoute;
import webike.webike.logic.User;
import webike.webike.ubicacion.MapCreateRoute;
import webike.webike.utils.FData;
import webike.webike.utils.SingleValueActions;

import static webike.webike.utils.FData.postPlannedRoutes;

public class CreatePlannedRouteActivity extends AppCompatActivity {

    FirebaseAuth fbAuth;
    DatabaseReference dbReference;
    FirebaseDatabase fbDatabase;

    EditText et_nombre;
    EditText et_inicio;
    EditText et_fin;
    EditText et_fecha;
    EditText et_descripcion;

    Button b_mapa;
    Button b_promocionar;

    String routeUrl;
    PlannedRoute route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_planned_route);

        fbDatabase = FirebaseDatabase.getInstance();
        fbAuth = FirebaseAuth.getInstance();

        et_nombre = (EditText) findViewById(R.id.et_nombre);
        et_inicio = (EditText) findViewById(R.id.et_inicio);
        et_fin = (EditText) findViewById(R.id.et_fin);
        et_fecha = (EditText) findViewById(R.id.et_fecha);
        et_descripcion = (EditText) findViewById(R.id.et_descripcion);

        b_promocionar = (Button)findViewById(R.id.b_promocionar);
        b_mapa = (Button)findViewById(R.id.b_mapa);

        b_mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent obj = new Intent(CreatePlannedRouteActivity.this, MapCreateRoute.class);
                obj.putExtra("URL", routeUrl);
                startActivityForResult(obj, 100);
            }
        });

        b_promocionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    savePlannedRoute();
                    Toast.makeText(getBaseContext(),"Promoción exitosa",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreatePlannedRouteActivity.this, HomeActivity.class);
                    intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                    startActivity( intent );
                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(),"Error al promocionar recorrido",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void savePlannedRoute() {
        FData.getUserFromId(fbDatabase, fbAuth.getCurrentUser().getUid(), new SingleValueActions<User>() {
            @Override
            public void onReceiveSingleValue(User data, DatabaseReference reference) {
                route.setNombre(et_nombre.toString());
                route.setOrganiza(data.getFirstName() + " " + data.getLastName());
                route.setOrigen(et_inicio.toString());
                route.setDestino(et_fin.toString());
                route.setFecha(et_fecha.toString());
                route.setDescripcion(et_descripcion.toString());
                route.setUrl(routeUrl);
                postPlannedRoutes(fbDatabase,route);
            }
            @Override
            public void onCancel(DatabaseError error) {

            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                //Toast.makeText(CreateGroupActivity.this, data.getStringExtra("result"), Toast.LENGTH_SHORT).show();
                routeUrl = data.getStringExtra("result");
            }
        }
    }
}

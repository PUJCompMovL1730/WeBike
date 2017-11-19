package webike.webike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreatePlannedRouteActivity extends AppCompatActivity {

    FirebaseAuth fbAuth;
    DatabaseReference dbReference;
    FirebaseDatabase fbDatabase;

    EditText et_nombre;
    EditText et_inicio;
    EditText et_fin;
    EditText et_fecha;
    EditText et_descripcion;

    Button b_promocionar;

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
        b_promocionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //TODO
                    //writeDatabase();
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
}

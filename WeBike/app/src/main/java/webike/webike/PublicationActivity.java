package webike.webike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

import webike.webike.R;
import webike.webike.logic.Publicacion;
import webike.webike.logic.Route;
import webike.webike.logic.User;
import webike.webike.utils.FData;

public class PublicationActivity extends AppCompatActivity {

    Button b_participar_publicacion;
    TextView tv_nombre_publicacion;
    TextView tv_inicio_publicacion;
    TextView tv_fin_publicacion;
    TextView tv_hora_publicacion;
    TextView tv_descripcion_publicacion;

    Route ruta;
    FirebaseAuth fbAuth;
    DatabaseReference dbReference;
    FirebaseDatabase fbDatabase;
    //String id_publicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publication);

        //id_publicacion = getIntent().getStringExtra("id_publicacion");
       // Bundle bundle = getIntent().getBundleExtra("bundle");
        //publicacion = (Publicacion) bundle.get("publicacion");
        ruta = new Route("camino a la u", "casa de dani", "javeriana", "12:00am", "","","","", "hgfjgh");

        fbDatabase = FirebaseDatabase.getInstance();
        fbAuth = FirebaseAuth.getInstance();

        tv_nombre_publicacion = (TextView) findViewById(R.id.tv_nombre_publicacion);
        tv_inicio_publicacion = (TextView) findViewById(R.id.tv_inicio_publicacion);
        tv_fin_publicacion = (TextView) findViewById(R.id.tv_fin_publicacion);
        tv_hora_publicacion = (TextView) findViewById(R.id.tv_hora_publicacion);
        tv_descripcion_publicacion = (TextView) findViewById(R.id.tv_descripcion_publicacion);

        tv_nombre_publicacion.setText(ruta.getNombre());
        tv_inicio_publicacion.setText(ruta.getOrigen());
        tv_fin_publicacion.setText(ruta.getDestino());
        tv_hora_publicacion.setText(ruta.getHora());
        tv_descripcion_publicacion.setText(ruta.getDescripcion());

        b_participar_publicacion = (Button)findViewById(R.id.b_participar_publicacion);
        b_participar_publicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Toast.makeText(getBaseContext(),"YAAAAAAS",Toast.LENGTH_SHORT).show();
                    writeDatabase();
                    Toast.makeText(getBaseContext(),"Se ha añadido el recorrido exitosamente",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PublicationActivity.this, HomeActivity.class);
                    intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                    startActivity( intent );
                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(),"Error al añadir recorrido",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void writeDatabase(){
        dbReference = fbDatabase.getReference(FData.PATH_TO_USERS + "/" + fbAuth.getCurrentUser().getUid());
        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User aux = dataSnapshot.getValue(User.class);
                if( aux.getHistory() == null ){
                    aux.setHistory(new ArrayList<Route>());
                }
                aux.getHistory().add(ruta);
                dbReference.setValue(aux);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
/*
    public void readDatabase(){
        dbReference = fbDatabase.getReference(FData.PATH_TO_PUBS + "/" + id_publicacion);
        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                publicacion = dataSnapshot.getValue(Publicacion.class);
                actualizar(publicacion);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void actualizar(){

    }*/
}

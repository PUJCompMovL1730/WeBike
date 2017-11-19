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

import webike.webike.logic.Publicacion;
import webike.webike.logic.Route;
import webike.webike.logic.User;
import webike.webike.utils.FData;
import webike.webike.utils.SingleValueActions;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publication);

        fbDatabase = FirebaseDatabase.getInstance();
        fbAuth = FirebaseAuth.getInstance();

        tv_nombre_publicacion = (TextView) findViewById(R.id.tv_nombre_publicacion);
        tv_inicio_publicacion = (TextView) findViewById(R.id.tv_inicio_publicacion);
        tv_fin_publicacion = (TextView) findViewById(R.id.tv_fin_publicacion);
        tv_hora_publicacion = (TextView) findViewById(R.id.tv_hora_publicacion);
        tv_descripcion_publicacion = (TextView) findViewById(R.id.tv_descripcion_publicacion);

        Intent tempIntent = getIntent();
        Bundle bundle = tempIntent.getExtras();

        Publicacion publicacion = new Publicacion();
        if(bundle != null){
            publicacion = (Publicacion) bundle.get("pub");
        }

        tv_nombre_publicacion.setText(publicacion.getDestino());
        tv_inicio_publicacion.setText(publicacion.getOrigen());
        tv_fin_publicacion.setText(publicacion.getDestino());
        tv_hora_publicacion.setText(publicacion.getHora());
        tv_descripcion_publicacion.setText(publicacion.getDescripcion());

        //ruta = new Route(publicacion.getNombre(),publicacion.getOrigen(),publicacion.getDestino(),publicacion.getHora(),"","","","",publicacion.getDescripcion());

        b_participar_publicacion = (Button)findViewById(R.id.b_participar_publicacion);
        b_participar_publicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
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

        FData.getUserFromId(fbDatabase, fbAuth.getCurrentUser().getUid(), new SingleValueActions<User>() {
            @Override
            public void onReceiveSingleValue(User data, DatabaseReference reference) {
                if( data.getHistoryPublications() == null ){
                    data.setHistoryPublications( new ArrayList<String>() );
                }
                data.getHistory().add(ruta);
                reference.setValue(data);
            }

            @Override
            public void onCancel( DatabaseError databaseError ) {

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

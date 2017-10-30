package webike.webike.utils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import webike.webike.R;
import webike.webike.logic.Publicacion;

public class PublicationActivity extends AppCompatActivity {

    Button b_participar_publicacion;
    TextView tv_nombre_publicacion;
    TextView tv_inicio_publicacion;
    TextView tv_fin_publicacion;
    TextView tv_hora_publicacion;
    TextView tv_descripcion_publicacion;

    String nombre_publicacion;
    String inicio_publicacion;
    String fin_publicacion;

    Publicacion publicacion;
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
        publicacion = new Publicacion("camino a la u", "casa de dani", "javeriana", "12:00am", "safdhgfsffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffgggggggggggggggggggggggggggggggggggggggggggggffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffooo");

        fbDatabase = FirebaseDatabase.getInstance();

        tv_nombre_publicacion = (TextView) findViewById(R.id.tv_nombre_publicacion);
        tv_inicio_publicacion = (TextView) findViewById(R.id.tv_inicio_publicacion);
        tv_fin_publicacion = (TextView) findViewById(R.id.tv_fin_publicacion);
        tv_hora_publicacion = (TextView) findViewById(R.id.tv_hora_publicacion);
        tv_descripcion_publicacion = (TextView) findViewById(R.id.tv_descripcion_publicacion);

        tv_nombre_publicacion.setText(publicacion.getNombre());
        tv_inicio_publicacion.setText(publicacion.getOrigen());
        tv_fin_publicacion.setText(publicacion.getDestino());
        tv_hora_publicacion.setText(publicacion.getHora());
        tv_descripcion_publicacion.setText(publicacion.getDescripcion());

        b_participar_publicacion = (Button)findViewById(R.id.b_participar_publicacion);
        b_participar_publicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Toast.makeText(getBaseContext(),"YAAAAAAS",Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    Toast.makeText(getBaseContext(),"Comin sun in yur neiberjud",Toast.LENGTH_SHORT).show();
                }
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

package webike.webike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import webike.webike.adaptadores.adapter_notification;
import webike.webike.logic.Publicacion;
import webike.webike.utils.FData;
import webike.webike.utils.ListActions;

import static android.R.attr.type;

public class PublicationsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FData fData;
    private FirebaseDatabase database;
    private boolean type = false;

    private Button b_panic;
    private ImageView b_help;
    private ImageView b_manual;

    private ListView publications_list;
    private ArrayList<Publicacion> current_pub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publications);

        mAuth = FirebaseAuth.getInstance();
        fData = new FData();
        database = FirebaseDatabase.getInstance();

        b_panic = (Button) findViewById(R.id.panic);
        b_help = (ImageView) findViewById(R.id.help);
        b_manual = (ImageView) findViewById(R.id.manual);

        b_panic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PublicationsActivity.this, PanicButtonActivity.class);
                startActivity(intent);
            }
        });
        b_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PublicationsActivity.this, HelpButtonActivity.class);
                startActivity(intent);
            }
        });
        b_manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PublicationsActivity.this, ManualActivity.class);
                startActivity(intent);
            }
        });

        publications_list = (ListView) findViewById(R.id.publications_list);

        publications_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(type)
                {
                    Intent myIntent = new Intent(PublicationsActivity.this, PublicationActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("pub",current_pub.get(position));
                    myIntent.putExtras(bundle);
                    startActivity(myIntent);
                }
            }
        });

        loadPubs();
    }

    public void loadPubs(){

        FData.getPublications(database, new ListActions<Publicacion>() {
            @Override
            public void onReceiveList(ArrayList<Publicacion> data, DatabaseReference reference) {
                current_pub = data;
                infalteListWithPubs( data );
            }

            @Override
            public void onCancel( DatabaseError databaseError ) {

            }
        });
    }

    public void infalteListWithPubs( ArrayList<Publicacion> pubs ){
        adapter_notification adapter = new adapter_notification(this,pubs);
        this.publications_list.setAdapter(adapter);
    }
}

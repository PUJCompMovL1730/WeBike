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

import webike.webike.adaptadores.adapter_special_publication;
import webike.webike.logic.PlacePromotion;
import webike.webike.logic.PlannedRoute;
import webike.webike.logic.SpecialPublication;
import webike.webike.utils.FData;
import webike.webike.utils.ListActions;

public class SpecialPublicationsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FData fData;
    private FirebaseDatabase database;
    private boolean type = false;

    private ListView specialPublication_list;
    private ArrayList<SpecialPublication> current_specialPublication;

    private Button b_panic;
    private ImageView b_help;
    private ImageView b_manual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_publications);

        mAuth = FirebaseAuth.getInstance();
        fData = new FData();
        database = FirebaseDatabase.getInstance();

        b_panic = (Button) findViewById(R.id.panic);
        b_help = (ImageView) findViewById(R.id.help);
        b_manual = (ImageView) findViewById(R.id.manual);

        b_panic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpecialPublicationsActivity.this, PanicButtonActivity.class);
                startActivity(intent);
            }
        });
        b_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpecialPublicationsActivity.this, HelpButtonActivity.class);
                startActivity(intent);
            }
        });
        b_manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpecialPublicationsActivity.this, ManualActivity.class);
                startActivity(intent);
            }
        });

        specialPublication_list = (ListView) findViewById(R.id.specialPublications_list);

        specialPublication_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent;
                Bundle bundle = new Bundle();
                if(current_specialPublication.get(position) instanceof PlacePromotion){
                    myIntent = new Intent(SpecialPublicationsActivity.this, PlaceActivity.class);
                    PlacePromotion placePromotion = (PlacePromotion) current_specialPublication.get(position);
                    bundle.putSerializable("pub",placePromotion);
                    myIntent.putExtras(bundle);
                    startActivity(myIntent);
                }
                if(current_specialPublication.get(position) instanceof PlannedRoute){
                    myIntent = new Intent(SpecialPublicationsActivity.this, PlannedRouteActivity.class);
                    PlannedRoute plannedRoute = (PlannedRoute) current_specialPublication.get(position);
                    bundle.putSerializable("pub",plannedRoute);
                    myIntent.putExtras(bundle);
                    startActivity(myIntent);
                }
            }
        });

        loadSpecialPublication();
    }

    public void loadSpecialPublication(){

        FData.getSpecialPublications(database, new ListActions<SpecialPublication>() {
            @Override
            public void onReceiveList(ArrayList<SpecialPublication> data, DatabaseReference reference) {
                current_specialPublication = data;
                inflateListWithSpecialPublication( data );
            }

            @Override
            public void onCancel( DatabaseError databaseError ) {

            }
        });
    }

    public void inflateListWithSpecialPublication( ArrayList<SpecialPublication> pubs ){
        adapter_special_publication adapter = new adapter_special_publication(this,pubs);
        this.specialPublication_list.setAdapter(adapter);
    }
}

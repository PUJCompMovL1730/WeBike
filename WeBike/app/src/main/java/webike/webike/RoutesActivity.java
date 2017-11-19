package webike.webike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import webike.webike.adaptadores.adapter_route;
import webike.webike.logic.Route;
import webike.webike.utils.FData;
import webike.webike.utils.ListActions;

public class RoutesActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    private ListView routes_list;
    private ArrayList<Route> current_route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        routes_list = (ListView) findViewById(R.id.publications_list);

        routes_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(RoutesActivity.this, RouteActivity.class);
                Bundle bundle = new Bundle();
                Route route = current_route.get(position);
                bundle.putSerializable("pub",route);
                myIntent.putExtras(bundle);
                startActivity(myIntent);
            }
        });
        loadRoutes();
    }

    public void loadRoutes(){

        FData.getRoutes(database, mAuth.getCurrentUser().getUid() , new ListActions<Route>() {
            @Override
            public void onReceiveList(ArrayList<Route> data, DatabaseReference reference) {
                current_route = data;
                infalteListWithRoutes( data );
            }

            @Override
            public void onCancel( DatabaseError databaseError ) {

            }
        });
    }

    public void infalteListWithRoutes( ArrayList<Route> pubs ){
        adapter_route adapter = new adapter_route(this,pubs);
        this.routes_list.setAdapter(adapter);
    }

}

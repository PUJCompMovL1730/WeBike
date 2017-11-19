package webike.webike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import webike.webike.logic.PlacePromotion;
import webike.webike.logic.PlannedRoute;
import webike.webike.logic.Route;
import webike.webike.utils.FData;

public class OrgProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FData fData;
    private FirebaseDatabase database;
    private ListView placePromotion_list;
    private ArrayList<String> current_placePromotion;
    private ListView plannedRoute_list;
    private ArrayList<String> current_plannedRoute;
    private ImageButton b_add_placePromotion;
    private ImageButton b_add_plannedRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_profile);

        mAuth = FirebaseAuth.getInstance();
        fData = new FData();
        database = FirebaseDatabase.getInstance();

        b_add_placePromotion = (ImageButton) findViewById(R.id.ib_add_place);
        b_add_placePromotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrgProfileActivity.this, CreatePlacePromotionActivity.class);
                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity( intent );
            }
        });

        b_add_plannedRoute = (ImageButton) findViewById(R.id.ib_add_route);
        b_add_plannedRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrgProfileActivity.this, CreatePlannedRouteActivity.class);
                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity( intent );
            }
        });

/*
        plannedRoute_list = (ListView) findViewById(R.id.lv_recorridos_planeados);

        placePromotion_list = (ListView) findViewById(R.id.lv_lugares_promocionados);
        placePromotion_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(OrgProfileActivity.this, PlacePromotion.class);
                Bundle bundle = new Bundle();
                PlacePromotion placePromotion = current_placePromotion.get(position);
                bundle.putSerializable("pub",placePromotion);
                myIntent.putExtras(bundle);
                startActivity(myIntent);
            }
        });

        ArrayAdapter<String> adapter_placePromoiton = new ArrayAdapter<String>(this, R.layout.simple_list_item_1, current_placePromotion);
        ArrayAdapter<String> adapter_plannedRoute = new ArrayAdapter<String>(this, R.layout.simple_list_item_1, current_plannedRoute);
        loadPlacePromotions();
        loadPlannedRoute();
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClicked = item.getItemId();
        Intent intent;
        switch(itemClicked) {
            case R.id.config_menuItem:
                intent = new Intent(OrgProfileActivity.this, ConfigActivity.class);
                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity( intent );
                break;
            default : // Optional
                // Statements
        }
        return super.onOptionsItemSelected(item);
    }


}

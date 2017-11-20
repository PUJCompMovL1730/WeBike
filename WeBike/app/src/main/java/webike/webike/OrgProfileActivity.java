package webike.webike;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import webike.webike.adaptadores.SpecialPublicationAdapter;
import webike.webike.logic.OrgUser;
import webike.webike.logic.PlacePromotion;
import webike.webike.logic.PlannedRoute;
import webike.webike.logic.Route;
import webike.webike.logic.SpecialPublication;
import webike.webike.utils.FData;
import webike.webike.utils.ListActions;
import webike.webike.utils.SingleValueActions;
import webike.webike.utils.Utils;

public class OrgProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FData fData;
    private FirebaseDatabase database;

    private ListView placePromotion_list;
    private ListView plannedRoute_list;
    private ImageButton b_add_placePromotion;
    private ImageButton b_add_plannedRoute;

    private ArrayList<String> current_placePromotion;
    private ArrayList<String> current_plannedRoute;
    private ArrayList<PlacePromotion> promotions;
    private ArrayList<PlannedRoute> routes;

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

        plannedRoute_list = (ListView) findViewById(R.id.lv_recorridos_planeados);

        placePromotion_list = (ListView) findViewById(R.id.lv_lugares_promocionados);
        placePromotion_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(OrgProfileActivity.this, PlacePromotion.class);
                Bundle bundle = new Bundle();
                PlacePromotion placePromotion = promotions.get(position);
                bundle.putSerializable("pub",placePromotion);
                myIntent.putExtras(bundle);
                startActivity(myIntent);
            }
        });

        loadPlacePromotions();
        loadPlannedRoute();
        loadUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_org_menu, menu);
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
            case R.id.logout_menuItem:
                mAuth.signOut();
                intent = new Intent(OrgProfileActivity.this, LoginActivity.class);
                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity( intent );
                break;
            default : // Optional
                // Statements
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(OrgProfileActivity.this).create();
        alertDialog.setTitle("Salir de sesion");
        alertDialog.setMessage( getString(R.string.logout_dialog_text) );
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getText( R.string.logout_confirm_text ) ,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        OrgProfileActivity.this.mAuth.signOut();
                        startActivity( new Intent( OrgProfileActivity.this , LoginActivity.class));
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getText( R.string.logout_cancel_text ) ,
                new DialogInterface.OnClickListener(){
                    public void onClick( DialogInterface dialog , int which) {

                    }
                }) ;
        alertDialog.show();
    }

    public void loadPlannedRoute(){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FData.getOrgPlannedRoutes(database, uid, new ListActions<PlannedRoute>() {
            @Override
            public void onReceiveList(ArrayList<PlannedRoute> data, DatabaseReference reference) {
                ArrayList<SpecialPublication> arr = new ArrayList<SpecialPublication>(data);
                SpecialPublicationAdapter adapter = new SpecialPublicationAdapter( OrgProfileActivity.this , arr);
                plannedRoute_list.setAdapter(adapter);
            }

            @Override
            public void onCancel(DatabaseError error) {
                Utils.shortToast( OrgProfileActivity.this , "Error buscando rutas planeadas");
            }
        });
    }

    public void loadPlacePromotions(){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FData.getOrgPromotions(database, uid, new ListActions<PlacePromotion>() {
            @Override
            public void onReceiveList(ArrayList<PlacePromotion> data, DatabaseReference reference) {
                ArrayList<SpecialPublication> arr = new ArrayList<SpecialPublication>(data);
                SpecialPublicationAdapter adapter = new SpecialPublicationAdapter( OrgProfileActivity.this , arr);
                placePromotion_list.setAdapter(adapter);
            }

            @Override
            public void onCancel(DatabaseError error) {
                Utils.shortToast( OrgProfileActivity.this , "Error buscando lugares promocioandaos");
            }
        });
    }

    public void loadUser(){
        FData.getOrgUserFromId(FirebaseDatabase.getInstance(), FirebaseAuth.getInstance().getCurrentUser().getUid(), new SingleValueActions<OrgUser>() {
            @Override
            public void onReceiveSingleValue(OrgUser data, DatabaseReference reference) {
                TextView username = (TextView) findViewById(R.id.nombre_organizacion);
                username.setText( data.getName() );
            }

            @Override
            public void onCancel(DatabaseError error) {

            }
        });
    }
}

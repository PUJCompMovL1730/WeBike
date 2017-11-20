
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
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


import webike.webike.adaptadores.MessageAdapter;
import webike.webike.adaptadores.adaptador_home_notificacion;
import webike.webike.adaptadores.adapter_all_publication;
import webike.webike.logic.AbstractPublication;
import webike.webike.logic.Message;
import webike.webike.logic.PlacePromotion;
import webike.webike.logic.PlannedRoute;
import webike.webike.logic.Publicacion;

import webike.webike.logic.Weather;
import webike.webike.ubicacion.Map;
import webike.webike.ubicacion.MapCreateRoute;
import webike.webike.utils.FData;
import webike.webike.utils.ListActions;
import webike.webike.utils.Utils;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private ListView homeList;

    private ArrayList<AbstractPublication> current_pub;
    private Button b_panic;
    private ImageView b_help;
    private ImageView b_manual;
    private ImageView b_weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        b_panic = (Button) findViewById(R.id.panic);
        b_help = (ImageView) findViewById(R.id.help);
        b_manual = (ImageView) findViewById(R.id.manual);
        b_weather = (ImageView) findViewById(R.id.imageButton);

        b_panic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PanicButtonActivity.class);
                startActivity(intent);
            }
        });
        b_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, HelpButtonActivity.class);
                startActivity(intent);
            }
        });
        b_manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ManualActivity.class);
                startActivity(intent);
            }
        });
        b_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Weather.class);
                startActivity(intent);
            }
        });
        homeList = (ListView) findViewById(R.id.home_list);

        homeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent myIntent;
                Bundle bundle = new Bundle();
                if(current_pub.get(position) instanceof Publicacion){
                    myIntent = new Intent(HomeActivity.this, PublicationActivity.class);
                    Publicacion publicacion = (Publicacion) current_pub.get(position);
                    bundle.putSerializable("pub",publicacion);
                    myIntent.putExtras(bundle);
                    startActivity(myIntent);
                }
                if(current_pub.get(position) instanceof PlacePromotion){
                    myIntent = new Intent(HomeActivity.this, PlaceActivity.class);
                    PlacePromotion placePromotion = (PlacePromotion) current_pub.get(position);
                    bundle.putSerializable("pub",placePromotion);
                    myIntent.putExtras(bundle);
                    startActivity(myIntent);
                }
                if(current_pub.get(position) instanceof PlannedRoute){
                    myIntent = new Intent(HomeActivity.this, PlannedRouteActivity.class);
                    PlannedRoute plannedRoute = (PlannedRoute) current_pub.get(position);
                    bundle.putSerializable("pub",plannedRoute);
                    myIntent.putExtras(bundle);
                    startActivity(myIntent);
                }
            }
        });
        loadPubs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClicked = item.getItemId();
        Intent intent;
        switch(itemClicked) {
            case R.id.publicaciones_menuItem:
                intent = new Intent(HomeActivity.this, PublicationsActivity.class);
                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity( intent );
                break;
            case R.id.profile_menuItem:
                intent = new Intent(HomeActivity.this, ViewProfileActivity.class);
                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity( intent );
                break;
            case R.id.publicaciones_especiales_menuItem:
                intent = new Intent(HomeActivity.this, SpecialPublicationsActivity.class);
                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity( intent );
                break;
            case R.id.logout_menuItem:
                mAuth.signOut();
                intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity( intent );
                break;
            /*case R.id.config_menuItem:
                //TODO: cambiar
                //intent = new Intent(HomeActivity.this, ConfigActivity.class);
                intent = new Intent(HomeActivity.this, OrgProfileActivity.class);
                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity( intent );
                break;*/
            case R.id.inbox_home_menu:
                intent = new Intent(this,MailboxActivity.class);
                startActivity(intent);
                break;
            case R.id.search_user_home_menu:
                startActivity( new Intent(HomeActivity.this, SearchUserActivity.class));
                break;
            case R.id.go_to_planroute:
                startActivity( new Intent(HomeActivity.this, Map.class));
                break;
            case R.id.create_group:
                startActivity( new Intent(HomeActivity.this, CreateGroupActivity.class));
                break;
            default : // Optional
                // Statements
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
        alertDialog.setTitle("Salir de sesion");
        alertDialog.setMessage( getString(R.string.logout_dialog_text) );
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getText( R.string.logout_confirm_text ) ,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        HomeActivity.this.mAuth.signOut();
                        startActivity( new Intent( HomeActivity.this , LoginActivity.class));
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getText( R.string.logout_cancel_text ) ,
                new DialogInterface.OnClickListener(){
                    public void onClick( DialogInterface dialog , int which) {

                    }
                }) ;
        alertDialog.show();
    }

    public void loadPubs(){
        FData.getAllPublications(database, new ListActions<AbstractPublication>() {
            @Override
            public void onReceiveList(ArrayList<AbstractPublication> data, DatabaseReference reference) {
                current_pub = data;
                adapter_all_publication adapter = new adapter_all_publication(HomeActivity.this,current_pub);
                HomeActivity.this.homeList.setAdapter(adapter);
            }

            @Override
            public void onCancel( DatabaseError databaseError ) {

            }
        });
    }
}

package webike.webike;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import webike.webike.adaptadores.adaptador_home_mensaje;
import webike.webike.adaptadores.adaptador_home_notificacion;
import webike.webike.logic.Mailbox;
import webike.webike.logic.Message;
import webike.webike.logic.Publicacion;
import webike.webike.logic.User;
import webike.webike.ubicacion.Map;
import webike.webike.utils.FData;
import webike.webike.utils.ListActions;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FData fData;
    private FirebaseDatabase database;
    private boolean type = false;
    private ListView homeList;

    private Button msgButton;
    private Button pubButton;
    private ArrayList<Message> current_msg;
    private ArrayList<Publicacion> current_pub;
    private Button b_panic;
    private ImageView b_help;
    private ImageView b_manual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        fData = new FData();
        database = FirebaseDatabase.getInstance();

        b_panic = (Button) findViewById(R.id.panic);
        b_help = (ImageView) findViewById(R.id.help);
        b_manual = (ImageView) findViewById(R.id.manual);

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

        homeList = (ListView) findViewById(R.id.home_list);

        homeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(type)
                {
                    Intent myIntent = new Intent(HomeActivity.this, PublicationActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("pub",current_pub.get(position));
                    myIntent.putExtras(bundle);
                    startActivity(myIntent);
                }
            }
        });

        loadPubs();

        /*
        msgButton = (Button) findViewById(R.id.load_msg_button);
        pubButton = (Button) findViewById(R.id.load_pubs_button);

        msgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMsg( mAuth.getCurrentUser().getUid() );
                type = false;
            }
        });

        pubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPubs();
                type = true;
            }
        });

        homeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(type)
                {
                    Intent myIntent = new Intent(HomeActivity.this, PublicationActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("pub",current_pub.get(position));
                    myIntent.putExtras(bundle);
                    startActivity(myIntent);
                }
            }
        });*/
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
            case R.id.config_menuItem:
                intent = new Intent(HomeActivity.this, ConfigActivity.class);
                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity( intent );
                break;
            case R.id.send_msg_test:
                User usr = new User();
                usr.setKey( this.mAuth.getCurrentUser().getUid() );
                usr.setEmail("juanmig8@hotmail.com");
                Message msg = new Message("Hola esto es una prueba",usr , usr );
                this.fData.postMessage(usr.getKey() , usr.getKey() , msg);
                break;
            case R.id.search_user_test:
                startActivity( new Intent(HomeActivity.this, SearchUserActivity.class));
                break;
            case R.id.go_to_planroute:
                startActivity( new Intent(HomeActivity.this, Map.class));
            default : // Optional
                // Statements
        }
        /*
        if( itemClicked == R.id.logout_menuItem ){
            mAuth.signOut();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
            startActivity( intent );
        }else if( itemClicked == R.id.config_menuItem ){
            Intent intent = new Intent(HomeActivity.this, ConfigActivity.class);
            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
            startActivity( intent );
        }else if( itemClicked == R.id.send_msg_test){
            User usr = new User();
            usr.setKey( this.mAuth.getCurrentUser().getUid() );
            usr.setEmail("juanmig8@hotmail.com");
            Message msg = new Message("Hola esto es una prueba",usr , usr );
            this.fData.postMessage(usr.getKey() , usr.getKey() , msg);
        }else if( itemClicked == R.id.search_user_test){
            startActivity( new Intent(HomeActivity.this, SearchUserActivity.class));
        }else if( itemClicked == R.id.go_to_planroute){
            startActivity( new Intent(HomeActivity.this, Map.class));
        }
        */
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

    public void loadMsg( String key ){

        FData.getReceivedMessages(database, key, new ListActions<Message>() {
            @Override
            public void onReceiveList(ArrayList<Message> data, DatabaseReference reference) {
                current_msg = data;
                inflateListWithMsgs( data );
            }

            @Override
            public void onCancel( DatabaseError databaseError ) {

            }
        });
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

    public void inflateListWithMsgs(ArrayList<Message> msgs ){
        if( msgs.isEmpty() ){
            ArrayList<String> info = new ArrayList<>();
            info.add("---- No hay mensajes ----");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_list_item_1 ,info);
            this.homeList.setAdapter(adapter);
        }else {
            adaptador_home_mensaje adapter = new adaptador_home_mensaje(this, msgs);
            this.homeList.setAdapter(adapter);
        }
    }

    public void infalteListWithPubs( ArrayList<Publicacion> pubs ){
        adaptador_home_notificacion adapter = new adaptador_home_notificacion(this,pubs);
        this.homeList.setAdapter(adapter);
    }
}

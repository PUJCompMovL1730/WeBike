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
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FData fData;
    private FirebaseDatabase database;

    private ListView homeList;

    private Button msgButton;
    private Button pubButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        fData = new FData();
        database = FirebaseDatabase.getInstance();
        homeList = (ListView) findViewById(R.id.home_listView);
        msgButton = (Button) findViewById(R.id.load_msg_button);
        pubButton = (Button) findViewById(R.id.load_pubs_button);

        msgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMsg( mAuth.getCurrentUser().getUid() );
            }
        });

        pubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPubs();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClicked = item.getItemId();
        if( itemClicked == R.id.logout_menuItem ){
            mAuth.signOut();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
            startActivity( intent );
        }else if( itemClicked == R.id.config_menuItem ){
            //configuaracion
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
        DatabaseReference ref = database.getReference(FData.PATH_TO_USERS + "/" + key + "/mailbox/received" );
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Message> msgs = new ArrayList<Message>();
                for( DataSnapshot ds : dataSnapshot.getChildren() ){
                    HashMap<String,Object> mbox = (HashMap<String,Object>) ds.getValue();
                    Message msg = new Message();
                    msg.setMsg( (String) mbox.get("msg") );

                    User receiver = new User();
                    HashMap<String, Object> hr = (HashMap<String, Object>) mbox.get("receiver");
                    receiver.setEmail( (String) hr.get("email") );
                    msg.setReceiver( receiver );

                    User sender = new User();
                    HashMap<String, Object> hs = (HashMap<String, Object>) mbox.get("sender");
                    sender.setEmail( (String) hs.get("email") );
                    msg.setSender( sender );

                    msgs.add(msg);
                }
                inflateListWithMsgs( msgs );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void loadPubs(){
        DatabaseReference ref = database.getReference(FData.PATH_TO_PUBS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Publicacion> pubs = new ArrayList<Publicacion>();
                for ( DataSnapshot ds : dataSnapshot.getChildren() ){
                    Publicacion p = ds.getValue(Publicacion.class);
                    pubs.add(p);
                }
                infalteListWithPubs( pubs );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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

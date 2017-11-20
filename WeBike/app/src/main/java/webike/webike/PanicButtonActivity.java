package webike.webike;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import webike.webike.logic.Message;
import webike.webike.logic.User;
import webike.webike.utils.FData;
import webike.webike.utils.ListActions;
import webike.webike.utils.Permisos;
import webike.webike.utils.Utils;

import static android.Manifest.permission_group.LOCATION;

public class PanicButtonActivity extends AppCompatActivity {
    private FusedLocationProviderClient mFusedLocationClient;
    private class Friend{
        public String id;
        public String name;
        public String email;
        Friend( String id , String name, String email){
            this.id = id;
            this.name = name;
            this.email = email;
        }
    }
    Button b_panic;
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    ArrayList<Friend> friends;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panic_button);
        Button b_panic = (Button) findViewById(R.id.panicbut);
        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        friends = new ArrayList<>();
        mFusedLocationClient =	LocationServices.getFusedLocationProviderClient(this);
        b_panic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Permisos.askPermission(PanicButtonActivity.this, Permisos.FINE_LOCATION, "Se requiere el acceso a la localización.")) {
                    locationAction();
                }
            }
        });
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (Permisos.permissionGranted(requestCode, permissions, grantResults)) {
            locationAction();
        }
    }

    private void locationAction() {
        if (Permisos.checkSelfPermission(this, Permisos.FINE_LOCATION)) {
            getFriends();

        }
    }
    public void getFriends(){
        FirebaseUser user = fAuth.getCurrentUser();
        FData.getFriends(database, user.getUid(), new ListActions<User>() {
            @Override
            public void onReceiveList(ArrayList<User> data, DatabaseReference reference) {
                mFusedLocationClient =	LocationServices.getFusedLocationProviderClient(PanicButtonActivity.this);
                for ( User user : data ) {
                    friends.add( new Friend( user.getKey() , user.getFirstName() + " " + user.getLastName() , user.getEmail() ) );
                }
                sendMessage();
                Toast.makeText(getBaseContext(),"Mensaje de panico enviado.... que la suerte este de tu lado",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(DatabaseError error) {

            }
        });
    }
    public void sendMessage(){
        for (Friend amigo:friends) {
            String src = this.fAuth.getCurrentUser().getUid();
            String dst = amigo.id;
            String subject = "EMERGENCIA";
            String message = "Me encuentro en una emergencia";
            Message msg = new Message(message, subject, src, dst);
            FData.postMessage(database, msg);
        }
    }

}

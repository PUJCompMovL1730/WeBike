package webike.webike;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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
import webike.webike.utils.ListFilteredActions;
import webike.webike.utils.Permisos;

import static android.R.attr.data;

public class HelpButtonActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_help_button);
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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FData.getUsers(database, 5, new ListFilteredActions<User, Integer>() {
            @Override
            public boolean searchCriteria(User data, Integer filter) {
                return data.isBicitaller();
            }

            @Override
            public void onReceiveList(ArrayList<User> data, DatabaseReference reference) {
                for ( User user : data ) {
                    friends.add( new Friend( user.getKey() , user.getFirstName() + " " + user.getLastName() , user.getEmail() ) );
                }
                sendMessage();
                Toast.makeText(getBaseContext(),"Mensaje para bicitalleres enviados enviado.",Toast.LENGTH_SHORT).show();
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
            String subject = "Solicitud bicitaller";
            String message = "Hola solicito el servicio de bicitaller";
            Message msg = new Message(message, subject, src, dst);
            FData.postMessage(database, msg);
        }
    }

}

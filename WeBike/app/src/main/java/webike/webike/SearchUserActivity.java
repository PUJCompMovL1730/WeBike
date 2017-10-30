package webike.webike;

import android.nfc.Tag;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import webike.webike.logic.User;

public class SearchUserActivity extends AppCompatActivity {

    private EditText searchEditText;
    private Button searchButton;
    private ListView resultList;
    private String search_name;
    private ArrayList<User> results;
    ArrayList<User> usuarios;

    public static final String PATH_USERS = "users/";
    private FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        database = FirebaseDatabase.getInstance();
        loadUser("jueputa");
    }

   public void loadUser(final String buscar){

       final DatabaseReference myRef = database.getReference(PATH_USERS);
       myRef.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               usuarios = new ArrayList<>();
               for(DataSnapshot singleSnashot: dataSnapshot.getChildren()){
                   User myUser = singleSnashot.getValue(User.class);
                   String nombre = myUser.getFirstName()+" "+myUser.getLastName();
                   //String nombre = myUser.getEmail();
                   if(nombre.contains(buscar)){
                       usuarios.add(myUser);
                       Log.i("TAG","CORREO:"+nombre);
                   }
               }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

   }
}

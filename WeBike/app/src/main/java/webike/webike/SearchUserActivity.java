package webike.webike;

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
import java.util.HashMap;

import webike.webike.adaptadores.UserArrayAdapter;
import webike.webike.logic.User;
import webike.webike.utils.FAuth;
import webike.webike.utils.FData;

public class SearchUserActivity extends AppCompatActivity {

    private EditText searchEditText;
    private Button searchButton;
    private ListView resultList;
    private String search_name;
    private ArrayList<User> results;
    private FAuth mAuth;
    private FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        database = FirebaseDatabase.getInstance();
        searchEditText = (EditText) findViewById(R.id.searchText_editText);
        mAuth = new FAuth(this) {
        };
        resultList = (ListView) findViewById(R.id.search_list);

        searchButton  = (Button) findViewById(R.id.searchUser_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadUser( searchEditText.getText().toString().trim() );
            }
        });
    }

   public void loadUser(final String buscar){

       final DatabaseReference myRef = database.getReference(FData.PATH_TO_USERS);
       myRef.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               ArrayList<User> usuarios = new ArrayList<>();
               for(DataSnapshot singleSnashot: dataSnapshot.getChildren()){

                   User myUser = new User();
                   myUser.setEmail((String)((HashMap<String, Object>)singleSnashot.getValue()).get("email"));
                   myUser.setFirstName( (String)((HashMap<String, Object>)singleSnashot.getValue()).get("firstName") );
                   myUser.setLastName( (String)((HashMap<String, Object>)singleSnashot.getValue()).get("lastName") );
                   myUser.setGender( (String)((HashMap<String, Object>)singleSnashot.getValue()).get("gender") );
                   myUser.setKey( (String)((HashMap<String, Object>)singleSnashot.getValue()).get("key") );
                   myUser.setAge( ((Long)((HashMap<String, Object>)singleSnashot.getValue()).get("age") ).intValue() );

                   String nombre = myUser.getEmail();
                   nombre = nombre.substring(0 , nombre.indexOf('@'));
                   if(nombre.contains(buscar) && !myUser.getKey().equals( mAuth.getUser().getUid() ) ) {
                       usuarios.add(myUser);
                   }
               }
               updateView( usuarios );
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

   }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.onStop();
    }

    public void updateView(ArrayList<User> usrs) {
       Log.i("INFO_DATABASE", "updateView: "+ usrs.toString() );
       UserArrayAdapter adapter = new UserArrayAdapter(this, usrs);
       this.resultList.setAdapter(adapter);
   }
}

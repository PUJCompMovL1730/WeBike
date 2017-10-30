package webike.webike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import webike.webike.R;
import webike.webike.logic.User;
import webike.webike.utils.FData;

public class AddFriendActivity extends AppCompatActivity {

    private TextView username;
    private TextView email;
    private TextView age;
    private Button addFriend;
    private User friend;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        username = (TextView) findViewById(R.id.usr_name_inv);
        email = (TextView) findViewById(R.id.email_usr_inv);
        age = (TextView) findViewById(R.id.usr_age);
        addFriend = (Button) findViewById(R.id.send_inv_btn);

        Intent tempIntent = getIntent();
        Bundle bundle = tempIntent.getExtras();

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance();

        User new_friend = new User();
        if(bundle != null){
            new_friend = (User) bundle.get("user");
        }
        Log.i("INFO_DATABASE", "updateView: "+ new_friend.getEmail() );
        username.setText(new_friend.getFirstName() + " " + new_friend.getLastName());
        email.setText(new_friend.getEmail());
        age.setText( Integer.toString(new_friend.getAge()) + " años");

        this.friend = new_friend;
        addFriend.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 addFriendToCurrentUser( );
                 startActivity(new Intent(AddFriendActivity.this, HomeActivity.class));
             }
        });
    }

    public void addFriendToCurrentUser(){
        DatabaseReference ref = mData.getReference(FData.PATH_TO_USERS + "/" + mAuth.getCurrentUser().getUid() );
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("FRIEND", "onDataChange: "+ dataSnapshot.getValue() );


                User myUser = new User();
                myUser.setEmail((String)((HashMap<String, Object>)dataSnapshot.getValue()).get("email"));
                myUser.setFirstName( (String)((HashMap<String, Object>)dataSnapshot.getValue()).get("firstName") );
                myUser.setLastName( (String)((HashMap<String, Object>)dataSnapshot.getValue()).get("lastName") );
                myUser.setGender( (String)((HashMap<String, Object>)dataSnapshot.getValue()).get("gender") );
                myUser.setKey( (String)((HashMap<String, Object>)dataSnapshot.getValue()).get("key") );
                myUser.setAge( ((Long)((HashMap<String, Object>)dataSnapshot.getValue()).get("age") ).intValue() );


                ArrayList<String> friends = (ArrayList<String>) ((HashMap<String, Object>)dataSnapshot.getValue()).get("friends");
                if( friends == null )
                    friends = new ArrayList<String>();
                friends.add( AddFriendActivity.this.friend.getKey() );

                myUser.setFriends(friends);

                DatabaseReference ref2 = mData.getReference(FData.PATH_TO_USERS + "/" + myUser.getKey() );
                ref2.setValue(myUser);
                Toast.makeText(getBaseContext(),"Se ha añadido a sus amigos",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getBaseContext(),"Error en el proceso",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

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
import java.util.List;

import webike.webike.logic.User;
import webike.webike.utils.FData;
import webike.webike.utils.SingleValueActions;

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

        FData.getUserFromId(mData, mAuth.getCurrentUser().getUid(), new SingleValueActions<User>() {

            public void onReceiveSingleValue(User data , DatabaseReference reference) {
                List<String> friends = data.getFriends();
                if( friends == null )
                    friends = new ArrayList<>();
                friends.add( AddFriendActivity.this.friend.getKey() );

                data.setFriends(new ArrayList<>(friends));

                //DatabaseReference ref2 = mData.getReference(FData.PATH_TO_USERS + "/" + myUser.getKey() );
                //ref2.setValue(myUser);
                reference.setValue(data);
                Toast.makeText(getBaseContext(),"Se ha añadido a sus amigos",Toast.LENGTH_SHORT).show();
            }


            public void onCancel( DatabaseError databaseError ) {
                Toast.makeText(getBaseContext(),"Error en el proceso",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
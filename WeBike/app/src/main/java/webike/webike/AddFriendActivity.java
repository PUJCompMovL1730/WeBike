package webike.webike;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import webike.webike.logic.User;
import webike.webike.utils.FData;
import webike.webike.utils.SingleValueActions;

public class AddFriendActivity extends AppCompatActivity {

    private TextView username;
    private TextView email;
    private TextView knowHim;
    private Button addFriend;
    private ImageView biciTaller;

    private User friend;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        username = (TextView) findViewById(R.id.usr_name_inv);
        email = (TextView) findViewById(R.id.email_usr_inv);
        knowHim = (TextView) findViewById(R.id.know_him);
        biciTaller = (ImageView) findViewById(R.id.bicitaller_friend);
        addFriend = (Button) findViewById(R.id.send_inv_btn);
        addFriend.setVisibility(View.GONE);
        Intent tempIntent = getIntent();
        Bundle bundle = tempIntent.getExtras();

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance();
        knowHim.setVisibility(View.GONE);
        User new_friend = new User();
        if(bundle != null){
            new_friend = (User) bundle.get("user");
            if ( new_friend.isBicitaller() ){
                biciTaller.setVisibility(View.VISIBLE);
            }
        }
        Log.i("INFO_DATABASE", "updateView: "+ new_friend.getEmail() );
        username.setText(new_friend.getFirstName() + " " + new_friend.getLastName());
        email.setText(new_friend.getEmail());

        this.friend = new_friend;
        this.checkUserFriends();

    }

    public void checkUserFriends(){
        FData.getUserFromId(mData, mAuth.getCurrentUser().getUid(), new SingleValueActions<User>() {
            @Override
            public void onReceiveSingleValue(final User data, DatabaseReference reference) {
                final DatabaseReference ref = reference;
                final List<String> friends = (data.getFriends()==null)?new ArrayList<String>(): data.getFriends();
                if( friends.contains(friend.getKey()) ){
                    //if has
                    if (!data.isBicitaller()){
                        biciTaller.setVisibility(View.GONE);
                    }

                    addFriend.setText("Eliminar Amigo");
                    addFriend.setBackgroundColor( getColor(R.color.burnRed) );
                    addFriend.setVisibility(View.VISIBLE);
                    addFriend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            friends.remove(friend.getKey());
                            data.setFriends( new ArrayList<String>(friends ));
                            ref.setValue(data);
                            Toast.makeText(getBaseContext(),"Se ha eleminado de sus amigos",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddFriendActivity.this, HomeActivity.class));
                        }
                    });
                }else{
                    //if not
                    addFriend.setVisibility(View.VISIBLE);
                    knowHim.setVisibility(View.VISIBLE);
                    addFriend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            friends.add(friend.getKey());
                            data.setFriends( new ArrayList<String>(friends ));
                            ref.setValue(data);
                            Toast.makeText(getBaseContext(),"Se ha añadido a sus amigos",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddFriendActivity.this, HomeActivity.class));
                        }
                    });

                }
            }

            @Override
            public void onCancel(DatabaseError error) {

            }
        });
    }

    public void addFriendToCurrentUser(){

        FData.getUserFromId(mData, mAuth.getCurrentUser().getUid(), new SingleValueActions<User>() {
            public void onReceiveSingleValue(User data , DatabaseReference reference) {
                List<String> friends = data.getFriends();
                if( friends == null )
                    friends = new ArrayList<>();
                if( !friends.contains(friend.getKey()) )
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
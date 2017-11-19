package webike.webike;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class ViewProfileActivity extends AppCompatActivity {

    private ListView friendList;
    private ListView groupList;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mData;
    private FirebaseDatabase database;
    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
       // mAuth = new FAuth(this){};
        friendList = (ListView)findViewById(R.id.list_amigos);
        groupList = (ListView)findViewById(R.id.list_grupos);
        username = (TextView)findViewById(R.id.user_name);
  //      showFriends();


    }

   /* public void showFriends(){

        DatabaseReference ref = mData.getReference(FData.PATH_TO_USERS + "/" + mAuth.getCurrentUser().getUid() );
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User myUser= new User();
                ArrayList<User> friends = (ArrayList<User>) ((HashMap<String, Object>)dataSnapshot.getValue()).get("friends");

                updateAd(friends);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getBaseContext(),"Error mostrando amigos",Toast.LENGTH_SHORT).show();
            }

        });

    }

    public void updateAd(ArrayList<User> usrs) {
        Log.i("INFO_DATABASE", "updateView: "+ usrs.toString() );
        UserArrayAdapter adapter = new UserArrayAdapter(this, usrs);
        this.friendList.setAdapter(adapter);
    }
*/

}

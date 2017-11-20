package webike.webike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import webike.webike.adaptadores.GroupAdapter;
import webike.webike.adaptadores.UserArrayAdapter;
import webike.webike.logic.Group;
import webike.webike.logic.User;
import webike.webike.utils.FAuth;
import webike.webike.utils.FData;
import webike.webike.utils.ListActions;
import webike.webike.utils.SingleValueActions;

public class ViewProfileActivity extends AppCompatActivity {

    private ListView friendList;
    private ListView groupList;
    private TextView username;
    private TextView email;
    private Button config;
    private Button group;
    private Button route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        friendList = (ListView)findViewById(R.id.list_amigos);
        groupList = (ListView)findViewById(R.id.list_grupos);
        username = (TextView)findViewById(R.id.user_name);
        config = (Button)findViewById(R.id.user_config);
        group = (Button)findViewById(R.id.button_group);
        route = (Button)findViewById(R.id.button_recorr);
        email = (TextView) findViewById(R.id.user_mail);

        config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewProfileActivity.this, ConfigActivity.class);
                startActivity(intent);
            }
        });

        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewProfileActivity.this, CreateGroupActivity.class);
                startActivity(intent);
            }
        });

        route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewProfileActivity.this, RoutesActivity.class);
                startActivity(intent);
            }
        });

        this.loadName();
        this.loadFriends();
        //this.loadGroups();
    }


    public void loadFriends(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FData.getFriends(database, uid, new ListActions<User>() {
            @Override
            public void onReceiveList(ArrayList<User> data, DatabaseReference reference) {
                //adapter
                updateAdap(data);
            }

            @Override
            public void onCancel(DatabaseError error) {

            }
        });
    }

    public void loadGroups(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FData.getUserGroups(database, uid, new ListActions<Group>() {
            @Override
            public void onReceiveList(ArrayList<Group> data, DatabaseReference reference) {
                if(data.size()==0){
                    updateAdapGroup(data);
                }else{
                    Toast.makeText(ViewProfileActivity.this, "usuario sin grupos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancel(DatabaseError error) {
            }
        });
    }

    public void updateAdap(ArrayList<User> usrs) {
        Log.i("INFO_DATABASE", "updateView: "+ usrs.toString() );
        UserArrayAdapter adapter = new UserArrayAdapter(this, usrs);
        this.friendList.setAdapter(adapter);
    }


    public void updateAdapGroup(ArrayList<Group> groups){
        Log.i("INFO_DATABASE", "updateView: "+ groups.toString() );
        GroupAdapter adapter = new GroupAdapter(this, groups);
        this.groupList.setAdapter(adapter);
    }

    public void loadName(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FData.getUserFromId(database, uid, new SingleValueActions<User>() {
            @Override
            public void onReceiveSingleValue(User data, DatabaseReference reference) {
                username.setText(data.getFirstName() + " " + data.getLastName());
                email.setText(data.getEmail());
            }

            @Override
            public void onCancel(DatabaseError error) {

            }
        });

    }

}

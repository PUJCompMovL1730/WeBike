package webike.webike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import webike.webike.adaptadores.GroupArrayAdapter;
import webike.webike.adaptadores.UserArrayAdapter;
import webike.webike.logic.Group;
import webike.webike.logic.User;
import webike.webike.utils.*;

public class AddFriendToGroup extends AppCompatActivity {

    private TextView username;
    private TextView email;
    private TextView age;
    private Button addToGroup;
    private ListView list;
    private User friend;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mData;
    private ArrayList<Group> results;
    private int selection;
    private Group temp_group;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend_to_group);

        username = (TextView) findViewById(R.id.usr_name_add_grp);
        email = (TextView) findViewById(R.id.frd_email_gp);
        age = (TextView) findViewById(R.id.age_frn_gp);
        addToGroup = (Button) findViewById(R.id.btn_add_frn_grp);
        list = (ListView) findViewById(R.id.grp_list_view);

        Intent tempIntent = getIntent();
        Bundle bundle = tempIntent.getExtras();

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance();

        User friend = new User();
        if (bundle != null) {
            friend = (User) bundle.get("user");
        }
        fileListView();

        Log.i("INFO_DATABASE", "updateView: " + friend.getEmail());
        username.setText(friend.getFirstName() + " " + friend.getLastName());
        email.setText(friend.getEmail());
        age.setText( Integer.toString(friend.getAge()) + " años");

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              temp_group = results.get(i);
            }
        });

        addToGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });

        //list.setOnClickListener();
    }

    private void fileListView() {
        FData.getUserGroups(this.mData, mAuth.getCurrentUser().getUid(), new ListActions<Group>() {

            @Override
            public void onReceiveList(ArrayList<Group> data, DatabaseReference reference) {
                if (data.isEmpty()) {
                    Toast.makeText(AddFriendToGroup.this, "Grupo creado", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AddFriendToGroup.this, HomeActivity.class);
                }
                updateView(data);
            }

            @Override
            public void onCancel(DatabaseError error) {

            }
        });
    }

    public void updateView(ArrayList<Group> groups) {
        Log.i("INFO_DATABASE", "updateView: " + groups.toString());
        GroupArrayAdapter adapter = new GroupArrayAdapter(this, groups);
        this.list.setAdapter(adapter);
    }
}

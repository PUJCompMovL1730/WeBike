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
import java.util.List;

import webike.webike.adaptadores.GroupArrayAdapter;
import webike.webike.adaptadores.UserArrayAdapter;
import webike.webike.logic.Group;
import webike.webike.logic.User;
import webike.webike.utils.*;

import static webike.webike.utils.FData.postGroups;
import static webike.webike.utils.FData.postUser;

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
        results = new ArrayList<>();
        username = (TextView) findViewById(R.id.usr_name_add_grp);
        email = (TextView) findViewById(R.id.frd_email_gp);
        age = (TextView) findViewById(R.id.age_frn_gp);
        addToGroup = (Button) findViewById(R.id.btn_add_frn_grp);
        list = (ListView) findViewById(R.id.grp_list_view);

        Intent tempIntent = getIntent();
        Bundle bundle = tempIntent.getExtras();

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance();

        friend = new User();
        if (bundle != null) {
            friend = (User) bundle.get("user");
        }
        fileListView();

        Log.i("INFO_DATABASE", "updateView: " + friend.getEmail());
        username.setText(friend.getFirstName() + " " + friend.getLastName());
        email.setText(friend.getEmail());
        age.setText( friend.getAge() + " años");


        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int i, long j) {
                temp_group = results.get(i);
                Log.i("STATUS: ",temp_group.toString());
                Utils.shortToast(AddFriendToGroup.this,"Curso seleccionado!");
                return true;
            }
        });

        addToGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> g = temp_group.getUsers();
                if( !g.contains(friend.getKey()))
                    g.add(friend.getKey());
                temp_group.setUsers(g);
                Log.i("INFO_DATABASE", temp_group.getKey() +" " + temp_group.getRoute());
                postGroups(mData,temp_group);

                FData.getUsers(FirebaseDatabase.getInstance(), g, new ListFilteredActions<User, List<String>>() {
                    @Override
                    public boolean searchCriteria(User data, List<String> filter) {
                        if( filter != null ){
                            return filter.contains(data.getKey());
                        }
                        return false;
                    }

                    @Override
                    public void onReceiveList(ArrayList<User> data, DatabaseReference reference) {
                        for( User user : data ){
                            if( user.getGroups() == null ){
                                user.setGroups( new ArrayList<String>() );
                                user.getGroups().add(temp_group.getKey());
                                FData.postUser(FirebaseDatabase.getInstance(), user);
                            }else {
                                if( !user.getGroups().contains(temp_group.getKey()) ) {
                                    user.getGroups().add(temp_group.getKey());
                                    FData.postUser(FirebaseDatabase.getInstance(), user);
                                }
                            }

                        }
                    }

                    @Override
                    public void onCancel(DatabaseError error) {

                    }
                });

                Intent intent = new Intent(AddFriendToGroup.this, HomeActivity.class);
                startActivity(intent);
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
                results = data;
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

package webike.webike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import webike.webike.logic.User;

public class AddFriendToGroup extends AppCompatActivity {

    private TextView username;
    private TextView email;
    private TextView age;
    private Button addToGroup;
    private ListView list;
    private User friend;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mData;

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
        if(bundle != null){
            friend = (User) bundle.get("user");
        }
        fileListView();

        Log.i("INFO_DATABASE", "updateView: "+ friend.getEmail() );
        username.setText(friend.getFirstName() + " " + friend.getLastName());
        email.setText(friend.getEmail());
        age.setText( Integer.toString(friend.getAge()) + " años");

        addToGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //list.setOnClickListener();
    }

    private void fileListView() {

    }
}

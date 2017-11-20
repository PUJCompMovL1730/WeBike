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




    }



}

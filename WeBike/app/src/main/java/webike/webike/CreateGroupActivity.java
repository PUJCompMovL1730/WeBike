package webike.webike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import webike.webike.logic.Group;
import webike.webike.logic.User;
import webike.webike.ubicacion.MapCreateRoute;
import webike.webike.utils.FData;
import webike.webike.utils.SingleValueActions;

import static webike.webike.utils.FData.postGroups;
import static webike.webike.utils.FData.postUser;

public class CreateGroupActivity extends AppCompatActivity {

    private EditText GroupName;
    private EditText StartingPoint;
    private EditText EndingPoint;
    private Button NewRoute;
    private Button CreateGroup;
    private String routeUrl = "";
    private FData x;
    private Group g;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        GroupName = (EditText) findViewById(R.id.new_group_name);
        StartingPoint = (EditText) findViewById(R.id.new_grp_sp);
        EndingPoint = (EditText) findViewById(R.id.new_grp_ep);
        NewRoute = (Button) findViewById(R.id.new_grp_rt);
        CreateGroup = (Button) findViewById(R.id.create_group);

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance();

        NewRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent obj = new Intent(CreateGroupActivity.this, MapCreateRoute.class);
                obj.putExtra("URL", routeUrl);
                startActivityForResult(obj, 100);
            }
        });

        CreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gn = GroupName.getText().toString().trim();
                String sp = StartingPoint.getText().toString().trim();
                String ep = EndingPoint.getText().toString().trim();
                if (TextUtils.isEmpty(gn) || TextUtils.isEmpty(sp) || TextUtils.isEmpty(ep) || TextUtils.isEmpty(routeUrl)) {
                    Toast.makeText(CreateGroupActivity.this, "Faltan datos", Toast.LENGTH_LONG).show();
                } else {
                    g = new Group(gn, sp, ep, routeUrl);
                    Log.i("data: " , g.toString());
                    FData.postGroup( mData , g);
                    g.setRoute(routeUrl);
                    newGroup();
                }
            }
        });
    }

    private void newGroup() {
        FData.getUserFromId(mData, mAuth.getCurrentUser().getUid(), new SingleValueActions<User>() {
            @Override
            public void onReceiveSingleValue(User data, DatabaseReference reference) {
                List<String> temp = data.getGroups();
                if( temp == null ){
                    temp = new ArrayList<String>();
                }
                temp.add(g.getKey());
                data.setGroups((ArrayList<String>) temp);
                postUser(mData,data);

                List<String> temp2 = g.getAdmins();
                if( temp2 == null ){
                    temp2 = new ArrayList<String>();
                }
                temp2.add(data.getKey());
                g.setAdmins(temp2);
                g.setUsers(temp2);
                Log.i("STATUS: ", g.toString());
                postGroups(mData,g);

                Intent intent = new Intent(CreateGroupActivity.this, HomeActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancel(DatabaseError error) {

            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                //Toast.makeText(CreateGroupActivity.this, data.getStringExtra("result"), Toast.LENGTH_SHORT).show();
                routeUrl = data.getStringExtra("result");
            }
        }
    }
}


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
import webike.webike.logic.Group;
import webike.webike.logic.User;
import webike.webike.ubicacion.DisplayRoute;
import webike.webike.utils.FData;
import webike.webike.utils.ListActions;
import webike.webike.utils.ListFilteredActions;
import webike.webike.utils.Utils;

public class MyGroupsActivity extends AppCompatActivity {

    private ListView list;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mData;
    private ArrayList<Group> results;
    private Group temp_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_groups);

        list = (ListView) findViewById(R.id.mailbox_list_my_g);

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance();

        fileListView();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                temp_group = results.get(i);
                Utils.longToast(MyGroupsActivity.this,"Curso seleccionado!");

                Intent myIntent = new Intent(MyGroupsActivity.this, DisplayRoute.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("routes", temp_group.getRoute());
                myIntent.putExtras(bundle);
                startActivity(myIntent);
            }
        });
    }

    private void fileListView() {
        FData.getUserGroups(this.mData, mAuth.getCurrentUser().getUid(), new ListActions<Group>()  {
            @Override
            public void onReceiveList(ArrayList<Group> data, DatabaseReference reference) {
                if (data.isEmpty()) {
                    Toast.makeText(MyGroupsActivity.this, "Grupo creado", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MyGroupsActivity.this, HomeActivity.class);
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

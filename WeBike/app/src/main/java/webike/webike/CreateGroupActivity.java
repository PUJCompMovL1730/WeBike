package webike.webike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import webike.webike.logic.Group;
import webike.webike.ubicacion.MapCreateRoute;

public class CreateGroupActivity extends AppCompatActivity {

    private EditText GroupName;
    private EditText StartingPoint;
    private EditText EndingPoint;
    private Button NewRoute;
    private Button CreateGroup;
    private String route_url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        GroupName = (EditText) findViewById(R.id.new_group_name);
        StartingPoint = (EditText) findViewById(R.id.new_grp_sp);
        EndingPoint = (EditText) findViewById(R.id.new_grp_ep);
        NewRoute = (Button) findViewById(R.id.new_grp_rt);
        CreateGroup = (Button) findViewById(R.id.create_group);

        NewRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent obj = new Intent(CreateGroupActivity.this, MapCreateRoute.class);
                obj.putExtra("URL", route_url);
                startActivityForResult(obj, 100);
            }
        });
        CreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gn = GroupName.getText().toString().trim();
                String sp = StartingPoint.getText().toString().trim();
                String ep = EndingPoint.getText().toString().trim();
                if(TextUtils.isEmpty(gn) || TextUtils.isEmpty(sp) || TextUtils.isEmpty(ep) || TextUtils.isEmpty(route_url))
                {
                    Toast.makeText(CreateGroupActivity.this, "Faltan datos", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Group g = new Group(gn,sp,ep,route_url);
                    //TODO check if group already exists
                    //TODO add group to DB, and set creator as admin
                    Toast.makeText(CreateGroupActivity.this, "El grupo "+gn+" fue creado exitosamente!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(CreateGroupActivity.this, HomeActivity.class));
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(CreateGroupActivity.this, data.getStringExtra("result"), Toast.LENGTH_SHORT).show();
                route_url = data.getStringExtra("result");
            }
        }
    }

}


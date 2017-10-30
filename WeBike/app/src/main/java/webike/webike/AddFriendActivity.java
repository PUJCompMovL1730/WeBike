package webike.webike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import webike.webike.R;
import webike.webike.logic.User;

public class AddFriendActivity extends AppCompatActivity {

    private TextView username;
    private TextView email;
    private TextView age;
    private Button addFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        username = (TextView) findViewById(R.id.usr_name_inv);
        email = (TextView) findViewById(R.id.email_usr_inv);
        age = (TextView) findViewById(R.id.usr_age);
        addFriend = (Button) findViewById(R.id.send_inv_btn);

        /*Bundle bundle = getIntent().getExtras();

        User new_friend;
        if(bundle != null){
            new_friend = (User) bundle.get("user");
        }*/


        addFriend.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(AddFriendActivity.this, HomeActivity.class));
             }
        });
    }
}

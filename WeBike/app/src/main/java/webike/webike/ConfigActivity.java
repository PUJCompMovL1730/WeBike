package webike.webike;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import webike.webike.logic.User;
import webike.webike.utils.FData;
import webike.webike.utils.SingleValueActions;
import webike.webike.utils.Utils;

public class ConfigActivity extends AppCompatActivity {

    private Button profile;
    private TwitterLoginButton twitter;
    private Button delete;
    private Button bicitaller;
    //private Button fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        profile = (Button) findViewById(R.id.profile);
        delete = (Button)findViewById(R.id.delete_profile);
        bicitaller = (Button) findViewById(R.id.change_bicitaller_state);
        Twitter.initialize(this);
        twitter = (TwitterLoginButton) findViewById(R.id.tw_button);
        twitter.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;

                Toast.makeText(ConfigActivity.this, "Twitter authentication succeeded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                Toast.makeText(ConfigActivity.this, "Twitter authentication failed", Toast.LENGTH_SHORT).show();
            }
        });

        profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( ConfigActivity.this , ConfigProfileActivity.class);
                startActivity(intent);
            }
        });

        bicitaller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfigActivity.this.changeBicitaller();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( ConfigActivity.this, AccountDeleteConfirmation.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        twitter.onActivityResult(requestCode, resultCode, data);
    }

    public void login(TwitterSession session){
        String user = session.getUserName();

    }

    public void changeBicitaller(){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FData.getUserFromId(database, uid, new SingleValueActions<User>() {
            @Override
            public void onReceiveSingleValue(User data, DatabaseReference reference) {
                data.setBicitaller( !data.isBicitaller() );
                reference.setValue(data);
                if( data.isBicitaller() )
                    Utils.shortToast( ConfigActivity.this, "Ahora eres bicitaller");
                else
                    Utils.shortToast( ConfigActivity.this, "Ya no eres bicitaller");
                startActivity( new Intent(ConfigActivity.this,ViewProfileActivity.class));
            }

            @Override
            public void onCancel(DatabaseError error) {

            }
        });
    }
}

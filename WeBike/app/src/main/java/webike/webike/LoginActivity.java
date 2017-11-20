package webike.webike;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import webike.webike.logic.AbstractUser;
import webike.webike.logic.OrgUser;
import webike.webike.logic.User;
import webike.webike.utils.FAuth;
import webike.webike.utils.FData;
import webike.webike.utils.Response;
import webike.webike.utils.SingleValueActions;
import webike.webike.utils.Utils;

public class LoginActivity extends AppCompatActivity {

    private FAuth mAuth;

    private EditText emailEditText;
    private EditText passwordEditText;

    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = (EditText) findViewById(R.id.email_editText);
        passwordEditText = (EditText) findViewById(R.id.password_editText);

        loginButton = (Button) findViewById(R.id.login_button);
        registerButton = (Button) findViewById(R.id.register_button);

        mAuth = new FAuth( this ) {
            @Override
            public void onSignIn() {
                Log.i("LOGIN:"," User successfully signed in.");
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                LoginActivity.this.getUser(id);
            }

            @Override
            public void onSignOut() {
                Log.i("LOGOUT:"," User successfully signed out.");
            }

            @Override
            public void onSignInFail(ArrayList<Response> errors ) {
                if ( errors.contains(Response.INVALID_EMAIL) ){
                    Snackbar.make(findViewById(R.id.login_parent), "El correo es invalido.", Snackbar.LENGTH_SHORT).show();
                }else
                if ( errors.contains(Response.LOGIN_ERROR ) ){
                    Log.i("Fail:", "onSignInFail: Could not sign in. Check log.");
                    Snackbar.make(findViewById(R.id.login_parent), "Usuario o contraseña invalido.", Snackbar.LENGTH_SHORT).show();
                }

            }
        };

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString().trim();
                String pass  = passwordEditText.getText().toString();
                mAuth.authenticate( email , pass );
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent( LoginActivity.this , SelectUserTypeActivity.class ) );
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.onStart();
        if( mAuth.isLoggedIn() ){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Log.i("Userid", "onStart: "+id);
            getUser(id);
        }
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.onStop();
    }

    public void getUser( final String id ){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refToUsers = database.getReference(FData.PATH_TO_USERS);
        refToUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String,Object> hash = (HashMap<String, Object>) dataSnapshot.getValue();
                ArrayList<String> keys = new ArrayList<String>(hash.keySet());
                boolean isOrg = true;
                for( String key : keys ){
                    if( key.equals(id) ){
                        isOrg = false;
                        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                        startActivity(intent);
                    }
                }
                if( isOrg ) {
                    Intent intent = new Intent(LoginActivity.this, OrgProfileActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

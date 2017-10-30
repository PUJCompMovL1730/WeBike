package webike.webike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import webike.webike.utils.FAuth;
import webike.webike.utils.Response;

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
                startActivity(new Intent( LoginActivity.this , HomeActivity.class ));
            }

            @Override
            public void onSignOut() {
                Log.i("LOGOUT:"," User successfully signed out.");
            }

            @Override
            public void onSignInFail(ArrayList<Response> errors ) {
                if ( errors.contains(Response.LOGIN_ERROR ) ){
                    Log.i("Fail:", "onSignInFail: Could not sign in. Check log.");
                }
                if ( errors.contains(Response.INVALID_PASSWORD) ){

                }
            }
        };

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String pass  = passwordEditText.getText().toString();
                mAuth.authenticate( email , pass );
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent( LoginActivity.this , RegisterActivity.class ) );
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.onStart();
        if( mAuth.isLoggedIn() ){
            startActivity(new Intent( LoginActivity.this , HomeActivity.class ));
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
}

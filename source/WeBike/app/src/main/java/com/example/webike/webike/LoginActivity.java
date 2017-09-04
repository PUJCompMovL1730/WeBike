package com.example.webike.webike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button login;
    Button signup;
    EditText user;
    EditText pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button)findViewById(R.id.login_button);
        signup = (Button)findViewById(R.id.signup_button);
        user = (EditText)findViewById(R.id.username_textfield);
        pass = (EditText)findViewById(R.id.password_textfield);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = user.getText().toString();
                String passwd = pass.getText().toString();
                //verify user and pass

                if( verify(username, passwd) ){
                    Intent loggedIn = new Intent(getBaseContext(),Home.class);
                    startActivity(loggedIn);
                }else{
                    Toast.makeText(getBaseContext(),"Error",Toast.LENGTH_SHORT).show();
                }

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(getBaseContext(),SignUp.class);
                startActivity(register);
            }
        });
    }

    public boolean verify(String usr , String pass){
        if(usr.equals("juan") && pass.equals("1234")){
            return true;
        }else{
            return false;
        }
    }
}

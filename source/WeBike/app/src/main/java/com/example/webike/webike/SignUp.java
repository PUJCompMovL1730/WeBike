package com.example.webike.webike;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    Button finish;
    EditText user;
    EditText passwd;
    EditText confirm;
    CheckBox corporate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        finish = (Button)findViewById(R.id.register_button);
        user = (EditText)findViewById(R.id.username_reg_input);
        passwd = (EditText)findViewById(R.id.password_reg_input);
        confirm = (EditText)findViewById(R.id.password_confirm_reg_input);
        corporate = (CheckBox)findViewById(R.id.corporate_checkbox);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usr = user.getText().toString();
                String pass = passwd.getText().toString();
                String conf = confirm.getText().toString();
                if( verify(usr,pass,conf) ){
                    Intent home = new Intent(getBaseContext(),Home.class);
                    startActivity(home);
                }else{
                    Toast.makeText(getBaseContext(),"Error",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean verify(String usr , String pass , String conf){
        //user taken?
        if( usr.equals("juan") ){
            return false;
        }
        if( !pass.equals(conf)){
            return false;
        }
        return true;
    }
}

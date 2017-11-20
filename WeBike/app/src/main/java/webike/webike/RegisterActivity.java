package webike.webike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import webike.webike.logic.User;
import webike.webike.utils.FAuth;
import webike.webike.utils.FData;
import webike.webike.utils.Response;
import webike.webike.utils.Validator;

public class RegisterActivity extends AppCompatActivity {

    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    EditText ageEditText;

    Spinner genderSpinner;

    Button submitButton;

    FAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstNameEditText = (EditText) findViewById(R.id.reg_firstName_editText);
        lastNameEditText = (EditText) findViewById(R.id.reg_lastName_editText);
        emailEditText = (EditText) findViewById(R.id.reg_email_editText);
        passwordEditText = (EditText) findViewById(R.id.reg_password_editText);
        ageEditText = (EditText) findViewById(R.id.reg_age_editText);

        genderSpinner = (Spinner) findViewById(R.id.reg_gender_spinner);

        submitButton = (Button) findViewById(R.id.reg_submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmit();
            }
        });

        mAuth = new FAuth(this) {
            @Override
            public void onSuccessfulSignUp(){

                String firstName = firstNameEditText.getText().toString().trim();
                String lastName = lastNameEditText.getText().toString().trim();
                String age = (ageEditText.getText().toString());
                String gender = genderSpinner.getSelectedItem().toString().trim();
                String email = emailEditText.getText().toString().trim();

                User user = new User( mAuth.getUser().getUid()  ,firstName , lastName , age , gender,email);

                FData.postUser( FirebaseDatabase.getInstance() , user);

                startActivity( new Intent( RegisterActivity.this, HomeActivity.class ) );
            }

            @Override
            public void onFailedSignUp(){

            }
        };

    }

    public void onSubmit(){

        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String age = ageEditText.getText().toString().trim();

        ArrayList<String> fields = new ArrayList<>();
        fields.add(firstName);
        fields.add(lastName);
        fields.add(email);
        fields.add(password);
        fields.add(age);

        ArrayList<Response> res = Validator.validateRegisterFields( fields );

        if( res.contains(Response.INVALID_EMAIL) ){
            emailEditText.setError(getText(R.string.error_invalid_email));
        }
        if( res.contains(Response.INVALID_NAME) ){
            firstNameEditText.setError(getText(R.string.error_invalid_name));
        }
        if( res.contains(Response.INVALID_LAST) ){
            lastNameEditText.setError(getText(R.string.error_invalid_last));
        }
        if( res.contains(Response.INVALID_PASSWORD) ){
            passwordEditText.setError(getText(R.string.error_invalid_password));
        }
        if( res.contains(Response.INVALID_AGE) ){
            ageEditText.setError(getText(R.string.error_invalid_age));
        }

        ArrayList<Integer> emptyFields = new ArrayList<>();
        for ( int i = 0 ; i < res.size() ; i++ ){
            Response r = res.get(i);
            if( r == Response.EMPTY_FIELD) {
                emptyFields.add(i);
            }
        }
        if ( emptyFields.contains(0) ){
            firstNameEditText.setError(getText(R.string.error_empty_field));
        }
        if ( emptyFields.contains(1) ){
            lastNameEditText.setError(getText(R.string.error_empty_field));
        }
        if ( emptyFields.contains(2) ){
            emailEditText.setError(getText(R.string.error_empty_field));
        }
        if ( emptyFields.contains(3) ){
            passwordEditText.setError(getText(R.string.error_empty_field));
        }
        if ( emptyFields.contains(4) ){
            ageEditText.setError(getText(R.string.error_empty_field));
        }

        Log.i("EMPTY FIELDS", "onSubmit: "+ emptyFields.toString());

        if( isOk(res) ){
            Log.i("A_OK", "onSubmit: YAY");
            mAuth.createUser( email , password );
        }

    }

    private boolean isOk( ArrayList<Response> res ){
        boolean isOk = true;
        for ( Response r : res ){
            if( r != Response.FIELD_OK ){
                isOk = false;
            }
        }
        return isOk;
    }
}

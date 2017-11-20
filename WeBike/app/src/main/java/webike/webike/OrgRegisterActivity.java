package webike.webike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import webike.webike.logic.OrgUser;
import webike.webike.utils.FAuth;
import webike.webike.utils.FData;
import webike.webike.utils.Response;
import webike.webike.utils.Validator;

public class OrgRegisterActivity extends AppCompatActivity {

    FAuth mAuth;

    EditText et_nombre;
    EditText et_localizacion;
    EditText et_email;
    EditText et_password;
    Button b_crear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_register);

        et_nombre = (EditText) findViewById(R.id.et_nombre);
        et_localizacion = (EditText) findViewById(R.id.et_localizacion);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);

        b_crear = (Button) findViewById(R.id.b_crear);
        b_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmit();
            }
        });

        mAuth = new FAuth(this) {
            @Override
            public void onSuccessfulSignUp(){

                String nombre = et_nombre.getText().toString().trim();
                String localizacion = et_localizacion.getText().toString().trim();
                String email = et_email.getText().toString().trim();

                OrgUser user = new OrgUser( mAuth.getUser().getUid()  ,nombre , localizacion,email);

                FData.postUser( FirebaseDatabase.getInstance() , user);

                startActivity( new Intent( OrgRegisterActivity.this, OrgProfileActivity.class ) );
            }

            @Override
            public void onFailedSignUp(){

            }
        };
    }
    public void onSubmit(){

        String nombre = et_nombre.getText().toString().trim();
        String localizacion = et_localizacion.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        ArrayList<String> fields = new ArrayList<>();
        fields.add(nombre);
        fields.add(localizacion);
        fields.add(email);
        fields.add(password);

        ArrayList<Response> res = Validator.validateRegisterFields( fields );

        if( res.contains(Response.INVALID_EMAIL) ){
            et_email.setError(getText(R.string.error_invalid_email));
        }
        if( res.contains(Response.INVALID_NAME) ){
            et_nombre.setError(getText(R.string.error_invalid_name));
        }
        if( res.contains(Response.INVALID_LAST) ){
            et_localizacion.setError(getText(R.string.error_invalid_localization));
        }
        if( res.contains(Response.INVALID_PASSWORD) ){
            et_password.setError(getText(R.string.error_invalid_password));
        }

        ArrayList<Integer> emptyFields = new ArrayList<>();
        for ( int i = 0 ; i < res.size() ; i++ ){
            Response r = res.get(i);
            if( r == Response.EMPTY_FIELD) {
                emptyFields.add(i);
            }
        }
        if ( emptyFields.contains(0) ){
            et_nombre.setError(getText(R.string.error_empty_field));
        }
        if ( emptyFields.contains(1) ){
            et_localizacion.setError(getText(R.string.error_empty_field));
        }
        if ( emptyFields.contains(2) ){
            et_email.setError(getText(R.string.error_empty_field));
        }
        if ( emptyFields.contains(3) ){
            et_password.setError(getText(R.string.error_empty_field));
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

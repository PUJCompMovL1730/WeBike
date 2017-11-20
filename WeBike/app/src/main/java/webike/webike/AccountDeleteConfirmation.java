package webike.webike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import webike.webike.utils.FAuth;
import webike.webike.utils.FData;
import webike.webike.utils.RemoveUserInterface;

public class AccountDeleteConfirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_delete_confirmation);
        Button buton = (Button) findViewById(R.id.boton_confirmar);


        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView password = (TextView) findViewById(R.id.confirm_password);
                TextView email = (TextView) findViewById(R.id.mail_confirmText);


                FAuth.deleteCurrentUser(email.getText().toString().trim(), password.getText().toString(), new RemoveUserInterface() {
                    @Override
                    public void onSuccesfulRemoval() {
                        Intent intent = new Intent(AccountDeleteConfirmation.this, LoginActivity.class);
                        startActivity( intent );
                    }
                });
            }
        });



    }
}

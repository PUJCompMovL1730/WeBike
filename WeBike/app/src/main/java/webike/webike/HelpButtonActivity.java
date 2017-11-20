package webike.webike;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import webike.webike.logic.Message;
import webike.webike.logic.User;
import webike.webike.utils.FData;
import webike.webike.utils.ListFilteredActions;
import webike.webike.utils.Utils;

public class HelpButtonActivity extends AppCompatActivity {

    private Button ayudaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_button);
        ayudaButton = (Button)findViewById(R.id.ayuda_button);
        ayudaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessageToTalleres();
            }
        });
    }

    public void sendMessageToTalleres(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        FData.getUsers(database, true , new ListFilteredActions<User, Boolean>() {

            @Override
            public void onReceiveList(ArrayList<User> data, DatabaseReference reference) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                for ( User user : data ){
                    Message msg = new Message();
                    msg.setMsg("Solicitud de ayuda por "+currentUser.getEmail()+":" + "Hola solicito el servicio de bicitaller");
                    msg.setSender( currentUser.getUid() );
                    msg.setReceiver( user.getKey() );
                    msg.setSubject("Solicitud de Bicitaller");
                    FData.postMessage( database , msg);
                }
                Utils.shortToast(HelpButtonActivity.this,"Se mando mensaje a los bicitalleres");
            }

            @Override
            public void onCancel(DatabaseError error) {
                Utils.shortToast(HelpButtonActivity.this,"Error pidiendo ayuda D:");
            }

            @Override
            public boolean searchCriteria(User data, Boolean filter) {
                return data.isBicitaller();
            }
        });
    }

}

package webike.webike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import webike.webike.logic.Mailbox;
import webike.webike.logic.Message;
import webike.webike.logic.User;
import webike.webike.utils.FAuth;
import webike.webike.utils.FData;
import webike.webike.utils.ListActions;
import webike.webike.utils.ListFilteredActions;
import webike.webike.utils.SingleValueActions;

public class WriteMessageActivity extends AppCompatActivity {

    private class Friend{
        public String id;
        public String name;
        public String email;
        Friend( String id , String name, String email){
            this.id = id;
            this.name = name;
            this.email = email;
        }
    }

    Spinner friendSpinner;
    EditText subjectEditText;
    EditText messageEditText;
    Button sendButton;
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    ArrayList<Friend> friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_message);

        friendSpinner = (Spinner) findViewById(R.id.friends_spinner);
        subjectEditText = (EditText) findViewById(R.id.subject_editText);
        messageEditText = (EditText) findViewById(R.id.message_editText);
        sendButton = (Button) findViewById(R.id.send_msg_button);
        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        friends = new ArrayList<>();

        this.getFriends();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    public void getFriends(){
        FirebaseUser user = fAuth.getCurrentUser();
        FData.getFriends(database, user.getUid(), new ListActions<User>() {
            @Override
            public void onReceiveList(ArrayList<User> data, DatabaseReference reference) {
                for ( User user : data ) {
                    friends.add( new Friend( user.getKey() , user.getFirstName() + " " + user.getLastName() , user.getEmail() ) );
                }
                fillSpinner();
            }

            @Override
            public void onCancel(DatabaseError error) {

            }
        });
    }

    public void fillSpinner(){
        ArrayList<String> names = new ArrayList<>();
        for ( Friend f : friends ){
            names.add( f.name + " <"+f.email+">");
        }
        String[] nameArr = names.toArray(new String[ names.size() ]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,nameArr);
        this.friendSpinner.setAdapter(adapter);
    }

    public void sendMessage(){
        String src = this.fAuth.getCurrentUser().getUid();
        String dst = findFriend( friendSpinner.getSelectedItem().toString() );
        String subject = this.subjectEditText.getText().toString().trim();
        if( subject.equals("") || subject == null ){
            subject = "<Sin Asunto>";
        }
        String message = this.messageEditText.getText().toString().trim();
        if( message.equals("") || message == null ){
            Toast.makeText(this, "El mensaje no puede ser vacío", Toast.LENGTH_SHORT).show();
        }else {
            Message msg = new Message(message, subject, src, dst);
            FData.postMessage(database, msg);
            Toast.makeText(this, "Mensaje enviado exitosamente", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Mailbox.class));
        }
    }

    public String findFriend(String spinnerName){
        String name = spinnerName.substring(0 , spinnerName.indexOf('<') ).trim();
        for ( Friend f : this.friends ){
            if( f.name.equals(name) ){
                return f.id;
            }
        }
        return null;
    }
}

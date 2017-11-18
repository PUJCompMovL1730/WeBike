package webike.webike;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import webike.webike.logic.User;
import webike.webike.utils.FAuth;
import webike.webike.utils.FData;
import webike.webike.utils.ListFilteredActions;
import webike.webike.utils.SingleValueActions;

public class WriteMessageActivity extends AppCompatActivity {

    private class Friend{
        public String id;
        public String name;
        Friend( String id , String name){
            this.id = id;
            this.name = name;
        }
    }

    Spinner friendSpinner;
    EditText subjectEditText;
    EditText messageEditText;
    Button sendButton;
    FAuth fAuth;
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
        fAuth = new FAuth(this) {};
        database = FirebaseDatabase.getInstance();
        friends = new ArrayList<>();
        this.getFriends();
    }

    public void getFriends(){
        FirebaseUser user = fAuth.getUser();
        FData.getUserFromId(database, user.getUid(), new SingleValueActions<User>() {
            @Override
            public void onReceiveSingleValue(User data, DatabaseReference reference) {
                if( data.getFriends() != null ){
                    final List<String> fIds = data.getFriends();

                    FData.getUsers(database, null, new ListFilteredActions<User, String>() {

                        @Override
                        public void onReceiveList(ArrayList<User> data, DatabaseReference reference) {
                            for ( User user : data ){
                                friends.add( new Friend( user.getKey() , user.getFirstName() ) );
                            }
                            fillSpinner();
                        }

                        @Override
                        public void onCancel(DatabaseError error) {

                        }

                        @Override
                        public boolean searchCriteria(User data, String filter) {
                            return fIds.contains( data.getKey() );
                        }
                    });

                }
            }

            @Override
            public void onCancel(DatabaseError error) {

            }
        });
    }

    public void fillSpinner(){
        ArrayList<String> names = new ArrayList<>();
        for ( Friend f : friends ){
            names.add( f.name );
        }
        String[] nameArr = names.toArray(new String[ names.size() ]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.friend_spinner_item,nameArr);
        this.friendSpinner.setAdapter(adapter);
    }
}

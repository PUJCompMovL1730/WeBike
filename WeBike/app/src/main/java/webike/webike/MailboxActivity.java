package webike.webike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import webike.webike.adaptadores.MessageAdapter;
import webike.webike.logic.Message;
import webike.webike.logic.User;
import webike.webike.utils.FData;
import webike.webike.utils.ListActions;
import webike.webike.utils.SingleValueActions;

public class MailboxActivity extends AppCompatActivity {

    private ListView messageList;
    private TextView titleTextView;
    private ImageButton searchButton;
    private EditText searchText;

    private FirebaseAuth fAuth;
    private FirebaseDatabase fData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mailbox);

        messageList = (ListView) findViewById(R.id.mailbox_list);
        titleTextView = (TextView) findViewById(R.id.mailbox_title);
        searchButton = (ImageButton) findViewById(R.id.search_message_button);
        searchText = (EditText) findViewById(R.id.search_message_editText);

        fAuth = FirebaseAuth.getInstance();
        fData = FirebaseDatabase.getInstance();

        loadInbox();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,HomeActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inbox_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClicked = item.getItemId();
        switch (itemClicked){
            case R.id.inbox_menu_create_msg:
                Intent intent = new Intent(this, WriteMessageActivity.class);
                startActivity(intent);
                break;
            case R.id.inbox_menu_inbox:
                loadInbox();
                break;
            case R.id.inbox_menu_outbox:
                loadOutbox();
                break;
            default:
                //nothing
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadInbox(){
        this.titleTextView.setText("Inbox");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.i("CADSJBVANK", "loadInbox: "+"DLVJHBAKDSNVUHAKJDSBVAKNDBVIANBM");
        FData.getUsers(database, new ListActions<User>() {
            @Override
            public void onReceiveList(ArrayList<User> data, DatabaseReference reference) {
                final ArrayList<User> users = data;
                FData.getReceivedMessages(database, userID, new ListActions<Message>() {
                    @Override
                    public void onReceiveList(ArrayList<Message> data, DatabaseReference reference) {
                        for ( Message msg : data ){
                            for ( User user : users ){
                                if( user.getKey().equals( msg.getReceiver() )){
                                    msg.setReceiver( user.getFirstName() + " <" + user.getEmail()+">" );
                                }
                                if( user.getKey().equals( msg.getSender() )){
                                    msg.setSender( user.getFirstName() + " <" + user.getEmail()+">");
                                }
                            }
                        }
                        MessageAdapter adapter = new MessageAdapter(MailboxActivity.this, data);
                        messageList.setAdapter(adapter);
                    }

                    @Override
                    public void onCancel(DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancel(DatabaseError error) {

            }
        });
    }

    public void loadOutbox(){
        this.titleTextView.setText("Outbox");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FData.getUsers(database, new ListActions<User>() {
            @Override
            public void onReceiveList(ArrayList<User> data, DatabaseReference reference) {
                final ArrayList<User> users = data;
                FData.getSentMessages(database, userID, new ListActions<Message>() {
                    @Override
                    public void onReceiveList(ArrayList<Message> data, DatabaseReference reference) {
                        for ( Message msg : data ){
                            for ( User user : users ){
                                if( user.getKey().equals( msg.getReceiver() )){
                                    msg.setReceiver( user.getFirstName() + " <" + user.getEmail()+">" );
                                }
                                if( user.getKey().equals( msg.getSender() )){
                                    msg.setSender( user.getFirstName() + " <" + user.getEmail()+">");
                                }
                            }
                        }
                        MessageAdapter adapter = new MessageAdapter(MailboxActivity.this, data);
                        messageList.setAdapter(adapter);
                    }

                    @Override
                    public void onCancel(DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancel(DatabaseError error) {

            }
        });
    }
}

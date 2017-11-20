package webike.webike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

    private boolean inbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mailbox);
        inbox = true;
        messageList = (ListView) findViewById(R.id.mailbox_list);
        titleTextView = (TextView) findViewById(R.id.mailbox_title);
        searchButton = (ImageButton) findViewById(R.id.search_message_button);
        searchText = (EditText) findViewById(R.id.search_message_editText);

        fAuth = FirebaseAuth.getInstance();
        fData = FirebaseDatabase.getInstance();
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buscar = searchText.getText().toString();
                if( inbox ){
                    searchInInbox(buscar);
                }else{
                    searchInOutbox(buscar);
                }
            }
        });

        messageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MailboxActivity.this,ReadMessageActivity.class);
                Bundle bundle = new Bundle();
                Message msg = (Message) parent.getItemAtPosition(position);
                bundle.putSerializable("msg",msg);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
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
                inbox = true;
                loadInbox();
                break;
            case R.id.inbox_menu_outbox:
                inbox = false;
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
                        if( data.size() == 0){
                            String[] arr =new String[1];
                            arr[0] = "<-------No tiene mensajes------->";
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MailboxActivity.this, R.layout.simple_list_item_1, arr);
                            messageList.setAdapter(adapter);
                        }else {
                            MessageAdapter adapter = new MessageAdapter(MailboxActivity.this, data);
                            messageList.setAdapter(adapter);
                        }
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
                        if( data.size() == 0){
                            String[] arr =new String[1];
                            arr[0] = "<-------No tiene mensajes------->";
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MailboxActivity.this, R.layout.simple_list_item_1, arr);
                            messageList.setAdapter(adapter);
                        }else {
                            MessageAdapter adapter = new MessageAdapter(MailboxActivity.this, data);
                            messageList.setAdapter(adapter);
                        }
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

    public void searchInInbox( final String buscar ){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FData.getUsers(database, new ListActions<User>() {
            @Override
            public void onReceiveList(ArrayList<User> data, DatabaseReference reference) {
                final ArrayList<User> users = data;
                FData.getReceivedMessages(database, userID,  new ListActions<Message>() {
                    @Override
                    public void onReceiveList(ArrayList<Message> data, DatabaseReference reference) {
                        ArrayList<Message> filteredMsg = new ArrayList<Message>();
                        for ( Message msg : data ){
                            for ( User user : users ){
                                if( user.getKey().equals( msg.getReceiver() )){
                                    msg.setReceiver( user.getFirstName() + " <" + user.getEmail()+">" );
                                }
                                if( user.getKey().equals( msg.getSender() )){
                                    msg.setSender( user.getFirstName() + " <" + user.getEmail()+">");
                                }
                            }
                            if( msg.getSender().contains(buscar) || msg.getSubject().contains(buscar) ){
                                filteredMsg.add(msg);
                            }
                        }
                        if( filteredMsg.size() == 0){
                            String[] arr =new String[1];
                            arr[0] = "<-------No tiene mensajes------->";
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MailboxActivity.this, R.layout.simple_list_item_1, arr);
                            messageList.setAdapter(adapter);
                        }else {
                            MessageAdapter adapter = new MessageAdapter(MailboxActivity.this, filteredMsg);
                            messageList.setAdapter(adapter);
                        }
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

    public void searchInOutbox( final String buscar ){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FData.getUsers(database, new ListActions<User>() {
            @Override
            public void onReceiveList(ArrayList<User> data, DatabaseReference reference) {
                final ArrayList<User> users = data;
                FData.getSentMessages(database, userID,  new ListActions<Message>() {
                    @Override
                    public void onReceiveList(ArrayList<Message> data, DatabaseReference reference) {
                        ArrayList<Message> filteredMsg = new ArrayList<Message>();
                        for ( Message msg : data ){
                            for ( User user : users ){
                                if( user.getKey().equals( msg.getReceiver() )){
                                    msg.setReceiver( user.getFirstName() + " <" + user.getEmail()+">" );
                                }
                                if( user.getKey().equals( msg.getSender() )){
                                    msg.setSender( user.getFirstName() + " <" + user.getEmail()+">");
                                }
                            }
                            if( msg.getReceiver().contains(buscar) || msg.getSubject().contains(buscar) ){
                                filteredMsg.add(msg);
                            }
                        }
                        if( filteredMsg.size() == 0){
                            String[] arr =new String[1];
                            arr[0] = "<-------No tiene mensajes------->";
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MailboxActivity.this, R.layout.simple_list_item_1, arr);
                            messageList.setAdapter(adapter);
                        }else {
                            MessageAdapter adapter = new MessageAdapter(MailboxActivity.this, filteredMsg);
                            messageList.setAdapter(adapter);
                        }
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

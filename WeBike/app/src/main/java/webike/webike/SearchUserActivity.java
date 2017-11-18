package webike.webike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import webike.webike.adaptadores.UserArrayAdapter;
import webike.webike.logic.User;
import webike.webike.utils.FAuth;
import webike.webike.utils.FData;
import webike.webike.utils.ListActions;
import webike.webike.utils.ListFilteredActions;

public class SearchUserActivity extends AppCompatActivity {

    private EditText searchEditText;
    private ImageButton searchButton;
    private ListView resultList;
    private String search_name;
    private ArrayList<User> results;
    private FAuth mAuth;
    private FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        database = FirebaseDatabase.getInstance();
        searchEditText = (EditText) findViewById(R.id.searchText_editText);
        mAuth = new FAuth(this) {
        };
        resultList = (ListView) findViewById(R.id.search_list);

        searchButton  = (ImageButton) findViewById(R.id.searchUser_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadUser( searchEditText.getText().toString().trim() );
            }
        });


        resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent myIntent = new Intent(SearchUserActivity.this, AddFriendActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",results.get(i));
                myIntent.putExtras(bundle);
                Log.i("INFO_DATABASE", "updateView: "+ results.get(i).getEmail() );
                startActivity(myIntent);
            }
        });
    }

   public void loadUser(final String buscar){
       FData.getUsers(this.database, buscar, new ListFilteredActions<User,String>(){

           public void onReceiveList(ArrayList<User> data, DatabaseReference reference ){
               results = data;
               updateView( data );
           }

           public boolean searchCriteria(User data, String value) {
               return data.getFirstName().contains(buscar) && !data.getKey().equals( mAuth.getUser().getUid() );
           }

           @Override
           public void onCancel(DatabaseError databaseError ){

           }
       });

   }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.onStop();
    }

    public void updateView(ArrayList<User> usrs) {
       Log.i("INFO_DATABASE", "updateView: "+ usrs.toString() );
       UserArrayAdapter adapter = new UserArrayAdapter(this, usrs);
       this.resultList.setAdapter(adapter);
   }
}

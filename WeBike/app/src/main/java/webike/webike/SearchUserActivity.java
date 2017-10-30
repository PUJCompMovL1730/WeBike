package webike.webike;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import webike.webike.logic.User;

public class SearchUserActivity extends AppCompatActivity {

    private EditText searchEditText;
    private Button searchButton;
    private ListView resultList;
    private String search_name;
    private ArrayList<User> results;

    private FirebaseDatabase mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        searchEditText = (EditText) findViewById(R.id.searchText_editText);
        searchButton = (Button) findViewById(R.id.searchUser_button);
        resultList = (ListView) findViewById(R.id.search_list);

        mDatabase = FirebaseDatabase.getInstance();
        results = new ArrayList<>();


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_name = searchEditText.getText().toString();
                searchUser();
            }
        });
    }

    public void searchUser(){
        List<User> users = getUsers();
        ArrayList<Integer> trash = new ArrayList<>();
        for ( int i = 0 ; i < users.size() ; i++ ){
            if( !users.get(i).getFirstName().contains(search_name) && !users.get(i).getLastName().contains(search_name) ){
                trash.add(i);
            }
        }
        for ( int i = trash.size() - 1 ; i >= 0 ; i--){
            users.remove(trash.get(i));
        }

    }

    public ArrayList<User> getUsers(){
        return new ArrayList<User>();
    }
}

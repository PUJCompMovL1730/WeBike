package webike.webike;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PlaceActivity extends AppCompatActivity {

    FirebaseAuth fbAuth;
    DatabaseReference dbReference;
    FirebaseDatabase fbDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        fbDatabase = FirebaseDatabase.getInstance();
        fbAuth = FirebaseAuth.getInstance();
    }
}

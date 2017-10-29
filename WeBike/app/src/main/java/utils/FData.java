package utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import logic.Publicacion;
import logic.User;

public class FData {

    private FirebaseDatabase database;
    static String PATH_TO_USERS = "/users";
    static String PATH_TO_PUBS  = "/publications";

    public FData(){
        this.database = FirebaseDatabase.getInstance();
    }

    public void postUser( User user ){
        DatabaseReference ref = database.getReference(PATH_TO_USERS + "/" + user.getKey() );
        ref.setValue(user);
    }

    public void postPub( Publicacion p ){
        DatabaseReference ref = database.getReference(PATH_TO_PUBS);
        String auxKey = ref.push().getKey();
        ref = database.getReference(PATH_TO_PUBS + "/" + auxKey );
        ref.setValue( p );
    }

}

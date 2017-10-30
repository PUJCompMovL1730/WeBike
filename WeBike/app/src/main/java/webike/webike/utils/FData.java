package webike.webike.utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import webike.webike.logic.Message;
import webike.webike.logic.Publicacion;
import webike.webike.logic.User;

public class FData {

    private FirebaseDatabase database;
    public final static String PATH_TO_USERS = "/users";
    public final static String PATH_TO_PUBS  = "/publications";

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

    public void postMessage(String src , String dst , Message msg ){
        DatabaseReference sRef = database.getReference(PATH_TO_USERS + "/" + src + "/mailbox/sent" );
        String srcKey = sRef.push().getKey();

        DatabaseReference dRef = database.getReference(PATH_TO_USERS + "/" + dst + "/mailbox/received" );
        String dstKey = dRef.push().getKey();

        sRef = database.getReference( PATH_TO_USERS + "/" + src + "/mailbox/sent/" + srcKey );
        dRef = database.getReference( PATH_TO_USERS + "/" + dst + "/mailbox/received/" + dstKey );

        sRef.setValue(msg);
        dRef.setValue(msg);
    }

}

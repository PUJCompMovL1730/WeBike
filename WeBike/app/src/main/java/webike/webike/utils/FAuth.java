package webike.webike.utils;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public abstract class FAuth {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener listener;
    private FirebaseUser usr;

    Activity activity;

    public FAuth( Activity activity ){
        this.activity = activity;
        //Then go through auth process
        this.firebaseAuth = FirebaseAuth.getInstance();

        this.listener = (new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                usr = firebaseAuth.getCurrentUser();
                if( usr != null){
                    onSignIn();
                }
            }
        });
    }

    public void authenticate( String email , String password ){
        ArrayList<Response> res = Validator.validateCredentials( email , password );
        if( res.isEmpty() ){
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if( !task.isSuccessful() ){
                                ArrayList<Response> res = new ArrayList<Response>();
                                res.add(Response.LOGIN_ERROR);
                                Log.i("FAIL: ", "onFail: " + task.getException().getMessage() );
                                FAuth.this.onSignInFail( res );
                            }else{
                                usr = task.getResult().getUser();
                            }
                        }
                    });
        }else{
            this.onSignInFail( res );
        }
    }

    public void onStart(){
        this.firebaseAuth.addAuthStateListener( this.listener );
    }

    public void onStop(){
        if( this.listener != null ){
            this.firebaseAuth.removeAuthStateListener(this.listener);
        }
    }

    public void SignOut(){
        this.firebaseAuth.signOut();
        this.onSignOut();
    }

    public FirebaseUser getUser(){
        return this.usr;
    }

    public boolean isLoggedIn(){
        if( this.usr != null ){
            return true;
        }
        return false;
    }

    public void createUser( String email , String password ){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if( task.isSuccessful() ){
                    usr = task.getResult().getUser();
                    onSuccessfulSignUp();
                }else{
                    Log.i("SIGNUP_FAIL", "onComplete: " + task.getException().getMessage() );
                    onFailedSignUp();
                }
            }
        });
    }

    //--Override custom sign in callbacks ---
    public void onSignIn(){}
    public void onSignOut(){}
    public void onSignInFail( ArrayList<Response> errors ){}
    //-----------------------------

    //--Override custom sign up callbacks--------
    public void onSuccessfulSignUp(){}
    public void onFailedSignUp(){}
    //-----------------------------
}

package webike.webike;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import webike.webike.logic.Message;
import webike.webike.logic.User;
import webike.webike.ubicacion.Map;
import webike.webike.utils.FData;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FData fData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        fData = new FData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClicked = item.getItemId();
        if( itemClicked == R.id.logout_menuItem ){
            mAuth.signOut();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
            startActivity( intent );
        }else if( itemClicked == R.id.config_menuItem ){
            //configuaracion
        }else if( itemClicked == R.id.send_msg_test){
            User usr = new User();
            usr.setKey( this.mAuth.getCurrentUser().getUid() );
            Message msg = new Message("Hola esto es una prueba",usr , usr );
            this.fData.postMessage(usr.getKey() , usr.getKey() , msg);
        }else if( itemClicked == R.id.search_user_test){
            startActivity( new Intent(HomeActivity.this, SearchUserActivity.class));
        }else if( itemClicked == R.id.go_to_planroute){
            startActivity( new Intent(HomeActivity.this, Map.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
        alertDialog.setTitle("Salir de sesion");
        alertDialog.setMessage( getString(R.string.logout_dialog_text) );
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getText( R.string.logout_confirm_text ) ,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        HomeActivity.this.mAuth.signOut();
                        startActivity( new Intent( HomeActivity.this , LoginActivity.class));
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getText( R.string.logout_cancel_text ) ,
                new DialogInterface.OnClickListener(){
                    public void onClick( DialogInterface dialog , int which) {

                    }
                }) ;
        alertDialog.show();
    }
}

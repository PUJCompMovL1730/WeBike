package utils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Juan on 10/28/2017.
 */

public class Permissions {

    //permissions
    // -  codigos para permisos
    public static final int PERMISSION_FINE_LOCATION=1;
    public static final int REQUEST_CHECK_SETTINGS=2;
    public static final int REQUEST_READ_CONTACTS = 3;
    public static final int REQUEST_CAMERA = 4;
    public static final int REQUEST_READ_EXTERNAL_STORAGE = 5;

    //actions
    // -  Tipos de actividades con resultado
    public static final int IMAGE_PICKER_REQUEST=1;
    public static final int IMAGE_CAPTURE_REQUEST=2;


    //actor:           Actividad que pide permiso
    //permission:      Permiso definido en Manifest.permission
    //permissionCode:  Codigo definido en la clase Permission
    //explanation:     Mensaje a mostrar cuando se vuelve a pedir el permiso
    public static void askAnyPermission(AppCompatActivity actor, String permission, int permissionCode, String explanation) {
        if (ContextCompat.checkSelfPermission(actor, permission) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(actor, permission)) {
                // Show an expanation to the user
                Utils.shortToast( actor , explanation );
            }
            // Request the permission.
            ActivityCompat.requestPermissions(actor, new String[]{permission}, permissionCode);
        }
    }

}

package webike.webike;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.InputStream;

import webike.webike.logic.User;
import webike.webike.utils.FAuth;
import webike.webike.utils.FData;
import webike.webike.utils.SingleValueActions;

import static webike.webike.utils.FData.postUser;

public class ConfigProfileActivity extends AppCompatActivity {

    private Button saveButton;
    private Button selectImage;
    private Button camera;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 6;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 7;
    private static final int IMAGE_PICKER_REQUEST = 8;
    private static final int REQUEST_IMAGE_CAPTURE = 9;
    private ImageView image;
    private EditText name;
    private EditText last_name;
    private Spinner gender_spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_profile);

        selectImage = (Button)findViewById(R.id.img_button);
        camera = (Button)findViewById(R.id.camera_button);
        image = (ImageView)findViewById(R.id.user_img);
        name = (EditText)findViewById(R.id.firstName_editText);
        last_name = (EditText)findViewById(R.id.lastname_editText);
        gender_spinner = (Spinner)findViewById(R.id.gender_spinner);
        saveButton = (Button)findViewById(R.id.saveChanges_button);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            askAnyPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            pickImage();
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            askAnyPermission(android.Manifest.permission.CAMERA, MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            pickCamera();
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChanges();
            }
        });

    }

    public void saveChanges(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FData.getUserFromId(database, uid, new SingleValueActions<User>() {
            @Override
            public void onReceiveSingleValue(User data, DatabaseReference reference) {
                Log.i("USER","MI PUTO USUARIO:"+data.toString());
                String firstNAme = name.getText().toString().trim();
                String lastName = last_name.getText().toString().trim();
                String gender = gender_spinner.getSelectedItem().toString().trim();

                data.setFirstName(firstNAme);
                data.setLastName(lastName);
                data.setGender(gender);

                postUser(database,data);

                Toast.makeText(ConfigProfileActivity.this, "User updated", Toast.LENGTH_SHORT).show();
                startActivity( new Intent( ConfigProfileActivity.this, ViewProfileActivity.class ) );

            }

            @Override
            public void onCancel(DatabaseError error) {

            }
        });




    }


    private void askAnyPermission(String permission, int permissionCode) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                Toast.makeText(this, "Se necesita el permiso para poder mostrar los contactos", Toast.LENGTH_LONG).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{permission}, permissionCode);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permiso concedido", Toast.LENGTH_LONG).show();
                    pickImage();
                } else {
                    Toast.makeText(this, "Permiso denegado", Toast.LENGTH_LONG).show();
                }
                break;
            case MY_PERMISSIONS_REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permiso concedido", Toast.LENGTH_LONG).show();
                    pickCamera();
                } else {
                    Toast.makeText(this, "Permiso denegado", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMAGE_PICKER_REQUEST:
                if(resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        image.setImageBitmap(selectedImage);
                    } catch(FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    image.setImageBitmap(imageBitmap);
                }
                break;
        }
    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void pickImage(){
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, IMAGE_PICKER_REQUEST);
            }
        });
    }

    public void pickCamera() {
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
    }


}

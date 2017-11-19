package webike.webike.ubicacion;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import webike.webike.HomeActivity;
import webike.webike.LoginActivity;
import webike.webike.R;
import webike.webike.logic.Promocion_lugar;
import webike.webike.utils.FAuth;
import webike.webike.utils.Permisos;

public class Map extends AppCompatActivity implements OnMapReadyCallback, PlaceSelectionListener {

    private FAuth firebaseAuthentication;
    private LocacionController locationController;
    private GoogleMap googleMap;
    private Intent mainIntent;
    private FirebaseUser user;
    private Place place;
    private List<Promocion_lugar> promocionesLugares= new ArrayList<Promocion_lugar>();
    private Toolbar toolbar;
    private ImageView image;
    private TextView userName;
    private TextView userEmail;

    private static final double RADIUS_OF_EARTH_KM = 6371;
    private static int checkStyle = 0;
    public static final int ZOOM_CITY = 10;
    public static final int ZOOM_PATH = 13;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(this);

        firebaseAuthentication = new FAuth(this) {
            @Override
            public void onSignIn() {
                user = this.getUser();
            }

            @Override
            public void onSignOut() {
                startActivity( new Intent( Map.this , LoginActivity.class ));
            }
        };
        locationController =  new LocacionController(this) {
            @Override
            public void onMyLocationRecieved(Location location) {

                double distance = distance(location.getLatitude(), location.getLongitude(), place.getLatLng().latitude, place.getLatLng().longitude);
                addMarker(location, "Estas aquí", null);
                addMarker(place.getLatLng(), place.getAddress().toString(), "Distancia: " + distance + " km");
                drawPathBetween(new LatLng(location.getLatitude(), location.getLongitude()), place.getLatLng(), googleMap);
                Snackbar.make(Map.this.getCurrentFocus(), "Distancia: " + distance + " km", Snackbar.LENGTH_LONG).show();

                CameraPosition.Builder cameraPosition = CameraPosition.builder();
                cameraPosition.target(place.getLatLng());
                cameraPosition.zoom(ZOOM_PATH);
                cameraPosition.bearing(0);

                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition.build()), 1500, null);
            }
        };
        mainIntent = new Intent(this, HomeActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);



    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        crearPromocion();
        for(Promocion_lugar lugar:promocionesLugares) {
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lugar.getLatitud(),lugar.getLongitud()))
                    .title(lugar.getNombre())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.images))
                    .snippet(lugar.getDescripcion()));
        }
        this.googleMap = googleMap;
        this.googleMap.setPadding(0, 150, 0, 0);

        LatLng bogota = new LatLng(4.65, -74.05);
        CameraPosition.Builder cameraPosition = CameraPosition.builder();
        cameraPosition.target(bogota);
        cameraPosition.zoom(ZOOM_CITY);
        cameraPosition.bearing(0);

        this.googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition.build()), 1500, null);

        this.googleMap.getUiSettings().setCompassEnabled(false);
        this.googleMap.getUiSettings().setZoomGesturesEnabled(true);
        this.googleMap.getUiSettings().setZoomControlsEnabled(false);
        this.googleMap.getUiSettings().setMapToolbarEnabled(false);

        if(Permisos.askPermission(this, Permisos.FINE_LOCATION, "Se requiere el acceso a la localización.")) {
            locationAction();
        }
    }

    public void crearPromocion(){
        Promocion_lugar lugarP1 = new Promocion_lugar("casita",3,4.6582522,-74.1279583,"cosa linda, cosa hermosa, cosa bien hecha");
        Promocion_lugar lugarP2 = new Promocion_lugar("cochinilla",3,4.6476647212219975,-74.10066318538156,"Dispuesto a perder tu salario ven aquì");
        promocionesLugares.add(lugarP1);
        promocionesLugares.add(lugarP2);


    }






    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(Permisos.permissionGranted(requestCode, permissions, grantResults)) {
            locationAction();
        }
    }

    private void locationAction() {
        if(Permisos.checkSelfPermission(this, Permisos.FINE_LOCATION)) {
            googleMap.setMyLocationEnabled(true);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuthentication.onStart();
        locationController.askForGPS();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuthentication.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onPlaceSelected(Place place) {
        this.place = place;
        locationController.getMyLocation();
    }

    @Override
    public void onError(Status status) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemClicked = item.getItemId();
        switch (itemClicked) {
            case R.id.menu_signout:
                firebaseAuthentication.SignOut();
                break;
            case R.id.menu_style_map:
                showMapStyleSelectorDialog(this, googleMap);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void addMarker(Location location, String title, String snippet) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        addMarker(latLng, title, snippet);
    }

    public void addMarker(LatLng latLng, String title, String snippet) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(title);
        if(snippet != null) {
            markerOptions.snippet(snippet);
        }
        googleMap.addMarker(markerOptions);
    }

    public void addMarkerAndMove(Location location, String title, String snippet, int zoom) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        addMarkerAndMove(latLng, title, snippet, zoom);
    }

    public void addMarkerAndMove(LatLng latLng, String title, String snippet, int zoom) {
        addMarker(latLng, title, snippet);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    public static Bitmap cropImage(Bitmap image) {
        int width = Math.min(image.getWidth(), image.getHeight());
        int height = width;

        int x = (image.getWidth() - width) / 2;
        int y = 0;

        return Bitmap.createBitmap(image, x, y, width, height);
    }

    public static Bitmap getImageFormUri(Activity activity, Uri uri) {
        InputStream imageStream = null;
        try {
            imageStream = activity.getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(imageStream);
    }



    public static void showMapStyleSelectorDialog(final Activity activity, final GoogleMap mMap) {
        CharSequence[] MAP_TYPE_ITEMS = {"Dia", "Noche"};
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Escoja el tema para el mapa");
        builder.setSingleChoiceItems(MAP_TYPE_ITEMS, checkStyle, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            default:
                                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(activity, R.raw.map_day));
                                break;
                            case 1:
                                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(activity, R.raw.map_night));
                                break;
                        }
                        dialog.dismiss();
                        checkStyle = item;
                    }
                }
        );

        AlertDialog fMapTypeDialog = builder.create();
        fMapTypeDialog.setCanceledOnTouchOutside(true);
        fMapTypeDialog.show();
    }

    public static double distance(double lat1, double long1, double lat2, double long2) {
        double latDistance = Math.toRadians(lat1 - lat2);
        double lngDistance = Math.toRadians(long1 - long2);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1))  * Math.cos(Math.toRadians(lat2))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a),  Math.sqrt(1 - a));
        double result = RADIUS_OF_EARTH_KM * c;
        return Math.round(result*100.0)/100.0;
    }

    public static void drawPathBetween(LatLng origin, LatLng destiny, GoogleMap googleMap) {
        String url = obtenerDireccionesURL(origin, destiny);
        Descarga downloadTask = new Descarga(googleMap);
        downloadTask.execute(url);
    }

    public static String obtenerDireccionesURL(LatLng origin, LatLng destiny) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + destiny.latitude + "," + destiny.longitude;
        String sensor = "sensor=false";
        String parameters = str_origin + "&" + str_dest + "&" + sensor;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
        return url;
    }



}

package webike.webike.ubicacion;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import webike.webike.CreateGroupActivity;
import webike.webike.HomeActivity;
import webike.webike.R;
import webike.webike.utils.Permisos;

public class MapCreateRoute extends AppCompatActivity implements OnMapReadyCallback, PlaceSelectionListener {

    private Place place;
    private GoogleMap mMap;
    ArrayList markerPoints = new ArrayList();
    private LocacionController locationController;
    private Button accept;
    private Button cancel;
    private TextView dist;
    private String url;
    private EditText route_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_route);

        accept = (Button) findViewById(R.id.btn_checkout);
        cancel = (Button) findViewById(R.id.btn_cancel);
        dist = (TextView) findViewById(R.id.distance_route_map);
        route_name = (EditText) findViewById(R.id.route_name);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_GR);
        mapFragment.getMapAsync(this);
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_GR);
        autocompleteFragment.setOnPlaceSelectedListener(this);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rn = route_name.getText().toString().trim();
                if(TextUtils.isEmpty(rn))
                {
                    Toast.makeText(MapCreateRoute.this, "Faltan datos", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent obj = getIntent().putExtra("result",url);
                    obj.putExtra("nombre_ruta",rn);
                    setResult(RESULT_OK, obj);
                    finish();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MapCreateRoute.this, HomeActivity.class));
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(4.65, -74.05);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                markerLogic(latLng);
                if (markerPoints.size() >= 2) {
                    LatLng origin = (LatLng) markerPoints.get(0);
                    LatLng dest = (LatLng) markerPoints.get(1);

                    String url = getDirectionsUrl(origin, dest);
                    Descarga downloadTask = new Descarga(mMap);
                    downloadTask.execute(url);
                    dist.setText("Distancia: " + Double.toString(distance(origin.latitude,origin.longitude,dest.latitude,dest.longitude))+ " km.");
                }
            }
        });
        locationController = new LocacionController(this){
            public void onMyLocationRecieved(Location location) {
                double distance = distance(location.getLatitude(), location.getLongitude(), place.getLatLng().latitude, place.getLatLng().longitude);
                markerLogic(place.getLatLng());

                CameraPosition.Builder cameraPosition = CameraPosition.builder();
                cameraPosition.target(place.getLatLng());
                cameraPosition.zoom(15);
                cameraPosition.bearing(0);

                if (markerPoints.size() >= 2) {
                    LatLng origin = (LatLng) markerPoints.get(0);
                    LatLng dest = (LatLng) markerPoints.get(1);

                    String url = getDirectionsUrl(origin, dest);
                    Descarga downloadTask = new Descarga(mMap);
                    downloadTask.execute(url);
                    dist.setText("Distancia: " + Double.toString(distance(origin.latitude,origin.longitude,dest.latitude,dest.longitude))+ " km.");
                }

                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition.build()), 1500, null);
            }
        };
        if (Permisos.askPermission(this, Permisos.FINE_LOCATION, "Se requiere el acceso a la localización.")) {
            locationAction();
        }

    }

    private void markerLogic(LatLng latLng)
    {
        if (markerPoints.size() > 1) {
            markerPoints.clear();
            mMap.clear();
        }
        markerPoints.add(latLng);
        MarkerOptions options = new MarkerOptions();
        options.position(latLng);
        if (markerPoints.size() == 1)
        {
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        } else if (markerPoints.size() == 2)
        {
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        }
        mMap.addMarker(options);

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (Permisos.permissionGranted(requestCode, permissions, grantResults)) {
            locationAction();
        }
    }

    private void locationAction() {
        if (Permisos.checkSelfPermission(this, Permisos.FINE_LOCATION)) {
            mMap.setMyLocationEnabled(true);
        }
    }

    public void onPlaceSelected(Place place) {
        this.place = place;
        locationController.getMyLocation();
    }

    public void onError(Status status) {

    }

    public static double distance(double lat1, double long1, double lat2, double long2) {
        double latDistance = Math.toRadians(lat1 - lat2);
        double lngDistance = Math.toRadians(long1 - long2);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1))  * Math.cos(Math.toRadians(lat2))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a),  Math.sqrt(1 - a));
        double result = 6371 * c;
        return Math.round(result*100.0)/100.0;
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String sensor = "sensor=false";
        String parameters = str_origin + "&" + str_dest + "&" + sensor;
        String output = "json";
        url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
        return url;
    }
}

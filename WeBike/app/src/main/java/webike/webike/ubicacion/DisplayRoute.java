package webike.webike.ubicacion;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import webike.webike.HomeActivity;
import webike.webike.R;
import webike.webike.logic.PlacePromotion;
import webike.webike.utils.FData;
import webike.webike.utils.ListActions;
import webike.webike.utils.Permisos;

public class DisplayRoute extends AppCompatActivity implements OnMapReadyCallback, PlaceSelectionListener {

    private Place place;
    private GoogleMap mMap;
    ArrayList markerPoints;
    private LocacionController locationController;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_route);
        markerPoints = new ArrayList();
        FData.getPlacePromotions(FirebaseDatabase.getInstance(), new ListActions<PlacePromotion>() {
            @Override

            public void onReceiveList(ArrayList<PlacePromotion> data, DatabaseReference reference) {
                for(PlacePromotion lugar:data) {
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(lugar.getLatitud(),lugar.getLongitud()))
                            .title(lugar.getNombre())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.star_res))
                            .snippet(lugar.getDescription()));
                }
            }

            @Override
            public void onCancel(DatabaseError error) {

            }
        });

        Intent tempIntent = getIntent();
        Bundle bundle = tempIntent.getExtras();
        url = (String) bundle.get("routes");

        //url = "https://maps.googleapis.com/maps/api/directions/json?origin=4.648392150367993,-74.05153054744005&destination=4.650766113720292,-74.05103702098131&sensor=false";

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_ds);
        mapFragment.getMapAsync(this);
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_ds);
        autocompleteFragment.setOnPlaceSelectedListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng bogota = new LatLng(4.65, -74.05);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bogota, 16));

        Descarga downloadTask = new Descarga(mMap);
        downloadTask.execute(url);

        locationController = new LocacionController(this){
            public void onMyLocationRecieved(Location location) {
                markerLogic(place.getLatLng());

                CameraPosition.Builder cameraPosition = CameraPosition.builder();
                cameraPosition.target(place.getLatLng());
                cameraPosition.zoom(15);
                cameraPosition.bearing(0);

                if (markerPoints.size() >= 2) {
                    LatLng origin = (LatLng) markerPoints.get(0);
                    LatLng dest = (LatLng) markerPoints.get(1);

                    Descarga downloadTask = new Descarga(mMap);
                    downloadTask.execute(url);
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
            markerPoints.remove(markerPoints.size()-1);
            markerPoints.remove(markerPoints.size()-1);
            mMap.clear();
            FData.getPlacePromotions(FirebaseDatabase.getInstance(), new ListActions<PlacePromotion>() {
                @Override

                public void onReceiveList(ArrayList<PlacePromotion> data, DatabaseReference reference) {
                    for(PlacePromotion lugar:data) {
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(lugar.getLatitud(),lugar.getLongitud()))
                                .title(lugar.getNombre())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.star_res))
                                .snippet(lugar.getDescription()));
                    }
                }

                @Override
                public void onCancel(DatabaseError error) {

                }
            });
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


}

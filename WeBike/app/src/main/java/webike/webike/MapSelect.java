package webike.webike;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import webike.webike.logic.PlacePromotion;
import webike.webike.ubicacion.Descarga;
import webike.webike.ubicacion.LocacionController;
import webike.webike.utils.FData;
import webike.webike.utils.ListActions;
import webike.webike.utils.Permisos;

public class MapSelect extends AppCompatActivity implements OnMapReadyCallback, PlaceSelectionListener {

    private Place place;
    private GoogleMap mMap;
    ArrayList markerPoints = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        /*FData.getPlacePromotions(FirebaseDatabase.getInstance(), new ListActions<PlacePromotion>() {
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
*/
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(this);


    }

    @Override
    public void onPlaceSelected(Place place) {

    }

    @Override
    public void onError(Status status) {

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
                double lati = latLng.latitude;
                double lon = latLng.longitude;
                Intent intent = new Intent(MapSelect.this,CreatePlacePromotionActivity.class);
                intent.putExtra("lati",lati);
                intent.putExtra("lon",lon);
                startActivity(intent);
                    //Toast.makeText(getBaseContext(),latLng.toString(),Toast.LENGTH_SHORT).show();

            }
        });
        if (Permisos.askPermission(this, Permisos.FINE_LOCATION, "Se requiere el acceso a la localización.")) {
            locationAction();
        }
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
}

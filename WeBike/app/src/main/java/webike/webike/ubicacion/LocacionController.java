package webike.webike.ubicacion;

import android.app.Activity;
import android.content.IntentSender;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import webike.webike.utils.Permisos;

/**
 * Created by Carlos on 23/10/2017.
 */

public class LocacionController {

    private static final int REQUEST_CHECK_SETTINGS = 0;

    private Activity activity;
    private FusedLocationProviderClient locationProvider;
    private LocationRequest locationRequest;

    public LocacionController(Activity activity) {
        this.activity = activity;
        locationProvider = LocationServices.getFusedLocationProviderClient(activity);
        locationRequest = createLocationRequest(5000, 5000, LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private LocationRequest createLocationRequest(long timeToRefresh, long maxTimeToRefresh, int priority) {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(timeToRefresh);
        mLocationRequest.setFastestInterval(maxTimeToRefresh);
        mLocationRequest.setPriority(priority);
        return mLocationRequest;
    }

    public void getMyLocation() {
        if (Permisos.checkSelfPermission(activity, Permisos.FINE_LOCATION)) {
            Task<Location> task = locationProvider.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    onMyLocationRecieved(location);
                }
            });
        }
    }

    public boolean askForGPS() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        SettingsClient settingsClient = LocationServices.getSettingsClient(activity);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        task.addOnCompleteListener(activity, new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(activity, "GPS encendido", Toast.LENGTH_SHORT).show();
                } else {
                    Exception exception = task.getException();
                    int statusCode = ((ApiException) exception).getStatusCode();

                    switch(statusCode) {
                        case CommonStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                Log.e(LocacionController.class.getName(), resolvable + " !!!!!!!!!11");
                                resolvable.startResolutionForResult(activity, REQUEST_CHECK_SETTINGS);
                            } catch(IntentSender.SendIntentException sendEx) { }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                }
            }
        });
        return true;
    }

    public void onMyLocationRecieved(Location location) {}

}

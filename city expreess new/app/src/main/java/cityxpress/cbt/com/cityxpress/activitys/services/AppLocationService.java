package cityxpress.cbt.com.cityxpress.activitys.services;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.activity.ExpressCity;
import cityxpress.cbt.com.cityxpress.activitys.model.LatLongModel;

/**
 * Created by admin on 27-Aug-18.
 */

public class AppLocationService extends Service implements LocationListener {

    protected LocationManager locationManager;
    Location location;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    int NOTIFICATION_ID = 12345;
    ArrayList<LatLongModel> latlonglist;
    Context context;
    private static final long MIN_DISTANCE_FOR_UPDATE = 0;
    private static final long MIN_TIME_FOR_UPDATE = 1000;
    NotificationCompat.Builder builder;
    NotificationManager nManager;

    public AppLocationService() {

    }

    public AppLocationService(Context context, ArrayList<LatLongModel> latlonglist, NotificationCompat.Builder builder, NotificationManager nManager) {

        this.builder=builder;
        this.nManager=nManager;
        this.context = context;
        this.latlonglist = latlonglist;
        locationManager = (LocationManager) context
                .getSystemService(LOCATION_SERVICE);
        pref = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    }


    public Location getLocation(String provider) {
        locationManager.requestLocationUpdates(provider,
                MIN_TIME_FOR_UPDATE, MIN_DISTANCE_FOR_UPDATE, this);
        if (locationManager != null) {
            location = locationManager.getLastKnownLocation(provider);
            return location;
        }
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.e("location", "in service lat=" + location.getLatitude());
        Log.e("location", "long in service lat=" + location.getLongitude());
        editor = pref.edit();
        editor.putString("Lat", String.valueOf(location.getLatitude()));
        editor.putString("Long", String.valueOf(location.getLongitude()));
        editor.commit();
        for (int i = 0; i < latlonglist.size(); i++) {
            LatLongModel m = latlonglist.get(i);
            double def = distance(Double.parseDouble(m.getLat()), Double.parseDouble(m.getLon()), location.getLatitude(), location.getLongitude());
            Log.e("location", "diffrence=" + def);
            if (def < 0.10) {
                genRateNotification();
            }
        }
    }

    private void genRateNotification() {
        nManager.notify(NOTIFICATION_ID, builder.build());

    }


    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("location", "dddddddddddddd");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }
}

package cityxpress.cbt.com.cityxpress.activitys.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.Data.MyApplication;
import cityxpress.cbt.com.cityxpress.activitys.activity.ExpressCity;
import cityxpress.cbt.com.cityxpress.activitys.model.LatLongModel;

/**
 * Created by admin on 28-Aug-18.
 */

public class LocationService extends Service {
    public static final String BROADCAST_ACTION = "Hello World";
    private static final int TWO_MINUTES = 1000 * 60 ;
    public LocationManager locationManager;
    public MyLocationListener listener;
    Intent intent;
    ArrayList<LatLongModel> latlonglist;
    NotificationCompat.Builder builder;
    int NOTIFICATION_ID = 12345;
    NotificationManager nManager;

    public LocationService()
    {

    }

    public LocationService(ArrayList<LatLongModel> latlonglist, NotificationCompat.Builder builder, NotificationManager nManager) {

        Log.e("location", " in contruuctor size="+latlonglist.size());
}


    @Override
    public void onCreate() {
        super.onCreate();
        intent = new Intent(BROADCAST_ACTION);
        Log.e("location", "in create");

        latlonglist=new ArrayList<>();
        addLatLong();

       builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_logo)
                        .setContentTitle("My Notification Title")

                        .setContentText("Something interesting happened");

        Intent targetIntent = new Intent(this, ExpressCity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);





    }

    private void addLatLong() {
        latlonglist.add(new LatLongModel("18.467676","73.856897"));
    }

    @Override
    public void onStart(Intent intent, int startId) {

        Log.e("location", "in start");
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listener = new MyLocationListener();
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, TWO_MINUTES, 0, listener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, TWO_MINUTES, 0, listener);

    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    /**
     * Checks whether two providers are the same
     */



    @Override
    public void onDestroy() {
        // handler.removeCallbacks(sendUpdatesToUI);
        super.onDestroy();
        Log.v("STOP_SERVICE", "DONE");
       // locationManager.removeUpdates(listener);
    }




    public static Thread performOnBackgroundThread(final Runnable runnable) {
        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } finally {

                }
            }
        };
        t.start();
        return t;
    }

    private void locationChange(Location loc) {

       // Log.e("location", "MyApplication size="+MyApplication.latlonglist.size());
        Log.e("location", "size="+latlonglist.size());

        for (int i = 0; i < latlonglist.size(); i++) {
            LatLongModel m = latlonglist.get(i);
            double def = distance(Double.parseDouble(m.getLat()), Double.parseDouble(m.getLon()), loc.getLatitude(), loc.getLongitude());
            Log.e("location", "diffrence=" + def);
            if (def < 0.20) {
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

    public class MyLocationListener implements LocationListener
    {

        public void onLocationChanged(final Location loc)
        {
            locationChange(loc);
        }



        public void onProviderDisabled(String provider)
        {
        }


        public void onProviderEnabled(String provider)
        {
        }


        public void onStatusChanged(String provider, int status, Bundle extras)
        {

        }

    }
}
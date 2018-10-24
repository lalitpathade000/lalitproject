package cityxpress.cbt.com.cityxpress.activitys.activity;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.Data.MyApplication;
import cityxpress.cbt.com.cityxpress.activitys.model.LatLongModel;
import cityxpress.cbt.com.cityxpress.activitys.services.AppLocationService;
import cityxpress.cbt.com.cityxpress.activitys.services.GPSTracker;
import cityxpress.cbt.com.cityxpress.activitys.services.LocationService;

public class MapActivity extends AppCompatActivity {

    GPSTracker gps;
    Context context;
    TextView tt;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences pref;
    AppLocationService appLocationService;
    ArrayList<LatLongModel> latlonglist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        context = this;
        latlonglist=new ArrayList<>();
        addLatLong();

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_logo)
                        .setContentTitle("My Notification Title")
                        .setContentText("Something interesting happened");
        int NOTIFICATION_ID = 12345;

        Intent targetIntent = new Intent(this, ExpressCity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        /*appLocationService = new AppLocationService(
                context,latlonglist,builder,nManager);

        startService(new Intent(this,AppLocationService.class));
*/

        LocationService locationService=new LocationService(latlonglist,builder,nManager);
        startService(new Intent(this,LocationService.class));
        MyApplication.latlonglist=latlonglist;
        MyApplication.builder=builder;
        MyApplication.nManager=nManager;

        pref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        tt = findViewById(R.id.tt);
        tt.setText(pref.getString("Lat", "").toString() + " " + pref.getString("Long", "").toString());

    }

    private void addLatLong() {
        latlonglist.add(new LatLongModel("18.467676","73.856897"));
    }

    public void gpsclick(View view) {

        Location gpsLocation = appLocationService
                .getLocation(LocationManager.GPS_PROVIDER);

        if (gpsLocation != null) {
            double latitude = gpsLocation.getLatitude();
            double longitude = gpsLocation.getLongitude();
            Toast.makeText(
                    getApplicationContext(),
                    "Mobile Location (GPS): \nLatitude: " + latitude
                            + "\nLongitude: " + longitude,
                    Toast.LENGTH_LONG).show();
        } else {
            showSettingsAlert("GPS");
        }
    }

    public void nwclick(View view) {
        Location nwLocation = appLocationService
                .getLocation(LocationManager.NETWORK_PROVIDER);

        if (nwLocation != null) {
            double latitude = nwLocation.getLatitude();
            double longitude = nwLocation.getLongitude();
            Toast.makeText(
                    getApplicationContext(),
                    "Mobile Location (NW): \nLatitude: " + latitude
                            + "\nLongitude: " + longitude,
                    Toast.LENGTH_LONG).show();
        } else {
            showSettingsAlert("NETWORK");
        }

    }


    public void showSettingsAlert(String provider) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                context);
        alertDialog.setTitle(provider + " SETTINGS");
        alertDialog
                .setMessage(provider + " is not enabled! Want to go to settings menu?");

        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivity(intent);
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }
}

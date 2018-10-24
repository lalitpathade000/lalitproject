package cityxpress.cbt.com.cityxpress.activitys.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by admin on 27-Aug-18.
 */

public class GpsLocationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().matches("android.location.GPS_ENABLED_CHANGE")) {
            boolean enabled = intent.getBooleanExtra("enabled",false);
            if(enabled)
            {
                Intent pushIntent = new Intent(context, AppLocationService.class);
                context.startService(pushIntent);
                Log.e("location","location enable");
            }
            else
            {
                Log.e("location","location desable");
            }
        }
    }
}
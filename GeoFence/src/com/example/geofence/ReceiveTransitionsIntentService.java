package com.example.geofence;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class ReceiveTransitionsIntentService extends IntentService {

	public ReceiveTransitionsIntentService() {
		super("Recive Transition Intent Service");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		NotificationManager mNotifyMgr = 
		        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
		Log.e("vivek", "onhandelintent");
		Intent broadcast = new Intent();
		broadcast.addCategory(GeofenceUtils.CATEGORY_LOCATION_SERVICES);
		if (LocationClient.hasError(intent)) {
			Log.e(GeofenceUtils.APPTAG, "in has error 1");

			broadcast.setAction(GeofenceUtils.ACTION_GEOFENCE_ERROR).putExtra(
					GeofenceUtils.EXTRA_GEOFENCE_STATUS, "error");

			LocalBroadcastManager.getInstance(this).sendBroadcast(broadcast);

			int mNotificationId = 001;

			// Gets an instance of the NotificationManager service
					NotificationCompat.Builder mBuilder =
				    new NotificationCompat.Builder(this)
				    .setSmallIcon(R.drawable.common_ic_googleplayservices)
				    .setContentTitle("MyGeofence")
				    .setContentText("Not Available");
			
			// Builds the notification and issues it.
					mNotifyMgr.notify(mNotificationId, mBuilder.build());
			
		} else {
			int transition = LocationClient.getGeofenceTransition(intent);

			if (transition == Geofence.GEOFENCE_TRANSITION_ENTER)
					 {
				
				int mNotificationId = 001;

						NotificationCompat.Builder mBuilder =
					    new NotificationCompat.Builder(this)
					    .setSmallIcon(R.drawable.common_ic_googleplayservices)
					    .setContentTitle("MyGeofence")
					    .setContentText("You Have Entered the Geofence");
				 
				// Builds the notification and issues it.
						mNotifyMgr.notify(mNotificationId, mBuilder.build());
			}else if (transition==Geofence.GEOFENCE_TRANSITION_EXIT) {
				
				int mNotificationId = 002;
				
						NotificationCompat.Builder mBuilder =
					    new NotificationCompat.Builder(this)
					    .setSmallIcon(R.drawable.common_ic_googleplayservices)
					    .setContentTitle("MyGeofence")
					    .setContentText("You Have Exited the Geofence");
						mNotifyMgr.notify(mNotificationId, mBuilder.build());
			}
		}
	}

	@Override
	public void onCreate() {
		Log.e("vivek", "onCreate");
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.e("vivek", "onStart");
		super.onStart(intent, startId);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e("vivek", "onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		Log.e("vivek", "onDestroy");
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.e("vivek", "onBind");
		return super.onBind(intent);
	}
	
}

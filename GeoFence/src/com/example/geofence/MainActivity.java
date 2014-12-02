package com.example.geofence;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationClient.OnAddGeofencesResultListener;
import com.google.android.gms.location.LocationClient.OnRemoveGeofencesResultListener;
import com.google.android.gms.location.LocationStatusCodes;

public class MainActivity extends Activity implements ConnectionCallbacks,
		OnConnectionFailedListener, OnRemoveGeofencesResultListener,
		OnAddGeofencesResultListener {

	// edit text fields
	EditText lattitude, longitude, radius;

	TextView message;

	// fields for storing the values
	double Latitude, Longitude;
	float Radius;

	private static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;
	private static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = GEOFENCE_EXPIRATION_IN_HOURS
			* DateUtils.HOUR_IN_MILLIS;

	// locationClient
	LocationClient locationClient;
	Intent intent;

	public PendingIntent mGeofencePendingIntent = null;

	// oncreate method
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		lattitude = (EditText) findViewById(R.id.editText1);
		longitude = (EditText) findViewById(R.id.editText2);
		radius = (EditText) findViewById(R.id.editText3);
		message = (TextView) findViewById(R.id.textView1);

		locationClient = new LocationClient(this, this, this);
	}

	
	
	public void gettingTheValuesFromEditText(View view) {
		
		
		
		Latitude = (double) Double.valueOf(lattitude.getText().toString());
		Longitude = (double) Double.valueOf(longitude.getText().toString());
		Radius = (float) Float.valueOf(radius.getText().toString());
		List<Geofence> geofence = new ArrayList<Geofence>();
		geofence.add(creatingGeofence());

		locationClient.addGeofences(geofence, createRequestPendingIntent(),
				this);
		
		// if (Latitude != 0.0 && Longitude != 0.0 && Radius != 0) {

		/*
		 * if (geofence != null) { findingLocation(); }
		 */

		// } else {
		// Toast.makeText(getApplication(), "type somrthing",
		// Toast.LENGTH_SHORT).show();
		// }
	}
	
	public void settingValueToTheEditText(View view) {
		
	}

	public Geofence creatingGeofence() {
		return new Geofence.Builder()
				.setRequestId("1")
				.setCircularRegion(Latitude, Longitude, Radius)
				.setExpirationDuration(GEOFENCE_EXPIRATION_IN_MILLISECONDS)
				.setTransitionTypes(
						Geofence.GEOFENCE_TRANSITION_ENTER
								| Geofence.GEOFENCE_TRANSITION_EXIT).build();

	}

	public void findingLocation() {

		Log.d("vivek", "findingLocation");
		int reultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getApplication());
		if (ConnectionResult.SUCCESS == reultCode) {
			message.setText("you have entered the geoshere");
		} else {
			Log.d("vivek", "findingLocation: connection has been failed");
		}
	}

	@Override
	public void onRemoveGeofencesByPendingIntentResult(int arg0,
			PendingIntent arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onStart() {
		super.onStart();
		locationClient.connect();
	}

	@Override
	protected void onPause() {
		super.onPause();
		locationClient.disconnect();
		locationClient = null;
	}

	@Override
	public void onRemoveGeofencesByRequestIdsResult(int arg0, String[] arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {

		Toast.makeText(this, "connection Has failed", Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public void onConnected(Bundle arg0) {

		Toast.makeText(this, "Location client has been connecteds",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDisconnected() {

		Toast.makeText(this, "Locatio client has been disconnected",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onAddGeofencesResult(int arg0, String[] arg1) {
		if (LocationStatusCodes.SUCCESS == arg0) {
			Toast.makeText(getApplication(), "onAddGeofenceResult",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "geofence is not added", Toast.LENGTH_SHORT)
					.show();
		}
	}

	public PendingIntent createRequestPendingIntent() {

		// If the PendingIntent already exists
		if (null != mGeofencePendingIntent) {

			// Return the existing intent
			return mGeofencePendingIntent;

			// If no PendingIntent exists
		} else {
			Log.e("vivek", "createRequestPendingIntent");
			// Create an Intent pointing to the IntentService
			intent = new Intent(getApplication(),
					ReceiveTransitionsIntentService.class);
			
			

			/*
			 * Return a PendingIntent to start the IntentService. Always create
			 * a PendingIntent sent to Location Services with
			 * FLAG_UPDATE_CURRENT, so that sending the PendingIntent again
			 * updates the original. Otherwise, Location Services can't match
			 * the PendingIntent to requests made with it.
			 */

			return PendingIntent.getService(this, 1234, intent,
					PendingIntent.FLAG_UPDATE_CURRENT);
		}
	}

}

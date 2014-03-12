package com.example.demo4;

import com.example.demo4.fragments.ErrorDialogFragment;
import com.example.demo4.fragments.PlacesFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.IntentSender;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends FragmentActivity implements OnConnectionFailedListener{

	public final static int CONNECTION_FAILURE_REQUEST = 9000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		FragmentManager manager = getSupportFragmentManager();
		PlacesFragment fragment = (PlacesFragment)manager.findFragmentById(R.id.fragmentMap);
		
		if(servicesConnected()){
			manager.beginTransaction().show(fragment).commit();
		} else {
			manager.beginTransaction().hide(fragment).commit();
		}
	}
	

	private boolean servicesConnected() {
		// TODO Auto-generated method stub
		int code = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		
		if(code == ConnectionResult.SUCCESS){
			return true;
		} else {
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(code, this, 0);	
			
			if(dialog != null){
				ErrorDialogFragment fragment = new ErrorDialogFragment();
				fragment.setDialog(dialog);
				fragment.show(getSupportFragmentManager(), "error");
			}
			return false;
		}
		
	}


	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		// TODO Auto-generated method stub
		if(connectionResult.hasResolution()){
			try{
				connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_REQUEST);
			} catch (IntentSender.SendIntentException e){
				Log.e("ERROR", Log.getStackTraceString(e));
			}
		}else{
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this, CONNECTION_FAILURE_REQUEST);	
			
			if(dialog != null){
				ErrorDialogFragment fragment = new ErrorDialogFragment();
				fragment.setDialog(dialog);
				fragment.show(getSupportFragmentManager(), "error");
			}
		}
		
	}

}

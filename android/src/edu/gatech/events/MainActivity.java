package edu.gatech.events;

import android.app.*;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity {

    GoogleMap map;
    MapFragment mapFragment;
    AllEventsFragment eventsFragment;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.event_view_list, android.R.layout.simple_spinner_dropdown_item);
        actionBar.setListNavigationCallbacks(spinnerAdapter, new ActionBar.OnNavigationListener() {

            @Override
            public boolean onNavigationItemSelected(int i, long l) {
                Log.d("MainActivity", "Nav Item selected: " + i + ", " + l);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft;
                switch (i) {
                    case 0:
                        ft = fm.beginTransaction();
                        ft.hide(mapFragment);
                        ft.show(eventsFragment);
                        ft.commit();
                        break;
                    case 1:
                        ft = fm.beginTransaction();
                        ft.hide(eventsFragment);
                        ft.show(mapFragment);
                        ft.commit();
                        break;
                    default:
                        break;
                }
                return true;
            }

        });

        eventsFragment = (AllEventsFragment) getFragmentManager().findFragmentById(R.id.events);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        map = mapFragment.getMap();
        map.addMarker(new MarkerOptions().position(new LatLng(33.773792, -84.398497)).title("Student Center").snippet("More fun that you can shake a stick at!"));

    }

    @Override
    protected void onResume() {
        super.onResume();
        int result = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            GooglePlayServicesUtil.getErrorDialog(result, this, result);
        }
    }
}

package thejavaleague.holyghost;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.gms.maps.model.LatLng;
import thejavaleague.holyghost.database.Sighting;
import thejavaleague.holyghost.mapping.GhostMapFragment;
import thejavaleague.holyghost.mapping.MapViewType;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks{

    private static final String LOG_TAG = "MainActivity";

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment navigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    GhostMapFragment ghostMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
        mTitle = getTitle();

        // Set up the drawer.
        navigationDrawerFragment.setUp(
                R.id.navigation_drawer_fragment,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        // Add map to Activity
        ghostMap = new GhostMapFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.container_main, ghostMap)
                .commit();
    }



    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Log.i(LOG_TAG, "navigation drawer item selected");
        if(ghostMap == null){
            // This gets called before the map is ready. Default to 0;
            return;
        }
        switch (position){
            case 0:
                ghostMap.setMapType(MapViewType.NORMAL);
                break;
            case 1:
                ghostMap.setMapType(MapViewType.SATELLITE);
                break;
            case 2:
                ghostMap.setMapType(MapViewType.TERRAIN);
                break;
            case 3:
                ghostMap.setMapType(MapViewType.HYBRID);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!navigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_markGhostSighting:
                this.showGhostAlertDialog();
                break;
            case R.id.menu_getClosestSightings:
                Toast.makeText(this, "get closest ghost sightings menu clicked", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(navigationDrawerFragment.isDrawerOpen()){
                navigationDrawerFragment.closeDrawer();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showGhostAlertDialog() {
        GhostAlertDialog dialog = new GhostAlertDialog();
        dialog.setSightingLocation(ghostMap.getCurrentLocation());
        dialog.setOnButtonClickListener(new GhostAlertDialog.OnButtonClickListner() {
            @Override
            public void onPositiveButtonClicked(DialogInterface dialog, Sighting sighting) {
                LatLng location = new LatLng(
                        ghostMap.getCurrentLocation().getLatitude(),
                        ghostMap.getCurrentLocation().getLongitude()
                );
                sighting.setLocation(location);
                //TODO: call the SightingService to save the sighting
            }
            @Override
            public void onNegativeButtonClicked(DialogInterface dialog) {}
        });
        dialog.show(getFragmentManager(), "Ghost_Alert_Dialog");
    }
}

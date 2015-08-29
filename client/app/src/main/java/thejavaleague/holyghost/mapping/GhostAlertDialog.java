package thejavaleague.holyghost.mapping;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import thejavaleague.holyghost.R;

/**
 * Created by Chris on 8/29/15.
 */
public class GhostAlertDialog extends DialogFragment {

    private static final String LOG_TAG = "GhostAlertDialog";

    private Location sightingLocation;

    private EditText locationDescription;
    private EditText sightingDescription;
    private RatingBar rating;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Context appContext = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(appContext);
        builder.setIcon(R.drawable.ghost_marker);
        builder.setTitle(R.string.GhostSightedAlert_title);

        LayoutInflater inflater = LayoutInflater.from(appContext);
        final View inputView = inflater.inflate(R.layout.ghost_alert_dialog, null);

        locationDescription = (EditText) inputView.findViewById(R.id.editText_locationDescription);
        sightingDescription = (EditText) inputView.findViewById(R.id.editText_sightingDescription);
        rating = (RatingBar) inputView.findViewById(R.id.ratingBar_sightingRating);

        builder.setView(inputView);

        builder.setPositiveButton(R.string.GhostSightedAlert_posBtn,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(appContext, "pos btn clicked", Toast.LENGTH_SHORT).show();
                    }
                });
        builder.setNegativeButton(R.string.GhostSightedAlert_negBtn,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(appContext, "neg btn clicked", Toast.LENGTH_SHORT).show();
                    }
                });

        return builder.create();
    }

    public void setSightingLocation(Location location){
        sightingLocation = location;
    }
}

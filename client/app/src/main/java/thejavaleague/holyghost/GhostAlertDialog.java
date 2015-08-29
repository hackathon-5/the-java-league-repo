package thejavaleague.holyghost;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import thejavaleague.holyghost.R;
import thejavaleague.holyghost.database.Sighting;

/**
 * Created by Chris on 8/29/15.
 */
public class GhostAlertDialog extends DialogFragment {

    private static final String LOG_TAG = "GhostAlertDialog";

    private Location sightingLocation;

    private EditText sightingName;
    private EditText sightingDescription;
    private RatingBar rating;

    private OnButtonClickListner parentListener;

    interface OnButtonClickListner{
        void onPositiveButtonClicked(DialogInterface dialog, Sighting sighting);
        void onNegativeButtonClicked(DialogInterface dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Context appContext = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(appContext);
        builder.setIcon(R.drawable.ghost_marker);
        builder.setTitle(R.string.GhostSightedAlert_title);

        LayoutInflater inflater = LayoutInflater.from(appContext);
        final View inputView = inflater.inflate(R.layout.ghost_alert_dialog, null);

        sightingName = (EditText) inputView.findViewById(R.id.editText_sightingName);
        sightingDescription = (EditText) inputView.findViewById(R.id.editText_sightingDescription);
        rating = (RatingBar) inputView.findViewById(R.id.ratingBar_sightingRating);

        builder.setView(inputView);

        builder.setPositiveButton(R.string.GhostSightedAlert_posBtn,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Sighting sighting = createSightingFromData();
                        if(parentListener != null) {
                            parentListener.onPositiveButtonClicked(dialog, sighting);
                        }
                    }
                });
        builder.setNegativeButton(R.string.GhostSightedAlert_negBtn,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(parentListener != null) {
                            parentListener.onNegativeButtonClicked(dialog);
                        }
                    }
                });

        return builder.create();
    }

    public void setSightingLocation(Location location){
        sightingLocation = location;
    }

    public void setOnButtonClickListener(OnButtonClickListner listener) {
        this.parentListener = listener;
    }

    private Sighting createSightingFromData() {
        Sighting sighting = new Sighting();
        sighting.setTitle(sightingName.getText().toString());
        sighting.setDescription(sightingDescription.getText().toString());
        sighting.setRating(rating.getNumStars());
        return sighting;
    }
}

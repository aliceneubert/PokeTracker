package biz.raspbert.zacneubert.poketracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.gms.maps.model.Marker;

/**
 * Created by zneubert on 7/13/16.
 */
public class MarkerDialog {
    public static Dialog createDialog(Activity activity, final LocationRecord locationRecord, final Marker marker) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        Pokemon pokemon = locationRecord.getPokemon();
        builder.setTitle(pokemon.getName());
        builder.setMessage(locationRecord.readable());
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                locationRecord.delete();
                marker.remove();
                dialogInterface.dismiss();
            }
        });

        builder.setIcon(pokemon.getResourceSprite());
        // Create the AlertDialog object and return it
        return builder.create();
    }
}

package biz.raspbert.zacneubert.poketracker;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by zneubert on 7/14/16.
 */
public class SharingFeature {
    public static File pickFile(Activity activity, GoogleMap map) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        activity.startActivityForResult(intent, 10);
        return null;
    }

    public static void importPokemon(File file, GoogleMap map) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            String content = scanner.useDelimiter("\\A").next();
            Type listType = new TypeToken<List<LocationRecord>>() {
            }.getType();
            List<LocationRecord> locationRecords = new Gson().fromJson(content, listType);
            Set<LocationRecord> existingRecords = LocationRecord.setAll();
            for (LocationRecord locationRecord : locationRecords) {
                if (!existingRecords.contains(locationRecord)) {
                    locationRecord.save();
                    if(map != null) {
                        locationRecord.place(map);
                    }
                }
                else {
                    int i=0; i++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(scanner != null) scanner.close();
        }
    }

    public static void importWithChosenFile(Activity activity, final GoogleMap map) {
        FileChooser chooser = new FileChooser(activity);
        chooser.setFileListener(
            new FileChooser.FileSelectedListener() {
                @Override
                public void fileSelected(final File file) {
                    importPokemon(file, map);
                }
            }
        );
        chooser.showDialog();
    }

    public static void exportPokemon(Activity activity) {
        List<LocationRecord> locationRecords = LocationRecord.listAll(LocationRecord.class);
        Gson gson = new Gson();
        String toExport = gson.toJson(locationRecords);
        File exportFile = new File(Environment.getExternalStorageDirectory() + "/poketracker.json");
        exportFile.setWritable(true);
        PermissionsManager.requestStoragePermission(activity);
        if(PermissionsManager.hasStoragePermission(activity)) {
            PrintWriter printWriter = null;
            try {
                printWriter = new PrintWriter(exportFile);
                printWriter.write(toExport);
                printWriter.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Toast.makeText(activity, "Share the file at " + exportFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
            return;
        }
        else {
            Toast.makeText(activity, "You must grant the storage permission to use this feature", Toast.LENGTH_LONG).show();
        }
    }
}

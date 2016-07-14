package com.example.zneubert.poketracker;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.widget.Toast;

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
    public File pickFile(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        activity.startActivityForResult(intent, 10);
        return null;
    }

    public void importPokemon(File file) {
        try {
            String content = new Scanner(file).useDelimiter("\\Z").next();
            Type listType = new TypeToken<List<LocationRecord>>() {}.getType();
            List<LocationRecord> locationRecords = new Gson().fromJson(content, listType);
            Set<LocationRecord> existingRecords = LocationRecord.setAll();
            for(LocationRecord locationRecord : locationRecords) {
                if(!existingRecords.contains(locationRecord)) {
                    locationRecord.save();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void exportPokemon(Activity activity) {
        List<LocationRecord> locationRecords = LocationRecord.listAll(LocationRecord.class);
        Gson gson = new Gson();
        String toExport = gson.toJson(locationRecords);
        File extDir = activity.getExternalFilesDir(null);
        File exportFile = new File(extDir, "poketracker.json");
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

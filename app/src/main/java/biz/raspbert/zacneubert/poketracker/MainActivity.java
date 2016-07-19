package biz.raspbert.zacneubert.poketracker;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import biz.raspbert.zacneubert.poketracker.Settings.Settings_List_Activity;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
    private Pokemon selectedPokemon;
    private List<ImageView> spriteViews = new ArrayList<>();
    private LinearLayout pokemonPicker;
    private ImageView menu_button;

    public static GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        pokemonPicker = (LinearLayout) findViewById(R.id.pokemonpicker);
        Resources resources = this.getResources();

        menu_button = (ImageView) findViewById(R.id.menu_button);
        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                Intent startSettings = new Intent(getApplicationContext(), Settings_List_Activity.class);
                startActivity(startSettings);
            }
        });

        for(int pokemonnumber=1; pokemonnumber<152; pokemonnumber++) {
            int id = SpriteManager.allSprites.get(pokemonnumber);
            ImageView imageView = new ImageView(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageView.setImageDrawable(this.getDrawable(id));
            }
            final int spriteid = id;
            final int pokenum = pokemonnumber;
            imageView.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View view) {
                    view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    for(ImageView spriteView : spriteViews) {
                        spriteView.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                    }
                    view.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.grey));
                    selectedPokemon = Pokemon.getPokemon(pokenum);
                }
            });
            spriteViews.add(imageView);
            pokemonPicker.addView(imageView);
        }
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onMapReady(final GoogleMap map) {
        this.googleMap = map;

        while (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 6);
        }
        map.setMyLocationEnabled(true);
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        for(LocationRecord locationRecord : LocationRecord.listAll(LocationRecord.class)) {
            locationRecord.place(map);
        }

        selectedPokemon = Pokemon.getPokemon(1); //Bulbasaur!
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                pokemonPicker.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                LocationRecord locationRecord = new LocationRecord();
                locationRecord.latitude = latLng.latitude;
                locationRecord.longitude = latLng.longitude;
                locationRecord.placedDate = new Date(System.currentTimeMillis());
                locationRecord.pokemonnumber = selectedPokemon.number;
                locationRecord.save();

                locationRecord.place(map);
            }
        });

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                pokemonPicker.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                LatLng latlng = marker.getPosition();
                List<LocationRecord> asdf = LocationRecord.listAll(LocationRecord.class);
                int count = (int) LocationRecord.count(LocationRecord.class);
                LocationRecord locationRecord = LocationRecord.getByLatLng(latlng);

                if(locationRecord == null) {
                    //throw new Exception("No locationrecord for " + latlng.toString());
                }
                MarkerDialog.createDialog(MainActivity.this, locationRecord, marker).show();

                return true;
            }
        });
        zoomToUser(map);
    }

    public void zoomToUser(GoogleMap map) {
        this.googleMap = map;

        PermissionsManager.requestLocationPermission(this);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null) {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }
}

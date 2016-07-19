package biz.raspbert.zacneubert.poketracker;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.gson.annotations.Expose;
import com.orm.SugarRecord;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zneubert on 7/13/16.
 */
public class LocationRecord extends SugarRecord {

    public transient Long id;

    @Expose
    public int pokemonnumber;
    @Expose
    public Date placedDate;
    @Expose
    public double longitude;
    @Expose
    public double latitude;

    public LocationRecord() {}

    public void place(GoogleMap map) {
        LatLng latlng = new LatLng(latitude, longitude);
        map.addMarker(
                new MarkerOptions().position(latlng).icon(Pokemon.getPokemon(pokemonnumber).getBitmapDescriptor())
        );
        Pokemon.getPokemon(pokemonnumber);
    }

    public Pokemon getPokemon() {
        return Pokemon.getPokemon(pokemonnumber);
    }

    public static LocationRecord getByLatLng(LatLng latLng) {
        List<LocationRecord> matchingLocations = LocationRecord.find(LocationRecord.class,
                "latitude = ? AND longitude = ?",
                new String[]{""+latLng.latitude, ""+latLng.longitude}
        );
        if(matchingLocations.size() == 0) {
            return null;
        }
        else {
            return matchingLocations.get(0);
        }
    }

    public String readable() {
        Pokemon pokemon = getPokemon();
        return "#" + pokemonnumber + "\nName: " + pokemon.getName() + "\nType: " + pokemon.getType() + "\nDate Recorded: " + placedDate.toString();
    }

    /*@Override
    public int hashCode() {
        return (int) (placedDate.getTime()*17 + pokemonnumber*43 + longitude * 13 + latitude * 17);
    }*/

    @Override
    public int hashCode() {
        return pokemonnumber*31 + ((int) longitude)*31 + ((int) latitude)*31;
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof LocationRecord)) return false;
        if(other == this) return true;

        LocationRecord otherRecord = (LocationRecord) other;
        boolean latitudeMatches = Math.abs(otherRecord.latitude - this.latitude) < .01;
        boolean longitudeMatches = Math.abs(otherRecord.longitude - this.longitude) < .01;
        boolean isEqual = otherRecord.placedDate.equals(this.placedDate)
                && latitudeMatches
                && longitudeMatches
                && otherRecord.pokemonnumber == this.pokemonnumber;
        return isEqual;
    }

    public static Set<LocationRecord> setAll() {
        List<LocationRecord> recordsList = LocationRecord.listAll(LocationRecord.class);
        Set<LocationRecord> set = new HashSet<>();
        set.addAll(recordsList);
        return set;
    }
}

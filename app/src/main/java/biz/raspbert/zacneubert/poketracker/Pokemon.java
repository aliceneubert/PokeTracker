package biz.raspbert.zacneubert.poketracker;

import android.content.Context;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by zneubert on 7/13/16.
 */
public class Pokemon extends SugarRecord {
    private Long id;
    public int number;

    private String name;
    private String type;

    public BitmapDescriptor getBitmapDescriptor(Context context) {
        return BitmapDescriptorFactory.fromResource(getResourceSprite(context));
    }

    public int getResourceSprite(Context context) {
        return SpriteManager.allSprites(context).get(number);
    }

    public Pokemon() {

    }

    private Pokemon(int number) {
        this.number = number;
    }

    public static Pokemon getPokemon(int number) {
        List<Pokemon> allPokemon = Pokemon.listAll(Pokemon.class);
        List<Pokemon> pokemans = Pokemon.find(Pokemon.class, "number = ?", new String[]{""+number});
        if(pokemans.size() == 0) {
            Pokemon pokemon = new Pokemon(number);
            PokemonInflater.asyncInflatePokemon(pokemon);
            return pokemon;
        }
        else {
            for(int i=1; i<pokemans.size(); i++) {
                Pokemon extramon = pokemans.get(i);
                extramon.delete();
            }
            return pokemans.get(0);
        }
    }

    public String getName() {
        if(name == null) {
            //PokemonInflater.asyncInflatePokemon(this);
            return "Loading, try again";
        }
        else return name;
    }

    public String getType() {
        if(type == null) {
            //PokemonInflater.asyncInflatePokemon(this);
            return "Loading, try again";
        }
        else return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }
}

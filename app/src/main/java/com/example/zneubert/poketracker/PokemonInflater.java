package com.example.zneubert.poketracker;

import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient;
import me.sargunvohra.lib.pokekotlin.model.PokemonSpecies;

/**
 * Created by zneubert on 7/13/16.
 */
public class PokemonInflater {
    private static PokeApi pokeApi = new PokeApiClient();
    private static Pokemon inflatePokemon(Pokemon pokemon) {
        try {
            PokemonSpecies species = pokeApi.getPokemonSpecies(pokemon.number);
            char first = Character.toUpperCase(species.getName().charAt(0));
            String name = first + species.getName().substring(1);
            pokemon.setName(name);
            pokemon.setType("");
            pokemon.save();
            return pokemon;
        }
        catch (Exception e) {
            return pokemon;
        }
    }

    public static void asyncInflatePokemon(final Pokemon pokemon) {
        Thread thread = new Thread(){
            @Override
            public void run() {
                inflatePokemon(pokemon);
            }
        };
        thread.start();
    }
}

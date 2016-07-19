package biz.raspbert.zacneubert.poketracker;

import android.app.Application;

import com.orm.SugarApp;
import com.orm.SugarContext;

/**
 * Created by zneubert on 7/13/16.
 */
public class PokeTrackerApplication extends SugarApp {
    @Override
    public void onCreate() {
        super.onCreate();

        String pokefont = "Pokemon.ttf";
        FontsOverride.setDefaultFont(this, "DEFAULT", pokefont);
        FontsOverride.setDefaultFont(this, "MONOSPACE", pokefont);
        FontsOverride.setDefaultFont(this, "SERIF", pokefont);
        FontsOverride.setDefaultFont(this, "SANS_SERIF", pokefont);
        //SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        //SugarContext.terminate();
    }
}

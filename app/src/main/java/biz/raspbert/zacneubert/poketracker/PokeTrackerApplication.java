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
        //SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        //SugarContext.terminate();
    }
}

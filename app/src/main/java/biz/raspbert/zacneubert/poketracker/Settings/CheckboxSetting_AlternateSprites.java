package biz.raspbert.zacneubert.poketracker.Settings;

import android.content.Context;

import biz.raspbert.zacneubert.poketracker.MainActivity;
import biz.raspbert.zacneubert.poketracker.Music;

/**
 * Created by zneubert on 7/19/16.
 */
public class CheckboxSetting_AlternateSprites extends CheckboxSetting {
    @Override
    public String getTitleText() {
        return "";
    }

    @Override
    public String getSubtitleText() {
        return "Use alternate Sprites";
    }

    @Override
    public Boolean defaultBool() {
        return true;
    }

    @Override
    public void onChange(Context context, boolean ticked) {
        Music.playSelectSound(context);
        MainActivity.clearMarkers();
        MainActivity.addAllMarkers(context);
    }

    @Override
    public String getKey() {
        return "SETTING_ALTERNATE_SPRITES";
    }
}

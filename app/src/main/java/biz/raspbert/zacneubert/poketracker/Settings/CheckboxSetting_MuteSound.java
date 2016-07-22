package biz.raspbert.zacneubert.poketracker.Settings;

import android.content.Context;

/**
 * Created by zneubert on 7/19/16.
 */
public class CheckboxSetting_MuteSound extends CheckboxSetting {
    @Override
    public String getTitleText() {
        return "";
    }

    @Override
    public String getSubtitleText() {
        return "Enable Music";
    }

    @Override
    public void onChange(Context context, boolean ticked) {

    }

    @Override
    public String getKey() {
        return "SETTING_ENABLE_SOUND";
    }

    @Override
    public Boolean defaultBool() {
        return true;
    }

    public boolean soundMuted(Context context) {
        return ((boolean) getSavedValueOrDefault(context)) == false;
    }
}

package biz.raspbert.zacneubert.poketracker.Settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by zneubert on 7/6/16.
 */
public abstract class CheckboxSetting extends Setting {
    @Override
    public String getSavedString(Context c) {
        Boolean value = (Boolean) getSavedValueOrDefault(c);
        return value == null ? "false" : "true";
    }

    @Override
    public SettingType getSettingType() {
        return SettingType.bool;
    }

    @Override
    public View getView(Context c) {
        LayoutInflater inflater = LayoutInflater.from(c);
        Boolean isChecked = (Boolean) getSavedValueOrDefault(c);
        return new CheckboxSetting_View(inflater, c, getTitleText(), getSubtitleText(), isChecked, this).getView();
    }

    public abstract String getTitleText();
    public abstract String getSubtitleText();

    public abstract void onChange(Context context, boolean ticked);
}

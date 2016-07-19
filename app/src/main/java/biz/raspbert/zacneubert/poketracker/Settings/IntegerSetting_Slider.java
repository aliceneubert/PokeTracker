package biz.raspbert.zacneubert.poketracker.Settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by zneubert on 7/6/16.
 */
public abstract class IntegerSetting_Slider extends IntegerSetting {
    @Override
    public String getSavedString(Context c) {
        Object value = getSavedValueOrDefault(c);
        return value != null ? value.toString() : "";
    }

    public abstract int minValue();
    public abstract int maxValue();
    public abstract String titletext();

    public abstract void onChange(int value);

    @Override
    public View getView(Context c) {
        LayoutInflater inflater = LayoutInflater.from(c);
        return new IntegerSetting_Slider_View(inflater, c, this).getView();
    }
}

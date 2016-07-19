package biz.raspbert.zacneubert.poketracker.Settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by zneubert on 7/19/16.
 */
public abstract class ButtonSetting extends Setting {
    public abstract void doOnClick(View view);
    public abstract String buttonText();

    @Override
    public View getView(Context c) {
        LayoutInflater inflater = LayoutInflater.from(c);
        return new ButtonSetting_View(inflater, c, this).getView();
    }
}

package biz.raspbert.zacneubert.poketracker.Settings;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import biz.raspbert.zacneubert.poketracker.SharingFeature;

/**
 * Created by zneubert on 7/19/16.
 */
public class ButtonSetting_ExportPokemon extends ButtonSetting {
    private Activity activity;
    public void SetActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void doOnClick(View view) {
        SharingFeature.exportPokemon(activity);
    }

    @Override
    public String buttonText() {
        return "Deposit Pokemon";
    }

    @Override
    public String getSavedString(Context c) {
        return null;
    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public SettingType getSettingType() {
        return null;
    }
}

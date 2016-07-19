package biz.raspbert.zacneubert.poketracker.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;

/**
 * Created by zacneubert on 11/23/15.
 */
public abstract class Setting {
    public Object getSavedValueOrDefault(Context c) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        switch (getSettingType()) {
            case bool:
                return sharedPreferences.getBoolean(getKey(), defaultBool());
            case string:
                return sharedPreferences.getString(getKey(), defaultString());
            case integer:
                return sharedPreferences.getInt(getKey(), defaultInt());
            default:
                return null;
        }
    }
    public Integer defaultInt() {
        return 0;
    }
    public String defaultString() {
        return "";
    }
    public Boolean defaultBool() {
        return false;
    }

    public boolean setSavedValue(Context c, Object value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (getSettingType()) {
            case bool:
                Boolean bvalue = (Boolean) value;
                editor.putBoolean(getKey(), bvalue);
                break;
            case string:
                String svalue = (String) value;
                editor.putString(getKey(), svalue);
                break;
            case integer:
                int ivalue = (int) value;
                editor.putInt(getKey(), ivalue);
                break;
            default:
                return false;
        }
        editor.commit();
        return true;
    }

    public abstract String getSavedString(Context c);
    public boolean setSavedString(Context c, String value) {
        try {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(getKey(), value);
            editor.commit();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public abstract String getKey();
    public abstract View getView(Context c);
    public abstract SettingType getSettingType();

    public static enum SettingType {
        string,integer,bool
    }
}

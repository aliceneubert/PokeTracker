package biz.raspbert.zacneubert.poketracker.Settings;

/**
 * Created by zneubert on 7/6/16.
 */
public abstract class IntegerSetting extends Setting {
    @Override
    public SettingType getSettingType() {
        return SettingType.integer;
    }
}

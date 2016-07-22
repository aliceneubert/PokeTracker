package biz.raspbert.zacneubert.poketracker.Settings;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import biz.raspbert.zacneubert.poketracker.R;

/**
 * Created by zacneubert on 11/23/15.
 */
public class Settings_List_Activity extends AppCompatActivity {
    ListView Settings_List;
    List<Setting> Settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_list_layout);

        Settings = new ArrayList<>();

        ButtonSetting_ExportPokemon buttonSetting_exportPokemon = new ButtonSetting_ExportPokemon();
        buttonSetting_exportPokemon.SetActivity(this);
        Settings.add(buttonSetting_exportPokemon);

        ButtonSetting_ImportPokemon buttonSetting_importPokemon = new ButtonSetting_ImportPokemon();
        buttonSetting_importPokemon.SetActivity(this);
        Settings.add(buttonSetting_importPokemon);

        Settings.add(new CheckboxSetting_MuteSound());
        Settings.add(new CheckboxSetting_AlternateSprites());

        Settings_List = (ListView) this.findViewById(R.id.settingsList);
        Settings_List.setAdapter(new Settings_List_Adapter(this, R.layout.setting_list_row_dummy, Settings));
    }

    public class Settings_List_Adapter extends ArrayAdapter<Setting> {
        public List<Setting> adapter_settings;

        public Settings_List_Adapter(Context context, int resource, List<Setting> objects) {
            super(context, resource, objects);

            adapter_settings = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Setting s = adapter_settings.get(position);
            return s.getView(getContext());
        }
    }
}

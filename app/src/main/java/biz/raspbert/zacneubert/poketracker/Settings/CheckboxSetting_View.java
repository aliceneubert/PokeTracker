package biz.raspbert.zacneubert.poketracker.Settings;

import android.content.Context;
import android.graphics.Typeface;
import android.icu.text.DisplayContext;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import biz.raspbert.zacneubert.poketracker.R;

/**
 * Created by zneubert on 7/6/16.
 */
public class CheckboxSetting_View implements CompoundButton.OnCheckedChangeListener {
    private View rootView;
    private CheckBox checkbox;
    private CheckboxSetting checkboxSetting;

    Context c;

    public CheckboxSetting_View(LayoutInflater inflater, Context c, String titletext, String subtitletext, boolean isChecked, CheckboxSetting checkboxSetting) {
        rootView = inflater.inflate(R.layout.setting_checkbox, null, false);

        this.c = c;
        this.checkboxSetting = checkboxSetting;

        TextView titleTextView = (TextView) rootView.findViewById(R.id.checkboxTitleText);
        titleTextView.setText(titletext);
        titleTextView.setTypeface(Typeface.DEFAULT);
        TextView subtitleTextView = (TextView) rootView.findViewById(R.id.checkboxSubtitleText);
        subtitleTextView.setText(subtitletext);
        subtitleTextView.setTypeface(Typeface.DEFAULT);

        checkbox = (CheckBox) rootView.findViewById(R.id.checkboxDisableAds);
        checkbox.setTypeface(Typeface.DEFAULT);
        if(isChecked) {
            checkbox.setChecked(true);
        }
        checkbox.setOnCheckedChangeListener(this);
    }
    public View getView() {
        return rootView;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        buttonView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        checkboxSetting.setSavedValue(c, isChecked);
        checkboxSetting.onChange(c, isChecked);
    }
}

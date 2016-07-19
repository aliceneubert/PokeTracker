package biz.raspbert.zacneubert.poketracker.Settings;

import android.content.Context;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import biz.raspbert.zacneubert.poketracker.R;

/**
 * Created by zneubert on 7/6/16.
 */
public class IntegerSetting_Slider_View implements SeekBar.OnSeekBarChangeListener {
    View rootView;
    SeekBar seekBar;
    TextView titleView;
    IntegerSetting_Slider setting;
    Context c;

    public IntegerSetting_Slider_View(LayoutInflater inflater, Context c, IntegerSetting_Slider setting) {
        rootView = inflater.inflate(R.layout.setting_slider, null, false);
        this.setting = setting;

        titleView = (TextView) rootView.findViewById(R.id.setting_title);
        titleView.setText(setting.titletext());

        seekBar = (SeekBar) rootView.findViewById(R.id.setting_integer_seekbar);
        seekBar.setMax(setting.maxValue());

        seekBar.setProgress((Integer) setting.getSavedValueOrDefault(rootView.getContext()));

        seekBar.setOnSeekBarChangeListener(this);

        this.c = c;
    }

    public View getView() {
        return rootView;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        seekBar.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        setting.setSavedValue(rootView.getContext(), i);
        setting.onChange(i);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

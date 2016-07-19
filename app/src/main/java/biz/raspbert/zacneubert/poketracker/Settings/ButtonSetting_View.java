package biz.raspbert.zacneubert.poketracker.Settings;

import android.content.Context;
import android.graphics.Typeface;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import biz.raspbert.zacneubert.poketracker.R;

/**
 * Created by zneubert on 7/19/16.
 */
public class ButtonSetting_View implements View.OnClickListener{
    private View rootView;
    private Button button;
    private ButtonSetting buttonSetting;

    Context c;

    public ButtonSetting_View(LayoutInflater inflater, Context c, ButtonSetting buttonSetting) {
        rootView = inflater.inflate(R.layout.setting_button, null, false);

        this.c = c;
        this.buttonSetting = buttonSetting;

        button = (Button) rootView.findViewById(R.id.button_setting_button);
        button.setTypeface(Typeface.DEFAULT);
        button.setText(buttonSetting.buttonText());
        button.setOnClickListener(this);
    }
    public View getView() {
        return rootView;
    }

    @Override
    public void onClick(View view) {
        buttonSetting.doOnClick(view);
    }
}

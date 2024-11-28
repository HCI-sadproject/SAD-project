package com.example.hci.ui.survey;

import android.text.InputFilter;
import android.text.Spanned;

public class InputFilterMinMax implements InputFilter {
    private int min, max;

    public InputFilterMinMax(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end,
                             Spanned dest, int dstart, int dend) {
        try {
            String newVal = dest.toString().substring(0, dstart) + 
                           source.toString().substring(start, end) + 
                           dest.toString().substring(dend);
            int input = Integer.parseInt(newVal);
            if (input >= min && input <= max) return null;
        } catch (NumberFormatException ignored) { }
        return "";
    }
} 
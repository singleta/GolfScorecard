package com.example.golfscorecard.buttons;

import android.content.Context;

import com.example.golfscorecard.shots.PuttLength;

import androidx.appcompat.widget.AppCompatButton;


public class PuttButton extends AppCompatButton {

    PuttLength puttLength;

    public PuttButton(Context context, PuttLength puttLength) {
        super(context);
        this.puttLength = puttLength;
        this.setText(puttLength.getPuttName());
    }

    public PuttLength getPuttLength() {
        return puttLength;
    }
}

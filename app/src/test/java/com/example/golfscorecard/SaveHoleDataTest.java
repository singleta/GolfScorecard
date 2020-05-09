package com.example.golfscorecard;

import android.content.Context;
import android.view.View;
import com.example.golfscorecard.buttons.PuttButton;
import com.example.golfscorecard.ui.main.HoleScoreFragment;
import com.example.golfscorecard.ui.main.PuttLength;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class SaveHoleDataTest  {

    PuttButton puttButton;
    SaveHoleData saveHoleData;
    List<HoleScoreFragment> holePages;

    @Mock
    HoleScoreFragment holeScoreFragment;

    @Mock
    Context context;

    @BeforeEach
    private void setup() {
        MockitoAnnotations.initMocks(this.getClass());
        saveHoleData = new SaveHoleData(holePages);
//        holePages.add(holeScoreFragment);
    }

    @Test
    void testSavePuttButtonValue() {
        puttButton = new PuttButton(context, PuttLength.HUGE);
        puttButton.setText("2");

        saveHoleData.savePuttButtonValue(puttButton);
        List<PuttLength> puttDistances = saveHoleData.getPuttDistances();

        assertThat(puttDistances.size(), is(1));
    }


//    protected void savePuttButtonValue(View v) {
//        PuttButton puttButton = (PuttButton)v;
//        if (puttButton.getText().length()==1) {
//            puttDistances.add(puttButton.getPuttLength());
//        }
//    }
//
//    protected void saveFirstPuttDistance() {
//        Optional<PuttLength> puttLength = puttDistances.stream().max((putt1, putt2) -> putt1.getDistance().compareTo(putt2.getDistance()));
//        puttLength.ifPresent(this::accept);
//    }


}

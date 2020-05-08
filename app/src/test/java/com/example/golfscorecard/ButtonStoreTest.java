package com.example.golfscorecard;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.View;

import android.widget.ToggleButton;
import com.example.golfscorecard.ui.main.ButtonStore;

import com.example.golfscorecard.ui.main.ShotOutcome;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Xml.class})
class ButtonStoreTest {

    public ButtonStoreTest() {
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(Xml.class);
    }

    @Mock
    ToggleButton toggleButton;

    @Mock
    private View view;

    @Mock
    private Resources resources;

    @Mock
    XmlResourceParser parser;

    @Mock
    AttributeSet attributeSet;


    @Test
    public void setupDriveButtons() {

        mockStatic(Xml.class);
        ShotOutcome shotOutcome = ShotOutcome.LEFT;
        when(view.getResources()).thenReturn(resources);
        when(view.getResources().getXml(shotOutcome.getId())).thenReturn(parser);
        when(resources.getXml(shotOutcome.getId())).thenReturn(parser);
        when(Xml.asAttributeSet(parser)).thenReturn(attributeSet);

        ButtonStore buttonStore = new ButtonStore(view);

        buttonStore.setupDriveButtons();

        buttonStore.getDriveButtons();

    }
}

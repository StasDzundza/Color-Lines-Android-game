package com.example.colorlinesclassic;

import android.graphics.Color;

import org.junit.Assert;
import org.junit.Test;

public class SettingsTest {
    @Test
    public void defaultSettingsTest(){
        Settings s = new Settings();
        Assert.assertEquals(9,s.getColumns());
        Assert.assertEquals(9,s.getRows());
        Assert.assertEquals(0,s.getRecord());
        int[] ballColors = {Color.RED,Color.GREEN,Color.BLUE,Color.WHITE,Color.YELLOW,Color.BLACK,Color.MAGENTA};
        Assert.assertEquals(7,s.ballColors.length);
        Assert.assertArrayEquals(ballColors,s.ballColors);
    }
}

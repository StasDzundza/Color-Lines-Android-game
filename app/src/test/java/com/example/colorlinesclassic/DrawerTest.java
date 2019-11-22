package com.example.colorlinesclassic;

import android.graphics.Bitmap;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Bitmap.class)
public class DrawerTest {
    @Test
    public void gettingBitmapTest(){
        PowerMockito.mockStatic(Bitmap.class);
        PowerMockito.when(Bitmap.createBitmap(10,10,Bitmap.Config.ARGB_8888)).thenReturn(null);
        Bitmap btm = Drawer.getBallBitmap(1,10,10,4);
        PowerMockito.verifyStatic();
    }
}

package com.example.colorlinesclassic;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PathFinder.class)
public class GameTest {
    @Test
    public void cellParametrTest() {
        Cell cell = new Cell();
        Assert.assertTrue(cell.isEmpty());
    }

    @Test
    public void colorLinesDefauldParametrTest(){
        ColorLines colorLines = new ColorLines();
        Assert.assertTrue(colorLines.getNumberOfBallsAddingPerStep()>0);
        Assert.assertTrue(colorLines.getNumberOfCellsForDestroying()>0);
        Assert.assertFalse(colorLines.gameOver());
    }

    @Test
    public void addingBallsTest(){
        ColorLines colorLines = new ColorLines();
        colorLines.createBalls();
        int numberOfBalls = 0;
        int rows = colorLines.getNumberOfRowsInField();
        int columns = colorLines.getNumberOfColumnsInField();
        for (int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                Cell c = colorLines.getCell(i,j);
                if(!c.isEmpty())
                    numberOfBalls++;
            }
        }
        Assert.assertEquals(2*colorLines.getNumberOfBallsAddingPerStep(),numberOfBalls);
    }

    @Test
    public void replaceBallTest(){
        ColorLines colorLines = new ColorLines();
        Cell f = colorLines.getCell(0,0);
        f.makeEmpty(false);
        Cell d = colorLines.getCell(1,1);
        d.makeEmpty(true);
        PowerMockito.mockStatic(PathFinder.class);
        PowerMockito.when(PathFinder.findPath(0,0,1,1,colorLines.getField(),colorLines))
                .thenReturn(true);
        colorLines.replaceBall(0,0,1,1);
        PowerMockito.verifyStatic();
    }
}
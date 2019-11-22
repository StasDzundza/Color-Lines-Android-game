package com.example.colorlinesclassic;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.FieldSetter;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
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
        PathFinder pathFinder = Mockito.mock(PathFinder.class);
        ColorLines colorLines = new ColorLines();
        Cell f = colorLines.getCell(0,0);
        f.makeEmpty(false);
        Cell d = colorLines.getCell(1,1);
        d.makeEmpty(true);
        Mockito.when(pathFinder.findPath(0,0,1,1,colorLines.getField())).thenReturn(true);
        colorLines.setPathFinder(pathFinder);
        colorLines.replaceBall(0,0,1,1);
        Mockito.verify(pathFinder).findPath(0,0,1,1,colorLines.getField());
    }
}
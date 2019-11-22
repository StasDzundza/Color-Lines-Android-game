package com.example.colorlinesclassic;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

public class PathFinderTest {
    @Test
    public void checkDefaultSettings() throws NoSuchFieldException,IllegalAccessException{
        ColorLines colorLines = new ColorLines();
        PathFinder p = new PathFinder(colorLines);
        Field dxField = PathFinder.class.
                getDeclaredField("dx");
        dxField.setAccessible(true);
        int[] dx = (int[]) dxField.get(p);
        Assert.assertArrayEquals(new int[]{0,1,0,-1},dx);

        Field dyField = PathFinder.class.
                getDeclaredField("dy");
        dyField.setAccessible(true);
        int[] dy = (int[]) dyField.get(p);
        Assert.assertArrayEquals(new int[]{-1,0,1,0},dy);

        Field wallField = PathFinder.class.
                getDeclaredField("wall");
        wallField.setAccessible(true);
        int wall = (int) wallField.get(p);
        Assert.assertTrue(wall!=-1);
    }

    @Test
    public void pathFind(){
        ColorLines colorLines = new ColorLines(10,10);
        Cell f = colorLines.getCell(0,0);
        f.makeEmpty(false);
        Cell d = colorLines.getCell(1,1);
        d.makeEmpty(true);
        PathFinder p = new PathFinder(colorLines);
        Assert.assertTrue(p.findPath(0,0,1,1,colorLines.getField()));
    }
}

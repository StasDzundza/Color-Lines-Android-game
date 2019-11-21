package com.example.colorlinesclassic;

import android.graphics.Color;

public class Settings {
    public static final int[] ballColors = {Color.RED,Color.GREEN,Color.BLUE,Color.WHITE,Color.YELLOW,Color.BLACK,Color.MAGENTA};
    private int rows = 9;
    private int columns = 9;
    private int record = 0;

    public Settings(){
    }

    public int getRows(){
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getRecord() {
        return record;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setRecord(int record) {
        this.record = record;
    }
}

package com.example.colorlinesclassic;

import java.io.Serializable;

public class Cell implements Serializable {
    private int cellColor;
    private boolean isEmpty = true;

    public Cell() {
    }

    public Cell(final Cell otherCell) {
        this.cellColor = otherCell.cellColor;
        this.isEmpty = otherCell.isEmpty;
    }

    public void copyOtherCell(Cell otherCell) {
        this.cellColor = otherCell.cellColor;
        this.isEmpty = otherCell.isEmpty;
    }

    public void setCellColor(int color) {
        cellColor = color;
    }

    public int getCellColor() {
        return cellColor;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void makeEmpty(boolean empty) {
        isEmpty = empty;
    }

    public void clear(){
        cellColor = 0;
        isEmpty = true;
    }
}

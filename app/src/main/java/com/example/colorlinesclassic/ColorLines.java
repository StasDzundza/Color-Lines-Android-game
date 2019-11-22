package com.example.colorlinesclassic;

import java.io.Serializable;
import java.util.Random;

public class ColorLines implements Serializable {

    private int numberOfRows = 9;
    private int numberOfColumns = 9;
    private int numberOfCellsForDestroying = 5;
    private PathFinder pathFinder;
    private final int numberOfBallsAddingPerStep = 3;
    private int score = 0;
    private int numberOfEmptyCells;
    private boolean gameOver = false;
    private int[] nextColors;

    private Cell[][] field;

    private void createCells() {
        field = new Cell[numberOfRows][numberOfColumns];
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                field[i][j] = new Cell();
            }
        }
    }

    public ColorLines() {
        initializeGame(9,9);
    }

    public ColorLines(int numberOfRows, int numberOfColumns) {
        initializeGame(numberOfRows,numberOfColumns);
    }

    private void initializeGame(int numberOfRows, int numberOfColumns){
        pathFinder = new PathFinder(this);
        this.numberOfColumns = numberOfColumns;
        this.numberOfRows = numberOfRows;
        numberOfEmptyCells = numberOfColumns * numberOfRows;
        nextColors = new int[numberOfBallsAddingPerStep];
        generateNextColors();
        createCells();
        createBalls();
    }

    private void generateNextColors() {
        Random randomGenerator = new Random();
        for (int i = 0; i < numberOfBallsAddingPerStep; i++) {
            int colorIndex = randomGenerator.nextInt(Settings.ballColors.length);
            nextColors[i] = colorIndex;
        }
    }

    public int getNextColor(int index) {
        return nextColors[index];
    }

    public int getNumberOfRowsInField() {
        return numberOfRows;
    }

    public int getNumberOfColumnsInField() {
        return numberOfColumns;
    }

    public Cell getCell(int row, int column) {
        return field[row][column];
    }

    public void createBalls() {
        if (numberOfEmptyCells - numberOfBallsAddingPerStep < 0) {
            gameOver = true;
            return;
        }

        Random randomGenerator = new Random();
        for (int i = 0; i < numberOfBallsAddingPerStep; i++) {
            while (true) {
                int row = randomGenerator.nextInt(numberOfRows);
                int column = randomGenerator.nextInt(numberOfColumns);
                Cell c = getCell(row, column);
                if (c.isEmpty()) {
                    c.makeEmpty(false);
                    c.setCellColor(Settings.ballColors[nextColors[i]]);
                    destroyCells(row, column);
                    break;
                }
            }
        }
        numberOfEmptyCells -= numberOfBallsAddingPerStep;
        if (numberOfEmptyCells <= 0)
            gameOver = true;
        else
            generateNextColors();
    }

    public boolean replaceBall(int row1, int column1, int row2, int column2) {
        Cell start = getCell(row1, column1);
        Cell destination = getCell(row2, column2);
        if (!start.isEmpty()) {
            if (destination.isEmpty()) {
                boolean isPath = pathFinder.findPath(row1, column1, row2, column2, field);
                if (isPath) {
                    Cell tmp = new Cell(destination);
                    destination.copyOtherCell(start);
                    start.copyOtherCell(tmp);
                    boolean destroyed = destroyCells(row2, column2);
                    if (!destroyed) {
                        createBalls();
                        generateNextColors();
                    }
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean destroyCells(int rowFrom, int columnFrom) {
        Cell cellFrom = getCell(rowFrom, columnFrom);
        //check combination in a row
        int numberOfSameBallsInRow = 1;
        int left = 0, right = 0;
        for (int i = columnFrom + 1; i < numberOfColumns; i++) {//on the right from main cell
            Cell current = getCell(rowFrom, i);
            if (current.getCellColor() == cellFrom.getCellColor()) {
                numberOfSameBallsInRow++;
                right++;
            } else
                break;
        }

        for (int i = columnFrom - 1; i >= 0; i--) {//on the left from main cell
            Cell current = getCell(rowFrom, i);
            if (current.getCellColor() == cellFrom.getCellColor()) {
                numberOfSameBallsInRow++;
                left++;
            } else
                break;
        }

        if (numberOfSameBallsInRow >= numberOfCellsForDestroying) {
            cellFrom.clear();
            for (int i = columnFrom + 1; i < columnFrom + right + 1; i++) {//on the right from main cell
                Cell current = getCell(rowFrom, i);
                current.clear();
            }

            for (int i = columnFrom - 1; i > columnFrom - 1 - left; i--) {//on the left from main cell
                Cell current = getCell(rowFrom, i);
                current.clear();
            }
            score += numberOfSameBallsInRow;
            numberOfEmptyCells += numberOfSameBallsInRow;
            return true;
        }


        //check combination in a column
        int numberOfSameBallsInColumn = 1;
        int up = 0, down = 0;
        for (int i = rowFrom + 1; i < numberOfRows; i++) {//on the right from main cell
            Cell current = getCell(i, columnFrom);
            if (current.getCellColor() == cellFrom.getCellColor()) {
                numberOfSameBallsInColumn++;
                down++;
            } else
                break;
        }

        for (int i = rowFrom - 1; i >= 0; i--) {//on the left from main cell
            Cell current = getCell(i, columnFrom);
            if (current.getCellColor() == cellFrom.getCellColor()) {
                numberOfSameBallsInColumn++;
                up++;
            } else
                break;
        }

        if (numberOfSameBallsInColumn >= numberOfCellsForDestroying) {
            cellFrom.clear();
            for (int i = rowFrom + 1; i < rowFrom + down + 1; i++) {//on the right from main cell
                Cell current = getCell(i, columnFrom);
                current.clear();
            }

            for (int i = rowFrom - 1; i > rowFrom - 1 - up; i--) {//on the left from main cell
                Cell current = getCell(i, columnFrom);
                current.clear();
            }
            score += numberOfSameBallsInColumn;
            numberOfEmptyCells += numberOfSameBallsInColumn;
            return true;
        }

        return false;
    }

    public int getScore() {
        return score;
    }

    public boolean gameOver() {
        return gameOver;
    }

    public int getNumberOfBallsAddingPerStep() {
        return numberOfBallsAddingPerStep;
    }

    public int getNumberOfCellsForDestroying() {
        return numberOfCellsForDestroying;
    }

    public void setPathFinder(PathFinder pathFinder){
        this.pathFinder = pathFinder;
    }

    public Cell[][]getField(){
        return field;
    }
}

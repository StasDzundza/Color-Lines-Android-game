package com.example.colorlinesclassic;

import java.util.ArrayList;
import java.util.List;

public class PathFinder {
    private ColorLines colorLines;
    private List<Point> wave = new ArrayList<Point>();
    private final int wall = 9999;
    private final int[] dx = { 0, 1, 0, -1 };
    private final int[] dy = { -1, 0, 1, 0 };

    PathFinder(ColorLines colorLines){
        this.colorLines = colorLines;
    }

    public boolean findPath(int rowFrom,int columnFrom,int rowTo,int columnTo,Cell[][]field){
        int numberOfRowsInField = colorLines.getNumberOfRowsInField();
        int numberOfColumnsInField = colorLines.getNumberOfColumnsInField();
        int [][] additionalMatrix = initializeAdditionalMatrix(numberOfRowsInField,numberOfColumnsInField,field);
        int ny = rowFrom+1;
        int nx = columnFrom+1;
        List<Point> oldWave = new ArrayList<Point>();
        oldWave.add(new Point(ny, nx));
        int nstep = 0;
        additionalMatrix[ny][nx] = nstep;

        while (oldWave.size() > 0) {
            nstep++;
            wave.clear();
            for (Point i : oldWave) {
                for (int d = 0; d < 4; d++) {
                    ny = i.first + dy[d];
                    nx = i.second + dx[d];
                    if (additionalMatrix[ny][nx] == -1) {
                        if(ny == rowTo+1 && nx == columnTo+1){
                            wave.clear();
                            oldWave.clear();
                            return true;
                        }else{
                            wave.add(new Point(ny, nx));
                            additionalMatrix[ny][nx] = nstep;
                        }
                    }
                }
            }
            oldWave = new ArrayList<Point>(wave);
        }
        return false;
    }

    private int[][] initializeAdditionalMatrix(int numberOfRowsInField,int numberOfColumnsInField,Cell[][]field){
        int [][] additional_field = new int[numberOfRowsInField+2][numberOfColumnsInField+2];
        for(int i = 0; i < numberOfRowsInField; i++){
            for(int j = 0; j < numberOfColumnsInField; j++){
                if(field[i][j].isEmpty()){
                   additional_field[i+1][j+1] = -1;//empty
                }
                else{
                    additional_field[i+1][j+1] = wall;//wall
                }
            }
        }

        for(int i = 0; i < numberOfRowsInField+2;i++){
            additional_field[i][0] = wall;
            additional_field[i][numberOfColumnsInField+1] = wall;
        }

        for(int i = 0; i < numberOfColumnsInField+2;i++){
            additional_field[0][i] = wall;
            additional_field[numberOfRowsInField+1][i] = wall;
        }

        return additional_field;
    }

    private class Point {
        public Point(int first, int second) {
            this.first = first;
            this.second = second;
        }

        public int first;
        public int second;
    }
}

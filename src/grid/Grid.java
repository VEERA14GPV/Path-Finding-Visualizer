package grid;

import java.util.ArrayList;
import java.util.List;

public class Grid {

    private final int rows, cols;
    private final Cell[][] cells;

    public Grid(int rows, int cols, int size) {
        this.rows = rows;
        this.cols = cols;
        cells = new Cell[rows][cols];
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                cells[r][c] = new Cell(r, c, size);
    }

    public Cell[][] getAllCells() {
        return cells;
    }

    public List<Cell> getNeighbors(Cell cell, boolean diagonal) {
        int[][] dirs4 = {{1,0},{-1,0},{0,1},{0,-1}};
        int[][] dirs8 = {{1,0},{-1,0},{0,1},{0,-1},{1,1},{1,-1},{-1,1},{-1,-1}};

        int[][] dirs = diagonal ? dirs8 : dirs4;
        List<Cell> list = new ArrayList<>();

        for (int[] d : dirs) {
            int r = cell.row + d[0];
            int c = cell.col + d[1];
            if (r>=0 && c>=0 && r<rows && c<cols)
                list.add(cells[r][c]);
        }
        return list;
    }
}

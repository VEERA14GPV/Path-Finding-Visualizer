package grid;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell {

    public final int row, col;
    public boolean isWall = false;
    public boolean isVisited = false;
    public boolean isPath = false;

    public Cell parent;   // 🔴 REQUIRED (fixes your error)

    public Rectangle rect;

    public Cell(int row, int col, int size) {
        this.row = row;
        this.col = col;
        rect = new Rectangle(size, size);
        rect.setStroke(Color.LIGHTGRAY);
        rect.setFill(Color.WHITE);
    }

    public void setWall(boolean wall) {
        isWall = wall;
        rect.setFill(wall ? Color.BLACK : Color.WHITE);
    }

    public void setStart() {
        rect.setFill(Color.GREEN);
    }

    public void setEnd() {
        rect.setFill(Color.RED);
    }

    public void visit() {
        isVisited = true;
        rect.setFill(Color.BLUE);
    }

    public void setPath() {
        isPath = true;
        rect.setFill(Color.ORANGE);
    }

    public void clear() {
        isVisited = false;
        isPath = false;
        parent = null;
        if (!isWall) rect.setFill(Color.WHITE);
    }
}

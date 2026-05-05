package algorithms;

import grid.Cell;
import java.util.List;

public class PathResult {
    public List<Cell> visited;
    public List<Cell> path;

    public PathResult(List<Cell> visited, List<Cell> path) {
        this.visited = visited;
        this.path = path;
    }
}

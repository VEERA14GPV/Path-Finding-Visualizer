package algorithms;

import grid.Cell;
import grid.Grid;
import java.util.List;

public interface PathAlgorithm {
    List<Cell> findPath(Grid grid, Cell start, Cell end, boolean diagonal);

    default PathResult findPathWithVisited(Grid grid, Cell start, Cell end, boolean diagonal) {
        return new PathResult(findPath(grid, start, end, diagonal), List.of());
    }
}

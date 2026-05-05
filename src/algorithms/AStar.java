package algorithms;

import grid.Cell;
import grid.Grid;
import java.util.*;

public class AStar implements PathAlgorithm {

    @Override
    public List<Cell> findPath(Grid grid, Cell start, Cell end, boolean diagonal) {
        return findPathWithVisited(grid, start, end, diagonal).path;
    }

    @Override
    public PathResult findPathWithVisited(Grid grid, Cell start, Cell end, boolean diagonal) {
        Map<Cell, Integer> gCost = new HashMap<>();
        Map<Cell, Integer> fCost = new HashMap<>();
        Comparator<Cell> comparator = Comparator.comparingInt(fCost::get);
        PriorityQueue<Cell> openSet = new PriorityQueue<>(comparator);
        List<Cell> visited = new ArrayList<>();

        for (Cell[] row : grid.getAllCells())
            for (Cell c : row) {
                gCost.put(c, Integer.MAX_VALUE);
                fCost.put(c, Integer.MAX_VALUE);
                c.parent = null;
            }

        gCost.put(start, 0);
        fCost.put(start, heuristic(start, end));
        openSet.add(start);

        while (!openSet.isEmpty()) {
            Cell current = openSet.poll();
            visited.add(current);
            if (current == end) break;

            for (Cell neighbor : grid.getNeighbors(current, diagonal)) {
                if (neighbor.isWall) continue;
                int tentativeG = gCost.get(current) + 1;
                if (tentativeG < gCost.get(neighbor)) {
                    neighbor.parent = current;
                    gCost.put(neighbor, tentativeG);
                    fCost.put(neighbor, tentativeG + heuristic(neighbor, end));
                    openSet.add(neighbor);
                }
            }
        }

        List<Cell> path = new ArrayList<>();
        Cell curr = end;
        while (curr != null && curr != start) {
            if (curr.parent != null) path.add(curr);
            curr = curr.parent;
        }
        Collections.reverse(path);
        return new PathResult(visited, path);
    }

    private int heuristic(Cell a, Cell b) {
        return Math.abs(a.row - b.row) + Math.abs(a.col - b.col);
    }
}

package algorithms;

import grid.Cell;
import grid.Grid;

import java.util.*;

public class BFS implements PathAlgorithm {

    @Override
    public List<Cell> findPath(Grid grid, Cell start, Cell end, boolean diagonal) {
        return findPathWithVisited(grid, start, end, diagonal).path;
    }

    @Override
    public PathResult findPathWithVisited(Grid grid, Cell start, Cell end, boolean diagonal) {

        Queue<Cell> queue = new LinkedList<>();
        Set<Cell> seen = new HashSet<>();

        List<Cell> visited = new ArrayList<>();

        // Defensive reset (important if grid reused)
        for (Cell[] row : grid.getAllCells()) {
            for (Cell c : row) {
                c.parent = null;
            }
        }

        queue.add(start);
        seen.add(start);

        while (!queue.isEmpty()) {

            Cell current = queue.poll();
            visited.add(current);   // ALWAYS add → fixes animation bug

            if (current == end) {
                break;
            }

            for (Cell neighbor : grid.getNeighbors(current, diagonal)) {

                if (neighbor.isWall) continue;
                if (seen.contains(neighbor)) continue;

                neighbor.parent = current;
                seen.add(neighbor);
                queue.add(neighbor);
            }
        }

        // ---------- BUILD PATH ----------
        List<Cell> path = new ArrayList<>();

        Cell curr = end;
        while (curr != null && curr != start) {
            if (curr.parent != null) {
                path.add(curr);
            }
            curr = curr.parent;
        }

        Collections.reverse(path);

        return new PathResult(visited, path);
    }
}

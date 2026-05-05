package algorithms;

import grid.Cell;
import grid.Grid;

import java.util.*;

public class DFS implements PathAlgorithm {

    @Override
    public List<Cell> findPath(Grid grid, Cell start, Cell end, boolean diagonal) {
        return findPathWithVisited(grid, start, end, diagonal).path;
    }

    @Override
    public PathResult findPathWithVisited(Grid grid, Cell start, Cell end, boolean diagonal) {

        Stack<Cell> stack = new Stack<>();
        Set<Cell> seen = new HashSet<>();

        List<Cell> visited = new ArrayList<>();

        // Defensive reset (important when rerunning)
        for (Cell[] row : grid.getAllCells()) {
            for (Cell c : row) {
                c.parent = null;
            }
        }

        stack.push(start);
        seen.add(start);

        while (!stack.isEmpty()) {

            Cell current = stack.pop();
            visited.add(current);   // ALWAYS record visit for animation

            if (current == end) {
                break;
            }

            for (Cell neighbor : grid.getNeighbors(current, diagonal)) {

                if (neighbor.isWall) continue;
                if (seen.contains(neighbor)) continue;

                neighbor.parent = current;
                seen.add(neighbor);
                stack.push(neighbor);
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

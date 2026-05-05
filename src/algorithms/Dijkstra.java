package algorithms;

import grid.Cell;
import grid.Grid;
import java.util.*;

public class Dijkstra implements PathAlgorithm {

    @Override
    public List<Cell> findPath(Grid grid, Cell start, Cell end, boolean diagonal) {
        return findPathWithVisited(grid, start, end, diagonal).path;
    }

    @Override
    public PathResult findPathWithVisited(Grid grid, Cell start, Cell end, boolean diagonal) {
        Map<Cell, Integer> dist = new HashMap<>();
        List<Cell> visited = new ArrayList<>();
        PriorityQueue<Cell> pq = new PriorityQueue<>(Comparator.comparingInt(dist::get));

        for (Cell[] row : grid.getAllCells())
            for (Cell c : row) {
                dist.put(c, Integer.MAX_VALUE);
                c.parent = null;
            }

        dist.put(start, 0);
        pq.add(start);

        while (!pq.isEmpty()) {
            Cell current = pq.poll();
            visited.add(current);

            if (current == end) break;

            for (Cell neighbor : grid.getNeighbors(current, diagonal)) {
                if (neighbor.isWall) continue;
                int alt = dist.get(current) + 1;
                if (alt < dist.get(neighbor)) {
                    dist.put(neighbor, alt);
                    neighbor.parent = current;
                    pq.add(neighbor);
                }
            }
        }

        List<Cell> path = new ArrayList<>();
        Cell curr = end;
        while (curr != null && curr != start) {
            if (curr.parent != null)
                path.add(curr);
            curr = curr.parent;
        }
        Collections.reverse(path);
        return new PathResult(visited, path);
    }
}

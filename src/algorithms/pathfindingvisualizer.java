package algorithms;

import grid.Cell;
import grid.Grid;

import java.util.*;

public class pathfindingvisualizer implements PathAlgorithm {

    @Override

    
    public PathResult findPathWithVisited(Grid grid,
                                              Cell start,
                                              Cell end,
                                              boolean diagonal) {

        List<Cell> visited = new ArrayList<>();
        List<Cell> path = new ArrayList<>();

        Queue<Cell> queue = new LinkedList<>();
        Set<Cell> seen = new HashSet<>();

        queue.add(start);
        seen.add(start);

        while (!queue.isEmpty()) {
            Cell current = queue.poll();
            visited.add(current);

            if (current == end) break;

            for (Cell neighbor : grid.getNeighbors(current, diagonal)) {
                if (!neighbor.isWall && !seen.contains(neighbor)) {
                    neighbor.parent = current;
                    queue.add(neighbor);
                    seen.add(neighbor);
                }
            }
        }

        // build path
        Cell cur = end;
        while (cur != null) {
            path.add(cur);
            cur = cur.parent;
        }
        Collections.reverse(path);

        return new PathResult(visited, path);
    }

	@Override
	public List<Cell> findPath(Grid grid, Cell start, Cell end, boolean diagonal) {
		// TODO Auto-generated method stub
		return null;
	}
}

package lab11.graphs;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private MinPQ<node> _fringe;

    private class node {
        int vertex;
        int distance;
        node (int v, int d) {
            vertex = v;
            distance = d;
        }
    }

    static class nodeComparator implements Comparator<node> {
        @Override
        public int compare(node x, node y) {
            return x.distance - y.distance;
        }
    }

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        nodeComparator comp = new nodeComparator();
        _fringe = new MinPQ<>(comp);
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(v) - maze.toX(t)) + Math.abs(maze.toY(v) - maze.toY(t));
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        // TODO
        _fringe.insert(new node(s, h(s)));
        node current;

        while (!_fringe.isEmpty()) {
            current = _fringe.delMin();
            marked[current.vertex] = true;
            announce();
            if (current.vertex == t) {
                return;
            }

            for (int i : maze.adj(current.vertex)) {
                if (!marked[i]) {
                    distTo[i] = distTo[current.vertex] + 1;
                    edgeTo[i] = current.vertex;
                    _fringe.insert(new node(i, distTo[i] + h(i)));
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}


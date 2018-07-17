package lab11.graphs;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int _source;
    private int _target;
    Queue<Integer> _fringe;


    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        _source = maze.xyTo1D(sourceX, sourceY);
        _target = maze.xyTo1D(targetX, targetY);
        _fringe = new ArrayDeque<>();
        // Add more variables here!
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        int current;
        _fringe.add(_source);
        distTo[_source] = 0;
        edgeTo[_source] = _source;

        while(!_fringe.isEmpty()) {
            // pull an element from the queue and visit
            current = _fringe.poll();
            marked[current] = true;
            announce();
            if (current == _target) {
                break;
            }

            // enqueue all of current's neighbors, if it is unmarked
            for (int i : maze.adj(current)) {
                if (!marked[i]) {
                    edgeTo[i] = current;
                    distTo[i] = distTo[current] + 1;
                    _fringe.add(i);
                }
            }
        }

    }

    @Override
    public void solve() {
        bfs();
    }
}


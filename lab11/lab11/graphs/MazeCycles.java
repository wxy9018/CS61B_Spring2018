package lab11.graphs;

import java.util.LinkedList;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private LinkedList<Integer> _fringe;
    private int[] _edgeTo;

    public MazeCycles(Maze m) {
        super(m);
        _fringe = new LinkedList<>();
        _edgeTo = new int[maze.V()];
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        dfsCycle();

    }

    // Helper methods go here
    private void dfsCycle() {
        for (int i = 0; i < maze.V(); i++) {
            if (!marked[i]) { // perform DFS
                _fringe.addFirst(i);
                int current = i;
                // int parent = i;
                _edgeTo[i] = i;

                while (_fringe.size() > 0) {
                    current = _fringe.removeFirst();
                    marked[current] = true;

                    for (int j : maze.adj(current)) {

                        if (!marked[j]) {
                            _edgeTo[j] = current;
                            _fringe.addFirst(j);
                        } else if (j != _edgeTo[current]) { // found a loop
                            _edgeTo[j] = current;
                            draw(j);
                            return;
                        }
                    }
                }

            }
        }


    }

    private void draw(int v) {
        int current = _edgeTo[v];
        while (current != v) {
            edgeTo[current] = _edgeTo[current];
            current = _edgeTo[current];
        }
        edgeTo[v] = _edgeTo[v];
        announce();
    }
}


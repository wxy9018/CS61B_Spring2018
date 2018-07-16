package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.*;

public final class Solver {

    private int _moves;
    private ArrayDeque<WorldState> _solution;
    private MinPQ<SearchNode> _fringe;
    private HashSet<WorldState> _triedNodes; // used to store the tried WorldStates. The tried Worldstates will not be enqueued in PQ.

    private class SearchNode {
        WorldState __node;
        int __numSteps;
        SearchNode __parent;

        SearchNode(WorldState node, int numSteps, SearchNode parent) {
            __node = node;
            __numSteps = numSteps;
            __parent = parent;
        }
    }

    static class nodeComparator implements Comparator<SearchNode> {
        public int compare(SearchNode a, SearchNode b) {
            return (a.__numSteps + a.__node.estimatedDistanceToGoal()) - (b.__numSteps + b.__node.estimatedDistanceToGoal());
        }
    }

    /**
     * Constructor which solves the puzzle, computing
     * everything necessary for moves() and solution() to
     * not have to solve the problem again. Solves the
     * puzzle using the A* algorithm. Assumes a solution exists.
     */
    public Solver(WorldState initial) {
        nodeComparator comp = new nodeComparator();
        _fringe = new MinPQ<>(comp); // instantiate a priority queue
        _solution = new ArrayDeque<>();
        _triedNodes = new HashSet<>();

        SearchNode current = new SearchNode(initial, 0, null);
        _fringe.insert(current);
        _triedNodes.add(initial);

        while (!_fringe.isEmpty() && !current.__node.isGoal()) {
            current = _fringe.delMin(); // pop up the min element in the PQ
            // System.out.println(current.__node);

            for (WorldState i : current.__node.neighbors()) { // add all current's neighbors into the PQ
                //if (current.__parent== null || !i.equals(current.__parent.__node)) {
                if(!_triedNodes.contains(i)) { // this will ensure no node will be enqueued twice.
                        _fringe.insert(new SearchNode(i, current.__numSteps + 1, current));
                        _triedNodes.add(i);
                    //}
                }
            }
        }

        if (!current.__node.isGoal()) {
            throw new IllegalArgumentException("Problem not solvable");
        } else {
            _moves = current.__numSteps;
            while (current != null) {
                _solution.addFirst(current.__node);
                current = current.__parent;
            }
        }
    }

    /**
     * Returns the minimum number of moves to solve the puzzle starting
     * at the initial WorldState.
     */
    public int moves() {
        return _moves;
    }

    /**
     * Returns a sequence of WorldStates from the initial WorldState
     * to the solution.
     */
    public Iterable<WorldState> solution() {
        return _solution;
    }
}

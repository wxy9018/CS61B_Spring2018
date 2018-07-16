package hw4.puzzle;

import java.util.ArrayDeque;
import java.util.Queue;

public class Board implements WorldState {

    private int[][] _tiles;
    private int _N;
    private int[][] _goal;


    /**
     * Board(tiles): Constructs a board from an N-by-N array of tiles where
     *               tiles[i][j] = tile at row i, column j
     * @param tiles
     */
    public Board(int[][] tiles) {
        _N = tiles.length;
        _tiles = new int[_N][_N];
        _goal = new int[_N][_N];
        for (int i = 0; i < _N; i++) {
            for (int j = 0; j < _N; j++) {
                _tiles[i][j] = tiles[i][j];
                _goal[i][j] = 1 + j + i * _N;
            }
        }
        _goal[_N-1][_N-1] = 0;
    }

    /**
     * tileAt(i, j): Returns value of tile at row i, column j (or 0 if blank)
     * @param i
     * @param j
     * @return
     */
    public int tileAt(int i, int j) {
        if (i < 0 || j < 0 || i >= _N || j >= _N) {
            throw new IllegalArgumentException("Exceeds tile dimension");
        }
        return _tiles[i][j];
    }

    /**
     * size():       Returns the board size N
     * @return
     */
    public int size() {
        return _N;

    }

    /**
     * neighbors():  Returns the neighbors of the current board
     * @return
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbor = new ArrayDeque<>();
        int xDim = 0, yDim = 0;
        int[][] tiles = new int[_N][_N];

        for (int i = 0; i < _N; i++) {
            for (int j = 0; j < _N; j++) {
                tiles[i][j] = _tiles[i][j];
                if (_tiles[i][j] == 0) {
                    xDim = i;
                    yDim = j;
                }
            }
        }

        for (int i = 0; i < _N; i++) {
            for (int j = 0; j < _N; j++) {
                if (Math.abs(i - xDim) + Math.abs(j - yDim) == 1) {
                    tiles[i][j] = _tiles[xDim][yDim];
                    tiles[xDim][yDim] = _tiles[i][j];
                    neighbor.add(new Board(tiles));
                    // System.out.println(new Board(tiles));
                    tiles[xDim][yDim] = _tiles[xDim][yDim];
                    tiles[i][j] = _tiles[i][j];
                }
            }
        }
        return neighbor;
    }

    /**
     * hamming():    Hamming estimate described below
     * @return
     */
    public int hamming() {
        int distance = 0;
        for (int i = 0; i < _N; i++) {
            for (int j = 0; j < _N; j++) {
                if (_tiles[i][j] != _goal[i][j]) {
                    distance++;
                }
            }
        }
        return distance;
    }

    /**
     * manhattan():  Manhattan estimate described below
     * @return
     */
    public int manhattan() {
        int distance = 0;
        for (int i = 0; i < _N; i++) {
            for (int j = 0; j < _N; j++) {
                int value = _tiles[i][j];
                if (value != 0) {
                    int yDim = (value - 1) % _N;
                    int xDim = (value - 1) / _N;
                    distance = distance + Math.abs(xDim - i) + Math.abs(yDim - j);
                }
            }
        }
        return distance;
    }

    /**
     * estimatedDistanceToGoal(): Estimated distance to goal. This method should
     *               simply return the results of manhattan() when submitted to
     *               Gradescope.
     * @return
     */
    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    /**
     * equals(y):    Returns true if this board's tile values are the same
     *               position as y's
     * @param y
     * @return
     */
    @Override
    public boolean equals(Object y) {
        if (!(y instanceof Board) || ((Board) y).size() != _N) {
            return false;
        }
        for (int i = 0; i < _N; i++) {
            for (int j = 0; j < _N; j++) {
                if (((Board) y).tileAt(i,j) != _tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Returns the string representation of the board.
     * Uncomment this method. */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        /*
        s.append(manhattan());
        s.append("\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", _goal[i][j]));
            }
            s.append("\n");
        }*/
        s.append("\n");
        return s.toString();
    }

}

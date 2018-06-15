package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random();

    public static TETile[][] addHexagon(int sideLength, int[] Pos, int WIDTH, int HEIGHT) { // generates the TETile matrix which contains the hex
        // of given sidelength at the given position. Everything except the hex is nothing. (the large hex which contains 3 unit hex each side)
        // int[] Pos contains two elements, the first one is y position and the second one is x position, both starting from 0.

        // initialize world map with NOTHING tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // throws an exception if the map is not large enough
        try {
            // check the four margins around the world map
            int topMargin = Pos[0] + 1 - 5 * sideLength;
            int bottomMargin = HEIGHT - Pos[0] -1 - 5 * sideLength;
            int leftMargin = Pos[1] + 1 - (int) Math.floor(11.0 * sideLength / 2 - 3);
            int rightMargin = WIDTH - Pos[1] - 1 - (int) Math.ceil(11.0 * sideLength / 2 - 3);
            if (topMargin < 0 || bottomMargin < 0 || leftMargin <0 || rightMargin < 0) {
                throw new RuntimeException("World Map not large enough!");
            }

            // for each unit hex, calculate the position and replace the empty tiles on the world map
            for (int col = 0; col < 5; col++){
                for (int row = 0; row < 5 - Math.abs(col - 2); row++) {
                    int[] unitPos = posGetter(col, row, Pos, sideLength);
                    int tileType = RANDOM.nextInt(6);
                    unitHexagon(sideLength, unitPos, world, tileType);
                }
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return world;
    }
    public static void unitHexagon(int sideLength, int[] Pos, TETile[][] world, int tileType) { // replaces the original tile with a hex of given tileType
        // and of the given sideLength at the given position in the World map (one small unit hex)

        // get the correct tile type
        TETile tile;
        System.out.println(tileType);
        switch (tileType) {
            case 0: tile = Tileset.WALL; break;
            case 1: tile = Tileset.FLOWER;break;
            case 2: tile = Tileset.GRASS;break;
            case 3: tile = Tileset.MOUNTAIN;break;
            case 4: tile = Tileset.FLOOR;break;
            case 5: tile = Tileset.TREE;break;
            default: tile = Tileset.WATER;break;
        }
        // tile = Tileset.TREE;
        // visit every tile in the target hex region
        int leftIndent = sideLength / 2 + sideLength % 2;
        int rightIndent = sideLength / 2 ;
        int start = Pos[1] - leftIndent + 1;
        int end = Pos[1] + rightIndent;
        for (int col = Pos[0] - sideLength + 1; col < Pos[0] + 1; col++) {
            for (int row = start; row < end + 1; row++) {
                world[row][col] = tile;
            }
            start--;
            end++;
        }
        for (int col = Pos [0] + 1; col < Pos[0] + 1 + sideLength; col++) {
            start++;
            end--;
            for (int row = start; row < end + 1; row++) {
                world[row][col] = tile;
            }
        }
    }
    public static int[] posGetter(int col, int row, int[] centerPos, int sideLength) {
        // given the center position of the large hex, calculate the center position of the unit hex on the given col and row.
        int xPos = centerPos[1] + (col - 2) * (sideLength * 2 - 1);
        int yPos = centerPos[0] + (row - 2) * (sideLength * 2) + sideLength * Math.abs(col - 2);
        // System.out.println(xPos + " " + yPos);
        return new int[]{yPos, xPos};
    }
    public static void main(String[] args) {
        int WIDTH = 60;
        int HEIGHT = 60;
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        /*
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        unitHexagon(4, new int[]{47,53}, world, 1); */


        TETile[][] hexWorld = addHexagon(6, new int[]{29, 29},WIDTH, HEIGHT);
        ter.renderFrame(hexWorld);
    }
}

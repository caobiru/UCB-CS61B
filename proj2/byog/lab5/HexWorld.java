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
    private static final int WIDTH = 80;
    private static final int HEIGHT = 80;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    public static class Position {
        public int x;
        public int y;

        public Position (int xCoord, int yCoord) {
            x = xCoord;
            y = yCoord;
        }
    }

    /** calculate the number of hexagons in row i of a size size hexagon
     * i is the row number, i = 0 is the bottom row
     */
    public static int hexRowWidth(int size, int i) {
        int realI = i;
        if (i >= size) {
            realI = size * 2 - 1 - i;
        }
        return size + 2 * realI;
    }

    /** calculate the x coordinate of the leftmost tile in the ith row
     * i = 0 is the bottom row
    */
    public static int hexRowOffset(int size, int i) {
        int realI = i;
        if (i >= size) {
            realI = size * 2 - 1 - i;
        }
        return -realI;
    }

    /** adds a row of the same tile
     * @param p the left position of the row
     * @param width the row's width
     * @param t the tile to draw
     */
    public static void drawRow(TETile[][] world, Position p, int width, TETile t) {
        for (int xi = 0; xi < width; xi++) {
            int xCoord = p.x + xi;
            int yCoord = p.y;
            System.out.printf("%d %d %d", xi, xCoord, yCoord);
            System.out.println();
                world[xCoord][yCoord] = TETile.colorVariant(t, 32, 32, 32, RANDOM);
        }
    }

    /** adds a hexagon of side length size to a given position in the world, starting from the bottom left corner
     * @param p the bottom left tile's position
     * @param size the size of the hexagon
     * @param t the tile to draw
     */
    public static void addHexagon(TETile[][] world, Position p, int size, TETile t) {
            if (size < 2) {
                throw new IllegalArgumentException("Hexagon must be at least size 2");
            }

        for (int yi = 0; yi < 2 * size; yi++) {
            //calculate how many tiles are in the yith row
            int width = hexRowWidth(size, yi);

            //calculate the position of the first tile of the yith row
            int thisRowY = p.y + yi;
            int xRowStart = p.x + hexRowOffset(size, yi);
            Position rowStartP = new Position(xRowStart, thisRowY);

            // draw the yith row
            drawRow(world, rowStartP, width, t);
        }
    }

    /** returns the topRight neighbor's bottom left tile's position
     *
     */
    public static Position topRightNeighbor(Position p, int size) {
        int topRightPx = p.x + size * 2 - 1;
        int topRightPy = p.y + size;
        Position topRightP = new Position(topRightPx, topRightPy);
        return topRightP;
    }

    /** returns the topRight neighbor's bottom left tile's position
     *
     */
    public static Position bottomRightNeighbor(Position p, int size) {
        int bottomRightPx = p.x + size * 2 - 1;
        int bottomRightPy = p.y - size;
        Position bottomRightP = new Position(bottomRightPx, bottomRightPy);
        return bottomRightP;
    }

    /** draws a column of N hexes
     * N is the number of hexes
     * size is every hex's side length
     * p is the top hex's bottom left tile's position
     */
    public static void drawRandomVerticalHexes(TETile[][] world, int N, int size, Position p) {
        for (int i = 0; i < N; i++) {
            // calculate the position p of every hex
            int bottomPy = p.y - size * 2 * i;
            Position bottomP = new Position(p.x, bottomPy);

            addHexagon(world, bottomP, size, randomTile());
        }
    }

    /** Picks a RANDOM tile for each hex
     */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0: return Tileset.SAND;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.TREE;
            case 4: return Tileset.MOUNTAIN;
            default: return Tileset.WATER;
        }
    }

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] hexWorld = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                hexWorld[x][y] = Tileset.NOTHING;
            }
        }

        // the side length of every hex
        int size = 4;
        Position firstTop = new Position(20, 38);

        // draw five columns of hexagons
        drawRandomVerticalHexes(hexWorld, 3, size, firstTop);
        firstTop = topRightNeighbor(firstTop, size);

        drawRandomVerticalHexes(hexWorld, 4, size, firstTop);
        firstTop = topRightNeighbor(firstTop, size);

        drawRandomVerticalHexes(hexWorld, 5, size, firstTop);
        firstTop = bottomRightNeighbor(firstTop, size);

        drawRandomVerticalHexes(hexWorld, 4, size, firstTop);
        firstTop = bottomRightNeighbor(firstTop, size);

        drawRandomVerticalHexes(hexWorld, 3, size, firstTop);

        // render the window
        ter.renderFrame(hexWorld);
    }

}

package model.maze;

import java.awt.*;

public class Maze {
    private final int width, height;
    private final boolean[][] maze;

    public Maze(int width, int height) {
        this.width =  width ;
        this.height =  height;
        maze = new boolean[this.width][this.height];
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                maze[i][j] = false;
            }
        }
        for (int i = 1; i < width ; i+=2) {
            for (int j = 1; j < height ; j+=2) {
                maze[i][j] = true;
            }
        }
    }

    public void unsetWallBetweenPoints(Point a, Point b) {
        Point   inA = convertToInnerPoint(a),
                inB = convertToInnerPoint(b);
        maze[(inA.x + inB.x) / 2][(inA.y + inB.y) / 2] = true;
    }

    public Point convertToInnerPoint(Point p) {
        return new Point(2 * p.x + 1, 2 * p.y + 1);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean getValue(int x, int y) {
        return maze[x][y];
    }

}

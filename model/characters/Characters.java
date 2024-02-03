package model.characters;

import model.Direction;
import model.maze.Maze;

import java.awt.*;

public class Characters {
    final Maze maze;
    final int width, height;
    Point position;

    public Characters(Maze maze) {
        this.maze = maze;
        width = this.maze.getWidth();
        height = this.maze.getHeight();
    }

    public boolean move(Direction dir) {
        Point moved = new Point(dir.x + position.x, dir.y + position.y);
        if(maze.getValue(moved.x, moved.y)) {
            position = moved;
            return true;
        }
        return false;
    }

    public boolean isClose(Characters c, int radius) {
        return ChebyshevDistance(c.position.x,c.position.y)
                <= radius * radius;
    }

    public int ChebyshevDistance(int x, int y) {
        return Math.max(Math.abs(position.x-x), Math.abs(position.y-y));
    }

    public Point getPosition() {
        return position;
    }
}

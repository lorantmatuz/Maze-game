package model.characters;

import model.maze.Maze;

import java.awt.*;

public class Aim extends Characters{

    public Aim(Maze maze) {
        super(maze);
        position = new Point(1, height-2);
    }
}

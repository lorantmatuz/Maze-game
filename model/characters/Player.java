package model.characters;

import model.Direction;
import model.maze.Maze;

import java.awt.*;

public class Player extends Characters {
    private boolean isAlive;
    private final boolean[][] stepTable;
    private int numOfSteps;
    private final int lookDistance;
    private final Aim aim;
    private final Dragon[] dragons;

    public Player(Maze maze, Dragon[] dragons, int distance) {
        super(maze);
        isAlive = true;
        numOfSteps = 0;
        lookDistance = distance;
        stepTable = new boolean[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                stepTable[i][j] = false;
            }
        }
        aim = new Aim(maze);
        position = new Point(width-2,1);
        this.dragons = dragons;
    }

    @Override
    public boolean move(Direction dir) {
        if(super.move(dir)) {
            if(!stepTable[position.x][position.y]) {
                stepTable[position.x][position.y] = true;
                ++numOfSteps;
            }
            return true;
        }
        return false;
    }

    public boolean isOutOfSight(int radius) {
        for(var dragon : dragons) {
            if(super.isClose(dragon,radius)) {
                isAlive = false;
                return false;
            }
        }
        return true;
    }

    public boolean isWinner() {
        return (position.x == aim.getPosition().x && position.y == aim.getPosition().y);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int getNumOfSteps() {
        return numOfSteps;
    }

    public int getLookDistance() {
        return lookDistance;
    }
}

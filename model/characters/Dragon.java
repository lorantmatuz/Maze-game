package model.characters;

import model.Direction;
import model.maze.Maze;

import java.awt.*;
import java.util.*;

public class Dragon extends Characters {

    Direction direction;

    public Dragon(Maze maze) {
        super(maze);
        position = new Point(-1,-1);
        direction = null;
    }

    public void init(Player p)
    {
        Random random = new Random();
        do {
            position = new Point(random.nextInt(width),random.nextInt(height));
        } while(isClose(p,3) || !maze.getValue(position.x,position.y));
        direction = findRandomDirection();
    }

    public Direction findRandomDirection() {
        ArrayList<Direction> list = new ArrayList<>();
        for(var dir : Direction.values()) {
            if(maze.getValue(position.x + dir.x,position.y + dir.y)) {
                list.add(dir);
            }
        }
        return list.isEmpty() ? null : list.get(new Random().nextInt(list.size()));
    }

    public void move() {
        if(!move(direction)) {
            direction = findRandomDirection();
            if(direction != null) {
                move(direction);
            }
        }
    }

    public Direction getDirection() {
        return direction;
    }
}

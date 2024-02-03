package model;

import model.characters.*;
import model.maze.Maze;

import java.awt.*;

public class Game {
    private final Maze maze;
    private final Levels level;
    private final Player player;
    private final Dragon[] dragons;
    private final Characters aim;
    private final CharactersEnum[][] characters;

    public Game(Maze maze, Levels level) {
        this.maze = maze;
        int width = maze.getWidth();
        int height = maze.getHeight();
        this.level = level;

        characters = new CharactersEnum[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                characters[i][j] = (maze.getValue(i,j)) ?
                    CharactersEnum.NULL : CharactersEnum.WALL;
            }
        }

        dragons = new Dragon[level.numOfDragons];
        for(int i = 0; i < dragons.length; ++i) {
            dragons[i] = new Dragon(maze);
        }
        player = new Player(maze, dragons, level.lookDistance);
        Point p = player.getPosition();
        characters[p.x][p.y] = CharactersEnum.PLAYER;
        for (Dragon dragon : dragons) {
            dragon.init(player);
            p = dragon.getPosition();
            characters[p.x][p.y] = CharactersEnum.DRAGON;
        }
        aim = new Aim(maze);
        p = aim.getPosition();
        characters[p.x][p.y] = CharactersEnum.AIM;
    }


    public Maze getMaze() {
        return maze;
    }

    public Levels getLevel() {
        return level;
    }

    public Player getPlayer() {
        return player;
    }

    public Dragon[] getDragons() {
        return dragons;
    }

    public Characters getAim() {
        return aim;
    }

    public CharactersEnum getCharacter(int x, int y) {
        return characters[x][y];
    }
}

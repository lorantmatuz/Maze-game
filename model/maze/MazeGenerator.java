package model.maze;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


public class MazeGenerator {

    private final int width, height;
    private final double density;
    private final Point start;
    private final Boolean[][] visited;
    private final Maze maze;
    private final Random random;

    public MazeGenerator(int w, int h, double density) {

        width =  (w % 2 == 0) ? w / 2 : (w - 1) / 2;
        height = (h % 2 == 0) ? h / 2 : (h - 1) / 2;

        this.density = density;
        start = new Point(0,height-1);
        visited = new Boolean[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                visited[i][j] = false;
            }
        }
        maze = new Maze(width*2+1, height*2+1);
        random = new Random();
    }

    public void run() {
        Stack<Point> stack = new Stack<>();
        stack.push(start);
        visited[start.x][start.y] = true;
        while (!stack.isEmpty()) {
            Point p = stack.pop();
            final var neighbors = getVertices(p);
            if(!neighbors.isEmpty()) {
                stack.push(p);
                Point n = neighbors.get(random.nextInt(neighbors.size()));
                if(random.nextDouble() < density) {
                    visited[n.x][n.y] = true;
                }
                maze.unsetWallBetweenPoints(p,n);
                stack.push(n);
            }
        }
    }

    private ArrayList<Point> getVertices(Point node)
    {
        ArrayList<Point> p = new ArrayList<>();
        if(node.x - 1 >= 0 && !visited[node.x - 1][node.y])
            p.add(new Point(node.x - 1,node.y));
        if(node.x + 1 < width && !visited[node.x + 1][node.y])
            p.add((new Point(node.x + 1,node.y)));
        if(node.y - 1 >= 0 && !visited[node.x][node.y - 1])
            p.add((new Point(node.x, node.y - 1)));
        if(node.y + 1 < height && !visited[node.x][node.y + 1])
            p.add((new Point(node.x, node.y + 1)));
        return p;
    }

    public Maze getMaze() {
        return maze;
    }

}
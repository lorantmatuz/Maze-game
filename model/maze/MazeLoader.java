package model.maze;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MazeLoader {

    public static void writeMaze(Maze maze, String filename) {
        try {
            File file = new File(filename);
            FileWriter writer = new FileWriter(file);
            BufferedWriter buffer = new BufferedWriter(writer);

            int h = maze.getWidth(), w = maze.getHeight();

            buffer.write(h + " " + w + "\n");

            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    buffer.write( (maze.getValue(i,j) ? 1 : 0) + " ");
                }
                buffer.write("\n");
            }

            buffer.close();
            writer.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        MazeGenerator generator = new MazeGenerator(19, 5, .7);
        generator.run();
        Maze maze = generator.getMaze();

        MazeLoader.writeMaze(maze, "./src/files/easy/file.txt");
    }
}


package view;

import model.maze.Maze;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import javax.swing.JPanel;


public class Board extends JPanel {

    private final Maze maze;
    //private final Image wall;
    private double scale;
    private int scaled_size;
    private final int tile_size = 32;

    public Board(Maze maze) throws IOException {
        this.maze = maze;
        scale = 1.0;
        scaled_size = (int)(scale * tile_size);
        //wall = ImageLoader.loadImage("resources/bush.png");
    }

    public void setScale(double scale){
        this.scale = scale;
        scaled_size = (int)(scale * tile_size);
        refresh();
    }

    public void refresh(){
        Dimension dim = new Dimension(maze.getHeight() * scaled_size, maze.getWidth() * scaled_size);
        setPreferredSize(dim);
        setMaximumSize(dim);
        setSize(dim);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D gr = (Graphics2D)g;
        final int w = maze.getWidth(),
                  h = maze.getHeight();
        //Position p = game.getPlayerPos();
        for (int y = 0; y < h; y++){
            for (int x = 0; x < w; x++){
                Image img = null;
                /*
                LevelItem li = game.getItem(y, x);
                switch (li){
                    case BOX: img = box; break;
                    case BOX_IN_PLACE: img = box_in_place; break;
                    case DESTINATION: img = destination; break;
                    case WALL: img = wall; break;
                    case EMPTY: img = empty; break;
                }
                if (p.x == x && p.y == y) img = player;
                if (img == null) continue;
                 */
                if(!maze.getValue(x,y)) {
                    //img = wall;
                    gr.drawImage(img, x * scaled_size, y * scaled_size, scaled_size, scaled_size, null);
                }
            }
        }
    }

}

package resources;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ImageLoader {

    final int width, height;
    final Image image;
    final ImageIcon imageIcon;

    public ImageLoader(String filename, int mazeSize) throws IOException {
        URL url = ImageLoader.class.getClassLoader().getResource(filename);
        assert url != null;
        image = ImageIO.read(url);
        final var screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int size = Math.min(screenSize.width, screenSize.height) ;
        width =  size / mazeSize;
        height = size / mazeSize;
        imageIcon = new ImageIcon(image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));
    }


    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

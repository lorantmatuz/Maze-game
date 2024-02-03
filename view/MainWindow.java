package view;

import model.Game;
import model.Levels;
import model.maze.Maze;
import model.maze.MazeGenerator;
import model.maze.MazeLoader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;

public class MainWindow extends JFrame  {

    private final List<Window> gameWindows = new ArrayList<>();

    public MainWindow() {
        setTitle("Maze Game");

        var url = Window.class.getClassLoader().getResource("resources/Dragon/Left.png");
        setIconImage(Toolkit.getDefaultToolkit().getImage(url));

        //setSize(1000, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        JPanel inputPanel = new JPanel(new GridBagLayout());

        JButton easy = new JButton("Easy");
        JButton medium = new JButton("Medium");
        JButton hard = new JButton("Hard");
        JButton impossible = new JButton("Impossible");

        easy.addActionListener      (e -> { startWindow(Levels.EASY);});
        medium.addActionListener    (e -> { startWindow(Levels.MEDIUM);});
        hard.addActionListener      (e -> { startWindow(Levels.HARD);});
        impossible.addActionListener(e -> { startWindow(Levels.IMPOSSIBLE);});

        inputPanel.add(easy);
        inputPanel.add(medium);
        inputPanel.add(hard);
        inputPanel.add(impossible);

        getContentPane().add(inputPanel);

        setPreferredSize(new Dimension(400, 150));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startWindow(Levels level) {
        MazeGenerator generator = new MazeGenerator(level.width,level.height,level.density);
        generator.run();
        Maze maze = generator.getMaze();
        Game game = new Game(maze, level);
        try {
            Window window = new Window(this, game);
            window.setVisible(true);
            gameWindows.add(window);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Window> getWindowList() {
        return gameWindows;
    }

}

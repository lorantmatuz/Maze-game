package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.time.LocalTime;
import javax.swing.*;

import model.*;
import model.characters.Player;
import resources.ImageLoader;


public class Window extends JFrame{

    private final MainWindow mainWindow;
    private final Game game;

    private final JLabel[][] labels;
    private final ImageIcon[][] icons;

    private final JLabel clockLabel;
    private int elapsedTimeInSeconds;

    private final ImageLoader wallImage, aimImage;
    private final ImageLoader[] playerImage, dragonImage;

    private final Timer timer;

    public Window(MainWindow mainWindow, Game game) throws IOException{
        this.mainWindow = mainWindow;
        this.game = game;

        int width = 2 * game.getPlayer().getLookDistance() + 1;
        int height = 2 * game.getPlayer().getLookDistance() + 1;

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(width, height));

        labels = new JLabel[width][height];
        icons = new ImageIcon[this.game.getMaze().getWidth()][this.game.getMaze().getHeight()];

        setTitle("Maze");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int size = Math.min(screenSize.width, screenSize.height) ;
        int mazeSize = 10;

        setSize(size, size);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        JPanel topPanel = new JPanel(new BorderLayout());

        // clock
        elapsedTimeInSeconds = 0;
        clockLabel = new JLabel("Time: " + LocalTime.ofSecondOfDay(elapsedTimeInSeconds).toString());
        clockLabel.setPreferredSize(new Dimension(100, 30));
        timer = new Timer(1000, e -> {
            elapsedTimeInSeconds++;
            clockLabel.setText("Time: " + LocalTime.ofSecondOfDay(elapsedTimeInSeconds).toString());
            if(!refreshDragon()) {
                newGame(false);
            }
        });
        timer.start();

        //
        JLabel stepsLabel = new JLabel();
        stepsLabel.setText("Steps: 0");
        stepsLabel.setPreferredSize(new Dimension(80, 30));
        //

        // Add the JLabels to the top of the panel
        topPanel.add(clockLabel, BorderLayout.WEST);
        topPanel.add(stepsLabel, BorderLayout.EAST);
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(Color.BLACK);
        topPanel.add(separator, BorderLayout.SOUTH);

        wallImage = new ImageLoader("resources/bush.jpg", mazeSize);
        aimImage = new ImageLoader("resources/aim.png", mazeSize);

        playerImage = new ImageLoader[4];
        dragonImage = new ImageLoader[4];
        for (int i = 0; i < 4; i++) {
            playerImage[i] = new ImageLoader("resources/Player/" +
                    Direction.intToDirection(i) + ".png", mazeSize );
            dragonImage[i] = new ImageLoader("resources/Dragon/" +
                    Direction.intToDirection(i) + ".png", mazeSize );
        }

        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                labels[i][j] = new JLabel();
                labels[i][j].setPreferredSize(new Dimension(wallImage.getWidth(), wallImage.getHeight()));
                mainPanel.add(labels[i][j]);
            }
        }

        for (int i = 0; i < this.game.getMaze().getWidth(); i++) {
            for (int j = 0; j < this.game.getMaze().getHeight(); j++) {
                switch (this.game.getCharacter(i,j)) {
                    case PLAYER -> icons[i][j] = playerImage[0].getImageIcon();
                    case DRAGON -> icons[i][j] = dragonImage[0].getImageIcon();
                    case WALL -> icons[i][j] = wallImage.getImageIcon();
                    case AIM -> icons[i][j] = aimImage.getImageIcon();
                    default -> icons[i][j] = null;
                }
            }
        }

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(new JSeparator(SwingConstants.HORIZONTAL));
        getContentPane().add(mainPanel);

        var url = Window.class.getClassLoader().getResource("resources/Dragon/Left.png");
        setIconImage(Toolkit.getDefaultToolkit().getImage(url));

        JMenuBar menuBar = new JMenuBar();
        JMenu menuGame = new JMenu("Game");
        //JMenu menuGameLevel = new JMenu("Pálya");
        //JMenu menuGameScale = new JMenu("Nagyítás");
        //createGameLevelMenuItems(menuGameLevel);
        //createScaleMenuItems(menuGameScale, 1.0, 2.0, 0.5);
        /*
        JMenuItem menuHighScores = new JMenuItem(new AbstractAction("Dicsőség tábla") {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HighScoreWindow(game.getHighScores(), MainWindow.this);
            }
        });
         */



        JMenuItem menuGameExit = new JMenuItem(new AbstractAction("Quit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });

        JMenuItem menuGameRestart = new JMenuItem(new AbstractAction("Restart") {
            @Override
            public void actionPerformed(ActionEvent e) {
                restart();
            }
        });

        //menuGame.add(menuGameLevel);
        //menuGame.add(menuGameScale);
        //menuGame.add(menuHighScores);

        menuGame.add(menuGameRestart);
        menuGame.addSeparator();
        menuGame.add(menuGameExit);
        menuBar.add(menuGame);

        setJMenuBar(menuBar);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                super.keyPressed(ke);

                int kk = ke.getKeyCode();
                Direction d = null;
                switch (kk) {
                    case KeyEvent.VK_LEFT   -> d = Direction.LEFT;
                    case KeyEvent.VK_RIGHT  -> d = Direction.RIGHT;
                    case KeyEvent.VK_UP     -> d = Direction.UP;
                    case KeyEvent.VK_DOWN   -> d = Direction.DOWN;
                    case KeyEvent.VK_ESCAPE -> quit();
                }

                if(!refreshPlayer(d)) {
                    newGame(false);
                } else if(game.getPlayer().isWinner()) {
                    newGame(true);
                } else {
                    stepsLabel.setText("Steps: " + game.getPlayer().getNumOfSteps());
                }
                //refreshGameStatLabel();
                //board.repaint();
                /*
                if (d != null && game.step(d)){
                    if (game.isGameEnded()){
                        String msg = "Gratulálok!";
                        if (game.isBetterHighScore()){
                            msg += " Megjavítottad az eddigi teljesítményedet!";
                        }
                        JOptionPane.showMessageDialog(MainWindow.this, msg, "Játék vége", JOptionPane.INFORMATION_MESSAGE);
                    }
                }

                 */
            }
        });

        setResizable(false);
        setLocationRelativeTo(null);
        //game.loadGame(new GameID("EASY", 1));
        //board.setScale(2.0); // board.refresh();
        pack();
        refreshGameStatLabel();
        setVisible(true);


        refreshScreen();
    }

    private boolean restart() {
        int n = JOptionPane.showConfirmDialog(this, "Do you want to restart?",
                "Confirmation", JOptionPane.YES_NO_OPTION);
        timer.stop();
        if (n == JOptionPane.YES_OPTION){
            Window newWindow ;
            try {
                newWindow = new Window(mainWindow, new Game(game.getMaze(), game.getLevel()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            newWindow.setVisible(true);
            this.dispose();
            mainWindow.getWindowList().remove(this);
            mainWindow.getWindowList().add(newWindow);
            return true;
        }
        return false;
    }

    private void quit() {
        timer.stop();
        this.dispose();
        mainWindow.getWindowList().remove(this);
    }

    private void newGame(boolean win) {
        timer.stop();
        if(win) {
            JOptionPane.showMessageDialog(Window.this, "You Won!",
                    "Message", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(Window.this, "Game Over!",
                    "Message", JOptionPane.INFORMATION_MESSAGE);
        }
        if(!restart())
            quit();
    }

    private boolean refreshPlayer(Direction d) {
        if(game.getPlayer().isAlive()) {
            Point prevPos = game.getPlayer().getPosition();
            if(d != null && game.getPlayer().move(d)) {
                icons[prevPos.x][prevPos.y] = null;
                Point position = game.getPlayer().getPosition();
                var image = playerImage[Direction.directionToInt(d)];
                icons[position.x][position.y] = image.getImageIcon();
                refreshScreen();
            }
            return game.getPlayer().isOutOfSight(1);
        }
        return true;
    }

    private boolean refreshDragon() {
        if(game.getPlayer().isAlive()) {
            for(var dragon : game.getDragons()) {
                if(game.getPlayer().isClose(dragon, 3)) {
                    Point prevPos = dragon.getPosition();
                    Point aimPos = game.getAim().getPosition();
                    dragon.move();
                    Direction d = dragon.getDirection();
                    if (d != null) {
                        icons[prevPos.x][prevPos.y] = (prevPos.equals(aimPos)) ?
                                aimImage.getImageIcon() : null;
                        Point position = dragon.getPosition();
                        var image = dragonImage[Direction.directionToInt(d)];
                        icons[position.x][position.y] = image.getImageIcon();
                        refreshScreen();
                    }
                }
            }
            return game.getPlayer().isOutOfSight(1);
        }
        return true;
    }

    private void refreshScreen() {
        Player player = game.getPlayer();
        Point pos = player.getPosition();

        final int   lookDist = player.getLookDistance(),
                    width = game.getMaze().getWidth(),
                    height = game.getMaze().getHeight(),
                    p_size = player.getLookDistance() * 2 + 1;

        for (int i = pos.x - lookDist, p_x = 0 ; p_x < p_size; i++) {
            for (int j = pos.y - lookDist, p_y = 0; p_y < p_size; j++) {
                if(i < 0 || i >= width || j < 0 || j >= height) {
                    labels[p_x][p_y].setIcon(null);
                } else {
                    labels[p_x][p_y].setIcon(icons[i][j]);
                }
                p_y++;
            }
            p_x++;
        }
    }

    private void refreshGameStatLabel(){
        /*
        String s = "Lépések száma: " + game.getNumSteps();
        s += ", dobozok a helyükön: " + game.getLevelNumBoxesInPlace() + "/" + game.getLevelNumBoxes();
        gameStatLabel.setText(s);
         */
    }


    public static void main(String[] args) {
        new MainWindow();
    }


}


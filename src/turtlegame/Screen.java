package turtlegame;

import turtlegame.games.Game;
import turtlegame.games.GameFactory;
import turtlegame.games.None;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Screen implements CommandListener {
    private JPanel screenPanel;
    private JButton goButton;
    private JButton stopButton;
    private JButton clearButton;
    private JTextArea readyTextArea;
    private JPanel renderPanel;
    private JLabel statusLabel;

    private CommandParser parser;
    private GameFactory gameFactory;

    private Game currentGame = new None();

    private File currentFile;
    private long sleepDelay = 1000L;

    public static void main(String[] args) {
        final Screen screen = new Screen();
        screen.init();
        final JFrame frame = createFrame("Turtle Game", screen.getScreenPanel(), 640, 480);

        screen.initMenus(frame);

        frame.setVisible(true);

    }

    public void init() {
        readyTextArea.setCaretColor(Color.YELLOW);

        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readyTextArea.requestFocusInWindow();
                go();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readyTextArea.requestFocusInWindow();
                stop();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readyTextArea.setText("");
                readyTextArea.requestFocusInWindow();
                parser.reparse();
                getRenderPanel().clear();
            }
        });

        parser = new CommandParser(readyTextArea);
        parser.addListener(this);

        gameFactory = new GameFactory();

        statusLabel.setBorder(new EmptyBorder(0,20,0,0));
        statusLabel.setText("Ready.");
    }

    private boolean stopRequested = false;

    public void go() {
        new Thread() {
            public void run() {
                statusLabel.setText("Running..");

                getRenderPanel().clear();

                try {
                    RenderPanel p = getRenderPanel();
                    p.clear();

                    setEventSuppression(true);
                    parser.reparse();
                    setEventSuppression(false);

                    java.util.List<Command> commands = parser.getCommands();
                    int count = 0;
                    for( Command command : commands ) {
                        statusLabel.setText("Running.. ("+count+"/"+commands.size()+")");
                        sleep(sleepDelay);
                        if( stopRequested ) {
                            stopRequested = false;
                            statusLabel.setText("Ready.");
                            return;
                        }
                        p.executeCommand(command);
                        count++;
                    }
                    statusLabel.setText("Running.. ("+count+"/"+commands.size()+")");
                    sleep(1000);
                } catch (InterruptedException e) {
                }
                stopRequested = false;
                statusLabel.setText("Ready.");
            }
        }.start();


    }

    public void stop() {
        stopRequested = true;
    }

    public JPanel getScreenPanel() {
        return screenPanel;
    }

    public RenderPanel getRenderPanel() {
        return (RenderPanel) renderPanel;
    }

    public static JFrame createFrame(String title, Component component, int w, int h) {
        JFrame f = new JFrame(title);

        JMenuBar menuBar = new JMenuBar();
        f.setJMenuBar(menuBar);

        f.getContentPane().add(component);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(w, h);

        component.requestFocusInWindow();

        return f;
    }

    private void createUIComponents() {
        renderPanel = new RenderPanel(this);
    }

    private boolean suppressEvents = false;
    private void setEventSuppression(boolean suppress) {
        suppressEvents = suppress;
    }

    @Override
    public void notifyCleared() {
        if( suppressEvents ) return;
        getRenderPanel().clear();
    }

    @Override
    public void notifyAdded() {
        if( suppressEvents ) return;
    }

    @Override
    public void notifyChanged() {
        if( suppressEvents ) return;
        java.util.List<Command> commands = parser.getCommands();
        if( commands.isEmpty() ) {
            return;
        }
        Command last = commands.get(commands.size()-1);
        getRenderPanel().executeCommand(last);
    }

    public void open() {
        JFileChooser fc = new JFileChooser(".");
        fc.setDialogTitle("Open..");
        int result = fc.showOpenDialog(screenPanel);
        if( result == JFileChooser.APPROVE_OPTION ) {
            currentFile = fc.getSelectedFile();
            if( currentFile.exists() && currentFile.canRead() ) {
                BufferedReader reader = null;

                try {
                    reader = new BufferedReader(new FileReader(currentFile));
                    String text = null;
                    StringBuilder sb = new StringBuilder();
                    // repeat until all lines are read
                    while ((text = reader.readLine()) != null) {
                        sb.append(text);
                        sb.append("\n");
                    }
                    setEventSuppression(true);
                    readyTextArea.setText(sb.toString());
                    notifyCleared();
                    parser.reparse();
                    setEventSuppression(false);

                } catch (IOException e) {
                    statusLabel.setText("Error reading file.");
                    System.out.println("Error: "+e.getMessage());
                    e.printStackTrace();
                } finally {
                    try {
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        System.out.println("Error: "+e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void save() {
        if( currentFile == null ) {
            saveAs();
        } else {
            try {
                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(currentFile)));
                out.print(readyTextArea.getText());
                out.close();
                statusLabel.setText("Saved.");
            } catch (IOException e) {
                statusLabel.setText("Error.");
                System.out.println("Error: "+e.getMessage());
            }
        }
    }

    public void saveAs() {
        JFileChooser fc = new JFileChooser(".");
        fc.setDialogTitle("Save as..");
        int result = fc.showSaveDialog(screenPanel);
        if( result == JFileChooser.APPROVE_OPTION ) {
            currentFile = fc.getSelectedFile();
            if( currentFile != null ) {
                save();
            }
        }
    }

    public void setSleepDelay(long ms) {
        sleepDelay = ms;
    }

    private void initMenus(final JFrame frame) {
        JMenu fileMenu = new JMenu("File");
        frame.getJMenuBar().add(fileMenu);

        addMenuItem(fileMenu, "Open..", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                open();
            }
        });

        addMenuItem(fileMenu, "Save", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });

        addMenuItem(fileMenu, "Save as..", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAs();
            }
        });

        fileMenu.add(new JPopupMenu.Separator());

        addMenuItem(fileMenu, "Exit", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                System.out.println("Thank you for playing with TurtleGame.");
                System.exit(0);
            }
        });

        JMenu gameMenu = new JMenu("Game");
        frame.getJMenuBar().add(gameMenu);

        for( final Game game : gameFactory.getGames() ) {
            addMenuItem(gameMenu, game.getName(), new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switchToGame(game);
                }
            });
        }

        JMenu speedMenu = new JMenu("Speed");
        frame.getJMenuBar().add(speedMenu);

        addMenuItem(speedMenu, "Slowest", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSleepDelay(5000);
            }
        });

        addMenuItem(speedMenu, "Slow", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSleepDelay(2000);
            }
        });

        addMenuItem(speedMenu, "Normal", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSleepDelay(1000);
            }
        });

        addMenuItem(speedMenu, "Fast", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSleepDelay(500);
            }
        });

        addMenuItem(speedMenu, "Fastest", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSleepDelay(200);
            }
        });

        addMenuItem(speedMenu, "Crazy!", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSleepDelay(20);
            }
        });


        frame.getJMenuBar().add(Box.createHorizontalGlue());

        JMenu helpMenu = new JMenu("Help");
        frame.getJMenuBar().add(helpMenu);

    }

    private JMenuItem addMenuItem(JMenu menu, String name, ActionListener listener) {
        JMenuItem item = new JMenuItem(name);
        menu.add(item);
        item.addActionListener(listener);
        return item;
    }

    private void switchToGame(Game game) {
        currentGame = game;
        getRenderPanel().clear();
        statusLabel.setText("Changed game to '"+currentGame.getName()+"'.");
    }

    public Game getCurrentGame() {
        return currentGame;
    }
}

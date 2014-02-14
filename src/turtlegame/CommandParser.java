package turtlegame;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class CommandParser {
    JTextArea textArea;
    List<Command> commandsList = new ArrayList();
    List<CommandListener> listeners = new ArrayList();

    public CommandParser(JTextArea textArea) {
        this.textArea = textArea;
        installListener();
    }

    private void installListener() {
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if( e.getKeyCode() == KeyEvent.VK_ENTER ) {
                    reparse();
                }
            }
        });
    }

    public void reparse() {
        List<Command> commands = new ArrayList<Command>();
        String[] lines = textArea.getText().split("\n");
        int lineNumber = 1;
        for( String line : lines ) {
            try {
                line = line.trim();
                String[] parts = line.split(" ");
                if( parts.length == 2 ) {
                    String commandName = parts[0].trim();
                    String amount = parts[1].trim();
                    int amountValue = Integer.parseInt(amount);

                    if( commandName.equalsIgnoreCase("forward") || commandName.equalsIgnoreCase("forwards") ) {
                        Command command = new Forward(amountValue);
                        commands.add(command);
                    } else
                    if( commandName.equalsIgnoreCase("left") ) {
                        Command command = new Turn(amountValue);
                        commands.add(command);
                    } else
                    if( commandName.equalsIgnoreCase("right") ) {
                        Command command = new Turn(-amountValue);
                        commands.add(command);
                    } else {
                        // unrecognized command
                    }
                } else {
                    // unrecognized command
                }
            } catch( Exception e ) {
                System.out.println("Error on line "+lineNumber+": "+e.getMessage()+" - for '"+line+"'");
            }
            lineNumber++;
        }
        this.commandsList = commands;
        notifyChanged();
    }

    private void notifyChanged() {
        for( CommandListener listener : listeners ) {
            listener.notifyChanged();
        }
    }

    public List<Command> getCommands() {
        return commandsList;
    }

    public void addListener(CommandListener listener) {
        listeners.add(listener);
    }

}

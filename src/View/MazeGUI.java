package View;

import Model.Maze;
import Model.Player;
import Controller.MazeUpdate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;

import static Controller.Solution.*;

/**
 * Represents a graphical user interface for displaying and interacting with a maze.
 */
public class MazeGUI extends JPanel implements KeyListener {
    private int cellSize; // Size of each cell
    private Player player;
    private Maze maze;
    private MazeUpdate controller;
    private int row;
    private int col;
    private ArrayList<int[]> traversedCopy;
    private ArrayList<int[]> traversedAgainCopy;

    /**
     * Creates a new instance of the MazeGUI class.
     * Sets this.row to equal the player's row position
     * Sets this.col to equal the player's column position
     * Adds a key listener for listening for arrow key inputs
     * to move player.
     *
     * @param maze       The maze to display.
     * @param player     The player object.
     * @param controller The maze controller for handling user input.
     * @param cellSize   The size of each cell in pixels.
     */
    public MazeGUI(Maze maze, Player player, MazeUpdate controller, int cellSize) {

        // Calculate the cell size to fit the maze within the fixed size
        this.cellSize = cellSize;
        // can receive keyboard focus, allowing it to respond to keyboard
        // input events like key presses
        setFocusable(true);
        // requests that the MazeGUI component be given keyboard focus
        requestFocusInWindow();
        this.player = player;
        this.row = this.player.getPlayerPosition()[0];
        this.col = this.player.getPlayerPosition()[1];
        this.maze = maze;
        this.controller = controller;
        // Adds a key listener to MazeGUI
        addKeyListener(this);

    }

    /**
     * Gets the cell size in pixels.
     *
     * @return The cell size.
     */
    public int getCellSize() {
        return this.cellSize;
    }

    /**
     * Sets the cell size in pixels.
     *
     * @param cellSize The new cell size.
     */
    public void setCellSize(int cellSize) {
        this.cellSize = cellSize; // Update the cell size
    }

    /**
     * Sets the maze to be displayed.
     *
     * @param maze The maze to display.
     */
    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Paints the maze and player's progress on the GUI.
     *
     * @param g The Graphics object used for drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMazeGui(g);
    }

    /**
     * Draws the maze and player's progress on the GUI
     * based off the Maze model.
     * Walls are black squares
     * Empty path is white squares
     * Exit is a red square
     * Player is a green square
     * Traversed paths are a cyan square
     * Traversed again paths are a dark blue square
     *
     * @param g The Graphics object used for drawing.
     */
    private void drawMazeGui(Graphics g) {
        // do nothing if the maze is empty
        if (maze.getMaze() == null) return;

        this.traversedCopy = new ArrayList<>(player.getTraversed());
        this.traversedAgainCopy = new ArrayList<>(player.getTraversedAgain());

        // sets graphic objects to be filled squares with the appropriate colour
        // corresponding to what the object will be representing
        for (int i = 0; i < maze.getMaze().length; i++) {
            for (int j = 0; j < maze.getMaze()[0].length; j++) {
                if (maze.getMaze()[i][j] == '░') { // Wall
                    g.setColor(Color.BLACK);
                    g.fillRect(j * this.cellSize, i * this.cellSize, this.cellSize, this.cellSize);
                } else if (maze.getMaze()[i][j] == ' ') { // Empty path
                    boolean traversed = false;
                    boolean traverseAgain = false;
                    int[] currentPosition = new int[]{i, j};

                    // Check if the current position has been traversed
                    // If it has, sets traversed to be true
                    for (int[] traversedPosition : this.traversedCopy) {
                        if (Arrays.equals(traversedPosition, currentPosition)) {
                            traversed = true;
                            break;
                        }
                    }
                    // Check if current position has been traversed again
                    // If it has, sets traversedAgain to true
                    for (int[] traversedAgainPosition : this.traversedAgainCopy) {
                        if (Arrays.equals(traversedAgainPosition, currentPosition)) {
                            traverseAgain = true;
                            break;
                        }
                    }

                    if (traverseAgain) {
                        // Create a new dark blue image based on the current cell size
                        // Represents backtracked paths
                        BufferedImage darkBlueImage = new BufferedImage(this.cellSize, this.cellSize, BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g3 = darkBlueImage.createGraphics();
                        g3.setColor(new Color(0, 0, 139, 128));
                        g3.fillRect(0, 0, this.cellSize, this.cellSize);
                        g3.dispose();
                        g.drawImage(darkBlueImage, j * this.cellSize, i * this.cellSize, this);
                    } else if (traversed) {
                        // Create a new cyan image based on the current cell size
                        // Represents traversed paths
                        BufferedImage cyanImage = new BufferedImage(this.cellSize, this.cellSize, BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g2 = cyanImage.createGraphics();
                        g2.setColor(new Color(0, 255, 255, 128));
                        g2.fillRect(0, 0, this.cellSize, this.cellSize);
                        g2.dispose();
                        g.drawImage(cyanImage, j * this.cellSize, i * this.cellSize, this);
                    } else {
                        g.setColor(Color.WHITE);
                        g.fillRect(j * this.cellSize, i * this.cellSize, this.cellSize, this.cellSize);
                    }

                } else if (maze.getMaze()[i][j] == '⚉') { // player
                    g.setColor(Color.GREEN);
                    g.fillRect(j * this.cellSize, i * this.cellSize, this.cellSize, this.cellSize);
                } else if (maze.getMaze()[i][j] == '|') { // exit
                    g.setColor(Color.RED);
                    g.fillRect(j * this.cellSize, i * this.cellSize, this.cellSize, this.cellSize);
                }
            }
        }
    }

    /**
     * Handles key-typed events.
     *
     * @param e The KeyEvent object representing the key-typed event.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Implementation not needed for this application
    }

    /**
     * Handles key-pressed events.
     * If key pressed is the up arrow, player moves up
     * If key pressed is the down arrow, player moves down
     * If key pressed is right arrow, player moves to the right
     * If key pressed is left arrow, player moves to the left
     * Note. move player using setMove method.
     *
     * @param e The KeyEvent object representing the key-pressed event.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // Handle key press events here
        int keyCode = e.getKeyCode();

        // depending on key pressed, associated move is set
        switch (keyCode) {
            case KeyEvent.VK_UP:
                controller.setMove("w");
                controller.updateMaze(maze, player, controller.getMove()); // Update the maze and player immediately
                break;
            case KeyEvent.VK_DOWN:
                controller.setMove("s");
                controller.updateMaze(maze, player, controller.getMove());
                break;
            case KeyEvent.VK_LEFT:
                controller.setMove("a");
                controller.updateMaze(maze, player, controller.getMove());
                break;
            case KeyEvent.VK_RIGHT:
                controller.setMove("d");
                controller.updateMaze(maze, player, controller.getMove());
                break;
        }

        // Trigger a repaint of the maze after each move
        repaint();
    }

    /**
     * Handles key-released events.
     *
     * @param e The KeyEvent object representing the key-released event.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // Implementation not needed for this application
    }

    /**
     * Asks the user if they want to see the solution.
     *
     * @return true if the user wants to see the solution, false otherwise.
     */
    public boolean askToShowSolution() {
        int result = JOptionPane.showConfirmDialog(
                null,
                "Do you want to see the solution?",
                "Show Solution",
                JOptionPane.YES_NO_OPTION
        );

        return result == JOptionPane.YES_OPTION;
    }

    /**
     * Asks the user if they want to play again after solving the maze.
     *
     * @return true if the user wants to play again, false otherwise.
     */
    public boolean askToPlayAgain() {
        int result = JOptionPane.showConfirmDialog(
                null,
                "Congrats you solved the maze! Do you want to play again?",
                "You Solved The Maze!",
                JOptionPane.YES_NO_OPTION
        );

        return result == JOptionPane.YES_OPTION;

    }

    /**
     * Draws the solution for the maze using a delay between each step.
     * The method first calls findSolution. If findSolution cannot find
     * a solution, it throws an IndexOutOfBoundsException which this method
     * catches and prints "No Solution".
     * Thread may throw InterruptedException in which this method catches and
     * throws a RuntimeException.
     */
    public void drawSolutionGui() {
        try {
            // finds the solution and for each move in the solution,
            // the maze updates and GUI repaints
            findSolution(this.maze, this.row, this.col, this.row, this.col);
            for (String move : moves) {
                this.controller.setMove(move);
                this.controller.updateMaze(this.maze, this.player, this.controller.getMove());
                repaint();

                // Introduce a delay between each repaint
                Thread.sleep(250);
            }
            // clears solution path and moves path
            // this is in case user plays again and wants to see the solution
            path.clear();
            moves.clear();
        } catch (IndexOutOfBoundsException e) {
            // index out of bounds exception indicates no solution
            System.out.println("No Solution");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

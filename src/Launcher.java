import Controller.Solution;
import Model.Maze;
import Model.Player;
import View.MazeGUI;

import View.MazeView;
import Controller.MazeUpdate;
import exceptions.MazeMalformedException;
import exceptions.MazeSizeMissmatchException;

import javax.swing.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import static Controller.Solution.findSolution;

/**
 * This class serves as the entry point for the maze-solving application.
 */
public class Launcher {

    /**
     * Recreates the maze display, resetting the player's position and allowing the user to either
     * play again or a try a different maze.
     *
     * @param maze      The Maze object to be displayed.
     * @param frame     The JFrame where the maze GUI is displayed.
     * @param fixedWidth The fixed width of the JFrame.
     * @param fixedHeight The fixed height of the JFrame.
     * @param mazeGUI   The MazeGUI object responsible for rendering the maze.
     */
    private static void recreateMaze(Maze maze, JFrame frame, int fixedWidth, int fixedHeight, MazeGUI mazeGUI) {
        maze.getPlayer().resetTraversed();
        mazeGUI.setMaze(maze);
        int newMazeWidth = maze.getNumCols();
        int newMazeHeight = maze.getNumRows();
        int newCellSize = Math.min(fixedWidth / newMazeWidth, fixedHeight / newMazeHeight);
        mazeGUI.setCellSize(newCellSize);
        // sets size of GUI
        mazeGUI.setPreferredSize(new java.awt.Dimension(newCellSize * newMazeWidth, newCellSize * newMazeHeight));
        mazeGUI.repaint();
        frame.pack();
        if (mazeGUI.askToShowSolution()) {
            // Display the solution
            mazeGUI.drawSolutionGui();
        }
    }

    /**
     * The main entry point of the maze-solving application.
     * Depending on command line arguments, game either loads
     * as text based or GUI based.
     *
     * @param args The command-line arguments.
     * @throws IOException                If an I/O error occurs while reading maze files.
     * @throws MazeSizeMissmatchException If the maze size mismatches the provided size.
     * @throws MazeMalformedException     If the maze file is malformed.
     */
    public static void main(String[] args) throws IOException, MazeSizeMissmatchException, MazeMalformedException {
        Player player = new Player(new int[]{0, 0});
        MazeUpdate controller = new MazeUpdate();
        Maze maze;
        String lastArgument = args[args.length - 1];
        try {
            maze = new Maze("/Users/jaimathur/IdeaProjects/A12002/src/Mazes/" + lastArgument, player);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MazeView view = new MazeView();

        // Check if we are running on macOS
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("mac")) {
            // Use the macOS system menu bar
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }

        // Create a JFrame if "GUI" argument is passed
        if (args.length > 0 && args[0].equals("GUI")) {
            boolean playAgain = true;
            while (playAgain) {
                JFrame frame = new JFrame("Maze GUI");
                // Calculate the preferred size based on maze dimensions and cell size
                int fixedWidth = 1000;
                int fixedHeight = 800;// Fixed window size
                int mazeWidth = maze.getNumCols();
                int mazeHeight = maze.getNumRows();

                // Calculate the cell size to fit the maze within the fixed size
                int cellSize = Math.min(fixedWidth / mazeWidth, fixedHeight / mazeHeight);

                MazeGUI mazeGUI = new MazeGUI(maze, maze.getPlayer(), controller, cellSize);
                mazeGUI.setPreferredSize(new java.awt.Dimension(cellSize * mazeWidth, cellSize * mazeHeight));

                // Create a system menu bar
                JMenuBar menuBar = new JMenuBar();
                JMenu mazeMenu = new JMenu("Select Maze");
                JMenuItem mazeItem1 = new JMenuItem("Maze 1");
                JMenuItem mazeItem2 = new JMenuItem("Maze 2");
                JMenuItem mazeItem3 = new JMenuItem("Maze 3");

                mazeItem1.addActionListener(e -> {
                    try {
                        // Load Maze 1
                        Maze newMaze = new Maze("/Users/jaimathur/IdeaProjects/A12002/src/Mazes/Maze001.txt", player);
                        recreateMaze(newMaze, frame, fixedWidth, fixedHeight, mazeGUI);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                mazeItem2.addActionListener(e -> {
                    try {
                        // Load Maze 2
                        Maze newMaze = new Maze("/Users/jaimathur/IdeaProjects/A12002/src/Mazes/Maze002.txt", player);
                        recreateMaze(newMaze, frame, fixedWidth, fixedHeight, mazeGUI);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                mazeItem3.addActionListener(e -> {
                    try {
                        // Load Maze 3
                        Maze newMaze = new Maze("/Users/jaimathur/IdeaProjects/A12002/src/Mazes/Maze003.txt", player);
                        recreateMaze(newMaze, frame, fixedWidth, fixedHeight, mazeGUI);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                // Add maze items to the maze menu
                mazeMenu.add(mazeItem1);
                mazeMenu.add(mazeItem2);
                mazeMenu.add(mazeItem3);

                // Add the maze menu to the system menu bar
                menuBar.add(mazeMenu);
                // Set the system menu bar for the JFrame
                frame.setJMenuBar(menuBar);
                // add GUI to the frame
                frame.add(mazeGUI);
                // Exit GUI on close
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                // Make the frame visible
                frame.setVisible(true);
                if (mazeGUI.askToShowSolution()) {
                    // Display the solution
                    mazeGUI.drawSolutionGui();
                }

                while (true) {
                    try {
                        Thread.sleep(100); // Add a small delay to prevent EDT blocking
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    // ask player if they want to play again
                    if (Arrays.equals(maze.getPlayer().getPlayerPosition(), maze.getEndOfMaze())) {
                        System.out.println("You have reached the end!");
                        playAgain = mazeGUI.askToPlayAgain();
                        if (!playAgain) {
                            break; // Exit the loop if the user doesn't want to play again
                        }
                        else {
                            // reloads the same maze if player wants to play again
                            maze = new Maze("/Users/jaimathur/IdeaProjects/A12002/src/Mazes/" + lastArgument, player);
                            recreateMaze(maze, frame, fixedWidth, fixedHeight, mazeGUI);
                        }
                    }
                }
            }
        } else { // text based
            // asks user if they want to solve the maze or see solution
            String method = controller.gameMode();

            if (Objects.equals(method, "myself")) { // manually solve
                // prints initial state of maze
                for (char[] row : maze.getMaze()) {
                    System.out.println(row);
                }
                // loop asks for moves and updates maze until
                // player reaches end of maze
                while (true) {
                    controller.playerMove();
                    controller.updateMaze(maze, maze.getPlayer(), controller.getMove());
                    view.drawMaze(maze);
                    // System.out.println(Arrays.toString(maze.getPlayer().getPlayerPosition()));
                    // System.out.print(Arrays.toString(maze.getEndOfMaze()));
                    if (Arrays.equals(maze.getPlayer().getPlayerPosition(), maze.getEndOfMaze())) {
                        System.out.println("You have solved the maze!");
                        break;
                    }
                }
            } else if (Objects.equals(method, "solution")){ // programmatically

                int row = maze.getPlayer().getPlayerPosition()[0];
                int col = maze.getPlayer().getPlayerPosition()[1];
                try {
                    // finds solution then draws the solution
                    findSolution(maze, row, col, row, col);
                    view.drawSolution(maze);
                    for (String move : Solution.moves) {
                        controller.updateMaze(maze, player, move);
                        view.drawSolution(maze);
                    }

                } catch (IndexOutOfBoundsException e) {
                    System.out.println("There is no solution");
                }
            }
        }
    }
}








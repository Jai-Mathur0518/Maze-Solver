package Model;

import exceptions.MazeMalformedException;
import exceptions.MazeSizeMissmatchException;
import io.FileLoader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents a Maze object, providing methods to load, manipulate, and access maze data.
 * Inherits from the FileLoader class so that Maze can be correctly loaded.
 * Contains attributes such as an ArrayList of Integers to store the dimensions of the maze,
 * a string to store the current map file path,
 * an array of integers to store the end position in the maze,
 * a 2d array of characters to store a character representation of the Maze,
 * an instance of a Player
 */
public class Maze extends FileLoader {
    private final ArrayList<Integer> dimensions;
    private String mapFile;
    private int[] endOfMaze;

    private char[][] maze;

    private Player player;



    /**
     * Constructs a Maze object by loading maze data from the specified file path.
     *
     * @param path   The path to the maze data file.
     * @param player The player associated with this maze.
     * @throws IOException If an I/O error occurs while reading the maze file.
     */
    public Maze(String path, Player player) throws IOException {
        this.mapFile = path;
        this.player = player;

        // Reading maze dimensions from file
        BufferedReader reader = new BufferedReader(new java.io.FileReader(path));
        String[] mapDimensions = reader.readLine().split(" ");
        ArrayList<Integer> newDimensions =  new ArrayList<Integer>();
        for (String num : mapDimensions) {
            newDimensions.add(Integer.parseInt(num));
        }
        reader.close();
        this.dimensions = newDimensions;
        this.maze = this.loadMaze();

    }

    /**
     * Retrieves the dimensions of the maze as [rows, columns].
     *
     * @return An ArrayList containing the number of rows and columns in the maze.
     */
    public ArrayList<Integer> getDimensions() {
        return this.dimensions;
    }

    /**
     * Retrieves the file path of the maze data.
     *
     * @return The file path of the maze data.
     */
    public String getMapFile() {
        return this.mapFile;
    }


    /**
     * Retrieves the number of rows in the maze.
     *
     * @return The number of rows in the maze.
     */
    public int getNumRows() {
        return this.dimensions.get(0);
    }

    /**
     * Retrieves the number of columns in the maze.
     *
     * @return The number of columns in the maze.
     */
    public int getNumCols() {
        return this.dimensions.get(1);
    }

    /**
     * Retrieves the position of the end point of the maze.
     *
     * @return An array of integers representing the row and column of the end point.
     */
    public int[] getEndOfMaze() {
        return this.endOfMaze;
    }

    /**
     * Loads and processes the maze data from the file.
     * When processing the 2D character array from the FileLoader load method,
     * if the character is a '#' it should be replaced with a '░',
     * if character is 'S' then it is replaced with '⚉' as this is where the player starts,
     * if character is a ' ' then it remains as ' '
     * finally, if character is 'E' then it is replaced with '|' to denote the end of the maze,
     * additionally, this.endOfMaze is updated to the position of the end of the maze.
     *
     * @return A 2D character array representing the processed maze.
     * @throws RuntimeException If there are issues with loading or processing the maze data.
     * i.e. MazeMalformed or MazeMisMatched exception are thrown in FileLoader load method,
     * loadMaze catches exception and throws RuntimeException.
     */
    public char[][] loadMaze() {
        try {
            char[][] maze = this.load(this.getMapFile());
            char[][] modifiedMaze = new char[maze.length][maze[0].length];
            // Alters the loaded maze to store different characters
            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[i].length; j++) {
                    if (maze[i][j] == '#') {
                        modifiedMaze[i][j] = '░';
                    }
                    else if (maze[i][j] == 'S') {
                        modifiedMaze[i][j] = this.getPlayer().getPlayerChar();
                        int[] newPosition = {i,j};
                        // Sets player position to start at 'S' and adds position to traversed
                        this.getPlayer().setPlayerPosition(newPosition);
                        this.getPlayer().addTraversed(this.getPlayer().getPlayerPosition());
                    }
                    else if (maze[i][j] == 'E') {
                        modifiedMaze[i][j] = '|';
                        this.endOfMaze = new int[]{i, j};
                    }
                    else if (maze[i][j] == ' ') {
                        modifiedMaze[i][j] = ' ';
                    }
                    else {
                        throw new MazeMalformedException("Invalid character in maze");
                    }
                }
            }
            return modifiedMaze;
        } catch (MazeMalformedException | MazeSizeMissmatchException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the current state of the maze as a 2D character array.
     *
     * @return A 2D character array representing the maze.
     */
    public char[][] getMaze() {
        return this.maze;
    }

    /**
     * Sets the current state of the maze to the provided 2D character array.
     *
     * @param newMaze The new maze state as a 2D character array.
     */
    public void setMaze(char[][] newMaze) {
        this.maze = newMaze;
    }

    /**
     * Checks if a move to the specified position is valid in the maze.
     * The move is valid if the position the player is moving to is not a wall.
     * @param position An array of integers representing the target position [row, column].
     * @return `true` if the move is valid, `false` otherwise.
     */
    public boolean isValidMove(int[] position){
        if (this.getMaze()[position[0]][position[1]] != '░') {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retrieves the player associated with this maze.
     *
     * @return The Player object associated with this maze.
     */
    public Player getPlayer() {
        return this.player;
    }
}

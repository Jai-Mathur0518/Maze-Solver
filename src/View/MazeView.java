package View;

import Controller.Solution;
import Model.Maze;

import java.util.Arrays;

/**
 * Represents a view for displaying a maze and its solution (Text based game mode).
 */
public class MazeView {

    /**
     * Draws the maze on the console by printing the 2D
     * character array representing the maze.
     *
     * @param maze The maze to be drawn.
     */
    public void drawMaze(Maze maze) {
        char[][] template = maze.getMaze();
        // Prints each row in 2D array
        for (char[] row : template) {
            System.out.println(row);
        }
    }

    /**
     * Draws the solution path on the maze template.
     * Note. drawSolution method should be called after findSolution
     * method is called to ensure path and moves for the solution exist.
     * The solution path to be represented with '*'.
     *
     * @param maze The maze to be drawn with the solution path.
     */
    public void drawSolution(Maze maze) {
        char[][] template = maze.getMaze();
        for (int i=0; i < template.length; i++) {
            for (int j=0; j < template[i].length; j++) {
                int[] position = {i, j};
                boolean positionFound = false;
                // checks if the current position that the for loop
                // is pointing at is in the solution path
                for (int[] pathPosition : Solution.path) {
                    if (Arrays.equals(pathPosition, position)) {
                        positionFound = true;
                        break; // No need to continue checking once found
                    }
                }

                // Replaces the position in the maze with '*'
                // if it's part of the solution path
                // Except does not replace player character
                if (positionFound && template[i][j] != '⚉') {
                    template[i][j] = '*';
                }
            }
        }
        // prints each row in 2D array
        for (char[] row : template) {
            System.out.println(row);
        }
    }

}

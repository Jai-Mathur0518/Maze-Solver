package Controller;

import Model.Maze;

import java.util.ArrayList;

/**
 * Represents a class for solving a maze using a recursive algorithm.
 */
public class Solution {

    // Stores the path of positions taken during the maze traversal.
    // Variable declared as public and static such that it can be accessed
    // from MazeView and MazeGUI class without having to make an instance of Solution
    public static ArrayList<int[]> path = new ArrayList<>();

    // Stores the list of moves corresponding to the path.
    // Variable declared as public and static such that it can be accessed
    // from another class without having to make an instance of Solution
    public static ArrayList<String> moves = new ArrayList<>();

    /**
     * Finds a solution to the maze starting from a given position.
     * Traverses through every possible path until it finds the exit.
     * While traversing through a path, it adds the position to path and
     * the corresponding move to moves. If the path leads to a dead end,
     * it back tracks, removing the previous positions and moves until
     * it can take a new path.
     * Method declared as static so that there is no need to create an instance
     * of solution.
     *
     * @param maze  The maze to solve.
     * @param row   The current row position.
     * @param col   The current column position.
     * @param prow  The previous row position.
     * @param pcol  The previous column position.
     * @return True if a solution is found, false otherwise.
     */
    public static boolean findSolution(Maze maze, int row, int col, int prow, int pcol) {

        int[] position = {row, col};

        // Base case: check if the position is not valid or already in the path.
        if (!maze.isValidMove(position) || path.contains(position)) {
            return false;
        }

        // adds the position to solution path
        // adds the associated move to solution moves
        path.add(position);
        if (prow < row) {
            moves.add("s");
        } else if (prow> row) {
            moves.add("w");
        } else if (pcol< col) {
            moves.add("d");
        } else if (pcol > col) {
            moves.add("a");
        }

        if (maze.getMaze()[row][col] == '|') {
            return true; // maze has a solution
        } else {
            // Try moving in all four directions, but avoid moving back to the previous position.
            // This stops the algorithm from repetitively moving back and forth in a loop.
            if ((row != prow + 1 && findSolution(maze, row - 1, col, row, col)) ||
                    (row != prow - 1 && findSolution(maze, row + 1, col, row, col)) ||
                    (col != pcol + 1 && findSolution(maze, row, col - 1, row, col)) ||
                    (col != pcol - 1 && findSolution(maze, row, col + 1, row, col))) {
                return true;
            }
            // if all possible moves from the current position return false,
            // then remove the current position from the solution path and
            // remove the current move from the solution move
            path.remove(position);
            moves.remove(moves.size() - 1);// Backtrack
            return false;
        }

    }

}

package Controller;

import Model.Maze;
import Model.Player;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

/**
 * Handles user input and updates the maze and player based on player moves.
 * Contains a move attribute stored as a String.
 * Move should be either 'a', 'w', 's', 'd'.
 */
public class MazeUpdate {

    private String move;

    /**
     * Get the current player move.
     *
     * @return The current player move as a string.
     */
    public String getMove() {
        return this.move;
    }

    /**
     * Set the player move.
     *
     * @param move The player move as a string.
     */
    public void setMove(String move) {
        this.move = move;
    }

    /**
     * Prompts the user to enter a move and sets it as the current player move.
     */
    public void playerMove() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a move: ");
        this.setMove(scanner.nextLine());
    }

    /**
     * Prompt the user to choose a game mode: solve the maze or see the solution.
     * User must type in game mode.
     *
     * @return The selected game mode as a string.
     */
    public String gameMode() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Would you like to solve the maze yourself or see the solution? " +
                "Type 'solution' to see the solution. " +
                "Else type 'myself' and good luck solving it yourself.");
        return scanner.nextLine();
    }


    /**
     * Update the maze based on the player's move.
     * Checks if the player's move is valid, if it is valid,
     * then player's position updates to the new position.
     * Maze is then updated to move the player character to
     * the new maze position.
     * The player's new position is then added to traversed positions.
     * If the player has already moved to that position before,
     * then it is added to traversed again positions.
     *
     * @param oldMaze The original maze object to be updated.
     * @param player  The player object.
     * @param move    The player's move as a string ('w', 's', 'a', or 'd').
     */
    public void updateMaze(Maze oldMaze, Player player, String move) {
        char[][] updatedMaze = new char[oldMaze.getNumRows()][oldMaze.getNumCols()];
        // Creates a copy of the old maze into a new maze but removes player character
        // and replaces it with ' '
        for (int i = 0; i < oldMaze.getMaze().length; i++) {
            for (int j = 0; j < oldMaze.getMaze()[i].length; j++) {
                if (oldMaze.getMaze()[i][j] == player.getPlayerChar()) {
                    updatedMaze[i][j] = ' ';
                }
                else {
                    updatedMaze[i][j] = oldMaze.getMaze()[i][j];
                }
            }
        }
        // finds player position and updates it based on the user's move
        int[] position = player.getPlayerPosition();
        int row = position[0];
        int col = position[1];
        if (Objects.equals(move, "w")) {
            if (oldMaze.isValidMove(new int[]{row - 1, col})) {
                row -=1;
                int[] newPosition = {row, col};
                player.setPlayerPosition(newPosition);
            }
        }
        else if (Objects.equals(move, "s")) {
            if (oldMaze.isValidMove(new int[]{row + 1, col})) {
                row +=1;
                int[] newPosition = {row, col};
                player.setPlayerPosition(newPosition);
            }
        }
        else if (Objects.equals(move, "a")) {
            if (oldMaze.isValidMove(new int[]{row, col-1})) {
                col -=1;
                int[] newPosition = {row, col};
                player.setPlayerPosition(newPosition);
            }
        }
        else if (Objects.equals(move, "d")) {
            if (oldMaze.isValidMove(new int[]{row, col+1})) {
                col +=1;
                int[] newPosition = {row, col};
                player.setPlayerPosition(newPosition);
            }
        }
        else {
            // move was not awsd
            System.out.println("Illegitimate move");
        }
        // Takes player position and puts it into updated maze
        if (updatedMaze[row][col] == ' ' || updatedMaze[row][col] == '*' ) {
            updatedMaze[row][col] = player.getPlayerChar();
        }

        // Finds if player has traversed the path before
        // and adds player position to traversed again if they have
        boolean found = false;

        for (int[] coordinates : player.getTraversed()) {
            if (Arrays.equals(coordinates, player.getPlayerPosition())) {
                player.addTraversedAgain(coordinates);
                found = true;
                break;
            }
        }

        if (!found) {
            // adds player position to traversed if it is a new position
            player.addTraversed(player.getPlayerPosition());
        }
        // updates the maze
        oldMaze.setMaze(updatedMaze);
    }

}

package Model;

import java.util.ArrayList;

/**
 * Represents a player within the maze.
 * Player has an integer array storing the players position on the maze.
 * Player has an ArrayList of int[] consisting of all the positions the
 * player has traversed.
 * Player has an ArrayList of int[] consisting of all the positions the
 * player has traversed again.
 */
public class Player {
    private int[] position;
    private ArrayList<int[]> traversed = new ArrayList<>();
    private ArrayList<int[]> traversedAgain = new ArrayList<>();

    /**
     * Initializes a new player with the specified position.
     *
     * @param position The initial position of the player as an array of two integers [row, col].
     */
    public Player(int[] position) {
        this.position = position;
    }

    /**
     * Get the current position of the player.
     *
     * @return The position of the player as an array of two integers [row, col].
     */
    public int[] getPlayerPosition() {
        return this.position;
    }

    /**
     * Set the position of the player.
     *
     * @param position The new position of the player as an array of two integers [row, col].
     */
    public void setPlayerPosition(int[] position) {
        this.position = position;
    }

    /**
     * Get the character representation of the player.
     *
     * @return The character representing the player ('⚉').
     */
    public char getPlayerChar() {
        return '⚉';
    }

    /**
     * Get the list of positions traversed by the player.
     *
     * @return An ArrayList containing the positions traversed by the player.
     */
    public ArrayList<int[]> getTraversed() {
        return this.traversed;
    }

    /**
     * Add a position to the list of traversed positions by the player.
     *
     * @param traversed The position to be added to the list of traversed positions.
     */
    public void addTraversed (int[] traversed) {
        this.traversed.add(traversed);
    }

    /**
     * Get the list of positions traversed again by the player.
     *
     * @return An ArrayList containing the positions traversed again by the player.
     */
    public ArrayList<int[]> getTraversedAgain() {
        return this.traversedAgain;
    }

    /**
     * Add a position to the list of traversed positions again by the player.
     *
     * @param traversedAgain The position to be added to the list of traversed again positions.
     */
    public void addTraversedAgain (int[] traversedAgain) {
        this.traversedAgain.add(traversedAgain);
    }

    /**
     * Reset the list of traversed positions to only contain the current player position.
     */
    public void resetTraversed() {
        this.traversed.clear();
        this.traversedAgain.clear();

        this.traversed.add(getPlayerPosition());
    }

}

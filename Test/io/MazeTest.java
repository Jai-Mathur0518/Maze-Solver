package io;

import Model.Maze;
import Model.Player;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class MazeTest {

    /**
     * Test the loading of a maze from a file by checking if the player
     * position is set to where it should be and that the end of maze
     * is in the correct position.
     *
     * @throws IOException If an I/O error occurs while loading the maze.
     */
    @Test
    public void testLoadMaze() throws IOException {
        Player player = new Player(new int[] {0,0});
        Maze maze = new Maze("/Users/jaimathur/IdeaProjects/A12002/src/Mazes/Maze001.txt", player);
        maze.loadMaze();
        assertArrayEquals(new int[]{1, 1}, maze.getPlayer().getPlayerPosition());
        assertEquals('|', maze.getMaze()[5][5]);
    }

    /**
     * Test the validity of a player move within the maze by setting the player
     * position at a wall and expecting False and also setting player position to a
     * traversable position and expecting True.
     *
     * @throws IOException If an I/O error occurs while loading the maze.
     */
    @Test
    public void testIsValidMove() throws IOException {
        Player player = new Player(new int[] {0,0});
        Maze maze = new Maze("/Users/jaimathur/IdeaProjects/A12002/src/Mazes/Maze001.txt", player);
        maze.getPlayer().setPlayerPosition(new int[]{2,2});
        assertFalse(maze.isValidMove(maze.getPlayer().getPlayerPosition()));
        maze.getPlayer().setPlayerPosition(new int[]{5,5});
        assertTrue(maze.isValidMove(maze.getPlayer().getPlayerPosition()));
    }

}

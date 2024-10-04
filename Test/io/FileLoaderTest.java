package io;

import exceptions.MazeMalformedException;
import exceptions.MazeSizeMissmatchException;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

/**
 * JUnit testing for FileLoader class.
 */
public class FileLoaderTest {

    /**
     * Test for MazeMalformedException when the maze file dimensions has an even number
     * of rows or columns or both rows and columns.
     *
     * @throws MazeMalformedException     If the maze file is malformed.
     * @throws MazeSizeMissmatchException If the maze size mismatches the provided size.
     * @throws IllegalArgumentException   If the maze contains invalid characters.
     * @throws FileNotFoundException      If the maze file is not found.
     */
    @Test(expected = MazeMalformedException.class)
    public void testMazeMalformedException1() throws MazeMalformedException, MazeSizeMissmatchException, IllegalArgumentException, FileNotFoundException {
        FileLoader fileReader = new FileLoader();
        fileReader.load("/Users/jaimathur/IdeaProjects/A12002/Test/InvalidMazes/InvalidMazeDimensions");
    }


    /**
     * Test for MazeMalformedException when the maze file is missing a specified dimension.
     *
     * @throws MazeMalformedException     If the maze file is malformed.
     * @throws MazeSizeMissmatchException If the maze size mismatches the provided size.
     * @throws IllegalArgumentException   If the maze contains invalid characters.
     * @throws FileNotFoundException      If the maze file is not found.
     */
    @Test(expected = MazeMalformedException.class)
    public void testMazeMalformedException2() throws MazeMalformedException, MazeSizeMissmatchException, IllegalArgumentException, FileNotFoundException {
        FileLoader fileReader = new FileLoader();
        fileReader.load("/Users/jaimathur/IdeaProjects/A12002/Test/InvalidMazes/InvalidMazeDimensions2");
    }

    /**
     * Test for MazeSizeMissmatchException when the number of columns in the maze
     * does not match the specified number of columns.
     *
     * @throws MazeMalformedException     If the maze file is malformed.
     * @throws MazeSizeMissmatchException If the maze size mismatches the provided size.
     * @throws IllegalArgumentException   If the maze contains invalid characters.
     * @throws FileNotFoundException      If the maze file is not found.
     */
    @Test(expected = MazeSizeMissmatchException.class)
    public void testMazeSizeMismatchException() throws MazeMalformedException, MazeSizeMissmatchException, IllegalArgumentException, FileNotFoundException {
        FileLoader fileReader = new FileLoader();
        fileReader.load("/Users/jaimathur/IdeaProjects/A12002/Test/InvalidMazes/InvalidMazeSize");
    }

    /**
     * Test for MazeSizeMissmatchException when the number of rows in the maze
     * is greater than the number of specified rows in the maze dimensions.
     *
     * @throws MazeMalformedException     If the maze file is malformed.
     * @throws MazeSizeMissmatchException If the maze size mismatches the provided size.
     * @throws IllegalArgumentException   If the maze contains invalid characters.
     * @throws FileNotFoundException      If the maze file is not found.
     */
    @Test(expected = MazeSizeMissmatchException.class)
    public void testMazeSizeMismatchException2() throws MazeMalformedException, MazeSizeMissmatchException, IllegalArgumentException, FileNotFoundException {
        FileLoader fileReader = new FileLoader();
        fileReader.load("/Users/jaimathur/IdeaProjects/A12002/Test/InvalidMazes/InvalidMazeSize2");
    }

    /**
     * Test for MazeSizeMissmatchException when the number of rows in the maze
     * is less than the number of specified rows in the maze dimensions.
     *
     * @throws MazeMalformedException     If the maze file is malformed.
     * @throws MazeSizeMissmatchException If the maze size mismatches the provided size.
     * @throws IllegalArgumentException   If the maze contains invalid characters.
     * @throws FileNotFoundException      If the maze file is not found.
     */
    @Test(expected = MazeSizeMissmatchException.class)
    public void testMazeSizeMismatchException3() throws MazeMalformedException, MazeSizeMissmatchException, IllegalArgumentException, FileNotFoundException {
        FileLoader fileReader = new FileLoader();
        fileReader.load("/Users/jaimathur/IdeaProjects/A12002/Test/InvalidMazes/InvalidMazeSize3");
    }

    /**
     * Test for IllegalArgumentException when the maze contains invalid characters.
     *
     * @throws MazeMalformedException     If the maze file is malformed.
     * @throws MazeSizeMissmatchException If the maze size mismatches the provided size.
     * @throws IllegalArgumentException   If the maze contains invalid characters.
     * @throws FileNotFoundException      If the maze file is not found.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentException() throws MazeMalformedException, MazeSizeMissmatchException, IllegalArgumentException, FileNotFoundException {
        FileLoader fileReader = new FileLoader();
        fileReader.load("/Users/jaimathur/IdeaProjects/A12002/Test/InvalidMazes/InvalidMazeCharacters");
    }


    /**
     * Test for FileNotFoundException when the maze file is not found.
     *
     * @throws MazeMalformedException     If the maze file is malformed.
     * @throws MazeSizeMissmatchException If the maze size mismatches the provided size.
     * @throws IllegalArgumentException   If the maze contains invalid characters.
     * @throws FileNotFoundException      If the maze file is not found.
     */
    @Test(expected = FileNotFoundException.class)
    public void testFileNotFoundException() throws MazeMalformedException, MazeSizeMissmatchException, IllegalArgumentException, FileNotFoundException {
        FileLoader fileReader = new FileLoader();
        fileReader.load("/nonexistent/path/to/file.txt");
    }

    /**
     * Test for successfully loading a valid maze file.
     *
     * @throws MazeMalformedException     If the maze file is malformed.
     * @throws MazeSizeMissmatchException If the maze size mismatches the provided size.
     * @throws IllegalArgumentException   If the maze contains invalid characters.
     * @throws FileNotFoundException      If the maze file is not found.
     */
    @Test
    public void testValidMazeLoading() throws MazeMalformedException, MazeSizeMissmatchException, IllegalArgumentException, FileNotFoundException {
        FileLoader filerReader = new FileLoader();
        char[][] maze = filerReader.load("/Users/jaimathur/IdeaProjects/A12002/src/Mazes/Maze001.txt");
        assertEquals(7, maze.length);
        assertEquals(7, maze[0].length);
        assertEquals('S', maze[1][1]);
        assertEquals('E', maze[5][5]);
    }
}

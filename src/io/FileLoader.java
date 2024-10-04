package io;

import exceptions.MazeMalformedException;
import exceptions.MazeSizeMissmatchException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * A class responsible for loading maze data from a file.
 * Implements FileInterface interface.
 */
public class FileLoader implements FileInterface {


    /**
     * Reads maze text file and loads maze data from the file and returns
     * it as a 2D character array.
     * Each array in the 2D array represents a row of the Maze.
     *
     * @param filename The path to the maze file to be loaded.
     * @return A two-dimensional character array representing the loaded maze.
     * @throws MazeMalformedException If the maze file has invalid formatting.
     * @throws MazeSizeMissmatchException If the maze dimensions do not match the provided size.
     * @throws IllegalArgumentException If the maze contains invalid characters.
     * @throws FileNotFoundException If the specified file is not found.
     */
    @Override
    public char[][] load(String filename) throws MazeMalformedException, MazeSizeMissmatchException, IllegalArgumentException, FileNotFoundException {
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(filename))) {

            char[] validCharacters = {'#', ' ', 'S', '.', 'E'};
            String[] dimensions = reader.readLine().split(" ");
            // if dimensions doesn't contain a width and height -> MazeMalformed
            if (dimensions.length != 2) {
                throw new MazeMalformedException("Invalid dimensions format.");
            }
            int numRows = Integer.parseInt(dimensions[0]);
            int numCol = Integer.parseInt(dimensions[1]);
            // if width and/or height is not an odd number -> MazeMalformed
            if (numRows % 2 ==0 || numCol % 2 ==0) {
                throw new MazeMalformedException("Number of Rows and columns should be odd");
            }
            char[][] maze = new char[numRows][numCol];
            // Read and populate the maze
            int lineCount = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                // if the current line does not contain the same number of
                // characters as the width of the maze -> MazeSizeMismatch
                if (line.length() != numCol) {
                    throw new MazeSizeMissmatchException("Maze dimensions do not match the provided size.");
                } else if (lineCount == numRows) {
                    // if line count is equal to num of rows, the next line should have been null
                    // but since the while loop is still running there is more lines than numRows
                    throw new MazeSizeMissmatchException("Number of lines does not match numRows.");
                }
                // checks if character in the current line is valid
                // if character is valid it adds that character to the 2D array
                for (int i = 0; i < numCol; i++) {
                    char c = line.charAt(i);
                    boolean isValid = false;
                    for (char validChar : validCharacters) {
                        if (c == validChar) {
                            isValid = true;
                            break;
                        }
                    }
                    // if character is not valid -> IllegalArgument
                    if (!isValid) {
                        throw new IllegalArgumentException("Invalid Character");
                    }
                    maze[lineCount][i] = c;
                }
                lineCount++;
            }
            // Checking that the number of lines matches numRows
            if (lineCount != numRows || reader.readLine() != null) {
                throw new MazeSizeMissmatchException("Number of lines does not match numRows.");
            }
            return maze;
        }
        catch (IllegalArgumentException | FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}



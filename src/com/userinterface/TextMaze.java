package com.userinterface;

import com.models.*;

import java.util.function.Consumer;

/**
 * TextMaze handles the output of the Maze
 * It holds the many char elements for the Maze
 */
public class TextMaze {
    private String title = "Maze:";
    private char heroElement = '@';
    private char assassin= '!';
    private char battleTokenElement = '$';
    private char wallElement = '#';
    private char openElement = ' ';
    private char hiddenElement = '.';
    private char deadElement = 'X';

    public void printMaze(Maze maze, boolean isHidden) {
        Cell[][] mazeArray = maze.getMazeArray();
        int height = maze.getHeight();
        int width = maze.getWidth();
        Consumer<String> display = a -> System.out.println(a);
        display.accept("\n" + title);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell cell = mazeArray[y][x];
                printElement(cell, isHidden);
            }
            display.accept("\n");
        }
    }

    private void printElement(Cell cell, boolean isHidden) {
        Cell.Element element = cell.getElement();
        switch (element) {
            case HERO -> {
                System.out.print(heroElement);
            }
            case ASSASSIN -> {
                System.out.print(assassin);
            }
            case BATTLETOKEN -> {
                System.out.print(battleTokenElement);
            }
            case DEAD -> {
                System.out.print(deadElement);
            }
            default -> {
                if (!isHidden || cell.isVisible()) {
                    if (element == Cell.Element.WALL) {
                        System.out.print(wallElement);
                    } else if (element == Cell.Element.OPEN) {
                        System.out.print(openElement);
                    }
                } else {
                    System.out.print(hiddenElement);
                }
            }
        }
    }
}

package com.models;

import java.util.*;

/**
 * Maze class handles the Maze algorithm using recursive backtracker
 * It generate the mazes and check the restrictions
 * It holds the mazeArray which is a array of Cells
 */
public final class Maze {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 15;
    private static final Cell[][] mazeArray = new Cell[HEIGHT][WIDTH];

    public static int getWidth() {
        return WIDTH;
    }

    public static int getHeight() {
        return HEIGHT;
    }

    public static Cell[][] getMazeArray() {
        return mazeArray;
    }

    public static Maze getInstance()
    {
        Maze maze = new Maze();
        return maze;
    }
    private Maze() {
        boolean isValidMaze = false;
        int[] position;
        int wallCount;
        int openCount;

        while (!isValidMaze) {
            // Set all Wall + visible borders
            isValidMaze = true;
            for (int y = 0; y < HEIGHT; y++) {
                mazeArray[y][0] = new Cell(true, Cell.Element.WALL);
                for (int x = 1; x < WIDTH -1; x++) {
                    mazeArray[y][x] = new Cell(false, Cell.Element.WALL);
                }
                mazeArray[y][WIDTH -1] = new Cell(true, Cell.Element.WALL);
            }

            for (int x = 0; x < WIDTH; x++) {
                mazeArray[0][x] = new Cell(true, Cell.Element.WALL);
                mazeArray[HEIGHT -1][x] = new Cell(true, Cell.Element.WALL);
            }

            generateMaze();
            mazeArray[1][WIDTH -2].setElement(Cell.Element.OPEN);
            mazeArray[HEIGHT -2][1].setElement(Cell.Element.OPEN);
            mazeArray[HEIGHT -2][WIDTH -2].setElement(Cell.Element.OPEN);

            //Remove 15 random walls
            Random random = new Random();
            for (int i = 0; i < 15; i++) {
                boolean hasRemovedWall = false;
                while (!hasRemovedWall) {
                    int x = random.nextInt(WIDTH - 2) + 1;
                    int y = random.nextInt(HEIGHT - 2) + 1;
                    position = new int[] {x,y};

                    if (mazeArray[y][x].getElement() == Cell.Element.WALL
                            && isValidRemove(position)) {
                        mazeArray[y][x].setElement(Cell.Element.OPEN);
                        hasRemovedWall = true;
                    }
                }
            }

            // Check for 2by2 walls
            for (int y = 1; y < HEIGHT -2; y++) {
                for (int x = 1; x < WIDTH -2; x++) {
                    position = new int[]{x, y};
                    wallCount = countTwoByTwoSquare(position, Cell.Element.WALL);
                    openCount = countTwoByTwoSquare(position, Cell.Element.OPEN);

                    if (wallCount == 4 || openCount == 4) {
                        isValidMaze = false;
                    }
                }
            }
        }
    }

    // Recursive backtracker
    // Algorithm: https://en.wikipedia.org/wiki/Maze_generation_algorithm#Recursive_backtracker
    private void generateMaze() {
        Random random = new Random();
        Deque<int[]> positionStack = new ArrayDeque<>();
        GameElements.Move move;

        // 1. Choose initial cell, marked visited, push to stack
        int[] startPosition = {1,1};
        mazeArray[startPosition[1]][startPosition[0]].setElement(Cell.Element.OPEN);
        positionStack.push(startPosition);

        // 2. While stack is not empty
        while (positionStack.size() != 0) {
            boolean hasNeighbours = false;

            // 1. Pop a cell from stack, make it a current cell
            int[] position = positionStack.pop();
            int[] nextWall = new int[1];
            int[] nextCell = new int[1];

            //2. Check + choose unvisited neighbours
            ArrayList<GameElements.Move> moves = new ArrayList<>();
            Collections.addAll(moves, GameElements.Move.UP, GameElements.Move.DOWN,
                    GameElements.Move.LEFT, GameElements.Move.RIGHT);

            while(moves.size() != 0 && !hasNeighbours) {
                int randomNumber = random.nextInt(moves.size());
                move = moves.get(randomNumber);
                moves.remove(move);

                nextWall = position.clone();
                nextCell = position.clone();
                applyMoveToPositions(nextWall, nextCell, move);

                if (!isOutOfBounds(nextWall) && !isOutOfBounds(nextCell)
                        && mazeArray[nextCell[1]][nextCell[0]].getElement() == Cell.Element.WALL) {
                    hasNeighbours = true;
                }
            }

            if (hasNeighbours) {
                // 1. Push current cell to stack
                positionStack.push(position);

                // 3. Remove Wall between cell/chosen cell
                mazeArray[nextWall[1]][nextWall[0]].setElement(Cell.Element.OPEN);
                mazeArray[nextCell[1]][nextCell[0]].setElement(Cell.Element.OPEN);

                // 4. Push Chosen wall to stack
                positionStack.push(nextCell);
            }
        }

    }

    private void applyMoveToPositions (int[] nextWall, int[] nextCell, GameElements.Move move) {
        switch (move) {
            case UP -> {
                nextWall[1]++;
                nextCell[1] = nextCell[1] + 2;
            }
            case DOWN -> {
                nextWall[1]--;
                nextCell[1] = nextCell[1] - 2;
            }
            case LEFT -> {
                nextWall[0]--;
                nextCell[0] = nextCell[0] - 2;
            }
            case RIGHT -> {
                nextWall[0]++;
                nextCell[0] = nextCell[0] + 2;
            }
            default -> {
                throw new AssertionError();
            }
        }
    }

    private boolean isOutOfBounds(int[] position) {
        int xCell = position[0];
        int yCell = position[1];

        if (xCell <= 0 || xCell >= WIDTH -1
                || yCell <= 0 || yCell >= HEIGHT -1) {
            return true;
        } else {
            return false;
        }
    }

    private int countTwoByTwoSquare(int[] position, Cell.Element element) {
        int count = 0;
        int xCell = position[0];
        int yCell = position[1];

        for (int y = yCell; y < yCell+2; y++) {
            for (int x = xCell; x < xCell+2; x++) {
                if (mazeArray[y][x].getElement() == element) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isValidRemove(int[] position) {
        int xCell = position[0];
        int yCell = position[1];

        int[] up = {xCell, yCell-1};
        int[] left = {xCell-1, yCell};
        int[] right = {xCell+1, yCell};
        int[] down = {xCell, yCell+1};

        int[][] validPositions = {up, left, right, down};

        for (int[] validPosition : validPositions) {
            int x = validPosition[0];
            int y = validPosition[1];

            if (mazeArray[y][x].getElement() == Cell.Element.OPEN) {
                return true;
            }
        }

        return false;
    }
}

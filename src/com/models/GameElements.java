package com.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * GameElements handles the connection of the Maze and Elements
 * It holds the Hero, BattleToken and Assassin and have a public enum Move
 * It handles the initializing and moving the Elements to reflect on Maze
 */
public class GameElements {
    private final Hero hero = new Hero();
    private final Assassin[] assassins = {new Assassin(), new Assassin(), new Assassin()};
    private final BattleToken battleToken = new BattleToken();
    public enum Move {UP, DOWN, LEFT, RIGHT}

    public Hero getHero() {
        return hero;
    }

    public Assassin[] getAssassins() {
        return assassins;
    }

    public BattleToken getBattleToken() {
        return battleToken;
    }

    public void initializeHero(Maze maze) {
        Cell[][] mazeArray = maze.getMazeArray();
        int[] position = hero.getPosition();
        mazeArray[position[1]][position[0]].setElement(Cell.Element.HERO);
        heroSurroundView(maze);
    }

    public void moveHero(Maze maze, Move move) {
        Cell[][] mazeArray = maze.getMazeArray();
        int[] previousPosition = hero.getPosition().clone();
        hero.setPreviousPosition(previousPosition);
        hero.movePosition(move);
        int[] position = hero.getPosition();

        // Allow Hero movement when not on wall
        if (mazeArray[position[1]][position[0]].getElement() == Cell.Element.WALL) {
            hero.setPosition(previousPosition);
        } else {
            mazeArray[previousPosition[1]][previousPosition[0]].setElement(Cell.Element.OPEN);
            mazeArray[position[1]][position[0]].setElement(Cell.Element.HERO);
            heroSurroundView(maze);
        }
    }

    private void heroSurroundView(Maze maze) {
        int[] position = hero.getPosition();
        Cell[][] mazeArray = maze.getMazeArray();

        for (int y = position[1]-1; y < position[1]+2; y++) {
            for (int x = position[0]-1; x < position[0]+2; x++) {
                mazeArray[y][x].setVisible(true);
            }
        }
    }

    public void initializeAssassins(Maze maze) {
        Cell[][] mazeArray = maze.getMazeArray();
        int width = maze.getWidth();
        int height = maze.getHeight();
        int[][] positions = {{width-2, 1}, {1, height-2}, {width-2, height-2}};

        for (int i = 0; i < 3; i++) {
            assassins[i].setPosition(positions[i]);
            mazeArray[positions[i][1]][positions[i][0]].setElement(Cell.Element.ASSASSIN);
        }
    }

    public void moveAssassins(Maze maze) {
        Random random = new Random();
        Cell[][] mazeArray = maze.getMazeArray();
        boolean isAllowed = false;
        Move move;

        for (int i = 0; i < 3; i++) {
            Assassin assassin = assassins[i];
            if (!assassin.isDead()) {
                ArrayList<Move> moves = new ArrayList<>();
                Collections.addAll(moves, Move.UP, Move.DOWN, Move.LEFT, Move.RIGHT);
                Move backTrack = assassin.getBackTrack();
                moves.remove(backTrack);

                while (!isAllowed) {
                    if (moves.size() == 0) {
                        move = backTrack;
                    } else {
                        int randomNumber = random.nextInt(moves.size());
                        move = moves.get(randomNumber);
                        moves.remove(move);
                    }

                    int[] previousPosition = assassin.getPosition().clone();
                    assassin.movePosition(move);
                    int[] position = assassin.getPosition();

                    // Allow Assassin movement when not on wall
                    if (mazeArray[position[1]][position[0]].getElement() == Cell.Element.WALL) {
                        assassin.setPosition(previousPosition);
                    } else {
                        mazeArray[previousPosition[1]][previousPosition[0]].setElement(Cell.Element.OPEN);
                        mazeArray[position[1]][position[0]].setElement(Cell.Element.ASSASSIN);
                        isAllowed = true;
                    }
                }
                isAllowed = false;
            }
        }

    }

    public void initializeBattleToken(Maze maze) {
        Random random = new Random();
        Cell[][] mazeArray = maze.getMazeArray();
        int width = maze.getWidth();
        int height = maze.getHeight();
        boolean isAllowed = false;

        // Randomly place BattleToken that's not on Wall/Hero
        while (!isAllowed) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            Cell cell = mazeArray[y][x];

            if (cell.getElement() != Cell.Element.WALL
                    && cell.getElement() != Cell.Element.HERO) {
                battleToken.setPosition(new int[]{x, y});
                cell.setElement(Cell.Element.BATTLETOKEN);
                isAllowed = true;
            }
        }
    }

    public void refreshBattleToken(Maze maze) {
        Cell[][] mazeArray = maze.getMazeArray();
        int[] position = battleToken.getPosition();
        Cell cell = mazeArray[position[1]][position[0]];

        // Show BattleToken when Hero/Monster not on it
        if (cell.getElement() != Cell.Element.HERO
                && cell.getElement() != Cell.Element.ASSASSIN) {
            cell.setElement(Cell.Element.BATTLETOKEN);
        }

    }
}

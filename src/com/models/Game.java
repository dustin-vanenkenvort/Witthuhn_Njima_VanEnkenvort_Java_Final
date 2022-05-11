package com.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

/**
 * Game handles the main game logic
 * It holds the Maze and GameElement objects and key information of the game
 * It handles the start and each round of the game and the collision of the objects
 */
public class Game {

    private final Maze maze;
    private final GameElements gameElements = new GameElements();
    private boolean isGameOver = false;
    private boolean hasWon = false;
    private int assassinsNeeded;
    private int battleTokensHeld;
    private int assassinsAlive;
    private boolean isValidMove;

    public Game()
    {
        maze = Maze.getInstance();
    }

    public Maze getMaze() {
        return maze;
    }

    public boolean isGameOver(){
        return isGameOver;
    }

    public boolean hasWon() {
        return hasWon;
    }

    public int getAssassinsNeeded() {
        return assassinsNeeded;
    }

    public void setAssassinsNeeded(int assassinsNeeded) {
        this.assassinsNeeded = assassinsNeeded;
    }

    public int getBattleTokenHeld() {
        return battleTokensHeld;
    }

    public int getAssassinsAlive() {
        return assassinsAlive;
    }

    public boolean isValidMove() {
        return isValidMove;
    }

    public void startGame() {
        gameElements.initializeHero(maze);
        gameElements.initializeBattleToken(maze);
        gameElements.initializeAssassins(maze);
        assassinsNeeded = 3;
        battleTokensHeld = 0;
        assassinsAlive = 3;
    }

    public void nextRound(GameElements.Move move) {
        gameElements.moveHero(maze, move);
        Hero hero = gameElements.getHero();

        // Hero hit Wall
        if (hero.getPreviousPosition() == hero.getPosition()) {
            isValidMove = false;
            return;
        }

        checkHeroAssassin();

        // Hero with no BattleTokens meets Assassin
        if (isGameOver) {
            return;
        }

        gameElements.moveAssassins(maze);
        checkHeroAssassin();
        checkHeroBattleToken();
        battleTokensHeld = hero.getBattleToken();

        // Win Game
        if (assassinsNeeded <= 3 - assassinsAlive) {
            hasWon = true;
            isGameOver = true;
        }

        gameElements.refreshBattleToken(maze);
        isValidMove = true;
    }

    private void checkHeroAssassin() {
        Hero hero = gameElements.getHero();
        Assassin[] assassins = gameElements.getAssassins();
        Cell[][] mazeArray = maze.getMazeArray();
        int[] heroPosition = hero.getPosition();
        int xHero = heroPosition[0];
        int yHero = heroPosition[1];

        for (Assassin assassin : assassins) {
            if (!assassin.isDead() && Arrays.equals(heroPosition, assassin.getPosition())) {
                if (hero.getBattleToken() > 0) {
                    hero.loseBattleToken();
                    Function<Integer, Integer> minusOne = assassinsAlive -> assassinsAlive-1;
                    assassin.setDead(true);
                    mazeArray[yHero][xHero].setElement(Cell.Element.HERO);
                } else {
                    mazeArray[yHero][xHero].setElement(Cell.Element.DEAD);
                    isGameOver = true;
                }
            }
        }
    }

    private void checkHeroBattleToken() {
        Hero hero = gameElements.getHero();
        BattleToken<Integer> battleToken = gameElements.getBattleToken();
        ArrayList<Integer> array= battleToken.getPosition();
        Integer[] heroPositions = Arrays.stream(hero.getPosition()).boxed().toArray( Integer[]::new );
        Integer[] positions = array.toArray(new Integer[0]);
        if (Arrays.equals(heroPositions,positions)) {
            hero.gainBattleToken();
            gameElements.initializeBattleToken(maze);
        }

    }
}

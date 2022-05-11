package com.userinterface;

import com.models.*;

import java.io.IOException;

/**
 * Main class handles the options of the menu options
 * It holds the objects in the TextUI and the main game object
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Game game = new Game();
        TextMenu textMenu = new TextMenu();
        TextMaze textMaze = new TextMaze();
        UserInput userInput = new UserInput();

        char move;

        game.startGame();
        Maze maze = game.getMaze();

        textMenu.printHelp();
        textMaze.printMaze(maze, true);
        printGameState(game, textMenu);

        while (!game.isGameOver()) {
            userInput.receiveInput();
            move = userInput.getMove();
            switch (move) {
                case '?' ->  {
                    textMenu.printHelp();
                }
                case 'c' -> {
                    game.setAssassinsNeeded(1);
                }
                case 'm' -> {
                    textMaze.printMaze(maze, false);
                    printGameState(game, textMenu);
                }
                default -> {
                    // When move is one of four directions
                    GameElements.Move moveElement = userInput.convertInputToMove(move);
                    game.nextRound(moveElement);

                    if (!game.isGameOver()) {
                        if (game.isValidMove()) {
                            textMaze.printMaze(maze, true);
                            printGameState(game, textMenu);
                        } else {
                            userInput.invalidMove();
                        }
                    }
                }
            }
        }

        textMenu.printFinalMessage(game.hasWon());
        textMaze.printMaze(maze, false);
    }

    private static void printGameState(Game game, TextMenu textMenu) {
        int assassinsNeeded = game.getAssassinsNeeded();
        int battleTokensHeld = game.getBattleTokenHeld();
        int assassinsAlive = game.getAssassinsAlive();
        textMenu.printState(assassinsNeeded, battleTokensHeld, assassinsAlive);
    }

}

package com.userinterface;

import com.models.*;
import java.util.Scanner;
import static com.models.GameElements.Move.*;

/**
 * UserInput handles the user input for the Game
 * It takes user input, validate it, and have a user input print
 */
public class UserInput {
    private final String enterMove = "Enter your move [WASD?]: ";
    private final String invalidKey = "Invalid move. Please enter just A (left), S (down), D (right), or W (up).";
    private final String invalidMoveMessage = "Invalid move: you cannot move through walls!";
    private final char[] validKeys = {'w', 'a', 's', 'd', '?', 'm', 'c'};
    private char move;

    public char getMove() {
        return move;
    }

    public void receiveInput() {
        Scanner in = new Scanner(System.in);
        boolean isValid = false;

        while (!isValid) {
            System.out.print(enterMove);
            String input = in.nextLine();
            move = validateChar(input);

            if (move != ' ') {
                isValid = true;
            } else {
                System.out.println(invalidKey);
            }
        }
    }

    private char validateChar(String input) {
        try {
            char inputChar = input.charAt(0);
            char lowerChar = Character.toLowerCase(inputChar);
            for (char key:validKeys) {
                if (key == lowerChar) {
                    return lowerChar;
                }
            }
            return ' ';
        } catch (Exception e) {
            return ' ';
        }
    }

    public void invalidMove() {
        System.out.println(invalidMoveMessage);
    }

    public GameElements.Move convertInputToMove(char move) {
        switch (move) {
            case 'w' -> {
                return UP;
            }
            case 's' -> {
                return DOWN;
            }
            case 'a' -> {
                return LEFT;
            }
            case 'd' -> {
                return RIGHT;
            }
            default -> {
                return null;
            }
        }
    }
}

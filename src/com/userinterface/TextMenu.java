package com.userinterface;


/**
 * TextMenu handles the print messages of the game
 * It has Help, State, and Game Over message
 */
public class TextMenu {
    private String[] headers = {"DIRECTIONS", "LEGEND", "MOVES"};
    private String[] directions = {"Kill 3 Assassin!"};
    private String[] legend = {"#: Wall",
            "@: You (a hero)", "!: Assassin",
            "$: BattleToken", ".: Unexplored space"};
    private String[] moves = {"Use W (up), A (left), S (down), and D (right) to move.",
            "(You must press enter after each move) ."};
    private String[] state = {"Total number of assassins to be executed: ",
            "Number of battle tokens currently in possession: ",
            "Number of assassins alive: "};
    private String win = "Congrats, you win the game!";
    private String lose = "I'm sorry, you have been assassinated!";

    public void printHelp() {
        String[][] body = {directions, legend, moves};

        for (int i = 0; i < headers.length; i++) {
            System.out.println(headers[i]);
            for (int j = 0; j < body[i].length; j++) {
                System.out.println("    " + body[i][j]);
            }
        }
    }

    public void printState(int assassinsNeeded, int battleTokensHeld, int assassinsAlive) {
        int[] count = {assassinsNeeded, battleTokensHeld, assassinsAlive};

        for (int i = 0; i <state.length; i++) {
            System.out.println(state[i] + count[i]);
        }
    }

    public void printFinalMessage(boolean hasWon) {
        if (hasWon) {
            System.out.println(win);
        } else {
            System.out.println(lose);
        }
    }
}
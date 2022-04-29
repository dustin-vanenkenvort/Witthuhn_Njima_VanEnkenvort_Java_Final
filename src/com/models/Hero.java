package com.models;

/**
 * Hero handles the Hero element information
 * It holds the battletoken count and it's position
 */
public class Hero {
    // 2.2 - Example of encapsulation.
    private int battletoken = 0;
    private int[] position = {1,1};
    private int[] previousPosition;

    public int getBattleToken() {
        return battletoken;
    }

    public void gainBattleToken() {
        battletoken++;
    }

    public void loseBattleToken() {
        battletoken--;
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public int[] getPreviousPosition() {
        return previousPosition;
    }

    public void setPreviousPosition(int[] previousPosition) {
        this.previousPosition = previousPosition;
    }

    public void movePosition(GameElements.Move move) {
        switch (move) {
            case UP -> {
                position[1]--;
            }
            case DOWN -> {
                position[1]++;
            }
            case LEFT -> {
                position[0]--;
            }
            case RIGHT -> {
                position[0]++;
            }
            default -> {
                throw new AssertionError();
            }
        }
    }
}

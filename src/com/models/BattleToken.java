package com.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * BattleToken handles the power element
 * It holds the position of the power
 */
public class BattleToken<T> {
    private ArrayList<T> position;

    public ArrayList<T> getPosition() {
        return position;
    }

    public void setPosition(ArrayList<? extends Number> position) {
        this.position = (ArrayList<T>) position;
    }

    public void displayPosition()
    {

        Stream<T> stream = position.stream();
        stream.forEach(p -> System.out.println(p));
    }
}

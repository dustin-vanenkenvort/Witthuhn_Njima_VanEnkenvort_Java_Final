package com.models;

/**
 * Cell class holds the content of each cell in the maze
 * It has a public enum for the available elements in the cell
 * A Cell holds it's visibility and element
 */
public class Cell {
    private boolean isVisible;
    public enum Element {OPEN, WALL, HERO, ASSASSIN, BATTLETOKEN, DEAD}
    private Element element;

    public Cell (boolean isVisible, Element element) {
        this.isVisible = isVisible;
        this.element = element;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }
}

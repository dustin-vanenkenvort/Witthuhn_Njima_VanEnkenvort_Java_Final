package com.models;

import java.util.Comparator;
import java.util.Optional;

public class Character implements Comparable<Character>{

    String name, description;

    public Character(String name, String description)
    {
        this.name = name;
        this.description =  description;
    }

    public Character(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String cName)
    {
        name=cName;
    }
    public Optional<String> getDescription()
    {
        Optional<String> opt = Optional.ofNullable(description);
        return opt;
    }

    public void setDescription(String cDescription)
    {
        description=cDescription;
    }

    public String toString()
    {
        return "The character name is " + name;
    }


    @Override
    public int compareTo(Character au) {
        return this.name.compareTo(au.name);
    }
}
class Sortbyname implements Comparator<Character> {

    // Method
    // Sorting in ascending order of name
    public int compare(Character a, Character b)
    {
        return a.name.compareTo(b.name);
    }
}

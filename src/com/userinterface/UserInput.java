package com.userinterface;

import com.models.*;

import java.io.*;
import java.lang.Character;
import java.util.ArrayList;
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

    public void receiveInput()  {
        try {
            Console c=System.console();
            boolean isValid = false;
            while (!isValid) {
                System.out.print(enterMove);
                String input = c.readLine();
                move = validateChar(input);

                if (move != ' ') {
                    isValid = true;
                } else {
                    System.out.println(invalidKey);
                }
            }
        }
        catch (NullPointerException e)
        {
            try {
                File file = new File("C:/data/file.txt");
                if(!file.getName().equals("filee"))
                {
                    throw new IncorrectFileNameException("Incorrect filename : " + file.getName());
                }
                //creates a new file instance
                FileReader fr = new FileReader(file);   //reads the file
                BufferedReader br = new BufferedReader(fr);  //creates a buffering character input stream
                ArrayList<String> sb = new ArrayList();    //constructs a string buffer with no characters
                String line;
                while ((line = br.readLine()) != null) {
                    sb.add(line);      //appends line to string buffer
                    //line feed
                }
                fr.close();  //closes the stream and release the resources
                sb.add(e.getMessage());

                BufferedWriter out = new BufferedWriter(
                        new FileWriter("C:/data/file.txt", true));

                // Writing on output stream
                for (int i = 0; i < sb.size(); i++) {
                    out.write(sb.get(i) + "\n");
                }
                // Closing the connection
                out.close();
                //System.out.println(sb.toString());
            }
            catch (IOException ioe)
            {

            }
            catch (IncorrectFileNameException ex) {

            }
            finally {
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

class IncorrectFileNameException extends Exception {
    public IncorrectFileNameException(String errorMessage) {
        super(errorMessage);
    }
}

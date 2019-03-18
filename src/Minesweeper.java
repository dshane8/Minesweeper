import java.awt.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by DylanShane on 11/14/16.
 * Minesweeper
 */
public class Minesweeper {
    private int difficulty;
    private Scanner in;

    public Minesweeper(){

    }

    /**
     * Prints the difficulty menu
     */
    public void printMenu(){
        System.out.println("1. Beginner (9x9 & 10 Mines)");
        System.out.println("2. Intermediate (16x16 & 40 Mines)");
        System.out.println("3. Expert (16x30 & 99 Mines)");
        System.out.println("4. Custom");
        System.out.println();
        System.out.print("Please select a difficulty: ");
    }

    /**
     * Gets an integer between two values
     * @param min - the minimum value that could be entered
     * @param max - the maximum value that could be entered
     * @return - returns chosen integer
     */
    public int getMenuOption(int min, int max){
        boolean worked = false;
        while (!worked) {
            try {
                int option = -1;
                while (option < min || option > max) {
                    option = in.nextInt();
                    if (option < min || option > max) {
                        System.out.println("Error. Difficulty setting must be an integer between " + min + "-" + max);
                        System.out.print("Try again: ");
                    } else { return option; }
                }
            }
            catch (InputMismatchException ime){
                System.out.println("Sorry. You need to enter an integer.");
                System.out.print("Try again: ");
                in.nextLine();
            }
        } return -1;
    }

    /**
     * Prints game over text dependent on if the player won or not
     * @param result contains true if the player won, and false if the player lost
     * @param x is the x coordinate for the text
     * @param y is the y coordinate for the text
     */
    public void winOrLose(boolean result, double x, double y){
        if (result){
            StdDraw.text(x, y, "Game over. You won.");
        } else{
            StdDraw.text(x, y, "Game over. You lost.");
        }
    }

    /**
     * Runs MineSweeper
     */
    public void run() {
        boolean newGame = true;
        //Opening Text & Initializer
        System.out.println("Welcome to Minesweeper!");
        System.out.println("-----------------------");
        System.out.println("Controls:");
        System.out.println("Click - Select Square");
        System.out.println("F key - Flag Square");
        System.out.println("-----------------------");
        in = new Scanner(System.in);
        Grid grid;
        int height;
        int width;
        int mines;
        while (newGame) {
            //Asking player for difficulty & Setting up Game
            printMenu();
            this.difficulty = getMenuOption(1, 4);
            boolean keepPlaying = true;
            if (this.difficulty == 1) {
                height = 9;
                width = 9;
                mines = 10;
                grid = new Grid(width, height, mines);
            } else if (this.difficulty == 2) {
                height = 16;
                width = 16;
                mines = 40;
                grid = new Grid(width, height, mines);
            } else if (this.difficulty == 3) {
                height = 16;
                width = 30;
                mines = 99;
                grid = new Grid(width, height, mines);
            } else {
                System.out.print("Grid Height: ");
                height = getMenuOption(9, 30);
                System.out.print("Grid Width: ");
                width = getMenuOption(9, 30);
                System.out.print("Number of Mines: ");
                mines = getMenuOption(1, (width * height) - 1);
                grid = new Grid(width, height, mines);

            }
            int numFlags = mines;
            int minesFlagged = 0;
            boolean clickOn = false;
            boolean released = false;
            //Run Game
            while (keepPlaying) {
                boolean mine = false;
                StdDraw.setPenColor(Color.BLACK);
                StdDraw.text(width/5, height + .5, "Flags:");
                String flags = Integer.toString(numFlags);
                StdDraw.text(width/5 + width/9, height + .5, flags);
                //Click
                try {
                    if (StdDraw.mousePressed() && !clickOn) {
                        clickOn = true;
                        mine = grid.clicked(StdDraw.mouseX(), StdDraw.mouseY());
                    } else if (StdDraw.mousePressed() == false && clickOn) {
                        clickOn = false;
                    }
                    //Flag
                    if (StdDraw.isKeyPressed(70) && !released) {
                        released = true;
                        StdDraw.setPenColor(Color.WHITE);
                        StdDraw.filledSquare(width/5 + width/9, height + .5, .5);
                        grid.flag(StdDraw.mouseX(), StdDraw.mouseY());
                        StdDraw.setPenColor(Color.BLACK);
                    } else if (StdDraw.isKeyPressed(70) == false && released) {
                        released = false;
                    }
                } catch (ArrayIndexOutOfBoundsException aiobe){

                }
                if (mine) {
                    grid.revealMines();
                    keepPlaying = false;
                    winOrLose(false, width/2 + width/4, height + .5);
                }
                if (minesFlagged == mines && numFlags == 0){
                    keepPlaying = false;
                    winOrLose(true, width/2 + width/4, height + .5);
                }
                numFlags = grid.numFlags(mines);
                minesFlagged = grid.minesFlagged();
            }
            //Determine if player wants to play again
            System.out.print("Game over. Would you like to play again? ");
            String yesOrNo = in.next().toLowerCase();
            if (!yesOrNo.equals("yes") && !yesOrNo.equals("y") && !yesOrNo.equals("yeah")){
                newGame = false;
            } else { newGame = true; }
        }

        System.out.println("Thanks for playing!");

    }

    public static void main(String[] args) {
        Minesweeper game = new Minesweeper();
        game.run();
    }
}

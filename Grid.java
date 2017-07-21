package Lab09_Minesweeper;
import Library.StdDraw;

import java.util.Random;

/**
 * Created by DylanShane on 11/14/16.
 */
public class Grid {
    private Square[][] grid;
    private int W;
    private int H;
    private int mines;

    //Constructor
    public Grid(int width, int height, int mines){
        this.W = width;
        this.H = height;
        this.mines = mines;
        Random rand = new Random();
        int pixelFactor = 600/this.W;
        StdDraw.setCanvasSize(pixelFactor*width, pixelFactor*height + 1);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height + 1);
        //Making Grid
        this.grid = new Square[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.grid[i][j] = new Square(i + .5, j+ .5);
                this.grid[i][j].draw();
            }
        }
        //Placing Mines
        int count = 0;
        while (!(count == this.mines)) {
            int squareX = rand.nextInt(width);
            int squareY = rand.nextInt(height);
            if (!(this.grid[squareX][squareY].hasMine())){
                this.grid[squareX][squareY].giveMine();
                count++;
            }
        }
        //Setting Adjacent Mine Numbers
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int temp = 0;
                try {
                    if (this.grid[i + 1][j].hasMine()) {
                        temp++;
                    }
                } catch (ArrayIndexOutOfBoundsException aiobe){}
                try {
                    if (this.grid[i + 1][j + 1].hasMine()) {
                        temp++;
                    }
                } catch (ArrayIndexOutOfBoundsException aiobe){}
                try {
                    if (this.grid[i][j + 1].hasMine()) {
                        temp++;
                    }
                } catch (ArrayIndexOutOfBoundsException aiobe){}
                try {
                    if (this.grid[i - 1][j + 1].hasMine()) {
                        temp++;
                    }
                } catch (ArrayIndexOutOfBoundsException aiobe){}
                try {
                    if (this.grid[i - 1][j].hasMine()) {
                        temp++;
                    }
                } catch (ArrayIndexOutOfBoundsException aiobe){}
                try {
                    if (this.grid[i - 1][j - 1].hasMine()) {
                        temp++;
                    }
                } catch (ArrayIndexOutOfBoundsException aiobe){}
                try {
                    if (this.grid[i][j - 1].hasMine()) {
                        temp++;
                    }
                } catch (ArrayIndexOutOfBoundsException aiobe){}
                try {
                    if (this.grid[i + 1][j - 1].hasMine()) {
                        temp++;
                    }
                } catch (ArrayIndexOutOfBoundsException aiobe){}
                this.grid[i][j].setAdjMines(temp);
            }
        }

    }

    /**
     * takes in a click from the user and passes it to the checkAdj function
     * @param x - contains the x coordinate of the click
     * @param y - contains
     * @return whether or not the square has a bomb
     */
    public boolean clicked(double x, double y){
        checkAdj((int)x, (int)y );
        if (this.grid[(int)x][(int)y].isFlagged()){
            return false;
        }
        return this.grid[(int)x][(int)y].hasMine();
    }

    /**
     * Reveals a square and if the square has no adjacent mines, recursively checks all of its adjacent squares
     * to see if they also have no adjacent mines
     * @param x - x coordinate of click
     * @param y - y coordinate of click
     */
    public void checkAdj(int x, int y){
        this.grid[x][y].reveal();
        if (this.grid[x][y].getAdjMines() == 0 && !this.grid[x][y].hasMine()) {
            if (x + 1 < this.W && !this.grid[x + 1][y].isRevealed()) {
                checkAdj(x + 1, y);
            }
            if (x + 1 < this.W && y + 1 < this.H && !this.grid[x + 1][y + 1].isRevealed()) {
                checkAdj(x + 1, y + 1);
            }
            if (y + 1 < this.H && !this.grid[x][y + 1].isRevealed()){
                checkAdj(x, y + 1);
            }
            if (x - 1 >= 0 && y + 1 < this.H && !this.grid[x-1][y+1].isRevealed()) {
                checkAdj(x - 1, y + 1);
            }
            if (x - 1 >= 0 && !this.grid[x-1][y].isRevealed()) {
                checkAdj(x - 1, y);
            }
            if (x - 1 >= 0 && y - 1  >= 0 && !this.grid[x-1][y-1].isRevealed()) {
                checkAdj(x - 1, y - 1);
            }
            if (y - 1 >= 0 && !this.grid[x][y-1].isRevealed()) {
                checkAdj(x, y - 1);
            }
            if (x + 1 < this.W && y - 1 >= 0 && !this.grid[x+1][y-1].isRevealed()) {
                checkAdj(x + 1, y - 1);
            }
        }
    }

    /**
     * Flags the square that the mouse was hovering over when "F" was pressed
     * @param x - x coordinate from the mouse
     * @param y - y coordinate from the mouse
     */
    public void flag(double x, double y){ this.grid[(int)x][(int)y].flag(); }

    /**
     * Iterates through every square to see how many flags are left to be placed
     * @param mines is an integer amount of mines on the play field
     * @return integer representing the number of flags left to be placed
     */
    public int numFlags(int mines){
        int flags = mines;
        for (int i = 0; i < this.W; i++) {
            for (int j = 0; j < this.H; j++) {
                if (this.grid[i][j].isFlagged()){
                    flags--;
                }
            }
        }
        return flags;
    }

    /**
     * Iterates through squares and counts how many mines have been flagged
     * @return the number of mines that are flagged
     */
    public int minesFlagged(){
        int minesFlagged = 0;
        for (int i = 0; i < this.W; i++) {
            for (int j = 0; j < this.H; j++) {
                if (this.grid[i][j].hasMine() && this.grid[i][j].isFlagged()){
                    minesFlagged++;
                }
            }
        }
        return minesFlagged;
    }

    /**
     * Reveals all of the mines on the board
     */
    public void revealMines(){
        for (int i = 0; i < this.W; i++) {
            for (int j = 0; j < this.H; j++) {
                if (this.grid[i][j].hasMine()) {
                    this.grid[i][j].reveal();
                }
            }
        }
    }


}

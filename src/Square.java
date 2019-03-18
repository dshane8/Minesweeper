import java.awt.*;

/**
 * Created by DylanShane on 11/14/16.
 */
public class Square {
    private double xLoc;
    private double yLoc;
    private boolean isFlagged;
    private boolean hasMine;
    private boolean revealed;
    private int adjMines;

    //Constructor
    public Square(double x, double y){
        this.xLoc = x;
        this.yLoc = y;
        this.isFlagged = false;
        this.hasMine = false;
        this.revealed = false;
        this.adjMines = 0;
    }

    /**
     * Draws a blue square
     */
    public void draw() {
        StdDraw.setPenColor(Color.BLUE);
        StdDraw.filledSquare(this.xLoc, this.yLoc, .48);
    }

    /**
     * Reveals the square showing if it was a mine or not and if not, displays the number of adjacent mines
     */
    public void reveal(){
        if (!this.isFlagged) {
            this.revealed = true;
            if (this.hasMine){
                StdDraw.setPenColor(Color.RED);
                StdDraw.filledSquare(this.xLoc, this.yLoc, .48);
            } else {
                StdDraw.setPenColor(Color.GRAY);
                StdDraw.filledSquare(this.xLoc, this.yLoc, .48);
                StdDraw.setPenColor(Color.BLACK);
                String num = Integer.toString(this.adjMines);
                StdDraw.text(this.xLoc, this.yLoc, num);
            }
        }
    }

    /**
     *
     * @return whether or not a square has been revealed
     */
    public boolean isRevealed(){ return this.revealed; }

    /**
     * sets the number of adjacent mines for a square
     * @param n is the number of adjacent squares
     */
    public void setAdjMines(int n){ this.adjMines = n; }

    /**
     *
     * @return returns the number of adjacent mines
     */
    public int getAdjMines(){ return this.adjMines; }

    /**
     * places a mine on a square
     */
    public void giveMine(){ this.hasMine = true; }

    /**
     *
     * @return whether or not a square has a mine
     */
    public boolean hasMine(){ return this.hasMine; }

    /**
     *
     * @return whether or not a square is flagged
     */
    public boolean isFlagged(){ return this.isFlagged; }

    /**
     * Toggles a square to either be flagged or unflagged
     */
    public void flag(){
        if (!this.revealed) {
            if (this.isFlagged){
                StdDraw.setPenColor(Color.BLUE);
                StdDraw.filledSquare(this.xLoc, this.yLoc, .48);
                this.isFlagged = false;
            } else {
                StdDraw.setPenColor(150, 170, 230);
                StdDraw.filledSquare(this.xLoc, this.yLoc, .48);
                StdDraw.setPenColor(Color.RED);
                StdDraw.text(this.xLoc, this.yLoc, "F");
                this.isFlagged = true;
            }
        }
    }
}
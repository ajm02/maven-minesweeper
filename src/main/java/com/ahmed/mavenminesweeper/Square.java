package com.ahmed.mavenminesweeper;

public class Square {

    private Integer row;
    private Integer col;

    private boolean hasBomb = false;
    private boolean uncovered = false;

    private boolean flagged = false;
    private int adjacentBombs = 0;

    public char drawSquare() {
        if (uncovered && hasBomb) {
            return 'X';
        }

        if (flagged) {
            return 'F';
        }

        if (uncovered) {
            return Integer.toString(adjacentBombs).charAt(0);
        }

        return '.';
    }

    public boolean hasBomb() {
        return hasBomb;
    }

    public void setHasBomb(boolean hasBomb) {
        this.hasBomb = hasBomb;
    }

    public boolean isUncovered() {
        return uncovered;
    }

    public void setUncovered(boolean uncovered) {
        this.uncovered = uncovered;
    }

    public int getAdjacentBombs() {
        return adjacentBombs;
    }

    public void setAdjacentBombs(int adjacentBombs) {
        this.adjacentBombs = adjacentBombs;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Integer getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flag) {
        flagged = flag;
    }
}

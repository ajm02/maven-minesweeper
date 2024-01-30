package com.ahmed.mavenminesweeper;

import java.lang.reflect.Array;
import java.util.*;

public class Board {
    private Square[][] board;
    private int rows;
    private int cols;

    // Use only for test
    public Square[][] getBoard() {
        return board;
    }

    // Use only for test
    public int getRows() {
        return rows;
    }

    // Use only for test
    public int getCols() {
        return cols;
    }

    public Board(int rows, int columns) {
        board = new Square[rows][columns];
        this.rows = rows;
        this.cols = columns;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = new Square();
            }
        }
    }

    public boolean hasLost() {
        for (Square[] row : board) {
            for (Square col : row) {
                if (col.hasBomb() && col.isUncovered()) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean hasWon() {
        for (Square[] row : board) {
            for (Square col : row) {
                if (col.hasBomb() && !col.isFlagged()) {
                    return false;
                }
            }
        }

        return true;
    }

    public Square getSquare(int row, int col) {
        return board[row][col];
    }

    public boolean indexExists(int row, int col) {
        if (row < 0 || row > rows - 1 || col < 0 || col > cols - 1) {
            return false;
        }

        return true;
    }

    public String drawBoard() {
        String board = "   ";
        for (int i = 0; i < cols; i++) {
            board += Colours.ANSI_BLUE + "[" + Colours.ANSI_RESET
                    + Integer.toString(i) +
                    Colours.ANSI_BLUE + "]" + Colours.ANSI_RESET;
        }
        board += "\n";


        for (int i = 0; i < rows; i++) {
            board += Colours.ANSI_PURPLE + "[" + Colours.ANSI_RESET +
                    Integer.toString(i) +
                    Colours.ANSI_PURPLE + "]" + Colours.ANSI_RESET;
            for (int j = 0; j < cols; j++) {
                board += Colours.ANSI_GREEN + "[" + Colours.ANSI_RESET
                        + this.board[i][j].drawSquare() +
                        Colours.ANSI_GREEN + "]" + Colours.ANSI_RESET;
            }
            board += "\n";
        }

        return board;
    }

    public void setMines(int num) {
        int curRow = 0;
        int curCol = 0;
        while (num > 0) {
            if (board[curRow][curCol].hasBomb()) {
                curRow += 1;
            }

            board[curRow][curCol].setHasBomb(true);
            curCol = (curCol + 1) % cols;
            num -= 1;
        }
        shuffle();
    }

    public int getNumFlagged() {
        int num = 0;
        for (Square[] row : board) {
            for (Square col : row) {
                if (col.isFlagged()) {
                    num++;
                }
            }
        }

        return num;
    }

    private void shuffle() {
        Random random = new Random();

        for (int i = board.length - 1; i > -1; i--) {
            for (int j = board[i].length - 1; j > -1; j--) {
                int m = random.nextInt(i + 1);
                int n = random.nextInt(j + 1);

                Square temp = board[i][j];
                board[i][j] = board[m][n];
                board[m][n] = temp;
            }
        }

        setAdjacentBombs();
        setPoses();
    }

    private void setAdjacentBombs() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j].setAdjacentBombs(getAdjacentBombs(i, j));
            }
        }
    }

    private void setPoses() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j].setRow(i);
                board[i][j].setCol(j);
            }
        }
    }

    private int getAdjacentBombs(int row, int col) {
        int total = 0;
        for (int i = col - 1; i < col + 2; i++) {
            if (indexExists(row - 1, i)) {
                if (board[row - 1][i].hasBomb()) {
                    total += 1;
                }
            }
            if (indexExists(row + 1, i)) {
                if (board[row + 1][i].hasBomb()) {
                    total += 1;
                }
            }
        }

        if (indexExists(row, col - 1)) {
            if (board[row][col - 1].hasBomb()) {
                total += 1;
            }
        }

        if (indexExists(row, col + 1)) {
            if (board[row][col + 1].hasBomb()) {
                total += 1;
            }
        }

        return total;
    }

    // Array of row of uncovered, col of uncovered
    public Integer[] useHint() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Square square = board[i][j];
                if (!square.isFlagged() && !square.isUncovered() && square.hasBomb()) {
                    square.setFlagged(true);
                    return new Integer[] {i, j};
                }
            }
        }

        return new Integer[]{0, 0};
    }


    public void uncoverSquare(int row, int col) {
        board[row][col].setUncovered(true);
    }

    public void uncoverSquare(Square square) {
        square.setUncovered(true);
    }

    public void setFlag(int row, int col) {
        boolean curFlag = board[row][col].isFlagged();
        board[row][col].setFlagged(!curFlag);
    }

    private ArrayList<Square> getAdjacentSquares(int row, int col) {
        ArrayList<Square> adjacents = new ArrayList<Square>();
        for (int i = col - 1; i < col + 2; i++) {
            if (indexExists(row - 1, i)) {
                if (!board[row - 1][i].hasBomb()
                        && !board[row - 1][i].isUncovered()) {
                    adjacents.add(board[row - 1][i]);
                }
            }
            if (indexExists(row + 1, i)) {
                if (!board[row + 1][i].hasBomb()
                        && !board[row + 1][i].isUncovered()) {
                    adjacents.add(board[row + 1][i]);
                }
            }
        }

        if (indexExists(row, col - 1)) {
            if (!board[row][col - 1].hasBomb()
                    && !board[row][col - 1].isUncovered()) {
                adjacents.add(board[row][col - 1]);
            }
        }

        if (indexExists(row, col + 1)) {
            if (!board[row][col + 1].hasBomb()
                    && !board[row][col + 1].isUncovered()) {
                adjacents.add(board[row][col + 1]);
            }
        }

        return adjacents;
    }

    private void recursiveUncover(int row, int col) {
        uncoverSquare(row, col);
        ArrayList<Square> adjacents = getAdjacentSquares(row, col);
        Square curSquare = getSquare(row, col);
        if (curSquare.getAdjacentBombs() == 0) {
            for (Square square : adjacents) {
                recursiveUncover(square.getRow(), square.getCol());
            }
        }
    }

    public void firstUncover(int row, int col) {
        recursiveUncover(row, col);
    }
}

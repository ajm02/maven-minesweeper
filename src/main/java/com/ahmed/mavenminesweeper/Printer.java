package com.ahmed.mavenminesweeper;

public class Printer {

    public static void printStartMessages() {
        System.out.println("Welcome to minesweeper!");
        System.out.println("Enter the difficulty of this game: ");
        System.out.println("1. Easy - approximately 10% of squares are bombs," +
                "and you get 2 hints");
        System.out.println("2. Medium - approximately 20% of squares are bombs," +
                "and you get 1 hint");
        System.out.println("3. Hard - approximately 40% of squares are bombs," +
                "and you get no hints");
    }

    public static void printEnterValidDifficulty() {
        System.out.println("Please enter a valid difficulty: ");
    }

    public static void printEnterNumRows() {
        System.out.println("Please enter a number of rows (min 5, max 50): ");
    }

    public static void printEnterNumCols() {
        System.out.println("Please enter a number of columns (min 5, max 50): ");
    }

    public static void printEnterValidNumRows() {
        System.out.println("Please enter a valid number of rows: ");
    }

    public static void printEnterValidNumCols() {
        System.out.println("Please enter a valid number of columns: ");
    }

    public static void printCurRound(Board board, int hints, int flags) {
        System.out.println("Number of hints remaining: " + hints);
        System.out.println("Number of flags remaining: " + (flags - board.getNumFlagged()));
        System.out.println("The current state of the board looks like this:\n");
        printBoard(board);

        System.out.println("1: Uncover a space");
        System.out.println("2: Add or remove a flag");
        System.out.println("3: Use a hint");

        System.out.println("\nEnter your option: ");
    }

    public static void printBoard(Board board) {
        System.out.println(board.drawBoard());
    }

    public static void printYouWin() {
        System.out.println("You flagged all the bombs, you win!");
    }

    public static void printYouLose() {
        System.out.println("Oh no!, there was a bomb on that spot, you lose");
    }

    public static void printEnterValidChoice() {
        System.out.println("Please enter a valid choice: ");
    }
}

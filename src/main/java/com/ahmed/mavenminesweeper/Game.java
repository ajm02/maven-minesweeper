package com.ahmed.mavenminesweeper;

import java.util.*;

public class Game {

    private Board board;

    private int flags;
    private int hints;
    private final Scanner scanner = new Scanner(System.in);
    private final List<Integer> choices = List.of(1, 2, 3);

    private boolean firstUncover = true;

    public void playGame() {
        System.out.println("Welcome to minesweeper!");
        System.out.println("Enter the difficulty of this game: ");
        System.out.println("1. Easy - approximately 10% of squares are bombs," +
                "and you get 2 hints");
        System.out.println("2. Medium - approximately 20% of squares are bombs," +
                "and you get 1 hint");
        System.out.println("3. Hard - approximately 40% of squares are bombs," +
                "and you get no hints");
        String difficulty = scanner.next().toLowerCase();

        while (!difficulty.equals("easy") && !difficulty.equals("medium") && !difficulty.equals("hard")) {
            System.out.println("Please enter a valid difficulty");
            difficulty = scanner.next().toLowerCase();
        }

        System.out.println("Please enter a number of rows (min 5, max 50): ");
        Integer rows = null;
        while (rows == null) {
            scanner.nextLine();
            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number of rows: ");
            } else {
                int num = scanner.nextInt();
                if (num < 5 || num > 50) {
                    System.out.println("Please enter a valid number of rows: ");
                } else {
                    rows = num;
                }
            }
        }

        System.out.println("Please enter a number of columns (min 5, max 50): ");
        Integer columns = null;
        while (columns == null) {
            scanner.nextLine();
            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number of columns: ");
            } else {
                int num = scanner.nextInt();
                if (num < 5 || num > 50) {
                    System.out.println("Please enter a valid number of columns: ");
                } else {
                    columns = num;
                }
            }
        }

        int squares = rows * columns;

        switch (difficulty) {
            case "easy" -> {
                flags = Math.round(squares * 0.1f);
                hints = 2;
            }
            case "medium" -> {
                flags = Math.round(squares * 0.2f);
                hints = 1;
            }
            case "hard" -> {
                flags = Math.round(squares * 0.4f);
                hints = 0;
            }
        }

        board = new Board(rows, columns);
        start();
    }

    private void start() {
        board.setMines(flags);
        while (!board.hasLost() && !board.hasWon()) {
            System.out.println("Number of hints remaining: " + hints);
            System.out.println("Number of flags remaining: " + (flags - board.getNumFlagged()));
            System.out.println("The current state of the board looks like this:\n");
            System.out.println(board.drawBoard());

            System.out.println("1: Uncover a space");
            System.out.println("2: Add or remove a flag");
            System.out.println("3: Use a hint");

            System.out.println("\nEnter your option: ");

            Integer choice = null;
            while (choice == null) {
                scanner.nextLine();
                if (!scanner.hasNextInt()) {
                    System.out.println("Please enter a valid choice: ");
                } else {
                    int num = scanner.nextInt();
                    if (!choices.contains(num)) {
                        System.out.println("Please enter a valid choice: ");
                    } else {
                        choice = num;
                    }
                }
            }

            if (choice == 1) {
                uncoverSquare();
            } else if (choice == 2) {
                toggleFlag();
            } else {
                if (hints <= 0) {
                    System.out.println("You have no hints remaining");
                } else {
                    if (flags == board.getNumFlagged()) {
                        System.out.println("Please remove a flag before you " +
                                "use your hint");
                    } else {
                        hints--;
                        Integer[] discovered = board.useHint();
                        System.out.println("Bomb flagged at row " + discovered[0]
                        + ", column " + discovered[1]);
                    }
                }
            }
        }

        System.out.println(board.drawBoard());
        if (board.hasWon()) {
            System.out.println("You flagged all the bombs, you win!");
        } else {
            System.out.println("Oh no!, there was a bomb on that spot, you lose");
        }
    }

    // The returned array has row at pos 0 and col at pos 1
    private Integer[] getValidUncover() {
        Integer[] choice = null;
        while (choice == null) {
            System.out.println("Please enter the row of the square: ");
            scanner.nextLine();
            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid row");
            } else {
                int row = scanner.nextInt();
                System.out.println("Please enter the col of the square: ");
                scanner.nextLine();
                if (!scanner.hasNextInt()) {
                    System.out.println("Please enter a valid col");
                } else {
                    int col = scanner.nextInt();
                    if (!board.indexExists(row, col)) {
                        System.out.println("The provided index doesn't exist");
                    } else {
                        Square square = board.getSquare(row, col);
                        if (square.isUncovered()) {
                            System.out.println("The square at the " +
                                    "provided index has already been uncovered");
                        } else {
                            choice = new Integer[]{row, col};
                        }
                    }
                }
            }
        }

        return choice;
    }

    private Integer[] getValidFlagging() {
        Integer[] choice = null;
        while (choice == null) {
            System.out.println("Please enter the row of the square: ");
            scanner.nextLine();
            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid row");
            } else {
                int row = scanner.nextInt();
                System.out.println("Please enter the col of the square: ");
                scanner.nextLine();
                if (!scanner.hasNextInt()) {
                    System.out.println("Please enter a valid col");
                } else {
                    int col = scanner.nextInt();
                    if (!board.indexExists(row, col)) {
                        System.out.println("The provided index doesn't exist");
                    } else {
                        Square square = board.getSquare(row, col);
                        if (square.isUncovered() && !square.isFlagged()) {
                            System.out.println("This is square is already uncovered " +
                                    "you cannot add a flag to it");
                        } else {
                            choice = new Integer[]{row, col};
                        }
                    }
                }
            }
        }

        return choice;
    }

    private void uncoverSquare() {
        Integer[] choice = getValidUncover();

        if (firstUncover) {
            if (board.getSquare(choice[0], choice[1]).hasBomb()) {
                System.out.println("Oops, that first spot has a bomb, " +
                        " choose a different spot");
            } else {
                firstUncover = false;
                board.firstUncover(
                        choice[0], // row
                        choice[1] // col
                );
            }
        } else {
            board.uncoverSquare(
                    choice[0], // row
                    choice[1] // col
            );
        }
    }

    private void toggleFlag() {
        Integer[] choice = getValidFlagging();
        int chosenRow = choice[0];
        int chosenCol = choice[1];

        if (board.getNumFlagged() == flags) {
            if (!board.getSquare(chosenRow, chosenCol).isFlagged()) {
                System.out.println("You can't add one here, you've ran out of flags");
            } else {
                board.setFlag(chosenRow, chosenCol);
            }
        } else {
            board.setFlag(chosenRow, chosenCol);
        }
    }
}

import com.ahmed.mavenminesweeper.Board;
import com.ahmed.mavenminesweeper.Square;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    public void testConstruction() {
        try {
            Board board = new Board(10, 10);
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    board.getSquare(i, j);
                }
            }

            Assertions.assertEquals(10, board.getRows(),
                    "Board has incorrect number of rows");
            Assertions.assertEquals(10, board.getCols(),
                    "Board has incorrect number of columns");
        } catch (Exception e) {
            Assertions.fail("Board is too small or does not contain squares");
        }
    }

    @Test
    public void testRegisterLoss() {
        Board board = new Board(10, 10);
        Assertions.assertFalse(board.hasLost(), "Board says player " +
                "has lost on initialisation");
        Square square = board.getSquare(0, 0);
        square.setHasBomb(true);
        Assertions.assertFalse(board.hasLost(), "Board says player " +
                "has lost when they haven't");
        square.setUncovered(true);
        Assertions.assertTrue(board.hasLost(), "Player has lost, " +
                "but board says they haven't");
    }

    @Test
    public void testRegisterWon() {
        Board board = new Board(10, 10);
        Square square = board.getSquare(0, 0);
        square.setHasBomb(true);
        Square square2 = board.getSquare(0, 1);
        square2.setHasBomb(true);
        square.setFlagged(true);
        Assertions.assertFalse(board.hasWon(), "Board says player has won");
        square2.setFlagged(true);
        Assertions.assertTrue(board.hasWon(), "Board says player has not won");
    }

//    @Test
//    public void testGetSquare() {
//        Board board = new Board(10, 10);
//        Square square = board.getSquare(0, 0);
//    }

    @Test
    public void testIndexExists() {
        Board board = new Board(10, 10);
        Assertions.assertTrue(board.indexExists(5, 5),
                "Index 5, 5 found to not exist on 10X10 board");
        Assertions.assertTrue(board.indexExists(0, 5),
                "Index 0, 5 found to not exist on 10X10 board");
        Assertions.assertTrue(board.indexExists(5, 0),
                "Index 5, 0 found to not exist on 10X10 board");
    }

    @Test
    public void testUncoverSquare() {
        Board board = new Board(10, 10);
        Square square = board.getSquare(0, 0);
        board.uncoverSquare(0, 0);
        Assertions.assertTrue(square.isUncovered(), "Square was not uncovered");
    }

    @Test
    public void testUncoverSquareByRef() {
        Board board = new Board(10, 10);
        Square square = board.getSquare(0, 0);
        board.uncoverSquare(square);
        Assertions.assertTrue(square.isUncovered(), "Square was not uncovered");
    }

    @Test
    public void testSetFlag() {
        Board board = new Board(10, 10);
        Square square = board.getSquare(0, 0);
        board.setFlag(0, 0);
        Assertions.assertTrue(square.isFlagged(), "Square was not flagged");
        board.setFlag(0, 0);
        Assertions.assertFalse(square.isFlagged(), "Square flag was not properly" +
                " toggled");
    }

//    @Test
//    public void testDrawBoard() {
//        Board board = new Board(10, 10);
//        String initialBoardState = """
//                   [0][1][2][3][4][5][6][7][8][9]
//                [0][.][.][.][.][.][.][.][.][.][.]
//                [1][.][.][.][.][.][.][.][.][.][.]
//                [2][.][.][.][.][.][.][.][.][.][.]
//                [3][.][.][.][.][.][.][.][.][.][.]
//                [4][.][.][.][.][.][.][.][.][.][.]
//                [5][.][.][.][.][.][.][.][.][.][.]
//                [6][.][.][.][.][.][.][.][.][.][.]
//                [7][.][.][.][.][.][.][.][.][.][.]
//                [8][.][.][.][.][.][.][.][.][.][.]
//                [9][.][.][.][.][.][.][.][.][.][.]
//                """;
//        String[] lines = initialBoardState.split("\\r?\\n");
//        Assertions.assertEquals(initialBoardState, board.drawBoard(),
//                "The board does not print itself correctly on init");
//    }

    @Test
    public void testUseHint() {
        Board board = new Board(10, 10);
        Square square = board.getSquare(5, 5);
        square.setHasBomb(true);
        Integer[] index = board.useHint();
        int row = index[0], col = index[1];
        Assertions.assertTrue(square.isFlagged(), "Square was not properly" +
                " flagged by hint");
        Assertions.assertEquals(row, 5,
                "Row uncovered is not correct");
        Assertions.assertEquals(col, 5,
                "Column uncovered is not correct");
    }

    private void assertNonBombSquares(Board board, Square square) {
        Assertions.assertTrue(square.isUncovered(), "Square at index" +
                " 0, 0 was not uncovered");

        Square nearHorizSquare = board.getSquare(0, 1);
        Assertions.assertTrue(nearHorizSquare.isUncovered(), "Square at index" +
                "0, 1 was not uncovered");

        Square nearVertSquare = board.getSquare(0, 1);
        Assertions.assertTrue(nearVertSquare.isUncovered(),
                "Square at index 1, 0 was not uncovered");

        Square nearDiagSquare = board.getSquare(1, 1);
        Assertions.assertTrue(nearDiagSquare.isUncovered(),
                "Square at index 1, 1 was not uncovered");
    }

    private void assertBombSquares(Board board) {
        for (int i = 0; i < 3; i++) {
            Square bombSquare = board.getSquare(2, i);
            Square otherBombSquare = board.getSquare(i, 2);
            Assertions.assertFalse(bombSquare.isUncovered(),
                    "Bomb square at index 0," + i + " was found" +
                            " to be uncovered");
            Assertions.assertFalse(otherBombSquare.isUncovered(),
                    "Bomb square at index "+ i + ",0 was found" +
                            " to be uncovered");
        }
    }
    @Test
    public void testFirstUncover() {
        Board board = new Board(10, 10);
        Square square = board.getSquare(0, 0);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Square setSquare = board.getSquare(i, j);
                setSquare.setRow(i);
                setSquare.setCol(j);
            }
        }

        for (int i = 0; i < 3; i++) {
            Square nearSquare = board.getSquare(2, i);
            Square otherNearSquare = board.getSquare(i, 2);
            nearSquare.setHasBomb(true);
            otherNearSquare.setHasBomb(true);
        }

        board.firstUncover(0, 0);
        assertNonBombSquares(board, square);
        assertBombSquares(board);
    }

    @Test
    public void testGetNumFlagged() {
        Board board = new Board(10, 10);
        Assertions.assertEquals(0, board.getNumFlagged(),
                "Number of flagged squares is not 0 on initialisation");
        Square square = board.getSquare(5, 5);
        square.setFlagged(true);
        Assertions.assertEquals(1, board.getNumFlagged(),
                "Number of flagged squares is not correct");
        Square anotherSquare = board.getSquare(4, 4);
        anotherSquare.setFlagged(true);
        Assertions.assertEquals(2, board.getNumFlagged(),
                "Number of flagged squares is not correct");
    }

//    @Test
//    public void testSetMines() {
//
//    }
}

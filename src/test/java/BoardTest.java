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
        Assertions.assertTrue(square.isUncovered(), "Square was not incovered");
    }
}

import com.ahmed.mavenminesweeper.Square;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SquareTest {

    @Test
    public void testHasBomb() {
        Square square = new Square();
        Assertions.assertFalse(square.hasBomb(), "Square " +
                "found to have bomb upon initialisation");
        square.setHasBomb(true);
        Assertions.assertTrue(square.hasBomb(), "Square does not have" +
                " bomb, although it has been set");
        square.setHasBomb(false);
        Assertions.assertFalse(square.hasBomb(), "Square has a bomb," +
                " although it has been set not to");
    }

    @Test
    public void testRows() {
        Square square = new Square();
        Assertions.assertNull(square.getRow(), "Square " +
                "has a row on initialisation");
        square.setRow(0);
        Assertions.assertEquals(0,
                (int) square.getRow(), "You cannot set and get the row properly");
    }

    @Test
    public void testCols() {
        Square square = new Square();
        Assertions.assertNull(square.getCol(), "Square " +
                "has a column on initialisation");
        square.setCol(0);
        Assertions.assertEquals(0,
                (int) square.getCol(), "You cannot set and get the col properly");

    }

    @Test
    public void testFlagged() {
        Square square = new Square();
        Assertions.assertFalse(square.isFlagged(), "Square " +
                "found to be flagged on initialisation");
        square.setFlagged(true);
        Assertions.assertTrue(square.isFlagged(), "Square was not set to flagged, " +
                "currently not flagged");
        square.setFlagged(false);
        Assertions.assertFalse(square.isFlagged(), "Square found to be flagged, "
                + "when it should not have been");
    }

    @Test
    public void testUncovered() {
        Square square = new Square();
        Assertions.assertFalse(square.isUncovered(), "Square " +
                "found to be uncovered on initialisation");
        square.setUncovered(true);
        Assertions.assertTrue(square.isUncovered(), "Square is not uncovered, " +
                "when it should be");
        square.setUncovered(false);
        Assertions.assertFalse(square.isUncovered(), "Square is uncovered, " +
                " when it should not be");
    }

    @Test
    public void testAdjacentBombs() {
        Square square = new Square();
        Assertions.assertEquals(0, square.getAdjacentBombs(), "Square" +
                " has an incorrect number of adjacent bombs on initialisation");
        square.setAdjacentBombs(1);
        Assertions.assertEquals(1, square.getAdjacentBombs(), "Square" +
                " has an incorrect number of adjacent bombs");
    }

    @Test
    public void testDrawSquare() {
        Square square = new Square();
        Assertions.assertEquals('.', square.drawSquare(), "Square doesn't print" +
                " as . upon initialisation");
        square.setFlagged(true);
        Assertions.assertEquals('F', square.drawSquare(), "Square does not" +
                " draw as F when flagged");
        square.setFlagged(false);
        square.setHasBomb(true);
        square.setUncovered(true);
        Assertions.assertEquals('X', square.drawSquare(), "Square does not" +
                "draw as X when it has a bomb and has been uncovered");
        square.setHasBomb(false);
        square.setAdjacentBombs(4);
        Assertions.assertEquals('4', square.drawSquare(), "Square does not" +
                "draw the correct number of adjacent bombs");
    }
}

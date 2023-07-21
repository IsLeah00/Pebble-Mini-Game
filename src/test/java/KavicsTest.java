
import hu.unideb.inf.model.KavicsModel;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KavicsTest {
    private KavicsModel model;

    @BeforeEach
    public void setUp() {
        model = new KavicsModel();
        model.setPlayerNames(new String[]{"Játékos 1", "Játékos 2"});
        model.createSquares();
    }

    @Test
    public void testGetPlayerNames() {
        String[] expectedNames = {"John", "Jane"};
        model.setPlayerNames(expectedNames);
        String[] actualNames = model.getPlayerNames();

        Assertions.assertArrayEquals(expectedNames, actualNames, "A visszaadott játékosnevek nem megfelelőek.");
    }

    @Test
    public void testGetSquares() {
        Rectangle[][] squares = model.getSquares();

        Assertions.assertNotNull(squares, "A visszaadott tömb nem lehet null.");
        assertEquals(4, squares.length, "A visszaadott tömb sorainak száma nem megfelelő.");
        assertEquals(4, squares[0].length, "A visszaadott tömb oszlopainak száma nem megfelelő.");
    }

    @Test
    public void testGetCurrentPlayerIndex() {
        int expectedIndex = 0;
        int actualIndex = model.getCurrentPlayerIndex();

        assertEquals(expectedIndex, actualIndex, "A visszaadott jelenlegi játékosindex nem megfelelő.");
    }

    @Test
    public void testHandleSquareClick_FirstClick() {
        KavicsModel model = new KavicsModel();
        model.setPlayerNames(new String[]{"Játékos 1", "Játékos 2"});
        model.createSquares();

        model.setLastClickedRow(-1);
        model.setLastClickedCol(-1);
        model.setRemainingClicks(1);

        Rectangle square = new Rectangle();
        square.setFill(Color.BLACK);
        GridPane.setColumnIndex(square, 0);
        GridPane.setRowIndex(square, 0);

        model.handleSquareClick(square);

        assertEquals(Color.WHITE, square.getFill());

        assertEquals(0, model.getRemainingClicks());

        assertEquals(0, model.getLastClickedRow());
        assertEquals(0, model.getLastClickedCol());
    }

    @Test
    public void testHandleSquareClick_HorizontalWithWhiteSquares() {
        KavicsModel model = new KavicsModel();
        model.setPlayerNames(new String[]{"Játékos 1", "Játékos 2"});
        model.createSquares();

        model.setLastClickedRow(0);
        model.setLastClickedCol(0);
        model.setRemainingClicks(1);

        Rectangle square1 = new Rectangle();
        square1.setFill(Color.BLACK);
        GridPane.setColumnIndex(square1, 0);
        GridPane.setRowIndex(square1, 0);

        Rectangle square2 = new Rectangle();
        square2.setFill(Color.BLACK);
        GridPane.setColumnIndex(square2, 3);
        GridPane.setRowIndex(square2, 0);

        model.getSquares()[0][1].setFill(Color.WHITE);
        model.getSquares()[0][2].setFill(Color.WHITE);

        model.handleSquareClick(square2);

        assertNotEquals(Color.WHITE, square2.getFill());

        assertEquals(1, model.getRemainingClicks());

        assertEquals(0, model.getLastClickedRow());
        assertEquals(0, model.getLastClickedCol());
    }

    @Test
    public void testHandleSquareClick_VerticalWithWhiteSquares() {
        KavicsModel model = new KavicsModel();
        model.setPlayerNames(new String[]{"Játékos 1", "Játékos 2"});
        model.createSquares();

        model.setLastClickedRow(0);
        model.setLastClickedCol(0);
        model.setRemainingClicks(1);

        Rectangle square1 = new Rectangle();
        square1.setFill(Color.BLACK);
        GridPane.setColumnIndex(square1, 0);
        GridPane.setRowIndex(square1, 0);

        Rectangle square2 = new Rectangle();
        square2.setFill(Color.BLACK);
        GridPane.setColumnIndex(square2, 0);
        GridPane.setRowIndex(square2, 3);

        model.getSquares()[1][0].setFill(Color.WHITE);
        model.getSquares()[2][0].setFill(Color.WHITE);

        model.handleSquareClick(square2);

        assertNotEquals(Color.WHITE, square2.getFill());

        assertEquals(1, model.getRemainingClicks());

        assertEquals(0, model.getLastClickedRow());
        assertEquals(0, model.getLastClickedCol());
    }

    @Test
    void checkGameOver_GameOver_OneBlackSquareLeft() {
        Rectangle[][] squares = model.getSquares();
        for (Rectangle[] square : squares) {
            for (Rectangle rectangle : square) {
                rectangle.setFill(Color.WHITE);
            }
        }
        squares[0][0].setFill(Color.BLACK);

        model.checkGameOver();

        assertTrue(model.isGameOver());
    }

    @Test
    void checkGameOver_NotGameOver() {
        Rectangle[][] squares = model.getSquares();
        squares[0][0].setFill(Color.WHITE);
        squares[0][1].setFill(Color.WHITE);
        squares[1][0].setFill(Color.WHITE);
        squares[1][1].setFill(Color.BLACK);

        model.checkGameOver();

        assertFalse(model.isGameOver());
    }

    @Test
    void checkGameOver_GameOver_NoBlackSquaresLeft() {
        Rectangle[][] squares = model.getSquares();
        for (Rectangle[] square : squares) {
            for (Rectangle rectangle : square) {
                rectangle.setFill(Color.WHITE);
            }
        }

        model.checkGameOver();

        assertTrue(model.isGameOver());
    }

    @Test
    public void testCountBlackSquares() {
        int expectedCount = 16;

        int actualCount = model.countBlackSquares();

        assertEquals(expectedCount, actualCount, "A fekete négyzetek száma nem megfelelő.");
    }

    @Test
    public void testChangePlayer() {
        model.changePlayer();
        assertEquals(0, model.getCurrentPlayerIndex());
        assertEquals(KavicsModel.MAX_CLICKS, model.getRemainingClicks());
        assertFalse(model.isGameOver());
    }

    @Test
    public void testStartNewGame() {
        model.startNewGame();
        assertEquals(-1, model.getLastClickedRow());
        assertEquals(-1, model.getLastClickedCol());
        assertEquals(0, model.getCurrentPlayerIndex());
        assertEquals(KavicsModel.MAX_CLICKS, model.getRemainingClicks());
        assertFalse(model.isGameOver());

        Rectangle[][] squares = model.getSquares();
        for (Rectangle[] row : squares) {
            for (Rectangle square : row) {
                assertEquals(Color.BLACK, square.getFill());
            }
        }
    }

    @Test
    public void testCreateSquares() {
        model.createSquares();
        Rectangle[][] squares = model.getSquares();
        assertEquals(KavicsModel.ROWS, squares.length);
        assertEquals(KavicsModel.COLS, squares[0].length);
        for (int i = 0; i < KavicsModel.ROWS; i++) {
            for (int j = 0; j < KavicsModel.COLS; j++) {
                assertNotNull(squares[i][j]);
            }
        }
    }

    @Test
    public void testSetLastClickedRow() {
        int row = 2;
        model.setLastClickedRow(row);
        assertEquals(row, model.getLastClickedRow());
    }

    @Test
    public void testSetLastClickedCol() {
        int col = 3;
        model.setLastClickedCol(col);
        assertEquals(col, model.getLastClickedCol());
    }

    @Test
    public void testSetRemainingClicks() {
        int clicks = 2;
        model.setRemainingClicks(clicks);
        assertEquals(clicks, model.getRemainingClicks());
    }
}

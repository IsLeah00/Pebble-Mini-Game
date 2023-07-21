package hu.unideb.inf.model;

import hu.unideb.inf.data.XMLFileHandling;
import hu.unideb.inf.view.KavicsView;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import org.jdom2.JDOMException;
import java.io.IOException;

/**
 * The KavicsModel class represents the game model in the Kavics game.
 * It contains the game state and provides methods to manipulate the state.
 */
public class KavicsModel {
    private KavicsView view;
    public static int ROWS = 4;
    public static int COLS = 4;
    public static int MAX_CLICKS = 4;

    private Rectangle[][] squares;
    private int currentPlayerIndex = 0;
    private int remainingClicks = MAX_CLICKS;
    private int lastClickedRow = -1;
    private int lastClickedCol = -1;
    private boolean gameOver = false;
    private String[] playerNames = new String[2];

    /**
     * Gets the player names currently set in the model.
     * @return An array of player names.
     */
    public String[] getPlayerNames() {
        return playerNames;
    }

    /**
     * Sets the player names based on the provided array.
     * @param name1 the name of the first player
     * @param name2 the name of the second player
     */
    public void setPlayerNames(String name1, String name2) {
        playerNames[0] = name1;
        playerNames[1] = name2;
    }

    /**
     * Creates a new instance of the KavicsModel class.
     * Initializes the game board and other properties.
     */
    public KavicsModel() {
        squares = new Rectangle[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                squares[i][j] = createSquare();
            }
        }
    }

    /**
     * Gets the game board squares.
     * @return A 2D array of Rectangle objects representing the squares.
     */
    public Rectangle[][] getSquares() {
        return squares;
    }

    /**
     * Gets the index of the current player.
     * @return The index of the current player.
     */
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    /**
     * Creates a new square with the default properties.
     * @return A new Rectangle object representing a square.
     */
    private Rectangle createSquare() {
        return new Rectangle(50, 50, Color.BLACK);
    }

    /**
     * Handles the click event on a square in the game view.
     * Updates the state of the game based on the clicked square.
     * @param square The Rectangle object representing the clicked square.
     */
    public void handleSquareClick(Rectangle square) {
        if (gameOver) {
            return;
        }
        int row = GridPane.getRowIndex(square);
        int col = GridPane.getColumnIndex(square);

        if (remainingClicks > 0 && square.getFill() == Color.BLACK) {

            if (lastClickedRow == -1 && lastClickedCol == -1) {
                lastClickedRow = row;
                lastClickedCol = col;
                square.setFill(Color.WHITE);
                remainingClicks--;
            } else {

                if (row == lastClickedRow || col == lastClickedCol) {
                    boolean hasWhiteSquareBetween = false;
                    if (row == lastClickedRow) {
                        int startCol = Math.min(col, lastClickedCol) + 1;
                        int endCol = Math.max(col, lastClickedCol);

                        for (int c = startCol; c < endCol; c++) {
                            if (squares[row][c].getFill() == Color.WHITE) {
                                hasWhiteSquareBetween = true;
                                break;
                            }
                        }
                    } else {
                        int startRow = Math.min(row, lastClickedRow) + 1;
                        int endRow = Math.max(row, lastClickedRow);

                        for (int r = startRow; r < endRow; r++) {
                            if (squares[r][col].getFill() == Color.WHITE) {
                                hasWhiteSquareBetween = true;
                                break;
                            }
                        }
                    }
                    if (!hasWhiteSquareBetween) {
                        square.setFill(Color.WHITE);
                        remainingClicks--;
                    }
                }
            }
        }
    }

    /**
     * Changes the active player and performs necessary actions when the player changes.
     * The method updates the last clicked row and column values to -1, advances to the next player,
     * resets the remaining clicks to the maximum value, checks if the game is over, and displays appropriate messages.
     * If the game is over, it prompts the user for the winner's name and saves it using the XMLFileHandling class and
     * displays the loser's name.
     * Finally, it closes the winners name stage and starts the game, or updates the current player label if the game is not over.
     */
    public void changePlayer() {
        lastClickedRow = -1;
        lastClickedCol = -1;
        if (remainingClicks != MAX_CLICKS || remainingClicks == 0) {
            currentPlayerIndex = (currentPlayerIndex + 1) % 2;
            remainingClicks = MAX_CLICKS;
            checkGameOver();
            if (gameOver) {
                view.getCurrentPlayerLabel().setText("JÁTÉK VÉGE! VESZTES: " + playerNames[currentPlayerIndex]);

                TextInputDialog dialog = new TextInputDialog();

                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(event -> {
                    Platform.runLater(() -> {
                        dialog.setHeaderText("NYERTES NEVE: ");
                        dialog.showAndWait().ifPresent(winnerName -> {
                            try {
                                XMLFileHandling.saveWinnerName(winnerName);
                            } catch (IOException | JDOMException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    });
                });
                pause.play();
            } else {
                view.getCurrentPlayerLabel().setText("JELENLEG JÁTSZIK: " + playerNames[currentPlayerIndex]);
            }
        }
    }

    /**
     * Checks if the game is over by counting the number of remaining black squares.
     * Updates the game over flag accordingly.
     */
    public void checkGameOver() {
        int blackSquareCount = countBlackSquares();
        if (blackSquareCount == 1) {
            gameOver = true;
        } else if (blackSquareCount == 0) {
            currentPlayerIndex = (currentPlayerIndex + 1) % 2;
            gameOver = true;
        }
    }

    /**
     * Counts the number of black squares on the game board.
     * @return The count of black squares.
     */
    public int countBlackSquares() {
        int count = 0;
        for (Rectangle[] row : squares) {
            for (Rectangle square : row) {
                if (square.getFill() == Color.BLACK) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Resets the game state to start a new game.
     * Sets all squares to black and resets the player and click counters.
     */
    public void startNewGame() {
        lastClickedRow = -1;
        lastClickedCol = -1;
        currentPlayerIndex = 0;
        remainingClicks = MAX_CLICKS;
        gameOver = false;

        for (Rectangle[] row : squares) {
            for (Rectangle square : row) {
                square.setFill(Color.BLACK);
            }
        }
        if (view != null) {
            view.getCurrentPlayerLabel().setText("JELENLEG JÁTSZIK: " + playerNames[currentPlayerIndex]);
        }
    }

    /**
     * Sets the associated KavicsView instance.
     * @param view The KavicsView instance to associate with the model.
     */
    public void setView(KavicsView view) {
        this.view = view;
    }

    /**
     * Checks if the game is over.
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Creates the squares for the game board.
     * Initializes the squares array with Rectangle objects based on the number of rows and columns.
     * Each square is created using the createSquare() method.
     */
    public void createSquares() {
        squares = new Rectangle[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                squares[i][j] = createSquare();
            }
        }
    }

    /**
     * @return the number of remaining clicks
     */
    public int getRemainingClicks() {
        return remainingClicks;
    }

    /**
     * @return the row index of the last clicked square
     */
    public int getLastClickedRow() {
        return lastClickedRow;
    }

    /**
     * @return the column index of the last clicked square
     */
    public int getLastClickedCol() {
        return lastClickedCol;
    }

    /**
     * Sets the row index of the last clicked square.
     * @param row the row index of the last clicked square
     */
    public void setLastClickedRow(int row) {
        this.lastClickedRow = row;
    }

    /**
     * Sets the column index of the last clicked square.
     * @param col the column index of the last clicked square
     */
    public void setLastClickedCol(int col) {
        this.lastClickedCol = col;
    }

    /**
     * Sets the number of remaining clicks.
     * @param clicks the number of remaining clicks
     */
    public void setRemainingClicks(int clicks) {
        this.remainingClicks = clicks;
    }

    /**
     * Returns the current game state.
     * @return The game state as a string.
     */
    public String getGameState() {
        if (isGameOver()) {
            return "GAME OVER!";
        } else {
            return "IN PROGRESS...";
        }
    }

    /**
     * Sets the player names based on the provided array.0
     * @param playerNames an array containing the player names
     */
    public void setPlayerNames(String[] playerNames) {
        this.playerNames = playerNames;
    }
}
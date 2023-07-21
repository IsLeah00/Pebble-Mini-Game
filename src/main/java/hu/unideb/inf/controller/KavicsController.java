package hu.unideb.inf.controller;

import hu.unideb.inf.model.KavicsModel;
import hu.unideb.inf.view.KavicsView;
import javafx.scene.shape.Rectangle;

import org.tinylog.Logger;

/**
 * The KavicsController class is responsible for controlling the game logic and user interactions in the Kavics game.
 * It interacts with the KavicsModel and KavicsView classes to update the game state and display the game view.
 */
public class KavicsController {
    private final KavicsModel model;
    private final KavicsView view;

    /**
     * Creates a new instance of the KavicsController class with the specified KavicsModel and KavicsView.
     * @param model The KavicsModel instance to associate with the controller.
     * @param view  The KavicsView instance to associate with the controller.
     */
    public KavicsController(KavicsModel model, KavicsView view) {
        this.model = model;
        this.view = view;
        attachHandlers();
    }

    /**
     * Attaches event handlers to the squares in the game view.
     * When a square is clicked, it invokes the corresponding action in the model and logs the event and its outcome.
     */
    private void attachHandlers() {
        Rectangle[][] squares = model.getSquares();
        for (Rectangle[] rectangles : squares) {
            for (Rectangle square : rectangles) {
                square.setOnMouseClicked(e -> {
                    try {
                        model.handleSquareClick(square);
                        Logger.info("The field has been clicked.",
                                "Coordinates: (" + square.getX() + ", " + square.getY() + ")");
                        Logger.debug("The field was managed successfully.",
                                "Game status: " + model.getGameState());
                    } catch (Exception ex) {
                        Logger.error("An error occurred while handling the field.", ex,
                                "Coordinates: (" + square.getX() + ", " + square.getY() + ")");
                    }
                });
            }
        }
    }
}
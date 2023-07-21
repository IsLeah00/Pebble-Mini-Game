package hu.unideb.inf;

import hu.unideb.inf.controller.KavicsController;
import hu.unideb.inf.model.KavicsModel;
import hu.unideb.inf.view.KavicsView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

/**
 * The KavicsApp class is the entry point for the Kavics game application.
 * It initializes the game model, view, and controller, and sets up the main application window.
 */
public class KavicsApp extends Application {

    /**
     * Starts the application by prompting for player names and initializing the game.
     * Displays the game view and sets up the game controller.
     * @param primaryStage the primary stage for the JavaFX application
     */
    @Override
    public void start(Stage primaryStage) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("ELSŐ JÁTÉKOS NEVE: ");
        dialog.showAndWait().ifPresent(player1Name -> {
            dialog.setHeaderText("MÁSODIK JÁTÉKOS NEVE: ");
            dialog.showAndWait().ifPresent(player2Name -> {
                KavicsModel model = new KavicsModel();
                model.setPlayerNames(player1Name, player2Name);
                KavicsView view = new KavicsView(model);
                KavicsController controller = new KavicsController(model, view);

                Scene scene = new Scene(view);
                primaryStage.setScene(scene);
                primaryStage.setTitle("KAVICS JÁTÉK");
                primaryStage.show();
            });
        });
    }
}
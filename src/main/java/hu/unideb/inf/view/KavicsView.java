package hu.unideb.inf.view;

import hu.unideb.inf.model.KavicsModel;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class KavicsView extends GridPane {
    private final KavicsModel model;

    private Label currentPlayerLabel;

    public KavicsView(KavicsModel model) {
        this.model = model;
        if (model != null) {
            model.setView(this);
        }
        createView();
    }

    private void createView() {
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);

        Rectangle[][] squares = model.getSquares();
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                Rectangle square = squares[i][j];
                add(square, j, i);
            }
        }

        currentPlayerLabel = new Label("JELENLEG JÁTSZIK: " + model.getPlayerNames()[model.getCurrentPlayerIndex()]);

        GridPane.setRowIndex(currentPlayerLabel, 0);
        GridPane.setColumnIndex(currentPlayerLabel, squares[0].length);
        GridPane.setColumnSpan(currentPlayerLabel, 2);
        GridPane.setHalignment(currentPlayerLabel, Pos.CENTER.getHpos());

        Button changePlayerButton = new Button("KÖR VÉGE");
        changePlayerButton.setOnAction(e -> model.changePlayer());

        GridPane.setRowIndex(changePlayerButton, 1);
        GridPane.setColumnIndex(changePlayerButton, squares[0].length);
        GridPane.setColumnSpan(changePlayerButton, 2);
        GridPane.setHalignment(changePlayerButton, Pos.CENTER.getHpos());

        Button newGameButton = new Button("ÚJ JÁTÉK");
        newGameButton.setOnAction(e -> model.startNewGame());

        GridPane.setRowIndex(newGameButton, 2);
        GridPane.setColumnIndex(newGameButton, squares[0].length);
        GridPane.setColumnSpan(newGameButton, 2);
        GridPane.setHalignment(newGameButton, Pos.CENTER.getHpos());

        Button resultButton = new Button("EREDMÉNYEK");
        resultButton.setOnAction(event -> {
            Stage resultStage = new Stage();
            ResultsWindow resultsWindow = new ResultsWindow();
            try {
                resultsWindow.start(resultStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        GridPane.setRowIndex(resultButton, 3);
        GridPane.setColumnIndex(resultButton, squares[0].length);
        GridPane.setColumnSpan(resultButton, 2);
        GridPane.setHalignment(resultButton, Pos.CENTER.getHpos());

        getChildren().addAll(currentPlayerLabel, changePlayerButton, newGameButton, resultButton);
    }

    public Label getCurrentPlayerLabel() {
        return currentPlayerLabel;
    }
}
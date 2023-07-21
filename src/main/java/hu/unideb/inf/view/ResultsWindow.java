package hu.unideb.inf.view;

import hu.unideb.inf.Main;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.InputStream;
import java.util.List;

public class ResultsWindow extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("/winners.xml");
        Label winnerLabel = null;
        Label namesLabel = null;
        if (inputStream != null) {
            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(inputStream);

            Element rootElement = document.getRootElement();
            List<Element> winnerElements = rootElement.getChildren("winner");

            StringBuilder winners = new StringBuilder();
            for (Element winnerElement : winnerElements) {
                String winnerName = winnerElement.getText();
                winners.append(winnerName).append("\n");
            }

            winnerLabel = new Label("NYERTESEK NEVEI");
            namesLabel = new Label(winners.toString());
        }

        Button backButton = new Button("VISSZA");
        backButton.setOnAction(event -> {
            primaryStage.close();
            Main.main(new String[]{});
        });

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(winnerLabel, namesLabel, backButton);

        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("EREDMÉNYEK");
        primaryStage.show();
    }
}

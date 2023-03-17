package Application;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Menu extends VBox {
    private Label titleLabel;
    private Button startButton;
    private Button highScoresButton;

    public Menu() {
        // Set the background
        setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        // Create the title label
        titleLabel = new Label(GameConstants.WINDOW_TITLE);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setAlignment(Pos.CENTER);

        // Create the start button
        startButton = new Button("Start Game");
        startButton.setOnAction(event -> {
            // Start the game
            ((Node) event.getSource()).getScene().getWindow().hide();
            new GameController().startGame();
        });

        // Create the high scores button
        highScoresButton = new Button("High Scores");
        highScoresButton.setOnAction(event -> {
            // Display the high scores
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("High Scores");
            alert.setHeaderText(null);
            alert.setContentText("No high scores yet.");
            alert.showAndWait();
        });

        // Add the title and buttons to the menu
        getChildren().addAll(titleLabel, startButton, highScoresButton);
        setSpacing(20);
        setAlignment(Pos.CENTER);
    }
}




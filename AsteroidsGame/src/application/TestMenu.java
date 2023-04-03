package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TestMenu extends Application {

    @Override
    public void start(Stage stage) {
        // create title
        Text title = new Text("Space Adventure");
        // set font, font weight and font size of title
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 60));
        // set color of title to white
        title.setFill(Color.WHITE);
        
        // create play button
        Button playButton = new Button("PLAY");
        // set font and font size of play button
        playButton.setFont(Font.font("Verdana", 30));
        // set background color of play button to cyan and text color to white
        playButton.setStyle("-fx-background-color: #00CCFF; -fx-text-fill: white;");
        // set event handler for play button
        playButton.setOnAction(e -> {
            // handle play button click event
        });
        
        // create quit button
        Button quitButton = new Button("QUIT");
        // set font and font size of quit button
        quitButton.setFont(Font.font("Verdana", 30));
        // set background color of quit button to red and text color to white
        quitButton.setStyle("-fx-background-color: #FF3300; -fx-text-fill: white;");
        // set event handler for quit button
        quitButton.setOnAction(e -> {
            // handle quit button click event
            stage.close(); // close the window when the quit button is clicked
        });
        
        // create layout
        StackPane layout = new StackPane();
        // set background color of layout to black
        layout.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        // center the title, play button and quit button in the layout
        layout.setAlignment(Pos.CENTER);
        // add the title, play button and quit button to the layout
        layout.getChildren().addAll(title, playButton, quitButton);
        
        // create scene
        Scene scene = new Scene(layout, 800, 600);
        
        // set scene
        stage.setTitle("Space Game Menu");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

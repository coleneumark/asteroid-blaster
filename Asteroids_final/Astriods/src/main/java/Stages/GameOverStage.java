package Stages;

import Interfaces.IGameObject;
import Interfaces.IStage;
import Logic.GameSettings;
import Logic.Score;
import Logic.ScoreHandler;
import Logic.GameEngine;
import java.io.File;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

//This stage is shown when the player dies
public class GameOverStage implements IStage{
 private String name;
    private Stage stage;
    private Scene scene;
    private Pane root;
    private final GameEngine engine;
    public static MenuStage instance;

    public GameOverStage(String name){
        this.engine = GameEngine.getEngine();
        GameSettings.GetInstance();
        this.name = name;
    }
    
    @Override
    public String getName() {
       return this.name; 
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void draw() {
        
    }

    @Override
    public void loadStage() {
        Platform.runLater(() -> {
            this.stage = new Stage();
            this.root = new Pane();
            root.setStyle("-fx-background-color: black;");
            this.scene = new Scene(root, 300,300);
            this.stage.initStyle(StageStyle.UNDECORATED);
            // Loads player's score
            Label label = new Label("Your high score is: " + Integer.toString(engine.getScore()));
            label.setFont(new Font("Arial", 20));
            label.setTextFill(Color.WHITE);
            label.setLayoutY(100);
            label.setLayoutX(20);

            Label nameLabel = new Label("Enter your name:");
            nameLabel.setFont(new Font("Arial", 20));
            nameLabel.setTextFill(Color.WHITE);
            nameLabel.setLayoutY(150);
            nameLabel.setLayoutX(20);
            
            TextField nameField = new TextField();
            nameField.setPromptText("Enter your name");
            nameField.setLayoutY(180);
            nameField.setLayoutX(20);
            
            Button submitButton = new Button("Submit");
            submitButton.setLayoutY(220);
            submitButton.setLayoutX(20);

            submitButton.setOnAction(e -> {
                String playerName = nameField.getText();                
                Score score = new Score(engine.getScore(),playerName);
                try {
                    // determine if player's score falls within top 10 range
                    new ScoreHandler().addScore(score);
                    engine.setCurrentStage("MainStage");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            root.getChildren().addAll(label, nameLabel, nameField, submitButton);
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

            double centerX = (screenBounds.getWidth() - scene.getWidth()) / 2;
            double centerY = (screenBounds.getHeight() - scene.getHeight()) / 2;
            stage.setX(centerX);
            stage.setY(centerY);
            stage.setScene(scene);
            
            String currentDir = System.getProperty("user.dir");
            String audioFile = currentDir + "/Dead.mp3";

            var media = new Media(new File(audioFile).toURI().toString());
            MediaPlayer mediaPlayer  = new MediaPlayer(media);
            // Play the audio clip
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();

            this.stage.show();
        });
    }
      

    
    public void start(Stage primaryStage) throws Exception {
        this.loadStage();
    }

    @Override
    public ArrayList<IGameObject> getGameObjects() {
        throw new UnsupportedOperationException("Scene does not contain game objects");
    }

    @Override
    public void setGameObject(ArrayList<IGameObject> gameObjects) {
        throw new UnsupportedOperationException("Scene does not contain game objects");
    }

    @Override
    public void addGameObject(IGameObject object) {
        throw new UnsupportedOperationException("Scene does not contain game objects");
    }

    @Override
    public void removeGameObject(IGameObject object) {
        throw new UnsupportedOperationException("Scene does not contain game objects"); 
    }
    
    @Override
    public void hide() {
        this.stage.hide();
    }
}

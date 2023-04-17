package Stages;

import java.util.ArrayList;

import Interfaces.IGameObject;
import Interfaces.IStage;
import Logic.GameEngine;
import Logic.GameSettings;
import Logic.Score;
import Logic.ScoreHandler;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
            root.setStyle("-fx-background-color: black; -fx-border-color: white;");

            this.scene = new Scene(root, 800, 600);
            this.stage.initStyle(StageStyle.UNDECORATED);

            // Add "Game Over" title with the alien font
            Font alienFont = Font.loadFont(getClass().getResourceAsStream("/AlienRavagerItalic-4B214.ttf"), 60);
            
            // Add "Asteroid Blaster" title label with the alien font
            Label titleLabel = new Label("Asteroid Blaster");
            titleLabel.setFont(new Font(alienFont.getName(), 85));
            titleLabel.setTextFill(Color.WHITE);
            titleLabel.setLayoutY(5);
            titleLabel.setLayoutX(10);
            root.getChildren().add(titleLabel);
            
            Label gameOverLabel = new Label("Game Over");
            gameOverLabel.setFont(new Font(alienFont.getName(), 110));
            gameOverLabel.setTextFill(Color.RED);
            gameOverLabel.setLayoutY(68);
            gameOverLabel.setLayoutX(139);
            root.getChildren().add(gameOverLabel);

            // Add two sentences after the "Game Over" title
            Label sentence1 = new Label("Your spaceship was defeated in outer space...");
            sentence1.setFont(new Font("Arial", 22));
            sentence1.setTextFill(Color.WHITE);
            sentence1.setLayoutY(180);
            sentence1.setLayoutX(100);

            Label sentence2 = new Label("Will your next attempt save the universe?");
            sentence2.setFont(new Font("Arial", 22));
            sentence2.setTextFill(Color.WHITE);
            sentence2.setLayoutY(220);
            sentence2.setLayoutX(180);
            root.getChildren().addAll(sentence1, sentence2);

            // Loads player's score
            Label scorelabel = new Label("Your Score is: " + Integer.toString(engine.getScore()));
            scorelabel.setFont(new Font("Arial", 30));
            scorelabel.setTextFill(Color.WHITE);
            scorelabel.setLayoutY(290);
            scorelabel.setLayoutX(240);
            root.getChildren().addAll(scorelabel);

            // Update nameLabel text and style
            Label nameLabel = new Label("Enter your name, Captain:");
            nameLabel.setFont(new Font("Arial", 22));
            nameLabel.setTextFill(Color.WHITE);
            nameLabel.setLayoutY(330);
            nameLabel.setLayoutX(220);
            root.getChildren().add(nameLabel);

            // Style nameField
            TextField nameField = new TextField();
            nameField.setLayoutY(369);
            nameField.setLayoutX(240);
            nameField.setPrefHeight(49);
            nameField.setPrefWidth(255);
            nameField.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-border-color: white; -fx-font-size: 18;");
            root.getChildren().add(nameField);

            String normalButtonStyle = "-fx-background-color: black; -fx-text-fill: white; -fx-border-color: white;";
            String hoverButtonStyle = "-fx-background-color: black; -fx-text-fill: red; -fx-border-color: red;";

            // Style submitButton and add a hover effect
            Button submitButton = new Button("Submit");
            submitButton.setLayoutX(305);               
            submitButton.setLayoutY(450);
            submitButton.setFont(new Font("Arial", 25));
            submitButton.setStyle(normalButtonStyle);
            
            
            root.getChildren().add(submitButton);

            // Add a verification for the text input
            nameField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.trim().isEmpty()) {
                    // Remove hover effect when submit button is disabled
                    submitButton.setOnMouseEntered(null);
                    submitButton.setOnMouseExited(null);
                } else {
                    // Add hover effect when submit button is enabled
                    submitButton.setOnMouseEntered(event -> submitButton.setStyle(hoverButtonStyle));
                    submitButton.setOnMouseExited(event -> submitButton.setStyle(normalButtonStyle));
                }
            });

            // Set the action for the submit button
            submitButton.setOnMouseClicked(event -> {
                String playerName = nameField.getText().trim();
                if (!playerName.isEmpty()) {
                    Score score = new Score(engine.getScore(), playerName);
                    try {
                        new ScoreHandler().addScore(score);
                        engine.setCurrentStage("HighScoreStage");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    nameLabel.setText("Please enter your name, Captain!");
                    nameLabel.setTextFill(Color.RED);
                }
            });  

            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

            double centerX = (screenBounds.getWidth() - scene.getWidth()) / 2;
            double centerY = (screenBounds.getHeight() - scene.getHeight()) / 2;
            stage.setX(centerX);
            stage.setY(centerY);
            stage.setScene(scene);

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

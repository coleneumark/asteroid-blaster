package Stages;

import java.util.ArrayList;
import java.util.List;

import Interfaces.IGameObject;
import Interfaces.IStage;
import Logic.GameSettings;
import Logic.Score;
import Logic.ScoreHandler;
import Logic.GameEngine;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

//stage is used to display the high score
public class HighScoreStage implements IStage{
 private String name;
    private Stage stage;
    private Scene scene;
    private Pane root;
    private final GameEngine engine;
    private final GameSettings settings ;
    
    public HighScoreStage(String name){
        this.engine = GameEngine.getEngine();
        this.settings = GameSettings.GetInstance();
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
            this.scene = new Scene(root, settings.BOARD_WIDTH,settings.BOARD_HEIGHT);
            
            root.setStyle("-fx-background-color: black; -fx-border-color: white;");

            Font runnerFont = Font.loadFont(getClass().getResourceAsStream("/AlienRavagerItalic-4B214.ttf"), 18);
            
            Text title = new Text("High Scores");
            title.setFill(Color.WHITE);
            title.setFont(new Font(runnerFont.getName(), 120));
            title.setX(27);
            title.setY(91);
            root.getChildren().add(title);

            this.stage.initStyle(StageStyle.UNDECORATED);
            
            // Add the labels to the root pane and position them in the center
            root.setCenterShape(true);

            
            // get all of the top ten scores
            List<Score> scores = new ScoreHandler().readScores();
            int y = 80;
            int nameWidth = 200; // set the width for the name column
            int scoreWidth = 100; // set the width for the score column
            int xName = 200; // starting x position for the name column
            int xScore = xName + nameWidth + 20; // starting x position for the score column
            // loop though scores and add player name + score
            for (var item : scores) {
                Label nameLabel = new Label(item.getName());
                nameLabel.setStyle("-fx-font-size: 25");
                nameLabel.setTextFill(Color.WHITE);
                nameLabel.setAlignment(Pos.CENTER_LEFT);
                nameLabel.setPrefWidth(nameWidth);
                nameLabel.setTranslateY(y += 40);
                nameLabel.setTranslateX(xName);
            
                Label scoreLabel = new Label(Integer.toString(item.getScore()));
                scoreLabel.setStyle("-fx-font-size: 25");
                scoreLabel.setTextFill(Color.WHITE);
                scoreLabel.setAlignment(Pos.CENTER_RIGHT);
                scoreLabel.setPrefWidth(scoreWidth);
                scoreLabel.setTranslateY(y);
                scoreLabel.setTranslateX(xScore);
                
                root.getChildren().addAll(nameLabel, scoreLabel);
            }
            
            String normalButtonStyle = "-fx-background-color: black; -fx-text-fill: white; -fx-border-color: white;";
            String hoverButtonStyle = "-fx-background-color: black; -fx-text-fill: red; -fx-border-color: red;";

            Button backButton = new Button("Back to Menu");
            backButton.setLayoutX(scene.getWidth() - 220);
            backButton.setLayoutY(530);
            backButton.setFont(new Font("Arial", 25));
            backButton.setStyle(normalButtonStyle);

            backButton.setOnMouseEntered(event -> {
                backButton.setStyle(hoverButtonStyle);
            });
            
            backButton.setOnMouseExited(event -> {
                backButton.setStyle(normalButtonStyle);
            });

            root.getChildren().add(backButton);

            backButton.setOnAction(event -> {
                try {
                    engine.setCurrentStage("MainStage");// Navigate back to the main menu
                } catch (Exception ex) {
                    ex.printStackTrace();
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

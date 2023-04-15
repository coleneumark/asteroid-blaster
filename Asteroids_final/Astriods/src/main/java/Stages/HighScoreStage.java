package Stages;

import Interfaces.IGameObject;
import Interfaces.IStage;
import Logic.GameSettings;
import Logic.Score;
import Logic.ScoreHandler;
import Logic.GameEngine;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
            this.scene = new Scene(root, settings.boardWidth,settings.boardHeight);
            
            Label title = new Label("Player high score");
            root.setStyle("-fx-background-color: black;");
            // Set the font and alignment of the labels
            title.setStyle("-fx-font-size: 50");
            title.setTextFill(Color.WHITE);
            title.setAlignment(Pos.CENTER);
            this.stage.initStyle(StageStyle.UNDECORATED);
            
            // Add the labels to the root pane and position them in the center
            root.getChildren().addAll(title);
            root.setCenterShape(true);

            title.setTranslateY(20);
            title.setTranslateX(20);
            // get all of the top ten scores
            List<Score> scores = new ScoreHandler().readScores();
            int y = 40;
            int nameWidth = 200; // set the width for the name column
            int scoreWidth = 100; // set the width for the score column
            int xName = 20; // starting x position for the name column
            int xScore = xName + nameWidth + 20; // starting x position for the score column
            // loop though scores and add player name + score
            for(var item:scores){
                Label nameLabel = new Label(item.getName());
                nameLabel.setStyle("-fx-font-size: 25");
                nameLabel.setTextFill(Color.WHITE);
                nameLabel.setAlignment(Pos.CENTER_LEFT);
                nameLabel.setPrefWidth(nameWidth);
                nameLabel.setTranslateY(y+=50);
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
            
            Button backButton = new Button("Back");
            backButton.setOnAction(event -> {
                try {
                    engine.setCurrentStage("MainStage");// Navigate back to the main menu
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            root.getChildren().add(backButton);
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

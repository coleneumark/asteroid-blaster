package Stages;

import Interfaces.IGameObject;
import Interfaces.IStage;
import Logic.GameSettings;
import Logic.GameEngine;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

//The main menu stage
public class MenuStage implements IStage{
 private String name;
    private Stage stage;
    private Scene scene;
    private Pane root;
    private final GameEngine engine;
    public static MenuStage instance;
    private final GameSettings settings ;
    
    public MenuStage(String name){
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
    public void draw() { // logic for stage happens on main thread
        
    }

    @Override
    public void loadStage() { // called as soon as stage is set
        Platform.runLater(() -> { // run on main thread as soon as it becomes available again for execution
            this.stage = new Stage();
            this.root = new Pane();
            // Just some styling
            this.scene = new Scene(root, settings.boardWidth,settings.boardHeight);
            Label trustLabel = new Label("⬆: Increase Thrust");
            Label shootLabel = new Label("Space: Shoot");
            Label leftLabel = new Label("⬅: Rotate Left");
            Label rightLabel = new Label("➡: Rotate Right");
            Label decreaseTrustLabel = new Label("⬇: Decrease Thrust");
            Label pause = new Label("Enter: Pause");
            
            trustLabel.setFont(new Font(20));
            shootLabel.setFont(new Font(20));
            leftLabel.setFont(new Font(20));
            rightLabel.setFont(new Font(20));
            decreaseTrustLabel.setFont(new Font(20));            
            pause.setFont(new Font(20));


            trustLabel.setTextFill(Color.WHITE);
            shootLabel.setTextFill(Color.WHITE);
            leftLabel.setTextFill(Color.WHITE);
            rightLabel.setTextFill(Color.WHITE);
            decreaseTrustLabel.setTextFill(Color.WHITE);
            pause.setTextFill(Color.WHITE);
            
            trustLabel.setAlignment(Pos.CENTER);
            shootLabel.setAlignment(Pos.CENTER);
            leftLabel.setAlignment(Pos.CENTER);
            rightLabel.setAlignment(Pos.CENTER);
            decreaseTrustLabel.setAlignment(Pos.CENTER);
            pause.setAlignment(Pos.CENTER);
            
            root.getChildren().addAll(trustLabel, shootLabel, leftLabel, rightLabel, decreaseTrustLabel,pause);

            trustLabel.layoutXProperty().bind(root.widthProperty().divide(2).subtract(trustLabel.widthProperty().divide(2)));
            shootLabel.layoutXProperty().bind(root.widthProperty().divide(2).subtract(shootLabel.widthProperty().divide(2)));
            leftLabel.layoutXProperty().bind(root.widthProperty().divide(2).subtract(leftLabel.widthProperty().divide(2)));
            rightLabel.layoutXProperty().bind(root.widthProperty().divide(2).subtract(rightLabel.widthProperty().divide(2)));
            decreaseTrustLabel.layoutXProperty().bind(root.widthProperty().divide(2).subtract(decreaseTrustLabel.widthProperty().divide(2)));
            pause.layoutXProperty().bind(root.widthProperty().divide(2).subtract(pause.widthProperty().divide(2)));
            
            trustLabel.layoutYProperty().bind(root.heightProperty().divide(2).add(trustLabel.heightProperty().multiply(3.0)));
            decreaseTrustLabel.layoutYProperty().bind(root.heightProperty().divide(2).add(decreaseTrustLabel.heightProperty().multiply(4)));
            leftLabel.layoutYProperty().bind(root.heightProperty().divide(2).add(leftLabel.heightProperty().multiply(5)));
            rightLabel.layoutYProperty().bind(root.heightProperty().divide(2).add(rightLabel.heightProperty().multiply(6)));
            shootLabel.layoutYProperty().bind(root.heightProperty().divide(2).add(shootLabel.heightProperty().multiply(7)));
            pause.layoutYProperty().bind(root.heightProperty().divide(2).add(pause.heightProperty().multiply(8)));

            
            Label playLabel = new Label("Play");
            Label highscoreLabel = new Label("Highscore");
            Label quitLabel = new Label("Quit");
            root.setStyle("-fx-background-color: black;");
            playLabel.setTextFill(Color.WHITE);
            quitLabel.setTextFill(Color.WHITE);
            highscoreLabel.setTextFill(Color.WHITE);
            
            quitLabel.setOnMouseClicked(event -> {
                // code to be executed when label is clicked
                System.exit(0);
             });
            
            highscoreLabel.setOnMouseClicked(event -> {
                try {
                    // code to be executed when label is clicked
                    engine.setCurrentStage("HighScoreStage");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
             });
            
            playLabel.setOnMouseClicked(event -> {
                try {
                    // code to be executed when label is clicked
                    engine.setCurrentStage("GameStage");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
             });

            playLabel.setFont(new Font(36));
            highscoreLabel.setFont(new Font(36));
            quitLabel.setFont(new Font(36));
            
            playLabel.setAlignment(Pos.CENTER);
            highscoreLabel.setAlignment(Pos.CENTER);
            quitLabel.setAlignment(Pos.CENTER);

            this.stage.initStyle(StageStyle.UNDECORATED);
            // Add the labels to the root pane and position them in the center
            root.getChildren().addAll(playLabel, highscoreLabel, quitLabel);
            root.setCenterShape(true);

            playLabel.layoutXProperty().bind(root.widthProperty().divide(2).subtract(playLabel.widthProperty().divide(2)));
            highscoreLabel.layoutXProperty().bind(root.widthProperty().divide(2).subtract(highscoreLabel.widthProperty().divide(2)));
            quitLabel.layoutXProperty().bind(root.widthProperty().divide(2).subtract(quitLabel.widthProperty().divide(2)));        

            playLabel.layoutYProperty().bind(root.heightProperty().divide(2).subtract(playLabel.heightProperty().multiply(1.5)));
            highscoreLabel.layoutYProperty().bind(root.heightProperty().divide(2).subtract(highscoreLabel.heightProperty().multiply(0.5)));
            quitLabel.layoutYProperty().bind(root.heightProperty().divide(2).add(quitLabel.heightProperty().multiply(0.5)));

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

package Stages;

import Models.Asteroid;
import Models.IGameObject;
import Models.IStage;
import Models.Ship;
import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MainMenuStage extends Application implements IStage {
    private Rectangle rect;
    private double dx = 5; // horizontal velocity
    private double dy = 2; // vertical velocity
    private String name;
    private ArrayList<IGameObject> gameObjects;
    private Stage stage;
    private Scene scene;
    private Pane root;
    
    public MainMenuStage(){
        this.gameObjects = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
                    gameObjects.add(new Asteroid());               
        }
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
    public ArrayList<IGameObject> getGameObjects() {
        return this.gameObjects;   
    }
    

    @Override
    public void setGameObject(ArrayList<IGameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    @Override
    public void draw() {
        Platform.runLater(() -> {
            root.getChildren().clear();
            for(IGameObject item : this.gameObjects){
                Polygon object = item.getPolygon();
                root.getChildren().add(object);
            }
            root.setStyle("-fx-background-color: black;");
            stage.setScene(scene);
            stage.show();
        });
    }

    @Override
    public void loadStage() {
        Platform.runLater(() -> {
            stage = new Stage();
            root = new Pane();
            scene = new Scene(root, 1000, 1000);
            stage.setX(0);
            stage.setY(0);

            stage.initStyle(StageStyle.UNDECORATED);
             for(IGameObject item : this.gameObjects){
                item.OnCreate();
             }
            this.draw();
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.loadStage();
    }
    
}

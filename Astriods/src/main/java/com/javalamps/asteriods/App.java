package com.javalamps.asteriods;
import Models.IGameObject;
import Models.IStage;
import Models.Ship;
import Stages.MainMenuStage;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.initStyle(StageStyle.UNDECORATED);
        Thread thread = new Thread(() -> {
            ArrayList<IStage> stages = new ArrayList<>();
            stages.add(new MainMenuStage());
            GameEngine engine = new GameEngine(stages,0);
            try {
                engine.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        thread.start();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }

}
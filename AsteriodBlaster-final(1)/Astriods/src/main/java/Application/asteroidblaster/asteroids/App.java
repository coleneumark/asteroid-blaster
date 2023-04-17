package Application.asteroidblaster.asteroids;
import Logic.GameEngine;
import Stages.GameOverStage;
import Stages.GameStage;
import Stages.HighScoreStage;
import Stages.MenuStage;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class App extends Application {
    private GameEngine engine; // no getters/setters because app is not accessed outside of class
    
    @Override
    public void start(Stage stage) throws Exception { // JavaFX Default start is always called when JavaFX is launched
        //CReate the engine
        // refers to the single instance of GameEngine (Singleton Design Pattern - when changes are applied to specific class, all referencing components accessing will access same values (updated)
        engine = GameEngine.getEngine(); // getEngine is called - static method in class (belongs to class no need for object)
        stage.initStyle(StageStyle.UNDECORATED); // remove border from stage to get "classic arcade feeling"
        //New thread is made because the game engine will lock the main thread when it starts
        //And the main thread is teh gui thread
        Thread thread = new Thread(() -> { // new thread is necessary otherwise main thread running application will be blocked
            //load alll the stages // AND no UI (interface updates can be done) accepts function (Lambda function created)
            engine.loadStage(new GameStage("GameStage")); // ctrl click
            engine.loadStage(new MenuStage("MainStage"));
            engine.loadStage(new GameOverStage("GameOverStage"));
            engine.loadStage(new HighScoreStage("HighScoreStage"));
            // need to 
            try {
                //set the current stage
                engine.setCurrentStage("MainStage");
                //start the game
                engine.start(); // start game after setting stage
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        thread.start();
    }

    // Main function to launch application -> spin GUI of JAVFX
    public static void main(String[] args) throws Exception {
        launch(args);
    }

}
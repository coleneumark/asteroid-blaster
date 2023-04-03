import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        GameController gameController = new GameController();
        primaryStage.setScene(gameController.getScene());
        primaryStage.setTitle(GameConstants.WINDOW_TITLE);
        primaryStage.show();
        gameController.startGame();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

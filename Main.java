package Application;
//import java.util.Iterator;

// JUST IGNORE THESE FOR NOW
//// Adding a Background Image
//import javafx.scene.layout.Background;
//import javafx.scene.layout.BackgroundImage;
//import javafx.scene.layout.BackgroundPosition;
//import javafx.scene.layout.BackgroundRepeat;
//import javafx.scene.layout.BackgroundSize;

// Adding Music
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
//import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {

        VBox vbox = new VBox();   //create new VBox instance
        vbox.setMinWidth(500);    //set minimum width, control screen size
        vbox.setAlignment(Pos.CENTER); // display screen in the middle
        primaryStage.setTitle("Asteroid Blaster!");
        StackPane root = new StackPane(); // Implementation based on Pane Class
        Scene scene = new Scene(root, 300, 250); // We can just specify a base size, to be altered later (as nice to have)
        for (Menu menu : Menu.values()) { // Implementation of Main User Menu based on Enum (view separate Enum file)
   
            Button btn = new Button();   
            btn.setMinWidth(200);
            btn.setMinHeight(50);
            btn.setLayoutX(100);
            btn.setText(menu.name());    
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override 
                public void handle(ActionEvent event) {
                    System.out.println(btn.getText()); //Or "1" as in your code
                }
            });
            vbox.getChildren().add(btn); //add button to your VBox
    	}
        // https://edencoding.com/scene-background
//    	try {
//	    // Load the background image
//	    Image backgroundImage = new Image(getClass().getResourceAsStream("https://img.freepik.com/premium-vector/space-asteroid-surface-planet-with-craters-surface-space-planets-landscape-comet-crater-cartoon-illustration_102902-834.jpg"));
//
//	    // Create a background image view
//	    BackgroundImage background = new BackgroundImage(backgroundImage,
//	            BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
//	            BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
//
//	    // Set the background of the scene
//	    root.setBackground(new Background(background));
//	} catch (Exception e) {
//	    // Handle the exception here
//	}

        root.getChildren().add(vbox); //root of your pane (pain :/), or all evil, wuahahaha
        primaryStage.setScene(scene); //primaryStage is your stage, scene is the current scene
        primaryStage.show(); // display on screen (pops up...like magic)
        

// Additional....
//    	try {
//    	    // Load the music file
//    	    Media media = new Media(getClass().getResource("/space_theme.mp3").toString());
//
//    	    // Create a media player
//    	    MediaPlayer mediaPlayer = new MediaPlayer(media);
//
//    	    // Set the player to loop indefinitely
//    	    mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
//
//    	    // Start playing the music
//    	    mediaPlayer.play();
//    	} catch (Exception e) {
//    	    // Handle the exception here
//    	}
    }
}

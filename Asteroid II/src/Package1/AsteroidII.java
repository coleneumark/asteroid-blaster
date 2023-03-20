package Package1;

//References
//https://java-programming.mooc.fi/part-14/3-larger-application-asteroids
//https://anna-scott.medium.com/game-of-asteroids-using-javafx-afd040353f13
//https://javafxtuts.wordpress.com/javafx-polygon/
//https://www.youtube.com/watch?v=9xsT6Z6HQfw&t=1710s (Asteroids in JavaFX - Part 1)
//https://www.youtube.com/watch?v=7Vb9StpxFtw&t=21s (Asteroids in JavaFX - Part 2)

import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.stage.Stage;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;

public class AsteroidII extends Application {

	//Create Space Objects////////////////////////////////////////////////////
	public static int WIDTH = 800;
	public static int HEIGHT = 600;
	
	@Override
	public void start(Stage stage) throws Exception {
		//Create the pane, scene and stage
		
		stage.setTitle("Asteroids II Game");
		BorderPane pane = new BorderPane();
	    
	    Scene scene = new Scene(pane);
	    stage.setScene(scene);
	    
	    Canvas canvas = new Canvas(WIDTH,HEIGHT);
	    GraphicsContext gContext = canvas.getGraphicsContext2D();
	    pane.setCenter(canvas);
	    
	    //Display Space background
	    SpaceObject space = new SpaceObject("C:/Users/corma/Desktop/Java/Labs/Asteroid II/src/Package1/Asteroidimages/Space-PNG-Picture.png");
	    space.position.set (WIDTH/2, HEIGHT/2);
	    
	    //Display Player Ship
	    SpaceObject playership = new SpaceObject("C:/Users/corma/Desktop/Java/Labs/Asteroid II/src/Package1/Asteroidimages/spaceship5.png");
	    playership.position.set (WIDTH/2, HEIGHT/2);
	    playership.speed.set(25,0);
	    
	    //Display Enemy Ship
	    SpaceObject enemy = new SpaceObject("C:/Users/corma/Desktop/Java/Labs/Asteroid II/src/Package1/Asteroidimages/enemy1.png");
	    enemy.position.set (200, 100);
	    
	    //Create a list of Large Asteroids
	    ArrayList<SpaceObject> largeasteroidlist = new ArrayList<SpaceObject>();
	    int numAst = 3;
	    for(int i=0;i<numAst;i++) {
	    	 SpaceObject largeasteroid = new SpaceObject("C:/Users/corma/Desktop/Java/Labs/Asteroid II/src/Package1/Asteroidimages/Asteroid1.png");
	    	 double x = WIDTH * Math.random();
	    	 double y = HEIGHT * Math.random();	 
	    	 double angle = 360 * Math.random();
	    	 largeasteroid.position.set (x, y);
	    	 largeasteroid.speed.setLength (25);
	    	 largeasteroid.speed.setAngle (angle);
	    	 largeasteroidlist.add(largeasteroid); 
	    }
	    //Display Medium Asteroid
	    SpaceObject mediumasteroid = new SpaceObject("C:/Users/corma/Desktop/Java/Labs/Asteroid II/src/Package1/Asteroidimages/Asteroid2.png");
	    mediumasteroid.position.set (200, 200);
	    
	    //Display Small Asteroid
	    SpaceObject smallasteroid = new SpaceObject("C:/Users/corma/Desktop/Java/Labs/Asteroid II/src/Package1/Asteroidimages/Asteroid3.png");
	    smallasteroid.position.set (50, 200);
	    
	    //Set Key Board Events - Press and Release //////////////////////////////////////////////////////////////////  
        Map<KeyCode, Boolean> pressKey = new HashMap<>();
        scene.setOnKeyPressed(event -> {
            pressKey.put(event.getCode(), Boolean.TRUE);
        });

        scene.setOnKeyReleased(event -> {
            pressKey.put(event.getCode(), Boolean.FALSE);
        });
	    
	    //Start the Animation
	    new AnimationTimer() {
            @Override
            //Movement for player ship on pressing keys
            public void handle(long now) {
            
            	if(pressKey.getOrDefault(KeyCode.LEFT, false)) {
                    playership.turn -= 2;
                }
                if(pressKey.getOrDefault(KeyCode.RIGHT, false)) {
                	playership.turn += 2;
                }
                if(pressKey.getOrDefault(KeyCode.UP, false)) {
                	playership.speed.setLength(100);
                	playership.speed.setAngle(playership.turn);
                }
                
            	//Update and Display all Space Objects
            	space.display(gContext);
            	playership.display(gContext);
            	playership.update(1/60.0);
            	for(SpaceObject asteroid : largeasteroidlist) {
            		asteroid.update(1/60.0);
            	}
            	for(SpaceObject asteroid : largeasteroidlist) {
            		asteroid.display(gContext);
            	}
            	//mediumasteroid.display(gContext);
            	//smallasteroid.display(gContext);
            	//enemy.update(1/60.0);
            	enemy.display(gContext);
            	
            	for (int astnum = 0;astnum < largeasteroidlist.size() ;astnum++) {
            		SpaceObject largeasteroid = largeasteroidlist.get(astnum);
            		if (largeasteroid.overlaps(playership)){
            			stop();
            		}
            	}
            	
            }
               
        }.start();
	    
	    
	    
	    
	    
	    
	    stage.show();
}	
	
	//Launch the main program//////////////////////////////////////
	public static void main(String[] args) { 
		try {
		launch(args);
		}
		catch (Exception error) {
		error.printStackTrace();
		}
		finally {
			System.exit(0);
		}
	}
	
}

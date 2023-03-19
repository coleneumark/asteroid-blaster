package package1;

//References
//https://java-programming.mooc.fi/part-14/3-larger-application-asteroids
//https://anna-scott.medium.com/game-of-asteroids-using-javafx-afd040353f13
//https://javafxtuts.wordpress.com/javafx-polygon/

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.paint.Color;
import javafx.animation.AnimationTimer;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;

public class Asteroid extends Application {

	//Create Space Objects////////////////////////////////////////////////////
	public static int WIDTH = 600;
	public static int HEIGHT = 400;
	
	@Override
	public void start(Stage stage) throws Exception {
		//Create the pane, scene and stage
		Pane pane = new Pane();
	    pane.setPrefSize(WIDTH, HEIGHT);
	    
	    Scene scene = new Scene(pane);
	    stage.setScene(scene);
	    scene.setFill(Color.LIGHTBLUE);
	    stage.setTitle("Asteroids Game");
	    stage.show();
	    
	    //Create Player Ship, Enemy Ship
	    PlayerShip playership = new PlayerShip(500, 300);
	    pane.getChildren().add(playership.getSpaceObject());
	    
	    EnemyShip enemyship = new EnemyShip(300, 200);
	    pane.getChildren().add(enemyship.getSpaceObject());
	    
	    //Create a list for Player Bullets
	    List<PlayerBullet> playerbullets = new ArrayList<>();
	    
	    //Create and move Large Asteroids////////////////////////////////
	    //Number of Large Asteroids
	    int numAsteroids = 1;
	    List<LargeAsteroid> largeasteroids = new ArrayList<>();
	    
	    //Add Large Asteroids to list and place in random parts of Stage
	    for (int i = 0; i < numAsteroids ; i++) {
	        Random rnd = new Random();
	        LargeAsteroid largeasteroid = new LargeAsteroid(rnd.nextInt(300), rnd.nextInt(200));
	        largeasteroids.add(largeasteroid);}
	    largeasteroids.forEach(largeasteroid -> pane.getChildren().add(largeasteroid.getSpaceObject()));
	    //Allows Large Asteroids to move (at different speeds) without pressing keys
        largeasteroids.forEach(largeasteroid -> largeasteroid.accelerate());
	    
	    //Create Small Asteroid
	    SmallAsteroid smallasteroid = new SmallAsteroid(50, 200);
	    pane.getChildren().add(smallasteroid.getSpaceObject());
	    //Accelerate small Asteroid
	    for (int i=0;i<50;i++) {
		    smallasteroid.accelerate();
		    }
	    
	    //Set Key Board Events - Press and Release //////////////////////////////////////////////////////////////////  
        Map<KeyCode, Boolean> pressKey = new HashMap<>();
        scene.setOnKeyPressed(event -> {
            pressKey.put(event.getCode(), Boolean.TRUE);
        });

        scene.setOnKeyReleased(event -> {
            pressKey.put(event.getCode(), Boolean.FALSE);
        });
          
       //Set Animation - starts objects moving//////////////////////////////////// 
        new AnimationTimer() {
            @Override
            //Movement for player ship on pressing keys
            public void handle(long now) {
                if(pressKey.getOrDefault(KeyCode.LEFT, false)) {
                    playership.turnLeft();
                }

                if(pressKey.getOrDefault(KeyCode.RIGHT, false)) {
                	playership.turnRight();
                }
                
                if(pressKey.getOrDefault(KeyCode.UP, false)) {
                	playership.accelerate();
                }
                
                //Create Player Bullet and Bullet Movement on pressing keys
                if (pressKey.getOrDefault(KeyCode.SPACE, false) && playerbullets.size() < 100 ) {
                    PlayerBullet playerbullet = new PlayerBullet((int) playership.getSpaceObject().getTranslateX(), (int) playership.getSpaceObject().getTranslateY());
                    playerbullet.getSpaceObject().setRotate(playership.getSpaceObject().getRotate());
                    playerbullets.add(playerbullet);
                    pane.getChildren().add(playerbullet.getSpaceObject());
                }
                
                System.out.println(playerbullets.size());
                //Set Space Objects in motion
                playership.move();
                //largeasteroids.forEach(largeasteroid -> largeasteroid.move());
                playerbullets.forEach(playerbullet -> playerbullet.move());
                //smallasteroid.move();
           
                //If collision happens stop the game
                largeasteroids.forEach(largeasteroid -> {
                    if (playership.collide(largeasteroid) || playership.collide(smallasteroid)) {
                        stop();
                    }
                    });
                }
        }.start();
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



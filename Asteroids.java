package Application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Asteroids extends Application {
	
	public static void main(String[] args)
	{
		// need to implement error-handling, wrap inside of try-catch 
		// to effectively handle exceptions in case of any error
		try
		{
			// FIRST TRY RUNNING (away... :( and never return...)
			launch(args);
		}
		catch (Exception error)
		{
			// THIS HAPPENS IF AN ERROR OCCURS
			// prints the throwable along 
			// with other details like the line number and class name where the exception occurred.
			error.printStackTrace();
		}
		finally
		{
			// This right here will always run (even if there is an error -_-
			System.exit(0); // --> exit application because it sucks (terminates the currently running Java Virtual Machine.)
		}
	}
		
		// Because it extends (inherits) from the Application class, we'll need to implement the start() method
		// The JavaFX Stage class is the top level JavaFX container.
		// that hosts a Scene, which consists of visual elements...ah the world's a stage...
		public void start(Stage primaryStage) {
			// Set title of primary window
			primaryStage.setTitle("Asteroid Blaster!");
			
			// Implementing the Layout Manager 
			// Object lays out children in top, left, right, bottom, and center positions.
			BorderPane root = new BorderPane();
			// Makin' a scene! Aww yisss
			// add root object as input into Scene
			Scene primaryScene = new Scene(root);
			// Attaching the scene to the Stage
			primaryStage.setScene(primaryScene);
			
			// Display graphics for the game, need a Canvas object		
			Canvas canvas = new Canvas(800,600); // 800 by 600 pixels is usually the default, fit for most screens :)
			// Necessary to perform draw operations on the Canvas:
			GraphicsContext context = canvas.getGraphicsContext2D();// get context object from the canvas
			// need to add the canvas to the root (of all evil)
			// BorderPane allows us to set containers, objects and nodes on different
			// regions of the screen
			root. setCenter(canvas); // this will add canvas directly in the center (midpoint)
			context.setFill(Color.PINK); // example to test colour, noice and pink ;-)
			context.fillRect(0, 0, 800, 600); // fills up the entire canvas (check pixels specified in prior section)
			// display window
			primaryStage.show();
		}
		
		
	}



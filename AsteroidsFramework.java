package application;
	
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
//import javafx.geometry.Dimension2D; // better way of implementing - look into it
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;


public class AsteroidsFramework extends Application {
	
	// just setting the screen size (we should think of allowing the 
	// player to set it in the settings later
	Point2D size = new Point2D(800, 600);
//	private static final Dimension2D WINDOW_SIZE = new Dimension2D(800, 600);
	
	// INPUT HANDLING
	// WANT TO SEE WHEN INPUT IS RECEIVED ON THE KEYBOARD BY THE SCENE
	Set<KeyCode> keysDown = new HashSet<>(); // here we set the specified keys (see below) for event handler (java.util)
	
	// need to determine when key is down
	int key(KeyCode k) {
		return keysDown.contains(k) ? 1 : 0; // ternary operator, basic True False
	}
	
	public void start(Stage stage) throws Exception {
		// default settings
		// creates a new Group object that serves as 
		// the root of the scene graph hierarchy and allows 
		// us to add Node objects to it, which will be displayed 
		// on the screen when the JavaFX application is run.
		Group root = new Group();
		Scene scene = new Scene(root, size.getX(), size.getY());
//		Scene scene = new Scene(root, WINDOW_SIZE.getWidth(), WINDOW_SIZE.getHeight());
		
		stage.setScene(scene);
		stage.setTitle("Asteroid Blaster!"); // just basic title of our game
		scene.setFill(Color.BLACK); // Set the background color of the scene, we can change this later when we improve the design
		
		// setting label to see frames per second
		// extends node from JavaFX
		Label label = new Label();
		label.setTranslateX(2); // moving label to two pixels
		label.setTextFill(Color.PURPLE); // text to display, nice and purple :p
		
		String labelText = "\nSurvival Time: %.2f seconds\n Asteroids Destroyed: %d";
		Group gameAssets = new Group();
		Group gameAsteroids = new Group();
		Group gameBullets = new Group();
		
		// 
		gameAssets.getChildren().addAll(gameAsteroids, gameBullets); // add bullets and asteroids to the game
		
		Ship ship = new Ship(gameAssets, size.multiply(0.5)); // start it at half the width and half the height of the scene
		// if the player loses/dies, the following text will be displayed
		Label loseLabel = new Label();
		// want to set it to the middle of the screen (approx. area)
		loseLabel.setTranslateX(size.getX() / 2 - 200);
		loseLabel.setTranslateY(size.getY() / 2 - 100);
		// standard colour text to appear on black screen
		loseLabel.setTextFill(Color.RED);
		loseLabel.setFont(Font.font(32));
		loseLabel.setVisible(false); // initially it will not be displayed, only to appear when player dies
		// create separate list of asteroids to keep track of individual asteroids
		List<Asteroid> asteroids = new LinkedList<>(); // linked list, basic addition and subtraction, reduce overhead
		List<Bullet> bullets = new LinkedList<>(); // list to keep track of our bullets
		
		// add list of elements to scene
		
		// add game assets on top of FPS label
		root.getChildren().addAll(gameAssets, loseLabel, label);
		
		// When a key is pressed on the keyboard
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				System.out.println(event.getCode()); // testing to see what key it is (for testing)
				keysDown.add(event.getCode()); // add key code whenever something is down
			}
		});
		
		// want to remove it when keys are not pressed
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				keysDown.remove(event.getCode());
			}
		});
		// need them to disappear from screen (leaves scene -> make sure it is really destroyed)
		// make node/shape the size of the screen
		Rectangle bounds = new Rectangle(0, 0, size.getX(), size.getY());
		// make it visible
		bounds.setVisible(false);
		// add to root to be used for collision "simulate collision"
		root.getChildren().add(bounds);
		
		/*BASIC SETUP*/
		// NB NB for our game loop to run smoothly
		AnimationTimer loop = new AnimationTimer() {
			// basically need to compare time between frames, 
			// need old time and new time and time elapsed to be calculated
			double old = -1;
			double elapsed = 0;
			// cooldown period for the bullet
			double bulletWait = 0.3;
			// timer to keep track of the cooldown/waiting period
			double bulletTimer = 0;
			
			int score = 0; // how many asteroids are destroyed
			boolean died = false;
			
			int asteroidCount = 20; // e.g we start with about 20 asteroids on screen (need to account for this later per object)
			double asteroidWait = 3;
			double asteroidTimer = 0;
			
			// Animation timer implement handle method
			// handle method is invoked at the start of every frame
			public void handle(long nano) {
//				double old = -1;
//				double elapsedTime = 0;
				if (old < 0) old = nano; // check if 'old' has been set up
				double delta = (nano - old) / 1e9; // NB, time receiving is in nanoseconds (timestamp) so we'll need to convert it to seconds
				
				old = nano; 
				elapsed += delta;
				// use string formatter to truncate the values to improve readability
				label.setText(String.format("%.2f  %.2f", 1/delta, elapsed)); // show us the time elapsed
				
				/* GAME LOOP AND STUFF THAT OCCURS IN THE GAME TO BE ADDED HERE*/
//				System.out.println(keysDown); // testing output to keep track of everything that happens when keys are down
				for (Asteroid asteroid : asteroids) {
					// iterate through the list of bullets
					for (Bullet bullet : bullets) {
						// if they intersect
						if (asteroid.collides(bullet)) {
							// we want to destroy the asteroid
							asteroid.destroy(gameAsteroids);
							// also destroy the bullet
							bullet.destroy(gameBullets);
							score++;
							// break out of the internal for loop
							break;
						}
					}
					// if the asteroid is already "dead" we do not want to
					// do anything, will just be skipped, to be removed 
					if (!asteroid.alive) continue;
					// determine if asteroid hit the player
					if (asteroid.collides(ship)) {
						// remove ship from game
						ship.destroy(gameAssets);
						// remove asteroid from game
						asteroid.destroy(gameAsteroids);
						loseLabel.setText(String.format(
							"Aww! You died :(",
							labelText, elapsed, score
						));
						loseLabel.setVisible(true); // now this label we displayed if the player lost/died
						died = true;
					}
					// if asteroid leaves the bounds, then we want to destroy it
					if (asteroid.outerBounds(bounds)) asteroid.destroy(gameAsteroids);
					// otherwise just update
					else asteroid.update(delta);
				}
				
				for (Bullet bullet : bullets) { 
					// if the bullet is leaving the bounds, we want to destroy it
					if (bullet.outerBounds(bounds)) bullet.destroy(gameBullets);
					// otherwise we want to update it
					else bullet.update(delta); // to move the bullets
				}
				// remove list of asteroids from game (already removed it from scene), use Lambda function, shortened
				asteroids.removeIf(a -> !a.alive);
				// same logic applies to bullets
				bullets.removeIf(b -> !b.alive);
				
				while (asteroids.size() < asteroidCount) {
					// once removed, asteroids will be added back in
					asteroids.add(Asteroid.make(gameAsteroids, size)); // add 20 asteroids in first frame
					// game will be re-populated with asteroids as we destroy them in the game
				}
				// Need to disable interaction if player died
				// first check if player is still alive
				if (!died) { // if we have not died yet
					asteroidTimer += delta;
					if (asteroidTimer > asteroidWait) { 
						asteroidCount++; // add another asteroid to increase difficulty, handle every 3 seconds
						asteroidTimer = 0;
					}
					
					// if space key is pressed and the bullet timer has run out
					if (key(KeyCode.SPACE) == 1 && bulletTimer <= 0) {
						// create new bullet inside of gameBullets, get ship's position, velocity, ship's angle
						Bullet bullet = new Bullet(gameBullets, ship.position, ship.velocity, ship.theta);
						// add to list of bullets
						bullets.add(bullet);
						// reset the bullet timer to bullet
						bulletTimer = bulletWait;
					}
					// decrement bullet timer by delta
					bulletTimer -= delta;
					// if the ship is out of bounds, the player also loses
					if (ship.outerBounds(bounds)) {
						ship.destroy(gameAssets);
						// need to account for code reuse, perhaps specify text to be displayed per instance, bad practice, reduce code reuse
						loseLabel.setText(String.format(
							"Oohf! You got sucked up by a black hole!",
							labelText, elapsed, score
						));
						loseLabel.setVisible(true);
						died = true;
					} else {
						// 4 is constant works well to smooth keystrokes
						double rotation = 4 * (key(KeyCode.RIGHT) - key(KeyCode.LEFT)); // if right is pressed, -1, if both pressed cancelled out
						ship.update(delta, rotation, key(KeyCode.UP)); // otherwise get rotation and call ship update, thrust
					}
				}
			}
			
		};
		loop.start(); // start our game loop, will now display the frame rate (60 fps) and time elapsed in seconds on screen, will change when we discuss further design
		
		
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
// we only want to inherit, as these are mainly our objects, so we don't want to implement them as specific types
// more generally defined
abstract class SpaceObject {
	Point2D position, velocity;
	double theta, omega; // angle and velocity of the object
	Group root, transform;
	boolean alive;
	// not built into Java Math module, so define it (set variable to handle)
	final static double TAU = Math.PI * 2;
	
	// Constructor with input to be passed
	// parent, initial position and velocity, as well as initial angle and angular velocity
	public SpaceObject(Group parent, Point2D p0, Point2D v0, double theta0, double omega0) {
		root = new Group();
		transform = new Group();
		root.getChildren().add(transform);
		parent.getChildren().add(root);
		
		// just basic implementation to set when constructor is called
		// sources referred to Jared Pincus tutorial
		position = p0;
		velocity = v0;
		theta = theta0;
		omega = omega0;
		alive = true; // start with life points (alive = true)
	}
	
	// need to update every frame
	public void update(double delta) { // need to know how many seconds have passed since the last frame (delta)
		position = position.add(velocity.multiply(delta)); // add current velocity * delta to position
		// velocity (pixels per second) * delta --> changes pixels per frame
		theta = (theta + omega * delta) % TAU; // * delta --> radians per second, we mod by TAU to keep it between zero and two pi
		
		transform.getTransforms().clear();
		transform.getTransforms().addAll(
				// 2D translation
				// Transforms need to be applied to be in correct order 			
			new Translate(position.getX(), position.getY()),
			// (is not in degrees, so we'll have to convert it first),
			new Rotate(Math.toDegrees(theta))
		);
	}
	// here we have a function which kills a game object from the game
	// still need to add asteroids to the destroy function to account for sizes
	public void destroy(Group parent) {
		// kills its own parent :/ 
		parent.getChildren().remove(root);
		// if object is destroyed
		alive = false;
	}
	// make it abstract for each space object to implement in its own manner
	abstract Shape getBounds();
	
	// handle collisions --> basic implementation
	public boolean collides(SpaceObject so) {
		// intersection between the boundaries is not nothing, indicating that some intersection occurs between our two shapes/objects
		return alive && so.alive && // need to compare two objects, determine if both are alive
			!Shape.intersect(getBounds(), so.getBounds())
			.getBoundsInLocal().isEmpty();
	}
	// asteroids within bounds, but they're moving into the bounds, we don't want them to disappear
	public boolean outerBounds(Rectangle bounds) {
		if (!Shape.intersect(getBounds(), bounds).getBoundsInLocal().isEmpty()) {
			// if there's an intersection with the rectangle (the screen) and the shape
			// we'll know it's not leaving the boundaries
			return false;
		}
		
		double x = position.getX();
		double y = position.getY();
		double boundWidth = bounds.getWidth();
		double boundHeight = bounds.getHeight();
		// determine if shape is leaving the bounds
		return
				// if moving to left, return true
			x <= 0 && velocity.getX() <= 0 ||
					// if on right side and moving to the right
			x >= boundWidth && velocity.getX() >= 0 ||
			// if it's on top/up return true
			y <= 0 && velocity.getY() <= 0 ||
			// if it's on bottom return true
			y >= boundHeight && velocity.getY() >= 0;
	}
	// need to focus on implementing Encapsulation, handle public effectively
	// so far this is just our basic framework
	// look at Dimensions for optimisation
	static Point2D angledVector(double angle, double magnitude) { // construct our vectors based on angle and magnitude
		return new Point2D(Math.cos(angle), Math.sin(angle)).multiply(magnitude);
	}
	// generate some randomness as we don't want our asteroids all clustered in one place
	static double rand(double min, double max) {
		// can optimise how randomness is generated - look at seeded randomness
		return Math.random() * (max - min) + min;
	}
}
// ship implements from our abstract SpaceObject 
// we'll focus on implementing separately, for now this is just to get our framework to look at basic functionality
class Ship extends SpaceObject {
	double thrust = 150; // thrust set to 150 pixels per second, which specifies its acceleration
	Polygon polygon; // draw the ship
	
	// constructor of our spaceship
	public Ship(Group parent, Point2D position) { // initial position passed in
		// parent, position, initial velocity, initial theta and omega also set to 0
		super(parent, position, Point2D.ZERO, 0, 0); // need to call super to implement functionality of the previously defined Space Object
		// give as list of points
		polygon = new Polygon(
				// list of points -> x, y, x, y, x, y...etc
				// start at x: 0.7, y: 0, x: -0.7, y: -0.4...and so on
			0.7, 0, -0.7, -0.4, -0.7, 0.4 // set to very small, will be scaled up later
		);
		// add specification and set styling
		transform.getChildren().add(polygon);
		
		polygon.setStroke(Color.rgb(226, 76, 181)); // #e24cb5
		polygon.setStrokeWidth(0.1); // set teeny tiny and add scaling
		polygon.getTransforms().add(new Scale(30, 30)); // scaling will make everything bigger, including the stroke
		
		update(0, 0, 0); // set up everything without moving the ship
	}
	// based on the input, we need to have some rotation for our ship
	// also have some thrust
	public void update(double delta, double omega, double throttle) {
		if (throttle != 0) { // if throttle is not 0 we'll need to determine acceleration of our ship
			Point2D acceleration = angledVector(theta, thrust * throttle); // get angled vector at theta, which is the angle of the ship (multiply thrust by throttle
			velocity = velocity.add(acceleration.multiply(delta));
		} else { // if throttle is zero, we'll need to slow down the ship
			velocity = velocity.multiply(1 - 0.2 * delta); // decrements a bit of the velocity at each frame
			// to prevent extensive speedup
		}
		// change on ship's rotation based on input
		this.omega = omega;
		super.update(delta); // call super.update to move the position, update the transform, etc specified in SpaceObject
	}
	// implement the abstract Shape object 
	public Shape getBounds() { return polygon; } // keep it simple, just return the polygon (shape = type of node)
}
// to handle our asteroids (extends from abstract physics object
class Asteroid extends SpaceObject {
//	Circle circle;//
	Polygon rock; // use polygons instead
	// similar implementation to ship
	public Asteroid(Group parent, double radius, Point2D p0, Point2D v0, double omega) {
		super(parent, p0, v0, 0, omega);

		rock = new Polygon(); // 20,50 - reddish, 0,0.2 - yellowish, still need to change depending on design
		rock.setStroke(Color.hsb(rand(20,50), rand(0,0.2), rand(0.8,1))); // hsb = hue saturation balance -> we can change hue, saturation, etc based on values
		rock.setStrokeWidth(3);
		rock.setFill(Color.TRANSPARENT);
		
		int points = 20;
		// go through the points
		for (int i = 0; i < points; i++) {
			// approximate circle to add some "roughness" edges to the asteroids (implemented idea from code jam 2020
			double angle = (i * TAU) / points; // split circle into 20 pieces/edges, get the radius of point from centre
			double r = radius * rand(0.9,1.1); 
			rock.getPoints().addAll(Math.cos(angle) * r, Math.sin(angle) * r); //x value + sine value
		}
		
		transform.getChildren().add(rock);		
		
		update(0);
	}
	
	public Shape getBounds() { return rock; }
	// set size of screen
	static Asteroid make(Group parent, Point2D size) {
		// pick angle relative to the centre of the screen
		double angle = Math.random() * TAU;
		double radius = rand(20, 30); // set radius of asteroids between, customise later
		double omega = rand(-2, 2); // rotational velocity
		// magnitude of the size of the screen
		// asteroid will start off screen somewhere between right off screen and double the distance off screen
		// will show up at different areas
		double cDistance = size.magnitude() / 2 * rand(1, 2); // distance between centre of the screen
		Point2D position = angledVector(angle, cDistance).add(size.multiply(0.5)); // set location
		Point2D velocity = angledVector(Math.PI + angle + rand(-0.2,0.2), rand(50, 100)); // need it to point back to the centre if the screen
		return new Asteroid(parent, radius, position, velocity, omega);
	}
}
// need a mu===
class Bullet extends SpaceObject {
	Circle circle;
	// muzzle velocity = the velocity with which a bullet or shell leaves the muzzle of a gun.
	static double muzzle = 400;
	
	public Bullet(Group parent, Point2D sPosition, Point2D vShip, double angle) {
		super(parent, sPosition, angledVector(angle, muzzle).add(vShip), angle, 0);
		circle = new Circle(3, Color.rgb(201, 47, 47)); // radius of three, colour #C92F2F (dark reddish, can change later)
		transform.getChildren().add(circle); // add to its own transform
	}
	
	Shape getBounds() { return circle; }
}


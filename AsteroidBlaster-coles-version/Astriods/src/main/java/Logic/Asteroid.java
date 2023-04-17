package Logic;

import Interfaces.IGameObject;
import Interfaces.IAsteroid;
import Interfaces.IBullet;
import static Logic.AsteroidSize.Medium;
import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

//An astroid has 3 sizes, based on the polygonSize the speed 
//will be changed
public class Asteroid implements IGameObject, IAsteroid {
    private int x;
    private int y;
    private final Random rnd;
    private Polygon polygon;
    private int currentXDestination;
    private int currentYDestination;
    private GameSettings settings = new GameSettings();
    private AsteroidSize size = AsteroidSize.Large;
    private boolean isAlive = true;
    private int speed;
    
    public Asteroid(AsteroidSize size){
        this.rnd = new Random();
        this.size = size;
        this.x = rnd.nextInt(settings.boardHeight)-60; 
        this.y = rnd.nextInt(settings.boardWidth)-60;
        settings = GameSettings.GetInstance();
    }
    
    //Since the astroids will be recreated a lot of times the on create function
    // is call in the constructor
    public Asteroid(AsteroidSize size,int x, int y){
        this.rnd = new Random();
        this.size = size;
        this.x = x;
        this.y = y;
        settings = GameSettings.GetInstance();
        this.onCreate();
    }
    
    //Returns the polygonSize of the asteroid
    @Override
    public AsteroidSize getSize(){
        return this.size;
    }
    
    //creates the polygons for the asteroid
    private Polygon createAstriod(double x, double y, AsteroidSize asteriodSize) {
        int polygonSize = 0;
        // switch case to assign the size of the asteroid
        switch (asteriodSize) {
            case Large: polygonSize = 100;
                break;
            case Medium: polygonSize = 70;
                break;
            case Small: polygonSize = 30;
                break;
        }
        Polygon shape = new Polygon();
        double[] points = { x + polygonSize * 0.1, y + polygonSize * 0.5,
                        x + polygonSize * 0.3, y + polygonSize * 0.2,
                        x + polygonSize * 0.8, y + polygonSize * 0.3,
                        x + polygonSize * 0.9, y + polygonSize * 0.7,
                        x + polygonSize * 0.5, y + polygonSize * 0.9,
                        x + polygonSize * 0.1, y + polygonSize * 0.7 };
         for (double point : points) {
            shape.getPoints().add(point);
        }
       
        shape.setStroke(Color.WHITE);
        shape.setStrokeWidth(2.0);
        return shape;
    }
    
    @Override
    public void onCreate() {
        this.setRandomDestination();
        int upperbound = 0;
        int lowerbound = 0;
        
        //Set the lower and upper bound of the asteroid which 
        //is used to random its speed
        switch(this.size){
            case Large:
                // lower bound inclusive ()
                // upper bound exclusive []
                lowerbound =1;
                upperbound = 3;
                break;
            case Medium:
                lowerbound = 3;
                upperbound = 6;
                break;
            case Small:
                lowerbound = 5;
                upperbound = 7;
                break;
        }
        
        //Random the speed based on the bounds.
        this.speed =  rnd.nextInt(upperbound-lowerbound) + lowerbound;
    }
    
    //Set a random destination on the stage for the asteroid to move to
    private void setRandomDestination(){
        this.currentXDestination = rnd.nextInt(settings.boardHeight)-60;        
        this.currentYDestination = rnd.nextInt(settings.boardWidth)-60;        
    }

    @Override
    public void update() {
        
        //If the asteroid is more or less 10 units within its target generate a new destination
        if((Math.abs(this.x - this.currentXDestination) < 10) && (Math.abs(this.y - this.currentYDestination) < 10)) {
            this.setRandomDestination();
        }
        
        //move x
        if(this.x != this.currentXDestination){
            if(this.x < this.currentXDestination){
                this.x+= speed;
            }else{
                this.x-= speed;
            }
        }
        //Move y
        if(this.y != this.currentYDestination){
            if(this.y < this.currentYDestination){
                this.y += speed;
            }else{
                this.y -= speed;
            }
        }
        // redraws the asteroid on a new position
        this.polygon = createAstriod(x,y,this.size);
    }
    
    
    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public Polygon getPolygon() {
        return polygon;
    }

    @Override
    public double getSpeed() {
       return this.speed;
    }
    
    //If the asteroid colides with a player bullet it should die
    @Override
    public boolean hasCollided(IGameObject object) {
        //check if the game object is a bullet. This is done by checking 
        //if the game object implements the IBullet interface
        if (object instanceof IBullet){
            //cast the game object to a bullet 
            IBullet bullet = (IBullet) object;
            //Check if this bullet is a player bullet
            if(bullet.getIsPlayerBullet()){
                this.isAlive = false;
            }
        }
        return false;
    }

    @Override
    public boolean getIsAlive() {
          return this.isAlive;
    }

}
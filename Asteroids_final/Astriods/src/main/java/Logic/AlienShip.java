package Logic;

import Interfaces.IAlienShip;
import Interfaces.IBullet;
import Interfaces.IGameObject;
import Stages.GameStage;
import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

//The alien ship class is created each round, the health of the ship is decided
//by the level x 3. The ship will fire at the players position.
public class AlienShip implements IGameObject, IAlienShip{
    private int x;
    private int y;
    private final GameSettings settings ;
    private final double speed = 3;
    private int lives;
    private final GameEngine engine;
    private boolean isAlive = true;
    private int currentXDestination;
    private int currentYDestination;
    private final Random rnd = new Random();
    private long timeStamp =-1;
    private final GameStage stage;
    
    //Default constructor
    public AlienShip(int lives){
        this.lives = lives;
        this.settings = GameSettings.GetInstance();
        this.engine = GameEngine.getEngine();
        this.stage = ((GameStage)engine.getCurrentStage());
    }
    
    @Override
    public void onCreate() {
    }

    @Override
    public void update() {
        
        //If the timestamp value is still default (-1) no valid value has been assigned yet
        if(timeStamp == -1){
            this.timeStamp = System.currentTimeMillis();
        }
        
        //Fire a bullet every 1 second
        if(((System.currentTimeMillis() - this.timeStamp) >  1000)){
            this.shoot();
            this.timeStamp = System.currentTimeMillis();
        } 
        
        //If the ship is more or less 10 units within its destination set a new course for AlienShip
        if((Math.abs(this.x - this.currentXDestination) < 10) && (Math.abs(this.y - this.currentYDestination) < 10)) {
            setRandomDestination();
        }
        
        //move the ship in the x direction
        if(this.x != this.currentXDestination){
            if(this.x < this.currentXDestination){
                this.x += speed;
            }else{
                this.x -= speed;
            }
        }
        //move the ship in the y direction
        if(this.y != this.currentYDestination){
            if(this.y < this.currentYDestination){
                this.y += speed;
            }else {
                this.y -= speed;
            }
        }
        //Return a new polygon with new destination
        this.getPolygon();
    }
    
    //Set a random destination for the ship based on the board
    private void setRandomDestination(){
        this.currentXDestination = rnd.nextInt(settings.boardWidth) - 80;        
        this.currentYDestination = rnd.nextInt(settings.boardHeight) - 60;        
    }
    
    //Draw the Alien ship
    @Override
    public Polygon getPolygon() {
        double width = 60;
        double height = 20;
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(new Double[]{
            0.0, height / 2,
            width / 3, height / 3,
            width / 2, -height / 2,
            2 * width / 3, height / 3,
            width, height / 2,
            2 * width / 3, 2 * height / 3,
            width / 2, height / 2,
            width / 3, 2 * height / 3
        });
        polygon.setTranslateX(x);
        polygon.setTranslateY(y);
        polygon.setStroke(Color.rgb(102, 255, 102));
        polygon.setStrokeWidth(2.0);
        polygon.setFill(Color.BLUE);
        return polygon;
    }
    
    //Return the ship Y pos
    @Override
    public int getY() {
        return this.y;
    }

    //Return the ship X pos
    @Override
    public int getX() {
       return this.x;
    }
    
    //Return the ships speed
    @Override
    public double getSpeed() {
        return this.speed;
    }

    //Check if the ship has collided with bullets from the player
    @Override
    public boolean hasCollided(IGameObject object) {
        //check if the game object is a bullet. This is done by checking 
        //if the game object implements the IBullet interface
        if (object instanceof IBullet){
            //cast the game object to a bullet 
            IBullet bullet = (IBullet) object;
            //Check if this bullet is a player bullet
            if(bullet.getIsPlayerBullet()){ 
                //reduce the ship's lives
                //this.getPolygon().setStroke(Color.RED);
                this.lives--;
                //Update its Alive status based on its lives
                if(this.lives <= 0) {
                    this.isAlive = false;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean getIsAlive() {
       return this.isAlive;
    }

    @Override
    public void shoot() {
        //Gets the player ships polygon
        Polygon targetPolygon = ((GameStage)engine.getCurrentStage()).getShip().getPolygon();
        
        //create a new bullet which is not a player bullet
        Bullet bullet = new Bullet((int)this.getPolygon().getBoundsInParent().getCenterX(), (int)this.getPolygon().getBoundsInParent().getCenterY(),false,this.speed);
        
        //Point the bullet in the direction of the player ship
        double deltaX = targetPolygon.getBoundsInParent().getCenterX() - this.getPolygon().getBoundsInParent().getCenterX();
        double deltaY = targetPolygon.getBoundsInParent().getCenterY() - this.getPolygon().getBoundsInParent().getCenterY();
        double angle = Math.atan2(deltaY, deltaX);
        bullet.getPolygon().setRotate(Math.toDegrees(angle));
        
        //Add it to the main stage bullet Queue
        stage.addBulletToQueue(bullet);
    } 
} 
    

package Logic;

import Interfaces.IGameObject;
import Interfaces.IPlayer;
import Stages.GameStage;
import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.LEFT;
import static javafx.scene.input.KeyCode.RIGHT;
import static javafx.scene.input.KeyCode.UP;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

//The player ship class
public class Ship implements IGameObject, IPlayer{
    private int x;
    private int y;
    private Polygon polygon;
    private final GameSettings settings ;
    private double speed = 2;
    private double angularVelocityX;
    private double angularVelocityY;
    Point2D anchor = new Point2D(0, 0);
    private int lives;
    private GameEngine engine;
    private boolean isAlive = true;
    private long startTime = 0;    
    private long invincibleStartTime = 0;
    private int bulletShot = 0;
    private boolean isInvincible = true;
    private GameStage stage;
    
    public Ship(){
        this.settings = GameSettings.GetInstance();
    }
    
    public int getLives(){
        return this.lives;
    }
    
    public void addLife(){
         this.lives++;
    }
    
    
    @Override
    public double getSpeed() {
        return this.speed;
    }
    
    //Subtracts a life from the player, and respawns it
    public void subtractLife(){
        if(this.lives != 0){
            this.lives--;
        }
        this.respawn();
    }
    
    //Create the player ship
    private Polygon createShip() {
        //Polygon shape = new Polygon(-10, -10, 20, 0, -10, 10);
        Polygon shape = new Polygon(0,-20,-20,-12.5,-20,-2.5,-40,-2.5,-30,-10,-30,-17.5,-40,-17.5,-40,-22.5,-30,-22.5,-30,-30,-40,-37.5,-20,-37.5,-20,-27.5);
        shape.setFill(Color.BLUEVIOLET);
        shape.setStroke(Color.WHITE);
        shape.setStrokeWidth(2.0);
        return shape;
    }
    
    @Override
    public void onCreate() {
        this.polygon = createShip();
        // places ship in centre of the screen
        this.x = Math.round(settings.boardWidth/2);
        this.y = Math.round(settings.boardHeight/2);
        this.polygon.setTranslateY(this.y);
        this.polygon.setTranslateX(this.x);
        this.turn(270);
        this.lives = GameSettings.settings.playerStartingLives;
        this.engine = GameEngine.getEngine();
        this.stage = ((GameStage)engine.getCurrentStage());
    }
    
    @Override
    public void update() {
        //If the player was invincible and the time has passed make the player vulnerable again
        if(this.isInvincible && ((System.currentTimeMillis() - invincibleStartTime) > settings.invincibleTime * 1000)){
            this.isInvincible = false;
             this.polygon.setStroke(Color.WHITE);
        }  
        // changes player's colour to indicate invincibility 
        if(isInvincible && ((System.currentTimeMillis() - invincibleStartTime ) % 5 == 0)){
            if(this.polygon.getStroke()  == Color.WHITE){
                this.polygon.setStroke(Color.RED);
            }else{
                this.polygon.setStroke(Color.WHITE);
            }
        }
        // quick maff stuff done over here
        double swiftX = Math.cos(Math.toRadians(this.polygon.getRotate()));
        double swiftY = Math.sin(Math.toRadians(this.polygon.getRotate()));
        this.polygon.setTranslateX(this.polygon.getTranslateX() + swiftX * this.speed);
        this.polygon.setTranslateY(this.polygon.getTranslateY() + swiftY * this.speed);
        this.checkIfInbound();
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
        return this.polygon;
    }
    
    public double getRotateX() { 
        return Math.cos(Math.toRadians(this.polygon.getRotate())); 
    }
    
    public double getRotateY() { 
        return Math.sin(Math.toRadians(this.polygon.getRotate())); 
    }
    
    public void turn(int angle) { 
        this.polygon.setRotate(this.polygon.getRotate() + angle); 
    }
    
    //Calculates where the player ships new position should be
    private Point2D calculateNewPosition() {
        angularVelocityY = getRotateY() * 0.01;
        angularVelocityX = getRotateX() * 0.01;
        return new Point2D(angularVelocityX, angularVelocityY);
    }
    
    //Event handler for the player when a key is pressed, essentially the player controller
    @Override
    public void handleKeyPress(KeyCode code) {
        //only allow input if the game state is not paused
        if(this.engine.getGameState() == GameState.Playing){
                switch (code) {
                    case UP:
                        Point2D speedPosition = calculateNewPosition();
                        if (getSpeed() <= settings.maxSpeed) {
                            this.speed+=.2;
                            // ensures that object turns around in centre and not a point
                            anchor = anchor.add(speedPosition);
                        } else {
                            anchor = anchor.subtract(speedPosition).normalize().multiply(settings.maxSpeed);
                    }
                    break;
                case LEFT:
                    this.turn(-10);
                    break;
                case RIGHT:
                    this.turn(10);
                    break;
                case DOWN:
                    speedPosition = calculateNewPosition();
                    if (getSpeed() >= 0) {
                        this.speed-=.2;
                        anchor = anchor.add(speedPosition);
                    }
                case X:
                    this.hyperSpace();
                    break;
            }
        }
    }
    
    //If the player moves out of bounds move it to the other side of the field
    private void checkIfInbound(){
        if (this.polygon.getBoundsInParent().getCenterX() < 0) {
            this.polygon.setTranslateX(this.polygon.getTranslateX() + this.settings.boardWidth);
        }

        if (this.polygon.getBoundsInParent().getCenterX() > this.settings.boardWidth) {
            this.polygon.setTranslateX(this.polygon.getTranslateX() % this.settings.boardWidth);
        }

        if (this.polygon.getBoundsInParent().getCenterY() < 0) {
            this.polygon.setTranslateY(this.polygon.getTranslateY() + this.settings.boardHeight);
        }

        if (this.polygon.getBoundsInParent().getCenterY() > this.settings.boardHeight) {
            this.polygon.setTranslateY(this.polygon.getTranslateY() % this.settings.boardHeight);
        }
    }

    @Override
    public void shoot() {
        if(startTime == 0){
            this.startTime = System.currentTimeMillis();
        }
        //Reset the bullets shot after second has passed
        if((System.currentTimeMillis() - startTime) > 1000){
            this.bulletShot = 0;
            this.startTime = System.currentTimeMillis();
        } 
        //Ensure the player can only shoot x amount of bullets a second
        if(bulletShot < settings.maxBulletsPerSecond){
            Bullet bullet = new Bullet((int)this.polygon.getBoundsInParent().getCenterX(), (int)this.polygon.getBoundsInParent().getCenterY(),true, this.speed);
            bullet.getPolygon().setRotate(getPolygon().getRotate());
            // Add bullet to the array in ship class.
            this.stage.addBulletToQueue(bullet);
            this.bulletShot++;
        }
    }

    @Override
    public boolean hasCollided(IGameObject object){
        // damage is not calculate if the player is invincible
        if(this.isInvincible){
           return false; 
        }
        Shape hitBox = Shape.intersect(this.polygon, object.getPolygon());
        boolean collided = hitBox.getBoundsInLocal().getWidth() != -1;
        return collided;
    }
    
    @Override
    public boolean getIsAlive() {
        return this.isAlive;
    }
    
    //Respawn the player in the center and make player Invincible
    private void respawn(){
        // player should be invincible for set amount of time after respawn
        this.isInvincible = true;
        this.invincibleStartTime = System.currentTimeMillis();
        // places the player at the centre of the screen
        anchor = new Point2D(0, 0);
        this.getPolygon().setTranslateX(x);
        this.getPolygon().setTranslateY(y);
        this.getPolygon().setRotate(270);
    }

    private void hyperSpace() {
        boolean intersects = true;
        // while player collides with object, keep generating a random position on the map
        while(intersects){
            Random rnd = new Random();
            this.getPolygon().setTranslateX(rnd.nextInt(this.settings.boardWidth));
            this.getPolygon().setTranslateY(rnd.nextInt(this.settings.boardHeight));
            for(var item:this.stage.getGameObjects()){
                var collided = this.hasCollided(item);
                if(collided){
                    break;
                }
            }
            intersects = false;
        }
    }
}

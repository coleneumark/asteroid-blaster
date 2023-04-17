package Logic;

import Interfaces.IBullet;
import Interfaces.IGameObject;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

//Bullet class used to fire bullets by the player ship
//and alien ship
public class Bullet implements IGameObject, IBullet{
    private final int x;
    private final int y;
    private final Polygon polygon;
    private final GameSettings settings;
    private double speed = 10;
    private boolean isAlive = true;
    private boolean isPlayerBullet = false;

    //Check to see if this bullet's source is from the player class
    @Override
    public boolean getIsPlayerBullet() {
        return isPlayerBullet;
    }
    
    @Override
    public void setIsPlayerBullet(boolean isPlayerBullet) {
        this.isPlayerBullet = isPlayerBullet;
    }

    public Bullet(int x, int y, boolean isPlayerBullet, double speed){
        this.x = x;
        this.y = y;
        this.polygon = new Polygon(2, -2, 2, 2, -2, 2, -2, -2);
        this.polygon.setFill(Color.RED);
        this.polygon.setTranslateY(this.y);
        this.polygon.setTranslateX(this.x);
        this.settings = GameSettings.GetInstance();
        this.isPlayerBullet = isPlayerBullet;
        this.speed = speed * 2.5; // ensure that the bullet is faster than the player's ship
    }
    
    @Override
    public void onCreate() {
        
    }

    //Keep moving in the direction which it was originally facing
    @Override
    public void update() {
        double swiftX = Math.cos(Math.toRadians(this.polygon.getRotate()));
        double swiftY = Math.sin(Math.toRadians(this.polygon.getRotate()));
        this.polygon.setTranslateX(this.polygon.getTranslateX() + swiftX * this.speed);
        this.polygon.setTranslateY(this.polygon.getTranslateY() + swiftY * this.speed);
        this.checkIfInbound();
    }

    @Override
    public Polygon getPolygon() {
       return this.polygon;
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
    public double getSpeed() {
        return this.speed;
    }

    //check if the bullet has collided with anything except the player
    @Override
    public boolean hasCollided(IGameObject object) {
        if (!this.isPlayerBullet) {
            return false;
        }
        
        try {
            Shape hitBox = Shape.intersect(this.polygon, object.getPolygon());
            boolean collided =  hitBox.getBoundsInLocal().getWidth() != -1;
            
            if(collided){
                this.isAlive = false;
            }
             
            return collided;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public boolean getIsAlive() {
          return this.isAlive;
    }
    
    //if the bullet leaves the field, destory it by setting its alive to false
    private void checkIfInbound(){
        if (this.polygon.getBoundsInParent().getCenterX() < 0 
            || this.polygon.getBoundsInParent().getCenterX() > this.settings.boardWidth
            ||this.polygon.getBoundsInParent().getCenterY() < 0
            || this.polygon.getBoundsInParent().getCenterY() > this.settings.boardHeight) {
         this.isAlive = false;
        }
    }
}
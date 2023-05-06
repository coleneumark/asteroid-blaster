package Interfaces;

import javafx.scene.shape.Polygon;

//A stage consists of game objects, each game object is an entity in the game
//which contains state, this state is updated every time the game engine ticks
public interface IGameObject {
    //Function which is called when a game object is created, similar to a constructor 
    //however, this function is used more 'genericly'
    public void onCreate();
    
    //The update function is called on each game tick to update the entity
    public void update();
    
    //Returns the polygons for the game object
    public Polygon getPolygon();
    
    //Returns the current y pos of the entity
    public int getY();
    
    //Returns the current x pos of the entity
    public int getX();
    
    //Gets the speed of the entity
    public double getSpeed();
    
    //Check if the current entity has collided with another
    boolean hasCollided(IGameObject object);
    
    //Check if the entity is alive
    public boolean getIsAlive();
}

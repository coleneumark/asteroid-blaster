package Interfaces;

import java.util.ArrayList;

//Stage class which each stage should implement with basic stage functions
//not all fucntions will be used by all stages
public interface IStage {
    //Is called when the stage is loaded the first time
    public void loadStage();
    
    //Returns the name of the stage
    public String getName();
    
    //Sets the name of the stage
    public void setName(String name);
    
    //Draw function wich will be called by the game engine to redraw the current frame
    //Also handles the update of game objects
    public void draw();
    
    //Gets a list of all the game objects in a stage
    public ArrayList<IGameObject> getGameObjects();
    
    //set the list of game objects in a stage
    public void setGameObject(ArrayList<IGameObject> gameObjects);
    
    //Adds a new game object to the stage
    public void addGameObject(IGameObject object);
    
    //removes the game object from the stage
    void removeGameObject(IGameObject object);
    
    //hides the stage
    void hide();
}

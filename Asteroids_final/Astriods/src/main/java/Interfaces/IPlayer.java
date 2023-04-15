package Interfaces;

import javafx.scene.input.KeyCode;

//Interface to identify if an object is a player
public interface IPlayer {
    //The player has a custom shoot funciton
    void shoot();
    
    //Event handler for the player when player presses a key
    public void handleKeyPress(KeyCode code);
}

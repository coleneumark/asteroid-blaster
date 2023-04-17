package Interfaces;

//Interface to identify a bullet
public interface IBullet {
    //function that checks if the current bullet source is from the player
    boolean getIsPlayerBullet();
    
    //function to set the source of this bullet
    void setIsPlayerBullet(boolean isPlayerBullet);
}

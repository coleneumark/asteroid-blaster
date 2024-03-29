package Logic;

//Class used to configure the game
public class GameSettings {
    static GameSettings settings;
    public final int BOARD_HEIGHT = 600;
    public final int BOARD_WIDTH = 800;
    public final int jumpLmt = 5;
    public final double maxSpeed = 10;    
    public final int playerStartingLives = 5;    
    public final int maxFramesPerSecond = 30;
    public final int maxBulletsPerSecond = 5;
    public final int invincibleTime = 5;
    public final int alienShipEveryLevel = 1; // configuration for which levels alien ship should appear
    public final int bulletsPerSecond = 2;
    
    //produces a single instance of this class
    public static GameSettings GetInstance(){
        if(GameSettings.settings == null){
           GameSettings.settings = new GameSettings();
        }
        return settings;
    }
}

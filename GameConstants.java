
public class GameConstants {
    
    public enum GameState {
    READY,
    PLAYING,
    GAME_OVER
}

    public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final String WINDOW_TITLE = "Asteroids";
    public static final int SHIP_SPEED = 5;
    public static final int SHIP_ROTATION_SPEED = 5;
    public static final int BULLET_SPEED = 10;
    public static final int BULLET_LIFETIME = 50;
    public static final int ASTEROID_SPEED = 2;
    public static final int ALIEN_SPEED = 4;
}

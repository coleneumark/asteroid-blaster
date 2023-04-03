//package application;
//
//import javafx.scene.shape.Polygon;
//import java.util.ArrayList;
//import java.util.Random;
//
//public class Asteroid extends GameObject {
//    
//    private static final double LARGE_SIZE = 60;
//    private static final double MEDIUM_SIZE = 30;
//    private static final double SMALL_SIZE = 15;
//    private static final double LARGE_SPEED = 1;
//    private static final double MEDIUM_SPEED = 2;
//    private static final double SMALL_SPEED = 3;
//    private static final int LARGE_POINTS = 20;
//    private static final int MEDIUM_POINTS = 50;
//    private static final int SMALL_POINTS = 100;
//    
//    private double size;
//    private double speed;
//    private ArrayList<Asteroid> children;
//    private int points;
//    
//    public Asteroid(double x, double y, double size, double speed) {
//        super(new Polygon(0, size, size, 0, 0, -size, -size, 0), x, y, 0, 0);
//        this.size = size;
//        this.speed = speed;
//        if (size == LARGE_SIZE) {
//            this.points = LARGE_POINTS;
//        } else if (size == MEDIUM_SIZE) {
//            this.points = MEDIUM_POINTS;
//        } else {
//            this.points = SMALL_POINTS;
//        }
//        children = new ArrayList<>();
//    }
//    
//    public int getPoints() {
//        return points;
//    }
//    
//    public ArrayList<Asteroid> getChildren() {
//        return children;
//    }
//    
//    public void hit() {
//        if (size == LARGE_SIZE) {
//            children.add(new Asteroid(getX(), getY(), MEDIUM_SIZE, getSpeed() + 1));
//            children.add(new Asteroid(getX(), getY(), MEDIUM_SIZE, getSpeed() - 1));
//        } else if (size == MEDIUM_SIZE) {
//            children.add(new Asteroid(getX(), getY(), SMALL_SIZE, getSpeed() + 2));
//            children.add(new Asteroid(getX(), getY(), SMALL_SIZE, getSpeed() - 2));
//        }
//        setActive(false);
//    }
//    
//    public static Asteroid createRandomAsteroid(double screenWidth, double screenHeight, int level) {
//        Random random = new Random();
//        double size = LARGE_SIZE / level;
//        double x = random.nextDouble() * screenWidth;
//        double y = random.nextDouble() * screenHeight;
//        double speed = LARGE_SPEED / level;
//        Asteroid asteroid = new Asteroid(x, y, size, speed);
//        asteroid.setRotate(random.nextDouble() * 360);
//        return asteroid;
//    }
//    
//    @Override
//    public void update() {
//        super.update();
//        if (getX() < 0 - size) {
//            setX(getScene().getWidth() + size);
//        } else if (getX() > getScene().getWidth() + size) {
//            setX(0 - size);
//        }
//        if (getY() < 0 - size) {
//            setY(getScene().getHeight() + size);
//        } else if (getY() > getScene().getHeight() + size) {
//            setY(0 - size);
//        }
//    }
//}

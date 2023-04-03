package Models;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Polygon;

public class Asteroid implements IGameObject {
    private int x;
    private int y;
    private ArrayList<LineTo> lineTo;
    private Random rnd = new Random();
    private Polygon polygon;
    private int currentXDestination;
    private int currentYDestination;
    private GameSettings settings = new GameSettings();
    private AsteroidSize size = AsteroidSize.Large;
    
    public Asteroid(){
        this.x = 500;
        this.y = 500;
        settings = GameSettings.GetInstince();
    }
    
    private Polygon createAstriod(double x, double y, AsteroidSize asteriodSize) {
        int size = 0;
        switch (asteriodSize) {
            case Large: size = 100;
                break;
            case Medium: size = 50;
                break;
            case Small: size = 15;
                break;
        }
        Polygon shape = new Polygon();
        double[] points = { x + size * 0.1, y + size * 0.5,
                        x + size * 0.3, y + size * 0.2,
                        x + size * 0.8, y + size * 0.3,
                        x + size * 0.9, y + size * 0.7,
                        x + size * 0.5, y + size * 0.9,
                        x + size * 0.1, y + size * 0.7 };
         for (double point : points) {
            shape.getPoints().add(point);
        }
        Color color = Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        shape.setFill(color);
        return shape;
    }
    
    @Override
    public void OnCreate() {
        this.setRandomDestination();
        int test =  rnd.nextInt(settings.boardWidth);
        if(test > 600){
            this.size = AsteroidSize.Large;
        }else if(test > 300){
            this.size = AsteroidSize.Medium;
        }else{
            this.size = AsteroidSize.Small;
        }
    }
    
    private void setRandomDestination(){
        this.currentXDestination = rnd.nextInt(settings.boardHeight)-60;        
        this.currentYDestination = rnd.nextInt(settings.boardWidth)-60;        
    }

    @Override
    public void Update() {
        if(this.x == this.currentXDestination && this.y == this.currentYDestination){
            setRandomDestination();
        }
        
        //move x
        if(this.x != this.currentXDestination){
            if(this.x < this.currentXDestination){
                this.x++;
            }else{
                this.x--;
            }
        }
        //Move y
        if(this.y != this.currentYDestination){
            if(this.y < this.currentYDestination){
                this.y++;
            }else{
                this.y--;
            }
        }
        this.polygon = createAstriod(x,y,size);
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
        return polygon;
    }
}
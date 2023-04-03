package Models;

import java.util.ArrayList;
import javafx.scene.shape.LineTo;

public class Ship {
    private ArrayList<LineTo> lines;
    private int positionY = 0;
    private int positionX = 50;
    
    public void OnCreate() {
    }

    public void Update() {
    }

    public ArrayList<LineTo> getLineTo() {
        if(lines == null){
           lines = new ArrayList<LineTo>() {
            {
                add(new LineTo(0,100));
                add(new LineTo(100,100));
                add(new LineTo(50,0));
            }};
        }
        return lines;
    }

    public int getY() {
        return this.positionY;
    }

    public int getX() {
        return this.positionX;
    }
}

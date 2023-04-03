package Models;

import javafx.scene.shape.Polygon;

public interface IGameObject {
    public void OnCreate();
    public void Update();
    public Polygon getPolygon();
    public int getY();
    public int getX();
}

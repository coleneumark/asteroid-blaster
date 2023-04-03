package Models;

import java.util.ArrayList;
import javafx.stage.Stage;

public interface IStage {
    public void loadStage();
    public String getName();
    public void setName(String name);
    public void draw();
    public ArrayList<IGameObject> getGameObjects();
    public void setGameObject(ArrayList<IGameObject> gameObjects);
}

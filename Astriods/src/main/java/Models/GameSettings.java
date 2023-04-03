/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

public class GameSettings {
    static GameSettings settings;
    public final int boardHeight = 1_000;
    public final int boardWidth = 1_000;
    public final int PlayerMaxLives = 3;
    
    public static GameSettings GetInstince(){
        if(GameSettings.settings == null){
           GameSettings.settings = new GameSettings();
        }
        return settings;
    }
}

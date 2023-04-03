/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.javalamps.asteriods;

import Models.IStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class RenderEngine extends Application {
    private final String[] args;
    
    public RenderEngine(String[] args){
        this.args = args;
    }
    
    public void drawStage(IStage stage){
        
        Thread thread = new Thread(() -> {
            // Launch the JavaFX application on the main thread
            Application.launch((Class<? extends Application>) stage.getClass(), args);
        });
        thread.start();
    }

    @Override
    public void start(Stage stage) throws Exception {
  
    }
}

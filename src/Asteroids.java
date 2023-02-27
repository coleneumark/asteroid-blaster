import javafx.application.Application;
import javafx.stage.Stage;


public class Asteroids extends Application {
	
	public static void main(String[] args) {
		try 
		{
			launch(args);
		}
		catch (Exception error) 
		{
			error.printStackTrace();
		}
		finally
		{
			System.exit(0);
		}
	}
	
	// need to create a public void start method because Application is extended
	public void start(Stage mainStage) {
		mainStage.setTitle("Asteroids");
		
		
		mainStage.show();
	}


}

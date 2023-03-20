package ship;

public class Spaceship {
// variables: 
	// xCoordinate, yCoordinate, direction (creates triangle with x-y point) 
	// rotationAngle (how much it rotates with key press)
	// speed - increased with thrust(), cannot be negative only 0
	// vulnerableZone - hittable zone around x-y coords
	// momentum flag variable: False by default
	// alive flag variable: True by default 
	
// methods:
	// move() - gets current x and y coordinates and speed, sets new x and y in directionPointer
	// 				if x coord reaches max limit, sets to min limit of panel (same with min to max)
	//				if y coord reaches max limit, sets to min limit of panel (same with min to max)
	// thrustForward() - increases the speed, if speed > 0, places momentum in flag loop; should have max speed
	// thrustBackward() - decreases the speed; if speed <= 0, removes momentum flag
	// lifeChecker() - if alive flag changes to False, checks if Player has more lives
	//				   if Player has another life, spawns spaceship in random SAFE place in Panel -- hyperspaceJump()
	//				   if Player does not have another life, ship is destroyed and game is over
	// rotateRight() - decreases direction's angle by rotationAngle; if smaller than 0, becomes 360
	// rotateLeft() -  increases direction's angle by rotationAngle; if larger than 360, becomes 0
	// collisionDetect() - checks each frame if vulnerable zone touches any dangerZones (asteroid/bullet but not self.bullets)
	//						if collision detected, calls lifeChecker()
	// fireBullet() - shoots self.bullet (which exists only for specific time) from x-y coords into direction, influenced by speed
	// 					if bullet hits, removes small asteroid, breaks medium into 2 small and large asteroid into 3 small asteroids
	// hyperspaceJump() - changes x and y coordinates to random SAFE place in panel, keeps same direction
	
	
	//public void movement(double direction, double speed) {
    	//double dx = speed * Math.cos(direction);
    	//double dy = speed * Math.sin(direction);
    	//x += dx;
    	//y += dy;
		//}
	
	
	//Additional thoughts:
		// frame update for movements through a loop or a frame rate control
		// size of asteroids can be calculated with directional sin/cos as well, making danger circle around central x-y coords
		// Player class that holds the score, lives, name, and Level
		// alien needs getter method of spaceship location
		// every object needs a score variable/addPoints method?
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

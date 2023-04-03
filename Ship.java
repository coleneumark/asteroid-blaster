
package Application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ship {
    private double x;
    private double y;
    private double dx;
    private double dy;
    private double angle;
    private double velo;
    private double velocityX;
    private double velocityY;



    public Ship() {
        // Set the initial position and velocity of the ship
        x = GameConstants.WINDOW_WIDTH / 2.0;
        y = GameConstants.WINDOW_HEIGHT / 2.0;
        dx = 0;
        dy = 0;
        angle = -90;
    }

    public void update() {
        // Apply friction to the ship
        dx *= 0.99;
        dy *= 0.99;

        // Update the position of the ship based on its velocity
        x += dx;
        y += dy;

        // Wrap the ship around the screen if it goes off the edge
        if (x < 0) {
            x += GameConstants.WINDOW_WIDTH;
        } else if (x > GameConstants.WINDOW_WIDTH) {
            x -= GameConstants.WINDOW_WIDTH;
        }
        if (y < 0) {
            y += GameConstants.WINDOW_HEIGHT;
        } else if (y > GameConstants.WINDOW_HEIGHT) {
            y -= GameConstants.WINDOW_HEIGHT;
        }
    }
        public void applyFriction() {
            velocityX *= 0.99;
            velocityY *= 0.99;
        }

        public void rotate(double angle) {
            this.angle += angle;
        }
    public void draw(GraphicsContext gc) {
        // Save the current state of the graphics context
        gc.save();

        // Translate the origin to the center of the ship
        gc.translate(x, y);

        // Rotate the ship to its current angle
        gc.rotate(angle);

        // Draw the ship
        gc.setStroke(Color.WHITE);
        gc.setFill(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokePolygon(new double[]{0, -10, 0, 10}, new double[]{20, -10, -5, -10}, 4);
        gc.fillPolygon(new double[]{0, -10, 0, 10}, new double[]{20, -10, -5, -10}, 4);

        // Restore the graphics context to its previous state
        gc.restore();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setVelocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
    
    public double getAngle() {
        return angle;
    }
}

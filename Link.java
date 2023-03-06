import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Graphics;

public class Link {
    // Integers for storing Link's coordinates
    int x, y;

    // TODO: Set Link's width and height to be width and height of current image?
    // Integers for Link's width/height
    int width = 78, height = 85;

    // Double for storing Link's movement speed
    double speed = 10;

    // BufferedImage for storing Link image
    BufferedImage image;

    public Link() {
        try {
            this.image = ImageIO.read(new File(""));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

    @Override
    public String toString() {
        return ("Link (x, y) = (" + this.x + ", " + this.y + ")");
    }

    public void update() {
        
    }

    public void draw(Graphics g) {

    }

    // TODO: #2 Fix Link's position if colliding with tile
    public void stopColliding() {

    }
}

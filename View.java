// Name: Cade DuPont
// Date: 02.15.23
// Description: Class for implementing contents of JFrame window

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

class View extends JPanel {
	Model model;

	// Integers for window width and height, use when performing movement operations
	public static final int minWidth = 0, minHeight = 0, maxWidth = 700, maxHeight = 500;

	// Integers to store window's scroll position
	public static int scroll_x, scroll_y;

	// BufferedImage variables for various tile images
	BufferedImage cyanTile, greenTile, magentaTile, redTile;
	
	// Array of Link images
	// Image[] links;

	public View(Controller c, Model m) {
		this.model = m;
		c.setView(this);
		
		// Load tile images into memory
		try {
			this.greenTile = ImageIO.read(new File("res/tiles/green.jpg"));
			this.redTile = ImageIO.read(new File("res/tiles/red.jpg"));
			this.magentaTile = ImageIO.read(new File("res/tiles/purple.jpg"));
			this.cyanTile = ImageIO.read(new File("res/tiles/cyan.jpg"));
		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}

		// Load tile images onto screen
		model.unmarshal(Json.load("map.json"));

		// Load link images into array; ignoring 0th index so indices align with file names
		// links = new Image[51];
		// for (int i = 1; i < links.length; i++)
		// 	links[i] = loadImage("link" + i + ".png");
	}

	// Method for loading image
	static Image loadImage(String filename) {
		Image image = null;
		try {
			image = ImageIO.read(new File(filename));
		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}
		return image;
	}

	// Method for drawing screen for each frame
	public void paintComponent(Graphics g) {
		// Calculate current positions of quadrants for setting background color
		int left = minWidth - scroll_x;
		int right = maxWidth - scroll_x;
		int top = minHeight - scroll_y;
		int bottom = maxHeight - scroll_y;
		
		// Green, top left quadrant
		g.setColor(new Color(146, 220, 167));
		g.fillRect(left, top, maxWidth, maxHeight);

		// Red, top right quadrant
		g.setColor(new Color(223, 135, 123));
		g.fillRect(right, top, maxWidth, maxHeight);

		// Magenta, bottom left quadrant
		g.setColor(new Color(213, 140, 234));
		g.fillRect(left, bottom, maxWidth, maxHeight);

		// Cyan, bottom right quadrant
		g.setColor(new Color(129, 227, 240));
		g.fillRect(right, bottom, maxWidth, maxHeight);

		// Place different tile images based on their position
		for (Tile tile : model.tiles) {
			int x = tile.x - scroll_x;
			int y = tile.y - scroll_y;
			
			if (tile.x < maxWidth && tile.y < maxHeight) g.drawImage(greenTile, x, y, null);
			if (tile.x >= maxWidth && tile.y < maxHeight) g.drawImage(redTile, x, y, null);
			if (tile.x < maxWidth && tile.y >= maxHeight) g.drawImage(magentaTile, x, y, null);
			if (tile.x >= maxWidth && tile.y >= maxHeight) g.drawImage(cyanTile, x, y, null);
		}

		// Make Link draw himself onto screen
		model.link.draw(g);

		// If edit mode is currently on, display text to screen
		if (Controller.editOn) {
			g.setColor(new Color(255, 255, 255));
			g.setFont(new Font("SansSerif", Font.BOLD, 24));
			g.drawString("Edit mode is ON", View.minWidth + 25, View.minHeight + 35);
		}
	}
}

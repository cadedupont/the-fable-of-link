// Author: Cade DuPont
// Date: 02.15.23
// Description: Class for implementing contents of JFrame window

import javax.swing.JPanel;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

class View extends JPanel {
	Model model;

	// Integers for window size, current scroll position
	public static final int minWidth = 0, minHeight = 0, maxWidth = 700, maxHeight = 500;
	int scroll_x, scroll_y;

	public View(Controller c, Model m) {
		this.model = m;
		c.setView(this);

		// Load tile images onto screen automatically
		model.unmarshal(Json.load("map.json"));
	}

	// Method for loading image passed as argument
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
		for (Tile tile : model.tiles)
			tile.draw(g, scroll_x, scroll_y);

		// Make Link draw himself onto screen
		model.link.draw(g, scroll_x, scroll_y);

		// If edit mode is currently on, display text to screen
		// Hard-coding text position on screen
		if (Controller.editOn) {
			g.setColor(new Color(255, 255, 0));
			g.setFont(new Font("Default", Font.BOLD, 24));
			g.drawString("Edit mode: ON", (View.maxWidth / 2) - 88, View.maxHeight - 17);
		}
	}
}

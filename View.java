// Name: Cade DuPont
// Date: 02.15.23
// Description: Class for implementing contents of JFrame window

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Color;

class View extends JPanel {
	static final int minWidth = 0, minHeight = 0, maxWidth = 700, maxHeight = 500;
	int scroll_x, scroll_y;
	BufferedImage cyanTile, greenTile, magentaTile, redTile;
	Model model;

	public View(Controller c, Model m) {
		this.model = m;
		c.setView(this);
		
		// load tile images into memory
		try {
			this.greenTile = ImageIO.read(new File("green_tile.jpg"));
			this.redTile = ImageIO.read(new File("red_tile.jpg"));
			this.magentaTile = ImageIO.read(new File("purple_tile.jpg"));
			this.cyanTile = ImageIO.read(new File("cyan_tile.jpg"));
		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}

	public void paintComponent(Graphics g) {
		// calculate current positions of quadrants for setting background color
		int left = minWidth - scroll_x;
		int right = maxWidth - scroll_x;
		int top = minHeight - scroll_y;
		int bottom = maxHeight - scroll_y;

		// green, top left quadrant
		g.setColor(new Color(146, 220, 167));
		g.fillRect(left, top, maxWidth, maxHeight);

		// red, top right quadrant
		g.setColor(new Color(223, 135, 123));
		g.fillRect(right, top, maxWidth, maxHeight);

		// magenta, bottom left quadrant
		g.setColor(new Color(213, 140, 234));
		g.fillRect(left, bottom, maxWidth, maxHeight);

		// cyan, bottom right quadrant
		g.setColor(new Color(129, 227, 240));
		g.fillRect(right, bottom, maxWidth, maxHeight);

		// place different tile images based on their position
		for (Tile tile : model.tiles) {
			int x = tile.x - scroll_x;
			int y = tile.y - scroll_y;
			
			if (tile.x < maxWidth && tile.y < maxHeight) g.drawImage(greenTile, x, y, null);
			if (tile.x >= maxWidth && tile.y < maxHeight) g.drawImage(redTile, x, y, null);
			if (tile.x < maxWidth && tile.y >= maxHeight) g.drawImage(magentaTile, x, y, null);
			if (tile.x >= maxWidth && tile.y >= maxHeight) g.drawImage(cyanTile, x, y, null);
		}
	}
}

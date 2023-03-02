// Name: Cade DuPont
// Date: 02.15.23
// Description: Class for implementing mouse / key listeners, operations to perform with user input

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller implements MouseListener, KeyListener {
	View view;
	Model model;
	boolean keyUp, keyDown, keyLeft, keyRight;
	
	public Controller(Model m) {
		this.model = m;
	}

	public void setView(View v) {
		this.view = v;
	}

	public void update() {
		// if arrow key has been pressed and scroll position hasn't reached border, continue updating scroll position
		if (keyLeft && view.scroll_x > View.minWidth) view.scroll_x -= 20;
		if (keyRight && view.scroll_x < View.maxWidth) view.scroll_x += 20;
		if (keyUp && view.scroll_y > View.minHeight) view.scroll_y -= 20;
		if (keyDown && view.scroll_y < View.maxHeight) view.scroll_y += 20;

		// if border has been reached, update boolean to false
		if (view.scroll_x == View.minWidth) keyLeft = false;
		if (view.scroll_x == View.maxWidth) keyRight = false;
		if (view.scroll_y == View.minHeight) keyUp = false;
		if (view.scroll_y == View.maxHeight) keyDown = false;
	}

	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
			// smooth scroll movement cases
			// if key is pressed and scroll positions have reached borders of valid quadrants, set corresponding arrow key boolean to true and other arrow key booleans to false
			case KeyEvent.VK_LEFT:
				if ((view.scroll_y == View.minHeight || view.scroll_y == View.maxHeight) && view.scroll_x == View.maxWidth) {
					keyLeft = true; 
					keyRight = false;
					keyUp = false;
					keyDown = false;
				}
				break;
			case KeyEvent.VK_RIGHT:
				if ((view.scroll_y == View.minHeight || view.scroll_y == View.maxHeight) && view.scroll_x == View.minWidth) {
					keyLeft = false;
					keyRight = true;
					keyUp = false;
					keyDown = false;
				}
				break;
			case KeyEvent.VK_UP:
				if ((view.scroll_x == View.minWidth || view.scroll_x == View.maxWidth) && view.scroll_y == View.maxHeight) {
					keyLeft = false;
					keyRight = false;
					keyUp = true;
					keyDown = false;
				}
				break;
			case KeyEvent.VK_DOWN:
				if ((view.scroll_x == View.minWidth || view.scroll_x == View.maxWidth) && view.scroll_y == View.minHeight) {
					keyLeft = false;
					keyRight = false;
					keyUp = false;
					keyDown = true;
				}
				break;

			// jump movement cases
			// if currently touching opposite border, update scroll position to desired border
			case KeyEvent.VK_A: if (view.scroll_x == View.maxWidth) view.scroll_x -= View.maxWidth; break;
			case KeyEvent.VK_D: if (view.scroll_x == View.minWidth) view.scroll_x += View.maxWidth; break;
			case KeyEvent.VK_W: if (view.scroll_y == View.maxHeight) view.scroll_y -= View.maxHeight; break;
			case KeyEvent.VK_X: if (view.scroll_y == View.minHeight) view.scroll_y += View.maxHeight; break;
					
			// program exit cases
			case KeyEvent.VK_Q:
            case KeyEvent.VK_ESCAPE: System.exit(0); break;

			// save / load map.json cases
			case KeyEvent.VK_S: model.marshal().save("map.json"); break;
			case KeyEvent.VK_L: model.unmarshal(Json.load("map.json")); break;
		}
	}
	public void keyPressed(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

	public void mousePressed(MouseEvent e) {
		// calculate position clicked - take into account scroll position, snapping tile to grid
		int x = (e.getX() + view.scroll_x)  - ((e.getX() + view.scroll_x) % 50);
		int y = (e.getY() + view.scroll_y) - ((e.getY() + view.scroll_y) % 50);

		// check each tile in ArrayList, if clicked on tile that already exists, remove tile and return
		for (Tile tile : model.tiles) {
			if (tile.isOnTile(x, y)) {
				model.tiles.remove(tile);
				return;
			}
		}

		// if clicked on empty space, add tile to position of empty space
		Tile tile = new Tile(x, y);
		model.tiles.add(tile);
	}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
}

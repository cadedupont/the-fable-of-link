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
	boolean editOn;
	
	public Controller(Model m) {
		this.model = m;
	}

	public void setView(View v) {
		this.view = v;
	}

	
	public void update() {
		// if Link is colliding with a tile, snap Link to adjacent side of tile

		// TODO: fix Link's position
		// add functionality for snapping Link's position to be adjacent to side of tile he's currently colliding with
		if (model.isColliding()) System.out.println("hello"); //model.link.stopColliding();
		else System.out.println("not hello");

		// if movement key is being pressed, then update Link's position
		if (keyUp) model.link.y -= model.link.speed;
		if (keyDown) model.link.y += model.link.speed;
		if (keyLeft) model.link.x -= model.link.speed;
		if (keyRight) model.link.x += model.link.speed;

		// if Link has moved to a new room, jump to room / update window
		if (model.link.y < View.maxHeight && view.scroll_y > View.minHeight) view.scroll_y -= View.maxHeight;
		if (model.link.y > View.maxHeight && view.scroll_y < View.maxHeight) view.scroll_y += View.maxHeight;
		if (model.link.x < View.maxWidth && view.scroll_x > View.minWidth) view.scroll_x -= View.maxWidth;
		if (model.link.x > View.maxWidth && view.scroll_x < View.maxWidth) view.scroll_x += View.maxWidth;
	}

	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
			// if movement keys have been released, update corresponding boolean variables
			case KeyEvent.VK_UP: keyUp = false; break;
			case KeyEvent.VK_DOWN: keyDown = false; break;
			case KeyEvent.VK_LEFT: keyLeft = false; break;
			case KeyEvent.VK_RIGHT: keyRight = false; break;

			// if currently touching opposite border, update scroll position to desired border
			case KeyEvent.VK_W: if (editOn && view.scroll_y == View.maxHeight) view.scroll_y -= View.maxHeight; break;
			case KeyEvent.VK_X: if (editOn && view.scroll_y == View.minHeight) view.scroll_y += View.maxHeight; break;
			case KeyEvent.VK_A: if (editOn && view.scroll_x == View.maxWidth) view.scroll_x -= View.maxWidth; break;
			case KeyEvent.VK_D: if (editOn && view.scroll_x == View.minWidth) view.scroll_x += View.maxWidth; break;

			// if editing mode is currently on, save current ArrayList of tiles to map.json
			case KeyEvent.VK_S: if (editOn) model.marshal().save("map.json"); break;

			// trigger editing mode / manual room movement
			case KeyEvent.VK_E: editOn = !editOn; break;

			// program exit cases
			case KeyEvent.VK_Q:
            case KeyEvent.VK_ESCAPE: System.exit(0); break;
		}
	}
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			// if movement keys have been pressed, update corresponding boolean variables
			case KeyEvent.VK_UP: if (!editOn) keyUp = true; break;
			case KeyEvent.VK_DOWN: if (!editOn) keyDown = true; break;
			case KeyEvent.VK_LEFT: if (!editOn) keyLeft = true; break;
			case KeyEvent.VK_RIGHT: if (!editOn) keyRight = true; break;
		}
	}
	public void keyTyped(KeyEvent e) {}

	public void mousePressed(MouseEvent e) {
		// if editing mode is not turned on, leave function
		if (!editOn) return;

		// calculate position clicked - take into account scroll position, snapping tile to grid
		int x = (e.getX() + view.scroll_x)  - ((e.getX() + view.scroll_x) % Tile.width);
		int y = (e.getY() + view.scroll_y) - ((e.getY() + view.scroll_y) % Tile.height);

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

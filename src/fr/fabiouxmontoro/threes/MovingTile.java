package fr.fabiouxmontoro.threes;

public class MovingTile extends Tile {
	public final static int TILES_SPEED_X = TILES_SIZE_X / 5; // Vitesse X des
																// tuiles
																// (pixels/frame)
	public final static int TILES_SPEED_Y = TILES_SIZE_Y / 5; // Vitesse Y des
																// tuiles
																// (pixels/frame)

	private boolean isMoving;
	private int movementX;
	private int movementY;
	private int targetPos;

	public MovingTile(int posx, int posy, int targetPos) {
		super(posx, posy);
		movementX = 0;
		movementY = 0;
		this.targetPos = targetPos;
	}

	public void setMove(int posx, int posy) {
		isMoving = true;
		movementX = posx;
		movementY = posy;
	}

	/**
	 * Déplace la tuile vers une position donnée
	 * 
	 * @param posX
	 *            position X
	 * @param posY
	 *            position Y
	 */
	public void moveTo(int posX, int posY) {
		shape.setFrame(posX, posY, shape.getWidth(), shape.getHeight());
	}

	/**
	 * Permet de savoir si la tuile est en mouvement
	 * 
	 * @return mouvement ou non
	 */
	public boolean isMoving() {
		return isMoving;
	}

	public int getTargetPos() {
		return targetPos;
	}

	/**
	 * Bouge la tuile si un mouvement à été initié
	 */
	public void move() {
		if (movementX > TILES_GAP) {
			moveTo((int) shape.getX() - TILES_SPEED_X, (int) shape.getY());
			movementX -= TILES_SPEED_X;
		} else if (movementX < -1 * TILES_GAP) {
			moveTo((int) shape.getX() + TILES_SPEED_X, (int) shape.getY());
			movementX += TILES_SPEED_X;
		} else if (movementY > TILES_GAP) {
			moveTo((int) shape.getX(), (int) shape.getY() - TILES_SPEED_Y);
			movementY -= TILES_SPEED_Y;
		} else if (movementY < -1 * TILES_GAP) {
			moveTo((int) shape.getX(), (int) shape.getY() + TILES_SPEED_Y);
			movementY += TILES_SPEED_Y;
		} else {
			if (movementX > 0)
				moveTo((int) shape.getX() - TILES_GAP, (int) shape.getY());
			else if (movementX < 0)
				moveTo((int) shape.getX() + TILES_GAP, (int) shape.getY());
			else if (movementY > 0)
				moveTo((int) shape.getX(), (int) shape.getY() - TILES_GAP);
			else if (movementY < 0)
				moveTo((int) shape.getX(), (int) shape.getY() + TILES_GAP);
			isMoving = false;
		}
	}
}

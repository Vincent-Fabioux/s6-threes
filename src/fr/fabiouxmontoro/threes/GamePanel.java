package fr.polytech.ihm.advthrees;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GamePanel extends JPanel implements KeyListener, MouseListener,MouseMotionListener {
	private final static long serialVersionUID = 1L;
	private final static int SWIPE_MIN_MOVE = 5; 

	private final static int POSITION_IMAGE_MIN = 54; 
	private final static int POSITION_IMAGE_MAX = 109; 	
	
	private boolean mouseLeftClick;
	private boolean mouseRightClick;
	private int mousePosX=0;
	private int mousePosY=0;
	private int mouseDirection;
	private int mouseDirectionTiles;
	private int mouseOverImage;
	
	private CircularMenu circularMenu;
	private Tile[] tiles;
	private ArrayList<MovingTile> movingTiles;

	private Threes threes;
	private int[] tilesValue;


	public GamePanel(Threes threes)
	{
		// Initialisations
		super(null);
		this.threes = threes;
		setFocusable(true);

		mouseDirection=-1;
		mouseLeftClick=false;
		mouseRightClick=false;
		mouseDirectionTiles=0;
		mouseOverImage=-1;
		
		// Tuiles du jeu
		int i;
		tilesValue = new int[Threes.TILES_NB];
		tiles = new Tile[Threes.TILES_NB];
		for(i = 0; i < Threes.TILES_NB; i++)
			tiles[i] = new Tile((Tile.TILES_SIZE_X+Tile.TILES_GAP)*(i%Threes.TILES_NB_L)+Tile.TILES_GAP,
					(Tile.TILES_SIZE_Y+Tile.TILES_GAP)*(i/Threes.TILES_NB_L)+Tile.TILES_GAP);

		movingTiles = new ArrayList<MovingTile>();
		circularMenu=new CircularMenu();
		
		// Evenements listeners
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
	}


	public void init()
	{
		requestFocusInWindow();
	}


	/**
	 * Ajoute une tuile
	 * @param pos position
	 * @param value valeur
	 */
	public void spawnTile(int pos, int value)
	{
		tilesValue[pos] = value;
		tiles[pos].setGrowth(Tile.TILES_SPAWN_GROWTH);
	}


	/**
	 * Remet la valeur d'une tuile à zéro
	 * @param pos position de la tuile
	 */
	public void resetValue(int pos)
	{
		tilesValue[pos] = 0;
	}


	/**
	 * Met à jour les valeurs des tuiles selon celles du modèle
	 */
	public void updateValues()
	{
		for(int i = 0; i < Threes.TILES_NB; i++)
			tilesValue[i] = threes.getTValue(i);
	}


	/**
	 * Ajoute une tuile qui bouge d'une case à une autre
	 * @param oldPos ancienne position de la tuile
	 * @param direction direction du mouvement
	 */
	public void addMovingTile(int oldPos, int direction)
	{
		// Création de la tuile
		movingTiles.add(new MovingTile((Tile.TILES_SIZE_X+Tile.TILES_GAP)*(oldPos%Threes.TILES_NB_L)+Tile.TILES_GAP,
				(Tile.TILES_SIZE_Y+Tile.TILES_GAP)*(oldPos/Threes.TILES_NB_L)+Tile.TILES_GAP, oldPos + direction));
		MovingTile e = movingTiles.get(movingTiles.size() - 1);
		e.setValue(threes.getTValue(oldPos));

		switch(direction)
		{
			case -1*Threes.TILES_NB_L: // Mouvement vers le haut
				e.setMove(0, Tile.TILES_SIZE_Y+Tile.TILES_GAP);
				break;

			case 1: // Mouvement vers la droite
				e.setMove(-1*Tile.TILES_SIZE_X-Tile.TILES_GAP, 0);
				break;

			case -1: // Mouvement vers la gauche
				e.setMove(Tile.TILES_SIZE_X+Tile.TILES_GAP, 0);
				break;

			case Threes.TILES_NB_L: // Mouvement vers le bas
				e.setMove(0, -1*Tile.TILES_SIZE_Y-Tile.TILES_GAP);
				break;
		}
	}


	/**
	 * Rendu graphique du jeu
	 * @param g contexte graphique
	 */
	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(15));
		int i, j;
		boolean growingTile = false;

			
		
		// Affichage des tuiles immobiles
		for(i = 0; i < Threes.TILES_NB; i++)
		{
			tiles[i].setValue(tilesValue[i]);
			tiles[i].render(g2);
			if(tiles[i].isGrowing())
				growingTile = true;
		}

		// Affichage des tuiles qui bougent
		for(i = 0; i < movingTiles.size(); i++)
		{
			movingTiles.get(i).move();
			movingTiles.get(i).render(g2);
			if(!movingTiles.get(i).isMoving())
			{
				j = movingTiles.get(i).getTargetPos();
				if(tilesValue[j] != 0)
					tiles[j].setGrowth(Tile.TILES_GROWTH);
				tilesValue[j] = threes.getTValue(j);
				tiles[j].setValue(tilesValue[j]);

				movingTiles.remove(i);
			}
		}

		// Si aucune tuile ne bouge, l'utilisateur peut jouer
		if(movingTiles.isEmpty() && !growingTile){
			if(mouseRightClick&&!mouseLeftClick&&mouseOverImage==-1)
				circularMenu.render(-1,getMouseX(),getMouseY(),g2);
			if(mouseOverImage!=-1){
				circularMenu.render(mouseOverImage,getMouseX(),getMouseY(),g2);
			}
			threes.unfreezeControls();
		}
		if(mouseLeftClick){
			circularMenu.render(mouseDirection,getMouseX(),getMouseY(),g2);
			if(mouseDirectionTiles!=0){
				threes.moveTiles(mouseDirectionTiles);
				mouseDirectionTiles=0;
				mouseLeftClick=false;
				mouseRightClick=false;
				mouseOverImage=-1;
			}
		}
	}

	 /**
     * Permets de savoir si une touche a été activée
     */
	@Override
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_UP: // Si l'on a appuyé sur la flèche du haut
				threes.moveTiles(-1*Threes.TILES_NB_L);
				break;

			case KeyEvent.VK_RIGHT: // Si l'on a appuyé sur la flèche de droite
				threes.moveTiles(1);
				break;

			case KeyEvent.VK_LEFT: // Si l'on a appuyé sur la flèche de gauche
				threes.moveTiles(-1);
				break;

			case KeyEvent.VK_DOWN: // Si on a appuyé sur la flèche du bas
				threes.moveTiles(Threes.TILES_NB_L);
				break;
				
		}
		mouseLeftClick=false;
		mouseRightClick=false;
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(mouseRightClick &&e.getButton()==1){
			if(e.getX()>mousePosX-circularMenu.getHeight()/2&&e.getX()<mousePosX+circularMenu.getHeight()/2){
				if(mousePosY-e.getY()>POSITION_IMAGE_MIN &&mousePosY-e.getY()<POSITION_IMAGE_MAX){
					mouseLeftClick=true;
					mouseDirection=0;
					mouseDirectionTiles=-4;
				}
				if(e.getY()-mousePosY>POSITION_IMAGE_MIN &&e.getY()-mousePosY<POSITION_IMAGE_MAX ){				
					mouseLeftClick=true;
					mouseDirection=2;
					mouseDirectionTiles=4;
				}
			}
			if(e.getY()>mousePosY-circularMenu.getWidth()/2&&e.getY()<mousePosY+circularMenu.getWidth()/2){
				if(mousePosX-e.getX()>POSITION_IMAGE_MIN&&mousePosX-e.getX()<POSITION_IMAGE_MAX ){
					mouseLeftClick=true;
					mouseDirection=3;
					mouseDirectionTiles=-1;
				}
				if(e.getX()-mousePosX>POSITION_IMAGE_MIN&&e.getX()-mousePosX<POSITION_IMAGE_MAX ){
					mouseLeftClick=true;
					mouseDirection=1;
					mouseDirectionTiles=1;
				}
			}
			if(!mouseLeftClick)
				mouseRightClick=false;
		}	
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		if(arg0.getButton()==1&&!mouseRightClick){
			mouseLeftClick=true;
			mouseDirection=-1;
			setMouseX(arg0.getX());
			setMouseY(arg0.getY());
		}
		if(arg0.getButton()==3){
			mouseRightClick=true;
			setMouseX(arg0.getX());
			setMouseY(arg0.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		if(arg0.getButton()==1&&!mouseRightClick){
			int diffX=Math.abs(arg0.getX()-mousePosX);
			int diffY=Math.abs(arg0.getY()-mousePosY);
			
			if(diffX>=diffY&&diffX>SWIPE_MIN_MOVE ){
				if(mousePosX>arg0.getX()){
					threes.moveTiles(-1);
				}
				else{
					threes.moveTiles(1);
				}
			}
			else if(diffY>diffX&&diffY>SWIPE_MIN_MOVE){
					if(mousePosY>arg0.getY()){
						threes.moveTiles(-1*Threes.TILES_NB_L);
					}
					else{
						threes.moveTiles(Threes.TILES_NB_L);
					}
			}
			mouseLeftClick=false;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e){
		if(SwingUtilities.isLeftMouseButton(e)&&!mouseRightClick){
			int diffX=Math.abs(e.getX()-mousePosX);
			int diffY=Math.abs(e.getY()-mousePosY);
			
			if(diffX>diffY&&diffX>SWIPE_MIN_MOVE){
				if(mousePosX>e.getX()){
					mouseDirection=3;
				}
				else{
					mouseDirection=1;
				}
			}
			else if(diffY>diffX&&diffY>SWIPE_MIN_MOVE){
				if(mousePosY>e.getY()){
					mouseDirection=0;
				}
				else{
					mouseDirection=2;
				}
			}
		}
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		mouseOverImage=-1;
		if(mouseRightClick){
			if(e.getX()>mousePosX-circularMenu.getHeight()/2&&e.getX()<mousePosX+circularMenu.getHeight()/2){
				if(mousePosY-e.getY()>POSITION_IMAGE_MIN &&mousePosY-e.getY()<POSITION_IMAGE_MAX){
					mouseOverImage=0;
				}
				if(e.getY()-mousePosY>POSITION_IMAGE_MIN &&e.getY()-mousePosY<POSITION_IMAGE_MAX ){				
					mouseOverImage=2;
				}
			}
			else if(e.getY()>mousePosY-circularMenu.getWidth()/2&&e.getY()<mousePosY+circularMenu.getWidth()/2){
				if(mousePosX-e.getX()>POSITION_IMAGE_MIN&&mousePosX-e.getX()<POSITION_IMAGE_MAX ){
					mouseOverImage=3;
				}
				if(e.getX()-mousePosX>POSITION_IMAGE_MIN&&e.getX()-mousePosX<POSITION_IMAGE_MAX ){
					mouseOverImage=1;
				}
			}
		}
	}
	
	public int getMouseX() {
		return mousePosX;
	}


	public void setMouseX(int mouseX) {
		this.mousePosX = mouseX;
	}


	public int getMouseY() {
		return mousePosY;
	}


	public void setMouseY(int mouseY) {
		this.mousePosY = mouseY;
	}
}

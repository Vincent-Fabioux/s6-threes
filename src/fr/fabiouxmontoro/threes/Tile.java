package fr.fabiouxmontoro.threes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

public class Tile
{
	public final static int TILES_SIZE_X = 80;						// Largeur des tuiles
	public final static int TILES_SIZE_Y = 120;						// Hauteur des tuiles
	public final static int TILES_ROUND = 50;						// Arrondi des tuiles
	public final static int TILES_GROWTH = 6;					// Grandissement des tuiles combinées
	public final static int TILES_SPAWN_GROWTH = -10;			// Grandissement des tuiles qui apparaissent
	public final static int TILES_GROWTH_SPEED = 1;			// Vitesse de grandissement des tuiles
	public final static int TILES_GAP = 10;							// Ecart entre chaque tuile
	public final static int TILES_POLICE_SIZE = TILES_SIZE_X / 3;	// Taille de la police des tuiles
	public final static int TILES_TEXT_DELTA_Y = TILES_SIZE_Y / 2;	// DÃ©calage horizontal du texte
	private final static Font TILES_FONT
			= new Font("Arial", Font.BOLD, Tile.TILES_POLICE_SIZE); // Police du texte

	
	private int value;
	private double growth;
	private int posX;
	private int posY;
	protected RoundRectangle2D shape;

	
	public Tile(int posX, int posY)
	{
		growth = 0;
		value = 0;
		this.posX = posX;
		this.posY = posY;
		shape = new RoundRectangle2D.Double(
				posX, posY,
				TILES_SIZE_X, TILES_SIZE_Y,
				TILES_ROUND, TILES_ROUND);
	}

	
	/**
	 * Change la valeur de la tuile
	 * @param value nouvelle valeur
	 */
	public void setValue(int value)
	{
		this.value = value;
	}

	
	public void setGrowth(int value)
	{
		growth = value;
	}
	
	
	public boolean isGrowing()
	{
		if(growth == 0)
			return false;
		else
			return true;
	}
	
	/**
	 * Affiche la tuile
	 * @param g2 contexte graphique
	 */
	public void render(Graphics2D g2)
	{
		// Si la tuile n'a pas de valeur
		if(value == 0)
		{
			g2.setPaint(Color.WHITE);
			g2.fill(shape);
		}
		// Sinon on affiche la valeur correspondante
		else
		{
			String text;
			int strLen;

			if(growth > 0)
			{
				growth -= TILES_GROWTH_SPEED;
				if(growth >= TILES_GROWTH/2)
				{
					shape.setFrame(posX + (growth-TILES_GROWTH)*TILES_SIZE_X/20, 
							posY + (growth-TILES_GROWTH)*TILES_SIZE_Y/20,
							TILES_SIZE_X*(1+(TILES_GROWTH-growth)/10),
							TILES_SIZE_Y*(1+(TILES_GROWTH-growth)/10));
				}
				else
				{
					shape.setFrame(posX - growth*TILES_SIZE_X/20, 
							posY - growth*TILES_SIZE_Y/20,
							TILES_SIZE_X*(1+growth/10),
							TILES_SIZE_Y*(1+growth/10));
				}
			}
			else if(growth < 0)
			{
				growth += TILES_GROWTH_SPEED;
				shape.setFrame(posX - growth*TILES_SIZE_X/20, 
						posY - growth*TILES_SIZE_Y/20,
						TILES_SIZE_X*(1+growth/10),
						TILES_SIZE_Y*(1+growth/10));
			}
			
			// Couleur de fond de la tuile
			if(value ==1)
				g2.setPaint(new Color(255, 100, 100, 255));
			else if(value == 2)
				g2.setPaint(new Color(100, 100, 255, 255));
			else if(value <= 255/4)
				g2.setPaint(new Color(255, 255-value*4, 0, 255));
			else if(value <= 255*5/4)
				g2.setPaint(new Color(255, 0, value-255/4, 255));
			else
				g2.setPaint(new Color(255, 0, 255, 255));
			// Affichage du fond de la tuile
			g2.fill(shape);

			// Affichage du texte
			g2.setFont(TILES_FONT);
			g2.setPaint(Color.BLACK);
			text = Integer.toString(value);
			strLen = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			if(growth > 0)
			{
				if(growth > TILES_GROWTH/2)
					g2.drawString(text, (int) (shape.getX()+TILES_SIZE_X*(1+(TILES_GROWTH - growth)/10)/2 - strLen/2),
							(int) (shape.getY()+TILES_TEXT_DELTA_Y*(1+(TILES_GROWTH - growth)/10)));
				else
					g2.drawString(text, (int) (shape.getX()+TILES_SIZE_X*(1 + growth/10)/2 - strLen/2),
							(int) (shape.getY()+TILES_TEXT_DELTA_Y*(1 + growth/10)));
					
			}
			else if(growth < 0)
				g2.drawString(text, (int) (shape.getX()+TILES_SIZE_X*(1 + growth/10)/2 - strLen/2),
						(int) (shape.getY()+TILES_TEXT_DELTA_Y*(1 + growth/10)));
			else
				g2.drawString(text, (int) (shape.getX()+TILES_SIZE_X/2 - strLen/2),
						(int) (shape.getY()+TILES_TEXT_DELTA_Y));
		}
	}
}

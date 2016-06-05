package fr.polytech.ihm.advthrees;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CircularMenu {

	private BufferedImage imgMouse = null; // Image lorsque la souris est
											// enfoncée sur le bouton ou
											// lorsqu'elle passe sur l'image
	private BufferedImage[] img = null; // Tableau des images du menu circulaire

	private static final double SCALING = 0.40; // Reduction des images du menu
												// circulaire
	private static final double SPACE = 1.8; // Espace entre les images du men
	private static final int NB_IMAGES = 4;// Nombre d'images pour former le
											// menuCirculaire

	public CircularMenu() {
		img = new BufferedImage[NB_IMAGES];
		// Chargement des images
		for (int i = 0; i < NB_IMAGES; i++) {
			try {
				img[i] = ImageIO.read(new File("fleche.png"));
			} catch (IOException e) {
			}
		}
		try {
			imgMouse = ImageIO.read(new File("flecheMouse.png"));
		} catch (IOException e) {
		}
	}

	/**
	 * Permet d'afficher les differentes images
	 * 
	 * @param mouseDirection
	 *            si la souris est sur une image mouseX position en x de la
	 *            souris mouseY position en y de la souris g2 permets de
	 *            dessiner
	 */
	public void render(int mouseDirection, int mouseX, int mouseY, Graphics2D g2) {
		for (int i = 0; i < NB_IMAGES; i++) {

			AffineTransform at = new AffineTransform();
			if (i == 0)
				at.translate(mouseX - img[i].getWidth() * SCALING / 2, mouseY - (img[i].getHeight() * SCALING * SPACE));
			else if (i == 1)
				at.translate(mouseX + (img[i].getHeight() * SCALING * SPACE), mouseY - img[i].getWidth() * SCALING / 2);
			else if (i == 2)
				at.translate(mouseX + img[i].getWidth() * SCALING / 2, mouseY + (img[i].getHeight() * SCALING * SPACE));
			else if (i == 3)
				at.translate(mouseX - (img[i].getHeight() * SCALING * SPACE), mouseY + img[i].getWidth() * SCALING / 2);

			at.rotate(i * Math.PI / 2);
			at.scale(SCALING, SCALING);

			if (mouseDirection != -1 && mouseDirection == i)
				g2.drawImage(imgMouse, at, null);
			else
				g2.drawImage(img[i], at, null);
		}
	}

	public double getHeight() {
		return (img[0].getHeight() * SCALING * SPACE);
	}

	public double getWidth() {
		return (img[0].getWidth() * SCALING);
	}
}

package fr.polytech.ihm.advthrees;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CircularMenu {
	
	BufferedImage imgMouse = null;
	BufferedImage[] img = null;
	
	private static final double SCALING = 0.40;
	private static final double SPACE = 1.8; 
	private static final int NB_IMAGES =4 ;
	
	public CircularMenu(){
		img= new BufferedImage[NB_IMAGES];
		for(int i=0;i<NB_IMAGES;i++){
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
	
	public void render(int mouseDirection,int mouseX,int mouseY,Graphics2D g2){
		for(int i=0;i<NB_IMAGES;i++){
			AffineTransform at = new AffineTransform();
			
			
			if(i==0)
				at.translate(mouseX-img[i].getWidth()*SCALING/2,mouseY-(img[i].getHeight()*SCALING*SPACE));
			else if(i==1)
				at.translate(mouseX+(img[i].getHeight()*SCALING*SPACE),mouseY-img[i].getWidth()*SCALING/2);
			else if(i==2)
				at.translate(mouseX+img[i].getWidth()*SCALING/2,mouseY+(img[i].getHeight()*SCALING*SPACE));
			else if(i==3)	
				at.translate(mouseX-(img[i].getHeight()*SCALING*SPACE),mouseY+img[i].getWidth()*SCALING/2);
			
			at.rotate(i*Math.PI/2);
			at.scale(SCALING, SCALING);
			
			if(mouseDirection!=-1&&mouseDirection==i)
				g2.drawImage(imgMouse, at, null);
			else
				g2.drawImage(img[i], at, null);
		}
	}
}

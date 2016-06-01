package fr.fabiouxmontoro.threes;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class EndMenu extends JPanel
{
	private final static long serialVersionUID = 1L;

	public final static int END_MENU_SIZE_X = 200;
	public final static int END_MENU_SIZE_Y = 100;
	public final static int END_MENU_ROUND = 50;


	public EndMenu()
	{
		this.setSize(END_MENU_SIZE_X, END_MENU_SIZE_Y);
		this.setLocation((Threes.WINDOW_SIZE_X - END_MENU_SIZE_X)/2, (Threes.WINDOW_SIZE_Y - END_MENU_SIZE_Y)/2);
		this.setBackground(new Color(255, 255, 255));
		this.setOpaque(true);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
}

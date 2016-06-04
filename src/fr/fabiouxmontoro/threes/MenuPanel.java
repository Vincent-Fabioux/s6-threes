package fr.polytech.ihm.advthrees;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MenuPanel extends JPanel
{
	private final static long serialVersionUID = 1L;
	
	private JPanel panel;
	private JButton playBasic;
	private JButton playAdvanced;
	private JLabel titleImage;
	private Threes threes;
	
	public MenuPanel(Threes threes)
	{
		this.threes = threes;

		panel=new JPanel();
		
	    // Image du titre
	    titleImage = new JLabel(new ImageIcon("threes.jpg"));
	    titleImage.setIcon(new ImageIcon(new ImageIcon("threes.jpg").getImage().getScaledInstance(Threes.MAIN_BUTTONS_SIZE_X, Threes.MAIN_BUTTONS_SIZE_Y, Image.SCALE_DEFAULT)));


		// Bouton JOUER
	    playBasic = new JButton("Version Basique");
	    playBasic.setFont(new Font("Arial", Font.BOLD, Threes.MAIN_BUTTONS_POLICE_SIZE));
	    playBasic.setSize(new Dimension(Threes.MAIN_BUTTONS_SIZE_X, Threes.MAIN_BUTTONS_SIZE_Y));
	    playBasic.setBackground(Color.white);
	    playBasic.addMouseListener(new MouseListener()
	    {
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				threes.goToGame(0);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
		});
	    playAdvanced = new JButton("Version Avancée");
	    playAdvanced.setFont(new Font("Arial", Font.BOLD, Threes.MAIN_BUTTONS_POLICE_SIZE));
	    playAdvanced.setSize(new Dimension(Threes.MAIN_BUTTONS_SIZE_X, Threes.MAIN_BUTTONS_SIZE_Y));
	    playAdvanced.setBackground(Color.white);
	    playAdvanced.addMouseListener(new MouseListener()
	    {
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				threes.goToGame(1);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
		});
	    

	    // Fenêtre du menu
		setBackground(Color.white);
		setSize(new Dimension(Threes.WINDOW_SIZE_X, Threes.WINDOW_SIZE_Y));
        panel.add(titleImage, BorderLayout.NORTH);
        panel.add(playAdvanced,BorderLayout.CENTER);
        panel.add(playBasic, BorderLayout.CENTER);
        panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
        add(panel,BorderLayout.SOUTH);
	}
}

package fr.fabiouxmontoro.threes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuPanel extends JPanel
{
	private final static long serialVersionUID = 1L;

	private JButton playButton;
	private JLabel titleImage;
	private Threes threes;

	public MenuPanel(Threes threes)
	{
		this.threes = threes;

	    // Image du titre
	    titleImage = new JLabel(new ImageIcon("threes.jpg"));

		// Bouton JOUER
	    playButton = new JButton("JOUER");
	    playButton.setFont(new Font("Arial", Font.BOLD, Threes.MAIN_BUTTONS_POLICE_SIZE));
	    playButton.setSize(new Dimension(Threes.MAIN_BUTTONS_SIZE_X, Threes.MAIN_BUTTONS_SIZE_Y));
	    playButton.setBackground(Color.white);
	    playButton.addMouseListener(new MouseListener()
	    {
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				threes.goToGame();
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
        add(titleImage, BorderLayout.NORTH);
        add(playButton, BorderLayout.CENTER);
	}
}

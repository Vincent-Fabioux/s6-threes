package fr.polytech.ihm.advthrees;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {
	private final static long serialVersionUID = 1L;

	private JButton playBasic;
	private JButton playAdvanced;
	private JLabel titleImage;
	@SuppressWarnings("unused")
	private Threes threes;

	public MenuPanel(Threes threes) {
		this.threes = threes;

		// Image du titre
		titleImage = new JLabel(new ImageIcon("threes.jpg"));
		titleImage.setIcon(
				new ImageIcon(new ImageIcon("threes.jpg").getImage().getScaledInstance(340, 85, Image.SCALE_DEFAULT)));

		// Version Base
		playBasic = new JButton("Version Basique");
		playBasic.setFont(new Font("Arial", Font.BOLD, Threes.MAIN_BUTTONS_POLICE_SIZE));
		playBasic.setPreferredSize(new Dimension(Threes.MAIN_BUTTONS_SIZE_X, Threes.MAIN_BUTTONS_SIZE_Y));
		playBasic.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				threes.goToGame(0);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		// Version Avancee
		playAdvanced = new JButton("Version Avancée");
		playAdvanced.setFont(new Font("Arial", Font.BOLD, Threes.MAIN_BUTTONS_POLICE_SIZE));
		playAdvanced.setPreferredSize(new Dimension(Threes.MAIN_BUTTONS_SIZE_X, Threes.MAIN_BUTTONS_SIZE_Y));
		playAdvanced.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				threes.goToGame(1);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		// Fenêtre du menu
		setBackground(Color.white);

		//Mise en place des composants
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weighty = 0.5;
		constraints.anchor = GridBagConstraints.NORTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		this.add(titleImage, constraints);

		constraints.anchor = GridBagConstraints.NORTH;
		constraints.gridy = 1;
		this.add(playBasic, constraints);

		constraints.gridy = 2;
		this.add(playAdvanced, constraints);

	}

}


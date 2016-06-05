package fr.polytech.ihm.advthrees;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EndMenu extends JPanel {
	private final static long serialVersionUID = 1L;

	public final static int END_MENU_SIZE_X = 300;
	public final static int END_MENU_SIZE_Y = 400;
	public final static int END_MENU_ROUND = 50;

	private int score;
	private FileOutputStream fos = null;
	private ArrayList<Integer> scores;

	private JLabel title;
	private JLabel renderScore;
	private JLabel titleRanking;
	private JLabel[] ranking;
	private JButton menu;
	private JButton exit;

	public EndMenu(Threes threes) {

		// Initialisation des composants
		title = new JLabel("Scores");
		renderScore = new JLabel();
		renderScore = new JLabel();
		menu = new JButton("Menu");
		exit = new JButton("Quitter");
		titleRanking = new JLabel("Meilleurs Scores");
		ranking = new JLabel[5];

		// Definition de la fenetre
		this.setSize(END_MENU_SIZE_X, END_MENU_SIZE_Y);
		this.setLocation((Threes.WINDOW_SIZE_X - END_MENU_SIZE_X) / 2, (Threes.WINDOW_SIZE_Y - END_MENU_SIZE_Y) / 2);
		this.setBackground(new Color(255, 255, 255));
		this.setOpaque(true);
		this.setBorder(BorderFactory.createLineBorder(Color.black));

		// Definition du boutonRejouer
		menu.setFont(new Font("Arial", Font.BOLD, Threes.MAIN_BUTTONS_POLICE_SIZE));
		menu.setSize(new Dimension(Threes.MAIN_BUTTONS_SIZE_X, Threes.MAIN_BUTTONS_SIZE_Y));
		menu.setBackground(Color.white);
		menu.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				threes.replay();
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

		// Definition du bouton Exit
		exit.setFont(new Font("Arial", Font.BOLD, Threes.MAIN_BUTTONS_POLICE_SIZE));
		exit.setSize(new Dimension(Threes.MAIN_BUTTONS_SIZE_X, Threes.MAIN_BUTTONS_SIZE_Y));
		exit.setBackground(Color.white);
		exit.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				threes.dispose();
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
		this.setSize(END_MENU_SIZE_X, END_MENU_SIZE_Y);
		this.setLocation((Threes.WINDOW_SIZE_X - END_MENU_SIZE_X) / 2, (Threes.WINDOW_SIZE_Y - END_MENU_SIZE_Y) / 2);
		this.setBackground(new Color(255, 255, 255));
		this.setOpaque(true);
		this.setBorder(BorderFactory.createLineBorder(Color.black));

		title.setFont(new Font("Arial", Font.BOLD, 60));
		titleRanking.setFont(new Font("Arial", Font.BOLD, 30));
		JLabel titleImage = new JLabel(new ImageIcon("podium.jpg"));
		this.add(titleImage);
		Box b1 = Box.createHorizontalBox();
		b1.add(title);

		Box b2 = Box.createHorizontalBox();
		b2.add(renderScore);

		Box b3 = Box.createHorizontalBox();
		b3.add(titleRanking);

		Box b4 = Box.createVerticalBox();
		for (int i = 0; i < 5; i++) {
			ranking[i] = new JLabel();
			b4.add(ranking[i]);
		}

		Box b6 = Box.createVerticalBox();
		b6.add(b1);
		b6.add(b2);
		b6.add(b3);
		b6.add(b4);
		b6.setAlignmentX(50);

		this.add(b6);
		this.add(menu);
		this.add(exit);
	}

	/**
	 * Permets d'ecrire dans un fichier txt le resultat de la partie
	 */
	public void writeFile() {
		String result = Integer.toString(score);
		result += ",";
		try {
			fos = new FileOutputStream(new File("scores.txt"), true);
			try {
				fos.write(result.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Permets de lire les differents scores des differentes parties a partir
	 * d'un fichier txt
	 */
	public void read() {
		scores = new ArrayList<Integer>();
		Scanner sc = null;
		String temp = "";
		try {
			try {
				sc = new Scanner(new File("scores.txt"));
				while (sc.hasNextLine()) {
					for (char c : sc.next().toCharArray()) {
						if (c != ',') {
							temp += c;
						} else {
							scores.add(Integer.parseInt(temp));
							Collections.sort(scores, Collections.reverseOrder());
							temp = "";
						}
					}
				}
			} finally {
				if (sc != null)
					sc.close();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Permets de mettre a jour le classement pour l'affichage
	 */
	public void updateValues() {
		renderScore.setText("Votre score est de " + Integer.toString(score));
		if (scores.size() > 5) {
			for (int i = 0; i < 5; i++) {
				ranking[i].setText((i + 1) + " : " + Integer.toString(scores.get(i)));
			}
		} else {
			for (int i = 0; i < scores.size(); i++) {
				ranking[i].setText((i + 1) + " : " + Integer.toString(scores.get(i)));
			}
		}
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}

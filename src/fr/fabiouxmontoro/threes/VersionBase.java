package fr.fabiouxmontoro.threes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VersionBase extends JPanel implements KeyListener, MouseListener {

	private final static long serialVersionUID = 1L;
	private final static int TILES_NB_L = 4; // Nombre de tuiles par ligne
	private final static int TILES_NB = TILES_NB_L * TILES_NB_L; // Nombre de
																	// tuiles
																	// total
	private final static int TILES_SIZE = 124; // Taille des tuiles
	private final static int TILES_POLICE_SIZE = TILES_SIZE / 3; // Taille de la
																	// police
																	// des
																	// tuiles
	private final static int TILES_GAP = 3; // Ecart entre chaque tuile
	private final static int TILES_INIT_MIN = 4; // Nombre de tuiles minimum au
													// démarrage
	private final static int TILES_INIT_MAX = 6; // Nombre de tuiles maximum au
													// démarrage


	private final static int MAIN_BUTTONS_SIZE_X = Threes.WINDOW_SIZE_X; // Largeur des
																	// boutons
																	// principaux
	private final static int MAIN_BUTTONS_SIZE_Y = Threes.WINDOW_SIZE_X / 8; // Hauteur
																		// des
																		// boutons
																		// principaux
	private final static int MAIN_BUTTONS_POLICE_SIZE = Threes.WINDOW_SIZE_X / 7; // Taille
																			// de
																			// la
																			// police
																			// des
																			// boutons
																			// principaux
	private final static int SCORE_LABEL_POLICE_SIZE = Threes.WINDOW_SIZE_X / 20; // Taille
																			// de
																			// la
																			// police
																			// du
																			// score

	private final static int SWIPE_MIN_MOVE = 5; // Mouvement minimal de la
													// souris pour être pris en
													// compte
	private final static float SWIPE_MIN_SPEED = 20 / 1000; // Mouvement minimal
															// de la souris pour
															// être pris en
															// compte

	private Threes threes;
	private JPanel gamePanel;

	private JButton replayButton;
	private JButton quitButton;
	private JPanel endPanel;
	private JPanel endSouthPanel;
	private JLabel scoreText;

	private JLabel[] tiles;
	private GridLayout grid;

	private int[] tilesValue;

	/**
	 * Créé une classe pour le jeu
	 */
	public VersionBase(Threes threes) {
		this.threes = threes;
	}

	/**
	 * Initialise tous les éléments graphiques du programme et lance le jeu
	 */
	public void play() {
		// Initialisation de chaque partie du programme
		initGame();
		goToGame();

		setVisible(true);
		addKeyListener(this);
		addMouseListener(new MouseListener() {
			int mousePosX = 0;
			int mousePosY = 0;
			long mouseTime = 0;
			long mouseDeltaTime = 0;
			int mouseDeltaX = 0;
			int mouseDeltaY = 0;
			float mouseSpeedX = 0;
			float mouseSpeedY = 0;

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// Début de l'appui: position initiale
				mousePosX = arg0.getX();
				mousePosY = arg0.getY();
				mouseTime = System.currentTimeMillis();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// Fin de l'appui: calcul de l'écart
				mouseDeltaX = mousePosX - arg0.getX();
				mouseDeltaY = mousePosY - arg0.getY();

				// Calcul de la vitesse du swipe
				mouseDeltaTime = System.currentTimeMillis() - mouseTime;
				mouseSpeedX = Math.abs(mouseDeltaX / (mouseDeltaTime));
				mouseSpeedY = Math.abs(mouseDeltaY / (mouseDeltaTime));

				if (mouseSpeedX > mouseSpeedY && (Math.abs(mouseDeltaX) > SWIPE_MIN_MOVE)
						&& (mouseSpeedX > SWIPE_MIN_SPEED)) {
					if (mouseDeltaX > 0)
						moveTiles(-1);
					else
						moveTiles(1);
				} else if ((Math.abs(mouseDeltaY) > SWIPE_MIN_MOVE) && (mouseSpeedY > SWIPE_MIN_SPEED)) {
					if (mouseDeltaY > 0)
						moveTiles(-1 * TILES_NB_L);
					else
						moveTiles(TILES_NB_L);
				}
			}
		});
	}

	/**
	 * Initialise les éléments graphiques du jeu
	 */
	private void initGame() {
		// Grille du jeu
		grid = new GridLayout(TILES_NB_L, TILES_NB_L);
		grid.setHgap(TILES_GAP);
		grid.setVgap(TILES_GAP);

		// Fenêtre de jeu
		gamePanel = new JPanel();
		gamePanel.setPreferredSize(new Dimension(Threes.WINDOW_SIZE_X,Threes.WINDOW_SIZE_Y-60));
		gamePanel.setLayout(grid);
		gamePanel.setBorder(BorderFactory.createEmptyBorder(TILES_GAP, TILES_GAP, TILES_GAP, TILES_GAP));

		// Tuiles du jeu
		int i;
		Font police = new Font("Arial", Font.BOLD, TILES_POLICE_SIZE); // On
																		// definit
																		// la
																		// police
		tiles = new JLabel[TILES_NB]; // On initialise les JLabel des tuiles
		tilesValue = new int[TILES_NB]; // On initilaise le tableau contenant
										// les valeurs des tuiles
		for (i = 0; i < TILES_NB; i++) {
			tiles[i] = new JLabel("");
			tiles[i].setFont(police); // On applique la police aux JLabel
			gamePanel.add(tiles[i]); // On les ajoute au JPanel de la fenetre
			tiles[i].setOpaque(true);
			tiles[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER); // On
																				// place
																				// l'ecriture
																				// au
																				// centre
																				// du
																				// JLabel
			tiles[i].setBorder(BorderFactory.createLineBorder(Color.BLACK)); // On
																				// definit
																				// les
																				// contours
																				// de
																				// chaque
																				// JLabel
		}
	}

	/**
	 * Lance une partie
	 */
	private void goToGame() {
		initTiles();
		renderTiles();

		this.removeAll();
		this.add(gamePanel);
		this.revalidate();
		this.repaint();
		setFocusable(true);
		requestFocusInWindow();
	}

	/**
	 * Affiche le score de fin
	 */
	private void goToEnd() {
		// Bouton REJOUER
		replayButton = new JButton("REJOUER");
		replayButton.setFont(new Font("Arial", Font.BOLD, MAIN_BUTTONS_POLICE_SIZE));
		replayButton.setPreferredSize(new Dimension(MAIN_BUTTONS_SIZE_X, MAIN_BUTTONS_SIZE_Y));
		replayButton.setBackground(Color.white);
		replayButton.addMouseListener(new MouseListener() {
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

		// Bouton QUITTER
		quitButton = new JButton("QUITTER");
		quitButton.setFont(new Font("Arial", Font.BOLD, MAIN_BUTTONS_POLICE_SIZE));
		quitButton.setPreferredSize(new Dimension(MAIN_BUTTONS_SIZE_X, MAIN_BUTTONS_SIZE_Y));
		quitButton.setBackground(Color.white);
		quitButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
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

		scoreText = new JLabel();
		scoreText.setFont(new Font("Arial", Font.BOLD, SCORE_LABEL_POLICE_SIZE));
		scoreText.setHorizontalAlignment(JLabel.CENTER);

		// Fenêtre sud du menu
		endSouthPanel = new JPanel(new BorderLayout());
		endSouthPanel.setBackground(Color.white);
		endSouthPanel.add(replayButton, BorderLayout.NORTH);
		endSouthPanel.add(quitButton, BorderLayout.SOUTH);

		// Fenêtre du menu
		endPanel = new JPanel(new BorderLayout());
		endPanel.setBackground(Color.white);
		endPanel.add(scoreText, BorderLayout.CENTER);
		endPanel.add(endSouthPanel, BorderLayout.SOUTH);

		scoreText.setText("Perdu! Votre score est de " + getScore() + ".");

		this.removeAll();
		this.add(endPanel);
		this.revalidate();
		this.repaint();
		setFocusable(true);
		requestFocusInWindow();
	}

	/**
	 * Initialise les tuiles
	 */
	private void initTiles() {
		// On met toutes les tuiles à zéro
		int i, tile;
		for (i = 0; i < TILES_NB; i++)
			tilesValue[i] = 0;

		// On ajoute les toutes premières tuiles
		Random r = new Random();
		i = 0;
		int tilesNumber = TILES_INIT_MIN + r.nextInt(TILES_INIT_MAX - TILES_INIT_MIN + 1);
		while (i < tilesNumber) {
			tile = getRandTileNumber(r);
			if (tilesValue[tile] == 0) {
				tilesValue[tile] = getRandTileValue(r);
				i++;
			}
		}
	}

	/**
	 * Affiche les valeurs des tuiles
	 */
	private void renderTiles() {
		for (int i = 0; i < TILES_NB; i++) {
			// Si la tuile a comme valeur 0, on affiche un caractere vide sur le
			// JLabel
			if (tilesValue[i] == 0) {
				tiles[i].setText("");
				tiles[i].setBackground(new Color(255, 255, 255, 255));
			}
			// Sinon on affiche la valeur correspondante
			else {
				if (tilesValue[i] == 1)
					tiles[i].setBackground(new Color(255, 100, 100, 255));
				else if (tilesValue[i] == 2)
					tiles[i].setBackground(new Color(100, 100, 255, 255));
				else if (tilesValue[i] <= 255 / 4)
					tiles[i].setBackground(new Color(255, 255 - tilesValue[i] * 4, 0, 255));
				else if (tilesValue[i] <= 255 * 5 / 4)
					tiles[i].setBackground(new Color(255, 0, tilesValue[i] - 255 / 4, 255));
				else
					tiles[i].setBackground(new Color(255, 0, 255, 255));
				tiles[i].setText(String.valueOf(tilesValue[i]));
			}
		}
	}

	/**
	 * Permet d'obtenir une valeur à affecter à une tuile
	 * 
	 * @param r
	 *            variable pour utiliser l'aléatoire
	 * @return valeur d'une tuile (1 ou 2)
	 */
	private int getRandTileValue(Random r) {
		return 1 + r.nextInt(2);
	}

	/**
	 * Permet d'obtenir le numéro de position tuile
	 * 
	 * @param r
	 *            variable pour utiliser l'aléatoire
	 * @return numéro de tuile
	 */
	private int getRandTileNumber(Random r) {
		return r.nextInt(TILES_NB);
	}

	/**
	 * Permets de créer une nouvelle tuile selon le deplacement réalisé
	 * 
	 * @param bornSup
	 *            position tuile maximale
	 * @param borneInf
	 *            position tuile minimale
	 * @param horizontal
	 *            mouvement horizontal ou non
	 * @param r
	 *            variable pour utiliser l'aléatoire
	 */
	private void addRandTile(int bornSup, int bornInf, boolean horizontal, Random r) {
		int temp;
		// Si le deplacement a été horizontal
		if (horizontal) {
			do {
				temp = getRandTileNumber(r);
			} while (((temp + bornSup + bornInf) % TILES_NB_L != 0) || (tilesValue[temp] != 0)); // On
																									// cherche
																									// une
																									// tuile
																									// vide
																									// et
																									// comprise
																									// entre
																									// les
																									// deux
																									// bornes
			tilesValue[temp] = getRandTileValue(r);
		}
		// S'il a été vertical
		else {
			do {
				temp = getRandTileNumber(r);
			} while (temp < bornSup || temp > bornInf || (tilesValue[temp] != 0)); // On
																					// cherche
																					// une
																					// tuile
																					// vide
																					// et
																					// comprise
																					// entre
																					// les
																					// deux
																					// bornes
			tilesValue[temp] = getRandTileValue(r);
		}
	}

	/**
	 * Deplacer les tuiles dans une direction
	 * 
	 * @param direction
	 *            du mouvement
	 */
	private void moveTiles(int direction) {
		Random r = new Random();
		boolean mouvement = false;

		switch (direction) {
		case -1 * TILES_NB_L: // Mouvement vers le haut
			for (int i = TILES_NB_L; i < TILES_NB; i++) {
				// On va verifier que le mouvement est possible pour au moins
				// une tuile et le realiser en montant les tuiles
				if (moveOneTile(i, -1 * TILES_NB_L))
					mouvement = true;
			}
			if (mouvement)
				addRandTile(TILES_NB - TILES_NB_L, TILES_NB, false, r);
			break;

		case 1: // Mouvement vers la droite
			for (int i = TILES_NB - 1; i >= 0; i--) {
				// On vérifie que le mouvement est possible pour au moins une
				// tuile et on le réalise
				if ((i + 1) % TILES_NB_L != 0 && moveOneTile(i, 1))
					mouvement = true;
			}
			if (mouvement)
				addRandTile(0, 0, true, r);
			break;

		case -1: // Mouvement vers la gauche
			for (int i = 0; i < TILES_NB; i++) {
				if (i % TILES_NB_L != 0 && moveOneTile(i, -1))
					mouvement = true;
			}
			if (mouvement)
				addRandTile(0, 1, true, r);
			break;

		case TILES_NB_L: // Mouvement vers le bas
			for (int i = TILES_NB - TILES_NB_L - 1; i >= 0; i--) {
				if (moveOneTile(i, TILES_NB_L))
					mouvement = true;
			}
			if (mouvement)
				addRandTile(0, TILES_NB_L, false, r);
			break;
		}

		if (mouvement) {
			// Fin du jeu si plus aucun mouvement n'est possible
			if (checkAllMoves())
				goToEnd();
			renderTiles();
		}
	}

	/**
	 * Deplacer une tuile si cela est possible
	 * 
	 * @param i
	 *            numéro de tuile
	 * @param direction
	 *            du mouvement
	 * @return mouvement réalisé ou non
	 */
	private boolean moveOneTile(int i, int direction) {
		if (checkMoves(i, direction)) {
			tilesValue[i + direction] += tilesValue[i];
			tilesValue[i] = 0;
			return true;
		}
		return false;
	}

	/**
	 * Permet de savoir si le mouvement est possible pour une tuile
	 * 
	 * @param i
	 *            numéro de tuile
	 * @param direction
	 *            du mouvement
	 * @return mouvement est possible ou non
	 */
	private boolean checkMoves(int i, int direction) {
		if ((tilesValue[i] == tilesValue[i + direction] && tilesValue[i] != 1 && tilesValue[i] != 2
				&& tilesValue[i] != 0) || ((tilesValue[i] == 1) && (tilesValue[i + direction] == 2))
				|| ((tilesValue[i] == 2) && (tilesValue[i + direction] == 1))
				|| ((tilesValue[i + direction] == 0) && tilesValue[i] != 0))
			return true;
		return false;
	}

	/**
	 * Permet de savoir si le jeu est fini, c'est à dire si plus aucun mouvement
	 * n'est possible.
	 * 
	 * @return fin du jeu ou non
	 */
	private boolean checkAllMoves() {
		int i = 0;
		// On va tout d'abord verifier que toutes les tuiles sont remplies
		for (i = 0; i < TILES_NB; i++) {
			if (tilesValue[i] == 0)
				return false;
		}
		// On va verifier que tous les mouvements vers le haut sont impossibles
		for (i = TILES_NB_L; i < TILES_NB; i++) {
			if (checkMoves(i, -1 * TILES_NB_L))
				return false;
		}
		// On va verifier que tous les mouvements vers la droite sont
		// impossibles
		for (i = TILES_NB - 1; i >= 0; i--) {
			if ((i + 1) % TILES_NB_L != 0 && checkMoves(i, 1))
				return false;
		}
		// On va verifier que tous les mouvements vers la gauche sont
		// impossibles
		for (i = 0; i < TILES_NB; i++) {
			if (i % TILES_NB_L != 0 && checkMoves(i, -1))
				return false;
		}
		// On va verifier que tous les mouvements vers le bas sont impossibles
		for (i = TILES_NB - TILES_NB_L - 1; i >= 0; i--) {
			if (checkMoves(i, TILES_NB_L))
				return false;
		}
		return true;
	}

	/**
	 * Calcule le score comme la somme de toutes les tuiles
	 * 
	 * @return score de la partie
	 */
	private int getScore() {
		int score = 0;
		for (int i = 0; i < TILES_NB; i++)
			score += tilesValue[i];
		return score;
	}

	/**
	 * Permets de savoir si une touche a été activée
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP: // Si l'on a appuyé sur la flèche du haut
			moveTiles(-1 * TILES_NB_L);
			break;

		case KeyEvent.VK_RIGHT: // Si l'on a appuyé sur la flèche de droite
			moveTiles(1);
			break;

		case KeyEvent.VK_LEFT: // Si l'on a appuyé sur la flèche de gauche
			moveTiles(-1);
			break;

		case KeyEvent.VK_DOWN: // Si on a appuyé sur la flèche du bas
			moveTiles(TILES_NB_L);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	/**
	 * Fonction principale
	 * 
	 * @param args
	 *            aucun argument
	 */
	public static void main(String[] args) {
		Threes t = new Threes();
		t.play();
	}
}

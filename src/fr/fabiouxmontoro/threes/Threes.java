package fr.polytech.ihm.advthrees;


import java.awt.Dimension;

import java.util.Random;
import java.util.Timer;


import javax.swing.JFrame;
import javax.swing.JPanel;

public class Threes extends JFrame {

	private final static long serialVersionUID = 1L;
	public final static int TILES_NB_L = 4;						// Nombre de tuiles par ligne
	public final static int TILES_NB = TILES_NB_L * TILES_NB_L;	// Nombre de tuiles total
	public final static int TILES_INIT_MIN = 4;					// Nombre de tuiles minimum au démarrage
	public final static int TILES_INIT_MAX = 6;					// Nombre de tuiles maximum au démarrage

	public final static int WINDOW_SIZE_X = Tile.TILES_SIZE_X * TILES_NB_L + Tile.TILES_GAP * (TILES_NB_L+1);	// Largeur de la fenêtre
	public final static int WINDOW_SIZE_Y = Tile.TILES_SIZE_Y * TILES_NB_L + Tile.TILES_GAP * (TILES_NB_L+1);	// Hauteur de la fenêtre

	public final static int MAIN_BUTTONS_SIZE_X = WINDOW_SIZE_X;			// Largeur des boutons principaux
	public final static int MAIN_BUTTONS_SIZE_Y = WINDOW_SIZE_X / 8;		// Hauteur des boutons principaux
	public final static int MAIN_BUTTONS_POLICE_SIZE = WINDOW_SIZE_X / 10;	// Taille de la police des boutons principaux
	public final static int SCORE_LABEL_POLICE_SIZE = WINDOW_SIZE_X / 20;	// Taille de la police du score

	public final static int FRAME_DELAY = 40; // Temps entre chaque frame (millisecondes)
	public final static int SWIPE_MIN_MOVE = 5;	// Mouvement minimal de la souris pour être pris en compte
	public final static float SWIPE_MIN_SPEED = 20/1000;	// Mouvement minimal de la souris pour être pris en compte


	private JPanel contentPane;
	private MenuPanel menuPanel;
	private GamePanel gamePanel;
	private VersionBase versionBase;
	private EndMenu endPanel;

	private int[] tilesValue;

	private Timer timer;
	private ThreesTimerTask timerTask;
	private boolean controlsFreeze;


	/**
	 * Créé une classe pour le jeu
	 */
	public Threes() {}


	/**
     * Initialise tous les éléments graphiques du programme et lance le jeu
     */
	public void play()
	{
	    this.setTitle("Threes"); // Titre de la fenêtre

	    //this.setSize(WINDOW_SIZE_X + 6, WINDOW_SIZE_Y + 29);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(WINDOW_SIZE_X, WINDOW_SIZE_Y));
	    contentPane = (JPanel) getContentPane();
	    
	    timer = new Timer();
	    timerTask = new ThreesTimerTask(this);

	    // Initialisation de chaque partie du programme
	    this.setResizable(false);

	    tilesValue = new int[TILES_NB];
		menuPanel = new MenuPanel(this);
	    gamePanel = new GamePanel(this);
	    endPanel = new EndMenu(this);
	    
	    contentPane.add(menuPanel);
	    
	    setVisible(true);
	    this.setLocationRelativeTo(null);
	}


	/**
	 * Lance une partie
	 */
	public void goToGame(int i)
	{
		if(i==1){
			initTiles();
			controlsFreeze = false;
			gamePanel.updateValues();
		    contentPane.removeAll();
		    contentPane.add(gamePanel, 0);
		    contentPane.revalidate();
		    contentPane.repaint();
		    gamePanel.init();
		    timerTask = new ThreesTimerTask(this);
		    timer.scheduleAtFixedRate(timerTask, 0, FRAME_DELAY);
		}
		else if(i==0){
			versionBase=new VersionBase();
		    contentPane.removeAll();
		    contentPane.add(versionBase, 0);
		    contentPane.revalidate();
		    contentPane.repaint();
		    versionBase.play(this.getWidth(),this.getHeight());
		}
	}


	public void replay(){
		 contentPane.removeAll();
		 play();
		 contentPane.revalidate();
		 contentPane.repaint();
	}
	
	/**
     * Initialise les tuiles
     */
	private void initTiles()
	{
		// On met toutes les tuiles à zéro
		int i, tile;
		for(i = 0; i < TILES_NB; i++)
			tilesValue[i] = 0;

		// On ajoute les toutes premières tuiles
	    Random r = new Random();
		i = 0;
		int tilesNumber = TILES_INIT_MIN + r.nextInt(TILES_INIT_MAX - TILES_INIT_MIN + 1);
		while(i < tilesNumber)
		{
			tile = getRandTileNumber(r);
			if(tilesValue[tile] == 0)
			{
				tilesValue[tile] = getRandTileValue(r);
				i++;
			}
		}
	}


	 /**
     * Permet d'obtenir une valeur à affecter à une tuile
     * @param   r variable pour utiliser l'aléatoire
     * @return  valeur d'une tuile (1 ou 2)
     */
	private int getRandTileValue(Random r)
	{
		return 1 + r.nextInt(2);
	}


	 /**
     * Permet d'obtenir le numéro de position tuile
     * @param	r variable pour utiliser l'aléatoire
     * @return	numéro de tuile
     */
	private int getRandTileNumber(Random r)
	{
		return r.nextInt(TILES_NB);
	}


    /**
     * Permets de créer une nouvelle tuile selon le deplacement réalisé
     * @param	bornSup position tuile maximale
     * @param	borneInf position tuile minimale
     * @param	horizontal mouvement horizontal ou non
     * @param	r variable pour utiliser l'aléatoire
     */
	private void addRandTile(int bornSup ,int bornInf, boolean horizontal , Random r )
	{
		int temp, newValue;
		// Si le deplacement a été horizontal
		if(horizontal)
		{
			do {
				temp = getRandTileNumber(r);
			}
			while(((temp+bornSup+bornInf)%TILES_NB_L != 0) || (tilesValue[temp] != 0)); // On cherche une tuile vide et comprise entre les deux bornes
		}
		// S'il a été vertical
		else
		{
			do {
				temp = getRandTileNumber(r);
			}
			while (temp < bornSup || temp > bornInf || (tilesValue[temp] != 0)); // On cherche une tuile vide et comprise entre les deux bornes
		}
		newValue = getRandTileValue(r);
		tilesValue[temp] = newValue;
		gamePanel.spawnTile(temp, newValue);
	}


    /**
     * Deplacer les tuiles dans une direction
     * @param	direction du mouvement
     */
	public void moveTiles(int direction)
	{
		if(!controlsFreeze)
		{
			Random r = new Random();
			boolean mouvement = false;

			switch(direction)
			{
				case -1*TILES_NB_L: // Mouvement vers le haut
					for(int i = TILES_NB_L; i < TILES_NB; i++)
					{
						//On va verifier que le mouvement est possible pour au moins une tuile et le realiser en montant les tuiles
						if(moveOneTile(i, -1*TILES_NB_L))
							mouvement = true;
					}
					if(mouvement)
						addRandTile(TILES_NB-TILES_NB_L, TILES_NB, false, r);
					break;

				case 1: // Mouvement vers la droite
					for(int i = TILES_NB-1; i >= 0; i--)
					{
						// On vérifie que le mouvement est possible pour au moins une tuile et on le réalise
						if((i+1)%TILES_NB_L != 0 && moveOneTile(i, 1))
							mouvement = true;
					}
					if (mouvement)
						addRandTile(0, 0, true, r);
					break;

				case -1: // Mouvement vers la gauche
					for(int i = 0; i < TILES_NB; i++)
					{
						if(i%TILES_NB_L != 0 && moveOneTile(i, -1))
							mouvement = true;
					}
					if (mouvement)
						 addRandTile(0, 1, true, r);
					break;

				case TILES_NB_L: // Mouvement vers le bas
					 for(int i = TILES_NB-TILES_NB_L-1; i >= 0; i--)
					 {
						if(moveOneTile(i, TILES_NB_L))
							mouvement = true;
					 }
					if(mouvement)
						addRandTile(0, TILES_NB_L, false, r);
					break;

			}

				// Fin du jeu si plus aucun mouvement n'est possible
			if(!mouvement && checkAllMoves())
			{
				controlsFreeze = true;
				endPanel.setScore(getScore());
				endPanel.writeFile();
				endPanel.read();
				endPanel.updateValues();
				contentPane.add(endPanel, 0);
			    contentPane.add(gamePanel, 1);
			    gamePanel.revalidate();
			    gamePanel.repaint();
			}
		}
	}


    /**
     * Deplacer une tuile si cela est possible
     * @param 	i numéro de tuile
     * @param	direction du mouvement
     * @return  mouvement réalisé ou non
     */
	private boolean moveOneTile(int i , int direction)
	{
		if(checkMoves(i, direction))
		{
			controlsFreeze = true;
			gamePanel.addMovingTile(i, direction);
			tilesValue[i+direction] += tilesValue[i];
			tilesValue[i] = 0;
			gamePanel.resetValue(i);
			return true;
		}
		return false;
	}


    /**
     * Permet de savoir si le mouvement est possible pour une tuile
     * @param 	i numéro de tuile
     * @param	direction du mouvement
     * @return	mouvement est possible ou non
     */
	private boolean checkMoves(int i , int direction)
	{
		if((tilesValue[i] == tilesValue[i+direction] && tilesValue[i] != 1 && tilesValue[i] != 2 && tilesValue[i] != 0)
				|| ((tilesValue[i] == 1) && (tilesValue[i+direction] == 2))
				|| ((tilesValue[i] == 2) && (tilesValue[i+direction] == 1))
				|| ((tilesValue[i+direction] == 0) && tilesValue[i] != 0))
			return true;
		return false;
	}



    /**
     * Permet de savoir si le jeu est fini, c'est à dire si plus aucun mouvement n'est possible.
     * @return	fin du jeu ou non
     */
	private boolean checkAllMoves()
	{
		int i = 0;
		// On va tout d'abord verifier que toutes les tuiles sont remplies
		for(i = 0; i < TILES_NB; i++)
		{
			if(tilesValue[i] == 0)
				return false;
		}
		// On va verifier que tous les mouvements vers le haut sont impossibles
		for(i = TILES_NB_L; i < TILES_NB; i++)
		{
			if(checkMoves(i, -1*TILES_NB_L))
				return false;
		}
		// On va verifier que tous les mouvements vers la droite sont impossibles
		for(i = TILES_NB-1; i >= 0; i--)
		{
			if((i+1)%TILES_NB_L != 0 && checkMoves(i, 1))
				return false;
		}
		// On va verifier que tous les mouvements vers la gauche sont impossibles
		for(i = 0; i < TILES_NB; i++)
		{
			if(i%TILES_NB_L != 0 && checkMoves(i, -1))
				return false;
		}
		// On va verifier que tous les mouvements vers le bas sont impossibles
		for(i = TILES_NB-TILES_NB_L-1; i >= 0; i--)
		{
			if(checkMoves(i, TILES_NB_L))
				return false;
		}
		return true;
	}


	/**
     * Calcule le score comme la somme de toutes les tuiles
     * @return	score de la partie
     */
	public int getScore()
	{
		int score = 0;
		for(int i = 0; i < TILES_NB; i++){
			score += tilesValue[i];
		}
		return score;
	}


	/**
	 * Retourne la valeur d'une tuile
	 * @param i position
	 * @return valeur
	 */
	public int getTValue(int i)
	{
		return tilesValue[i];
	}


	/**
	 * Permet à l'utilisateur d'utiliser les touches à nouveau
	 */
	public void unfreezeControls()
	{
		controlsFreeze = false;
	}


	/**
	 * Fonction principale
	 * @param args aucun argument
	 */
	public static void main(String[] args)
	{
		Threes t = new Threes();
		t.play();
	}
}

package fr.polytech.ihm.advthrees;

import java.util.TimerTask;

public class ThreesTimerTask extends TimerTask
{
	private Threes threes;
	
	public ThreesTimerTask(Threes threes)
	{
		super();
		this.threes = threes;
	}
	
	public void run()
	{
		threes.repaint();
	}
}

import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

import jeux.Accueil;
import jeux.MyCanvas;
import jeux.HighScoreManager;

public class FlesyDaoro extends MIDlet {
	private Display display;
	//les conteneurs
	private Accueil accueil;
	private MyCanvas myCanvas;
	//les commandes
	private Command exitCommand;
	private Command backCommand;
	private Command newGameCommand;
	private Command bestScoreCommand;
	private Command helpCommand;

	boolean is_playing = false;
	
	public FlesyDaoro() {
		super();
		accueil = new Accueil();
		exitCommand = new Command("Quitter", Command.EXIT, 0);
		backCommand = new Command("Retour", Command.EXIT, 1);
		newGameCommand = new Command("Jouer", Command.OK, 1);
		bestScoreCommand = new Command("Meuilleur score", Command.OK, 1);
		helpCommand = new Command("Aide?", Command.OK, 1);
	}

	public void startApp() {
		
		accueil.addCommand(exitCommand);
		accueil.addCommand(newGameCommand);
		accueil.addCommand(bestScoreCommand);
		accueil.addCommand(helpCommand);

		accueil.setCommandListener(new CommandListener() {
			public void commandAction(Command c, Displayable d) {
				if (c == exitCommand){
					display.setCurrent(null);
					notifyDestroyed();
				} else if (c == newGameCommand){
					is_playing = true;
					myCanvas = new MyCanvas();

					myCanvas.start();
					myCanvas.addCommand(backCommand);		

					myCanvas.setCommandListener(new CommandListener() {
						public void commandAction(Command c, Displayable d) {
							if (c == backCommand){
								is_playing = false;
								myCanvas.stop();
								display.setCurrent(accueil);
							}
						}
					});
					display.setCurrent(myCanvas);
				} else if (c == bestScoreCommand){
					int highScore = HighScoreManager.getHighScore();
					String shighScore = "Le meilleure score est : " + String.valueOf(highScore);

					Alert alert = new Alert("Meilleur score", shighScore, null, AlertType.INFO);

			        alert.setTimeout(javax.microedition.lcdui.Alert.FOREVER);
			        // Affichez l'alerte
			        display.setCurrent(alert, accueil);

				} else if (c == helpCommand) {

					String aide = "\tBienvenue sur Flesydaoro\n\nLe but du jeu est de tirer le maximum de pomme en une minute\nPour jouer:\n"
					+"- Utiliser les directions haut et bas pour deplacer l'arc.\n- Utiliser la touche du milieu pour tirer.\n\nBonne chance!\n\nPar R.H.J.A 2023";

					Alert alert = new Alert("Aide?", aide, null, AlertType.INFO);
					
			        alert.setTimeout(javax.microedition.lcdui.Alert.FOREVER);
			        // Affichez l'alerte
			        display.setCurrent(alert, accueil);
				}
			}
		});
		
		display = Display.getDisplay(this);
		if (!is_playing){
			display.setCurrent(accueil);
		} 
	}

	public void pauseApp() {
	}

	public void destroyApp(boolean unconditional) {
		notifyDestroyed();
	}
}
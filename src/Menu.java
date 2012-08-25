import javax.microedition.lcdui.List;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;

public class Menu implements CommandListener {
	private Display display;
	private BubbleBreaker midlet;
	private List menu;	
	private Game game;
	private Score HighScore;
	private Command cmdOk;
	private int gameScore;
	private OptionsMenu options;
	private Alert aboutAlert;
	
	public Menu (BubbleBreaker bubbleMidlet, Display d) {
		StrConsts.setLanguage(0);
		midlet = bubbleMidlet;
		options = new OptionsMenu (this);
		StrConsts.setLanguage (options.getLang());
		options = new OptionsMenu (this);
		HighScore = new Score (this);
		aboutAlert = new Alert (StrConsts.S_ABOUT, StrConsts.S_ABOUT_STR, null, AlertType.INFO);
		aboutAlert.setTimeout (Alert.FOREVER);
		cmdOk = new Command (StrConsts.S_CMD_OK, Command.OK, 1);
		menu = new List ("Bubble Breaker", List.IMPLICIT, StrConsts.S_MENU_COMMANDS, null);
		menu.setSelectCommand (cmdOk);
		menu.setCommandListener (this);
		display = d;
		display.setCurrent (menu);
	}

	public void commandAction (Command c, Displayable d){
		if (c == cmdOk && d == menu) {
			int setIndex = menu.getSelectedIndex();
			if (menu.getString(0) == StrConsts.S_RESUME) setIndex--;
			switch (setIndex) {
				case -1 :
					display.setCurrent (game);
					break;
				case 0 :
					game = null;
					game = new Game (this, (byte) options.getQuantity(), options.getSize());
					display.setCurrent (game);
					break;
				case 1 : options.show (display);
					break;
				case 2 :HighScore.showHighScores(display);
					break;
				case 3 : display.setCurrent (aboutAlert);
					 break;
				case 4 : midlet.destroyApp (true);
					 break;
				default :  return;
			}
		} else {
			if (c == game.cmdMenu) {
				if (menu.getString(0) != StrConsts.S_RESUME) menu.insert (0, StrConsts.S_RESUME, null);
				menu.setSelectedIndex(0, true);
				display.setCurrent (menu);
			}
			if (c == Score.cmdBack) display.setCurrent (menu);
			if (c == Score.cmdDone) {
				HighScore.addScore();
				display.setCurrent (menu);
			}
			if (c == options.cmdOk || c == options.cmdCancel ) {
				System.out.println ("pressssingg!!!");
				options.closeForm (c);
				display.setCurrent (menu);
			}
		}
	}

	public void endGame (int s) {
		gameScore = s;
		//game = null;
		Alert alert = new Alert("Info");
		alert.setTimeout (Alert.FOREVER);
		alert.setString (StrConsts.S_GAME_OVER + Integer.toString(gameScore));
		if (menu.getString(0) == StrConsts.S_RESUME) menu.delete(0);
		if (!HighScore.checkHighScore (display, gameScore)) display.setCurrent (menu);
	}
}

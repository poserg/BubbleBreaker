import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

public class BubbleBreaker extends MIDlet {
	protected void destroyApp (boolean destr) {
		notifyDestroyed ();
	}

	protected void pauseApp () {

	}

	protected void startApp(){
		Menu menu = new Menu(this, Display.getDisplay(this));
	}

	public void doQuit () {
		destroyApp (true);
	}

}


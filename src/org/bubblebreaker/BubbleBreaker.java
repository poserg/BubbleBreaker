package org.bubblebreaker;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

import org.bubblebreaker.menu.Menu;

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

	public void destroy() {
		destroyApp (true);
	}
}


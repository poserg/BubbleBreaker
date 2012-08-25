package org.bubblebreaker.view;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Image;

public class Cursor {
	public byte x;
	public byte y;
	//public Image image;
	//public byte type;
	private int size;
	/*
	private int minX = 0;
	private int minY = 0;*/
	public byte nCol;
	public byte nRow;

	Cursor (int s, byte maxX, byte maxY) {
		x = y = 0;	
		/*try {
			image = Image.createImage ("/cursor.png");
		} catch (Exception e) {}*/
		size = s;
		nCol = maxX;
		nRow = maxY;
		//image = new Image.createImage (size, size);
		//draw (image.getGraphics());
	}

	public void draw (Graphics g) {
		g.setColor (0xffffff);
		g.drawRect (x*size+2, y*size+2, size-4, size-4);
		g.drawRect (x*size+1, y*size+1, size-2, size-2);
		g.drawRect (x*size, y*size, size, size);
	}

	public void move (int action) {
		switch (action) {
			case Canvas.DOWN : 
				y = (nRow-1 > y) ? (byte) (y+1) : 0;
				break;
			case Canvas.UP : 
				 y = (y > 0) ? (byte) (y-1) : (byte) (nRow-1);
				 break;
			case Canvas.LEFT : 
				 x = (x > 0) ? (byte) (x-1) : (byte) (nCol-1);
				 break;
			case Canvas.RIGHT : 
				 x = (nCol-1 > x) ? (byte) (x+1) : 0;
				 break;
			default : return;
		}
	}

	public int getSize () { 
		return size;
	}
}

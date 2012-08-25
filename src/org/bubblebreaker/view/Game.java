package org.bubblebreaker.view;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import java.util.Random;
import java.lang.Math;

import org.bubblebreaker.consts.StrConsts;
import org.bubblebreaker.menu.Menu;

public class Game extends Canvas {
	//private Image[] images;
	private Image image;
	private Bubble[][] bubbles;
	//private Bubble[][] tmpBubbles;
	private int width;
	private int height;
	private int imageSize; 
	private byte quantity;
	private byte nCol;
	private byte nRow;
	private boolean isFire = false;

	public Command cmdMenu = new Command (StrConsts.S_CMD_MENU, Command.BACK, 1);
	//public Command cmdQuit = new Command (StrConsts.S_CMD_QUIT, Command.EXIT, 1);

	private Menu midlet;
	private Cursor cursor;
	private Area area;
	private int score = 0;
	private int localScore = 0;
	private int delta = 10;
	private Font stdFont = Font.getDefaultFont();
	private Font scoreFont = Font.getFont (Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);

	public Game (Menu menu, byte q, int size){
		super();
		midlet = menu;

		//only for MIDP2.0
		//setFullScreenMode (true);

		width = getWidth();
		height = getHeight()-delta;
		quantity = q;
		String name;
		switch (size) {
			case 0 : name = "/ball_small.png";
				 break;
			case 1 : name = "/ball_medium.png";
				 break;
			case 2 : name = "/ball.png";
				 break;
			default : name = "/ball.png";
				  break;
		}
		//images = new Image[5];
		try {
			image = Image.createImage (name);
			imageSize = image.getHeight();
			nCol = (byte)(width/imageSize);
			nRow = (byte)(height/imageSize);
			cursor = new Cursor (imageSize, nCol, nRow);
			genPos();
			area = new Area (bubbles, cursor);
		} catch (Exception e) {
			System.out.println ("Can't load images");
		}
	//	addCommand (cmdQuit);
		addCommand (cmdMenu);
		setCommandListener (midlet);	
		//System.out.println (nCol);
		//System.out.println (nRow);
	}	
	/* 
	   public void commandAction (Command c, Displayable d) {
//if (c == cmdQuit) midlet.doQuit();
//if (c == cmdMenu) System.out.println("Command Menu");
	   }
	   */
	private void genPos () {
		Random rnd = new Random();
		bubbles = new Bubble[nCol][nRow];
		for (byte i = 0; i < nCol; i++){
			for (byte j = 0; j < nRow; j++){
				bubbles[i][j] = new Bubble (i, j, (byte)Math.abs(rnd.nextInt()%quantity));
				//System.out.println(bubbles[i][j].getType());
			}
		}
	}

	private boolean checkFinish () {
		boolean result = true;
		for (int i = nCol-1; i>=0; i--){
			for (int j = nRow-1; j>=0; j--){
				byte t = bubbles[i][j].getType();
				try {
					if ( t != -1 &&
							(t == bubbles[i-1][j].getType() ||
							 t == bubbles[i+1][j].getType() ||
							 t == bubbles[i][j-1].getType() ||
							 t == bubbles[i][j+1].getType())){
						result = false;
						//System.out.println (i + " : " + j);
						break;	
							 }
				} catch (Exception e) {
					//System.out.println ("checkFinish's error :\t"+e.getMessage());
				}
			}
			if (!result) break;
		}
		return result;
	}

	protected void paint (Graphics g) {
		g.setColor (0);
		g.fillRect (0, 0, getWidth(), getHeight());
		g.setColor (0xffffff);
		g.setFont (scoreFont);
		g.drawString (StrConsts.S_SCORE + Integer.toString(score), 10, 0, Graphics.TOP | Graphics.LEFT); 
		g.translate (0, delta);
		//int tmp = g.getFont().getHeight()+1;
		//g.setClip (0, delta, getWidth(), getHeight()-delta);
		for (int i=0; i<nCol; i++)
			for (int j=0; j<nRow; j++) {
				byte t = bubbles[i][j].getType();
				/* 
				// Optimized painting Canvas
				// NOT WORK!
				byte tmp;
				try {
				tmp = tmpBubbles[i][j].getType();
				} catch (Exception e) {
				tmp = -2;
				}

				if (t > -1 && t != tmp) */
				if (t > -1)
					g.drawRegion (image,
							imageSize*t,
							0,
							imageSize,
							imageSize,
							0,
							imageSize*i,
							imageSize*j,
							Graphics.TOP | Graphics.LEFT);
			}


		cursor.draw (g);
		area.draw(g);
		drawScore (g);
		//if (area.size() > 1) area.draw(g); // - not work...why?

		//tmpBubbles = bubbles;
	}

	private void drawScore (Graphics g) {
		int col;
		int row;
		localScore = area.getScore();
		if (localScore > 0) {
			if (area.minX > 0 && area.minY > 0) {
				col = area.minX-1;
				row = area.minY-1;	
			} else if (area.minY == 0 && area.minX > 0){
				col = area.minX-1;
				row = area.minY; //row = 0
			} else if (area.minY > 0 && area.minX == 0) {
				col = area.minX;
				row = area.minY-1;
			} else 	{
				col = area.maxX+1;
				row = area.maxY+1;
			}
			//System.out.println (area.getScore()+" : " + col + " : " + row);
			//System.out.println ("minX = " + area.minX + " :\tminY = " +area.minY + " :\tmaxX = " +area.maxX + ":\tmaxY = " + area.maxY);
			//g.setColor (0);
			//g.fillRect (col*imageSize, row*imageSize, imageSize, imageSize);
			g.setColor (0xffffff);
			g.setFont (stdFont);
			g.drawString (Integer.toString(localScore), col*imageSize, row*imageSize, Graphics.LEFT | Graphics.TOP);
		}

	}

	private void fire () {
		int n = area.size();
		if (n > 1) {
			isFire = true;
			
			for (int i = 0; i < n; i++){
				Bubble b = (Bubble) area.elementAt(i);
				bubbles[b.col][b.row].setType ((byte) -1);
			}
			//repaint();
			//fired on vertical
			for (byte i = area.minX; i <= area.maxX; i++) {
				byte tmp = (byte) (nRow-1);
				for (byte j = (byte) (nRow-1); j>=0; j--){
					byte t = bubbles[i][j].getType();
					if (t != -1 && j != tmp) {
						bubbles[i][j].setType((byte) -1);
						bubbles[i][tmp].setType(t);
					} 
					if (t != -1) tmp--;
				}
			}
			//repaint();
			//fired on horisontal	
			for (byte i = 0; i < nRow; i++) {
				byte tmp = (byte) (nCol-1); //помнит последнее пустое место, если оно есть
				for (byte j = (byte) (nCol-1); j>=0; j--){
					byte t = bubbles[j][i].getType();
					if (t != -1 && j != tmp) {
						bubbles[j][i].setType((byte) -1);
						bubbles[tmp][i].setType(t);
					} 
					if (t != -1) tmp--; //запоминаем пустое место
				}
			}
		}
	}




	protected void keyPressed (int keyCode) {
		//isPress = true;
		int action = getGameAction (keyCode);
		int x = cursor.x;
		int y = cursor.y;
		switch (action) {
			case DOWN : 
			case UP :
			case LEFT :
			case RIGHT :
				cursor.move (action);
				while (bubbles[cursor.x][cursor.y].getType() == -1) cursor.move (action);
				break;	
			case FIRE : fire();
				    if (isFire) {
					    score+=localScore;
					    if (checkFinish()) {
						    System.out.println ("FINISHING!!!!!!!!!!!!!!");
						    midlet.endGame (score);
					    }
				    }
				    break;
			default : return;
		}
		if (x != cursor.x || y != cursor.y || isFire) {
			isFire = false;
			area = null;
			area = new Area (bubbles, cursor);
			repaint();
		}
	}
	/*
	   protected void keyReleased (int keyCode) {
	   isPress = false;
	   }

	   protected void keyRepeated (int keyCode) {
	   System.out.println ("Press!!!");
	   }*/
}



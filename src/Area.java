//import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import java.util.Vector;

public class Area extends Vector {
	private Bubble[][] bubbles;
	private byte type;
	private byte nCol;
	private byte nRow;
	private int imageSize;
	public byte maxX = 0;
	public byte maxY = 0;
	public byte minX;
	public byte minY;

	public Area (Bubble[][] b, Cursor c) {
		super();
		bubbles = b;
		type = bubbles[c.x][c.y].getType();
		minX = nCol = c.nCol;
		minY = nRow = c.nRow;
		imageSize = c.getSize();
		check (c.x, c.y);
		/*
		for (int i = 0; i < size(); i++){
			Bubble bb = (Bubble) elementAt (i);
			System.out.println (bb.col + ", " + bb.row);
		}*/
	}

	private void check (int x, int y) {
		addElement (bubbles[x][y]);
		if (bubbles[x][y].getType() > -1) {
			bubbles[x][y].state = true;
			if (x < nCol-1 && bubbles[x+1][y].getType() == type && !bubbles[x+1][y].state) check(x+1, y);
			if (x > 0 && bubbles[x-1][y].getType() == type && !bubbles[x-1][y].state) check(x-1, y);
			if (y < nRow-1 && bubbles[x][y+1].getType() == type && !bubbles[x][y+1].state) check(x, y+1);
			if (y > 0 && bubbles[x][y-1].getType() == type && !bubbles[x][y-1].state) check(x, y-1);
		}
	}

	public void draw (Graphics g) {
		Bubble bb;
		g.setColor (0xffffff);
		for (int i = 0; i < size(); i++){
			bb = (Bubble) elementAt (i);
			if (bb.col == 0 || !bubbles[bb.col-1][bb.row].state) {
				g.drawLine (bb.col*imageSize, bb.row*imageSize, bb.col*imageSize, (bb.row+1)*imageSize);
				minX = (bb.col < minX) ? bb.col : minX;
			}
			if (bb.col == nCol-1 || !bubbles[bb.col+1][bb.row].state) {
				g.drawLine ((bb.col+1)*imageSize, bb.row*imageSize, (bb.col+1)*imageSize, (bb.row+1)*imageSize);
				maxX = (bb.col > maxX) ? bb.col : maxX;
			}
			if (bb.row == 0 || !bubbles[bb.col][bb.row-1].state) {
				g.drawLine (bb.col*imageSize, bb.row*imageSize, (bb.col+1)*imageSize, bb.row*imageSize);
				//Testing
				if (bb.col <= minX) minY = (bb.row < minY) ? bb.row : minY;
			}
			if (bb.row == nRow-1 || !bubbles[bb.col][bb.row+1].state) {
				g.drawLine (bb.col*imageSize, (bb.row+1)*imageSize, (bb.col+1)*imageSize, (bb.row+1)*imageSize);
				maxY = (bb.row > maxY) ? bb.row : maxY;
			}
		}
		for (int i = 0; i< size(); i++) {
			bb = (Bubble) elementAt (i);
			bb.state = false;
		}
	}

	public int getScore () {
		int score = size();
		return score*score-score;
	}
}

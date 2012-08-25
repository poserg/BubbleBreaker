//import javax.microedition.lcdui.*;

public class Bubble extends Object
{
	public byte col;
	public byte row;
	private byte type;
	public boolean state;

	public Bubble (byte c, byte r, byte t) 
	{
		col = c;
		row = r;
		type = t;
		state = false;
	}

	public byte getType () { return type; }
	public void setType (byte t) { type = t; }
}


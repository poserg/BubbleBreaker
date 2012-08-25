import javax.microedition.lcdui.List;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.TextField;
import javax.microedition.lcdui.Form;
import javax.microedition.rms.RecordStore;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

class ScoreType {
	public String name;
	public int value;

	public ScoreType (String aname, int avalue) {
		name = aname;
		value = avalue;
	}
}

public class Score {
	private ScoreType[] scores;
	private Menu midlet;
	private TextField nameField; 
	private int gameScore;
	public static Command cmdBack = new Command (StrConsts.S_CMD_BACK, Command.BACK, 1);
	public static Command cmdDone = new Command (StrConsts.S_CMD_OK, Command.OK, 1);
	private List scoreList;

	public Score (Menu amenu) {
		midlet = amenu;
	}

	private void setDefaultScores() {
		scores[0] = new ScoreType ("one", 1000);
		scores[1] = new ScoreType ("two", 800);
		scores[2] = new ScoreType ("tree", 600);
		scores[3] = new ScoreType ("four", 400);
		scores[4] = new ScoreType ("five", 200);
	}

	private void loadScores () {
		scores = new ScoreType[SCORE_COUNT];
		try {
			RecordStore store = RecordStore.openRecordStore (SCORE_RECORD_NAME, true);
			byte[] data = store.getRecord (1);
			DataInputStream dis = new DataInputStream (new ByteArrayInputStream (data));
			for (int i = 0; i < SCORE_COUNT; i++){
				scores[i] = new ScoreType ("", 0);
				scores[i].name = dis.readUTF();
				scores[i].value = dis.readInt();
			}
			dis.close();
			store.closeRecordStore();
		} catch (Exception e) {
			System.out.println ("Can't load store. " + e.getMessage());
			setDefaultScores();
		}
	}
	
	private void saveScores () {
		try {
			RecordStore store = RecordStore.openRecordStore (SCORE_RECORD_NAME, true);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream (baos);
			for (int i = 0; i < SCORE_COUNT; i++){
				dos.writeUTF (scores[i].name);
				dos.writeInt (scores[i].value);
			}
			dos.close();
			byte[] data = baos.toByteArray();
			if (store.getNumRecords()>0)
				store.setRecord (1, data, 0, data.length);
			else store.addRecord (data, 0, data.length);

			store.closeRecordStore();
			baos.close();
		} catch (Exception e) {
			System.out.println ("Can't save Scores");
		}
	}

	public boolean checkHighScore (Display display, int agameScore) {
		boolean flag = false;
		gameScore = agameScore;
		loadScores();
		if (gameScore > scores[SCORE_COUNT-1].value) {	
			flag = true;
			Form inputForm = new Form (StrConsts.S_SCORE);
			inputForm.append (StrConsts.S_PRESENT);
			nameField = new TextField (StrConsts.S_INPUT_NAME, "", 10, TextField.ANY);	
			inputForm.append (nameField);
			inputForm.addCommand (cmdDone);
			inputForm.setCommandListener (midlet);
			display.setCurrent (inputForm);
		}
		return flag;
	}		

	
	public void showHighScores (Display display) {
		String[] scoreStr = new String[SCORE_COUNT];
		loadScores();
		for (int i = 0; i < SCORE_COUNT; i++){
			scoreStr[i] = scores[i].name;
			while (scoreStr[i].length() <= STR_LENGTH)
				scoreStr[i] += " ";
			scoreStr[i] += scores[i].value;
		}
		scoreList = new List (StrConsts.S_HIGH_SCORE, List.IMPLICIT, scoreStr, null);
		scoreList.addCommand (cmdBack);
		scoreList.setCommandListener (midlet);
		display.setCurrent (scoreList);
	}

	public void addScore () {
		String newName = nameField.getString();
		int i = SCORE_COUNT-1;
		while (i > -1 && gameScore > scores[i].value) i--;
		i++;
		for (int j = SCORE_COUNT - 2; j > i-1; j--){
			scores[j+1].value = scores[j].value;
			scores[j+1].name = scores[j].name;
		}
		scores[i].value = gameScore;
		scores[i].name = newName;
		saveScores();
	}


	private int SCORE_COUNT = 5;
	private int STR_LENGTH = 12;
	private String SCORE_RECORD_NAME = "Score record";
}

package org.bubblebreaker.menu;

import javax.microedition.lcdui.TextField;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.rms.RecordStore;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import org.bubblebreaker.consts.StrConsts;

public class OptionsMenu {
	private int quantity;
	private int size;
	private int lang;
	private Menu midlet;
	private Form form;
	private ChoiceGroup groupSize;
	private ChoiceGroup groupLang;
	private ChoiceGroup groupQ;
	public static Command cmdOk = new Command (StrConsts.S_CMD_OK, Command.OK, 1);
	public static Command cmdCancel = new Command (StrConsts.S_CMD_CANCEL, Command.CANCEL, 1);

	public OptionsMenu (Menu AMenu) {
		midlet = AMenu;
		loadOptions();	
	}

	public void show(Display display) {
		form = new Form (StrConsts.S_OPTIONS);
		groupSize = new ChoiceGroup (StrConsts.S_SIZE, ChoiceGroup.EXCLUSIVE, GROUP_SIZE, null);
		groupSize.setSelectedIndex (size, true);
		groupLang = new ChoiceGroup (StrConsts.S_LANGUAGE, ChoiceGroup.EXCLUSIVE, GROUP_LANG, null);
		groupLang.setSelectedIndex (lang, true);
		String[] q_str = new String[6];
		for (int i = 2; i <= 7; i++){
			q_str[i-2] = Integer.toString(i);
		}
		groupQ = new ChoiceGroup (StrConsts.S_QUANTITY, ChoiceGroup.POPUP, q_str, null);
		groupQ.setSelectedIndex (quantity-2, true);
		form.append(groupSize);
		form.append(groupQ);
		form.append(groupLang);
		form.addCommand(cmdOk);
		form.addCommand(cmdCancel);
		form.setCommandListener (midlet);
		display.setCurrent (form);
	}

	private void loadOptions () {
		try {
			RecordStore store = RecordStore.openRecordStore (OPTIONS_RECORD_NAME, true);
			byte[] data = store.getRecord (1);
			DataInputStream dis = new DataInputStream (new ByteArrayInputStream (data));
			quantity = dis.readInt();
			size = dis.readInt();
			lang = dis.readInt();
			dis.close();
			store.closeRecordStore();
		} catch (Exception e) {
			System.out.println ("Can't load options");
			setDefaultOptions();
		}
	}

	private void saveOptions () {
		try {
			RecordStore store = RecordStore.openRecordStore (OPTIONS_RECORD_NAME, true);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream (baos);
			dos.writeInt (quantity);
			dos.writeInt (size);
			dos.writeInt (lang);
			dos.close();
			byte[] data = baos.toByteArray();
			if (store.getNumRecords() > 0) store.setRecord (1, data, 0, data.length);
			else store.addRecord (data, 0, data.length);
			store.closeRecordStore();
			baos.close();
		} catch (Exception e) {
			System.out.println ("Can't save options");
		}
	}

	private void setDefaultOptions () {
		quantity = QUANTITY_DEFAULT;
		size = SIZE_DEFAULT;
		lang = LANG_DEFAULT;
	}

	public void closeForm (Command cmd) {
		if (cmd == cmdOk) {
			int newQ = groupQ.getSelectedIndex()+2;
			int newS = groupSize.getSelectedIndex();
			int newL = groupLang.getSelectedIndex();
			if (newQ != quantity || newS != size || newL != lang) {
				quantity = newQ;
				size = newS;
				lang = newL;
				saveOptions();
			}
		}
	}

	public int getQuantity () { return quantity; }

	public int getSize () { return size; }

	public int getLang () { return lang; }
	
	private static int QUANTITY_DEFAULT = 3;
	private static int SIZE_DEFAULT = 1;
	private static int LANG_DEFAULT = 0;
	private String[] GROUP_SIZE = StrConsts.S_GROUP_SIZE;
	private String[] GROUP_LANG = StrConsts.S_GROUP_LANG;
	private String OPTIONS_RECORD_NAME = "Options";
}

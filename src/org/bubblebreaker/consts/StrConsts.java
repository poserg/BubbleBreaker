package org.bubblebreaker.consts;

public class StrConsts {
	public static String S_OPTIONS;
	public static String S_ABOUT;
	public static String S_HIGH_SCORE;
	public static String[] S_MENU_COMMANDS;
	public static String S_RESUME;
	public static String S_CMD_BACK;
	public static String S_CMD_OK = "Ok";
	public static String S_PRESENT;
	public static String S_INPUT_NAME;
	public static String S_CMD_MENU;
	public static String S_CMD_QUIT;
	public static String S_SCORE;
	public static String S_SIZE;
	public static String S_LANGUAGE;
	public static String S_QUANTITY;
	public static String[] S_GROUP_SIZE;
	public static String[] S_GROUP_LANG = {"English", "Русский"};
	public static String S_ABOUT_STR;
	public static String S_GAME_OVER;
	public static String S_CMD_CANCEL;

	public static void setLanguage(int index) {
		if (index == 0) {
			S_OPTIONS = "Options";
			S_ABOUT = "About";
			S_HIGH_SCORE = "High Scores";
			S_CMD_QUIT = "Quit";
			S_MENU_COMMANDS = new String[] {
				"New game",
				S_OPTIONS,
				S_HIGH_SCORE,
				S_ABOUT,
				S_CMD_QUIT};
			S_RESUME = "Resume";
			S_CMD_BACK = "Back";
			S_PRESENT = "You have a high score.\nPlease enter your name ";
			S_INPUT_NAME = "your name is ";
			S_CMD_MENU = "Menu";
			S_SCORE = "Score: ";
			S_SIZE = "Size";
			S_LANGUAGE = "Language";
			S_QUANTITY = "Ball's quantity:";
			S_GROUP_SIZE = new String[] {
				"small",
				"medium",
				"large"};
			S_ABOUT_STR = "Game Bubble Breaker!\nAuthor: Sergey Popov\ne-mail: poserg@gmail.com";
			S_GAME_OVER = "GAME OVER\nYOUR SCORE :";
			S_CMD_CANCEL = "Cancel";
		} else {
			S_OPTIONS = "Настройки";
			S_ABOUT = "О программе";
			S_HIGH_SCORE = "Рекорды";
			S_CMD_QUIT = "Выйти";
			S_MENU_COMMANDS = new String[] {
				"Новая",
				S_OPTIONS,
				S_HIGH_SCORE,
				S_ABOUT,
				S_CMD_QUIT};
			S_RESUME = "Продолжить";
			S_CMD_BACK = "Назад";
			S_PRESENT = "Ваш счет один из лучших.\nПожалуйста, введите свое имя ";
			S_INPUT_NAME = "ваше имя ";
			S_CMD_MENU = "Меню";
			S_SCORE = "Счет: ";
			S_SIZE = "Размер";
			S_LANGUAGE = "Язык";
			S_QUANTITY = "Количество шаров:";
			S_GROUP_SIZE = new String[] {
				"маленькие",
				"средние",
				"большие"};
			S_ABOUT_STR = "Игра Bubble Breaker!\nАвтор: Сергей Попов\ne-mail: poserg@gmail.com";
			S_GAME_OVER = "ИГРА ОКОНЧЕНА\nВАШ СЧЕТ :";
			S_CMD_CANCEL = "Отмена";
		}
	}
}


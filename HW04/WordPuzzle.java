package il.ac.tau.cs.sw1.ex4;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

public class WordPuzzle {
	public static final char HIDDEN_CHAR = '_';
	public static final int MAX_VOCABULARY_SIZE = 3000;

	private static int array_count = 0;

	
	/*
	 * @pre: template is legal for word
	 */
	public static char[] createPuzzleFromTemplate(String word, boolean[] template) { // Q - 1
		int array_length = word.length();
		char[] puzzle = new char[array_length];
		for (int i = 0; i < array_length; i++){
			if (template[i]) puzzle[i] = HIDDEN_CHAR;
			else puzzle[i] = word.charAt(i);
		}
		return puzzle;
	}

	public static boolean checkLegalTemplate(String word, boolean[] template) { // Q - 2
		if (word.length()!=template.length) return false;

		boolean checkT = false, checkF = false;
		for (int i = 0; i < template.length; i++){
			if (template[i]) checkT = true;
			else checkF = true;
			if (checkT && checkF) break;
		}
		if (!checkT || !checkF) return false;

		char current_letter;
		boolean is_hidden;
		for (int i = 0; i < word.length() - 1; i++) {
			current_letter = word.charAt(i);
			is_hidden = template[i];
			for (int j = i + 1; j < word.length(); j++) {
				if (current_letter == word.charAt(j)) {
					if (is_hidden != template[j]) return false;
				}
			}
		}

		return true;
	}

	/*
	 * @pre: 0 < k < word.length(), word.length() <= 10
	 */
	private static String padBinaryTemplate(String template, int n){
		for (int i = template.length(); i < n; i++){
			template =  template + "0";
		}
		return template;
	}

	private static int factorial(int n) {
		if (n == 0) return 1;
		if (n == 1) return 1;
		return n * factorial(n-1);
	}

	private static int generateChoose(int n, int k) {
		return (factorial(n) / (factorial(k)*factorial(n-k)));
	}

	private static boolean countTrue(String bin, int k) {
		int count = 0;
		for (int i = 0; i < bin.length(); i++) {
			if (bin.charAt(i) == '1') count++;
		}
		if (count == k) return true;
		return false;
	}

	private static void buildLegalTemplate(String[] string_array, int k, int length, String binary_template, int n){

		if (k == 0) {
			if (countTrue(binary_template, length)) {
				string_array[array_count] = padBinaryTemplate(binary_template, n);
				array_count++;
			}
		}
		else {
			if (binary_template.length() < n) {
				buildLegalTemplate(string_array, k - 1, length, binary_template + "1", n);
				buildLegalTemplate(string_array, k, length, binary_template + "0", n);
			}
		}
	}

	public static boolean[][] getAllLegalTemplates(String word, int k){  // Q - 3
		int word_length = word.length();
		int array_size = generateChoose(word_length, k);

		String[] binary_strings = new String[array_size];
		boolean[][] rtrn_array = new boolean[array_size][word_length];

		buildLegalTemplate(binary_strings, k, k, "", word_length);

		String current_binary_str;
		for (int i = 0; i < array_size; i++) {
			current_binary_str = binary_strings[i];

			for (int j = 0; j < word_length; j++) {
				if (current_binary_str.charAt(j) == '1') rtrn_array[i][j] = true;
				else rtrn_array[i][j] = false;
			}
		}

		boolean[] check_templates = new boolean[array_size];
		int count_legal = 0;
		for (int i = 0; i < array_size; i++){
			if (checkLegalTemplate(word, rtrn_array[i])) {
				count_legal++;
				check_templates[i] = true;
			}
		}

		boolean[][] rtrn_array_final = new boolean[count_legal][word_length];
		int i = 0;
		for (int j = array_size - 1; j >= 0; j--) {
			if (check_templates[j]){
				rtrn_array_final[i] = rtrn_array[j];
				i++;
			}
		}

		return rtrn_array_final;
	}
	
	
	/*
	 * @pre: puzzle is a legal puzzle constructed from word, guess is in [a...z]
	 */
	public static int applyGuess(char guess, String word, char[] puzzle) { // Q - 4
		int count = 0;
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == guess) {
				count++;
				puzzle[i] = guess;
			}
		}
		return count;
	}
	

	/*
	 * @pre: puzzle is a legal puzzle constructed from word
	 * @pre: puzzle contains at least one hidden character. 
	 * @pre: there are at least 2 letters that don't appear in word, and the user didn't guess
	 */
	public static char[] getHint(String word, char[] puzzle, boolean[] already_guessed) { // Q - 5
		char[] rtrn_letters = new char[2];
		Random rand = new Random();
		int rand_true = rand.nextInt(word.length());
		int rand_false = rand.nextInt(26);
		int count_true = 0, count_false = 0;

		int chosen_i_t = 0;
		while (count_true <= rand_true) {
			chosen_i_t = 0;
			for (int i = 0; i < word.length(); i++) {
				if (count_true > rand_true) break;
				if (puzzle[i] == HIDDEN_CHAR) {
					chosen_i_t = i;
					count_true++;
				}
			}
		}

		int chosen_i_f = 0;
		while (count_false <= rand_false) {
			chosen_i_f = 0;
			for (int i = 0; i < 26; i++) {
				if (count_false > rand_false) break;
				if (!already_guessed[i] && word.indexOf((char)(97 + i)) == -1) {
					chosen_i_f = i;
					count_false++;
				}
			}
		}

		char f_letter = (char) (chosen_i_f + 97);
		char t_letter = word.charAt(chosen_i_t);
		if (t_letter > f_letter) {
			rtrn_letters[0] = f_letter;
			rtrn_letters[1] = t_letter;
		}
		else {
			rtrn_letters[0] = t_letter;
			rtrn_letters[1] = f_letter;
		}

		return rtrn_letters;
	}

	private static char[] createPuzzle(String word, boolean[] template) {
		char[] puzzle = new char[word.length()];
		for (int i = 0; i < word.length(); i++) {
			if (template[i]) puzzle[i] = HIDDEN_CHAR;
			else puzzle[i] = word.charAt(i);
		}

		return puzzle;
	}

	public static char[] mainTemplateSettings(String word, Scanner inputScanner) { // Q - 6
		Random rand = new Random();
		char[] rtrn_puzzle = new char[word.length()];
		boolean template_ready = false;

		printSettingsMessage();

		while (!template_ready) {

			printSelectTemplate();
			int user_template_choice = inputScanner.nextInt();

			if (user_template_choice == 1) {

				printSelectNumberOfHiddenChars();
				int user_num_of_letters = inputScanner.nextInt();
				boolean[][] template_array = getAllLegalTemplates(word, user_num_of_letters);

				if (template_array.length != 0) {
					int template_i = rand.nextInt(template_array.length);
					boolean[] rand_template = template_array[template_i];
					rtrn_puzzle = createPuzzle(word, rand_template);
					template_ready = true;

				}
				else {
					printWrongTemplateParameters();
				}

			}
			else {
				printEnterPuzzleTemplate();
				String user_custom_template = inputScanner.next();

				if (user_custom_template.length() == (word.length() * 2 - 1)) {
					boolean[] user_template = new boolean[word.length()];

					for (int i = 0; i < user_custom_template.length(); i++) {
						if (i % 2 == 0) {
							if (user_custom_template.charAt(i) == 'X') user_template[i/2] = false;
							else user_template[i/2] = true;
						}
					}

					if (checkLegalTemplate(word, user_template)) {
						template_ready = true;
						rtrn_puzzle = createPuzzle(word, user_template);
					}
					else printWrongTemplateParameters();
				}
				else printWrongTemplateParameters();
			}
		}
		return rtrn_puzzle;
	}

	private static int countHidden(char[] puzzle) {
		int count = 0;
		for (int i = 0; i < puzzle.length; i++) {
			if (puzzle[i] == '_') count++;
		}
		return count;
	}

	private static boolean checkGuess(char[] puzzle, String word, char guess) {
		boolean correct = false;
		for (int i = 0; i < word.length(); i++){
			if (word.charAt(i) == guess) {
				correct = true;
				puzzle[i] = guess;
			}
		}

		return correct;
	}

	private static void addDefaultGuesses(char[] puzzle, boolean[] allready_gussed){
		for (int i = 0; i < puzzle.length; i++){
			if (puzzle[i] != '_') allready_gussed[((int) puzzle[i] - 97)] = true;
		}
	}

	private static boolean checkWin(char[] puzzle){
		for (int i = 0; i < puzzle.length; i++){
			if (puzzle[i] == '_') return false;
		}
		return true;
	}

	public static void mainGame(String word, char[] puzzle, Scanner inputScanner){ // Q - 7
		printGameStageMessage();

		boolean[] allready_guessed = new boolean[26];
		addDefaultGuesses(puzzle, allready_guessed);
		boolean win = false;

		int number_of_guesses = countHidden(puzzle) + 3;
		int current_guess = 0;

		while (current_guess < number_of_guesses) {

			printPuzzle(puzzle);
			printEnterYourGuessMessage();
			char user_guess = inputScanner.next().charAt(0);

			if (user_guess != 'H') {
				current_guess++;
				boolean new_guess = allready_guessed[((int) user_guess) - 97];
				allready_guessed[((int) user_guess) - 97] = true;

				if (!new_guess && checkGuess(puzzle, word, user_guess)){
					if (checkWin(puzzle)){
						printWinMessage();
						win = true;
					}
					else {
						printCorrectGuess(number_of_guesses - current_guess);
					}
				}
				else {
					printWrongGuess(number_of_guesses - current_guess);
				}
			}

			else {
				char[] hint_letters = getHint(word, puzzle, allready_guessed);
				printHint(hint_letters);
			}
			if (win) break;
		}

		if (!win) printGameOver();

	}
				
				


/*************************************************************/
/********************* Don't change this ********************/
/*************************************************************/

	public static void main(String[] args) throws Exception {

		if (args.length < 1){
			throw new Exception("You must specify one argument to this program");
		}
		String wordForPuzzle = args[0].toLowerCase();
		if (wordForPuzzle.length() > 10){
			throw new Exception("The word should not contain more than 10 characters");
		}
		Scanner inputScanner = new Scanner(System.in);
		char[] puzzle = mainTemplateSettings(wordForPuzzle, inputScanner);
		mainGame(wordForPuzzle, puzzle, inputScanner);
		inputScanner.close();
	}


	public static void printSettingsMessage() {
		System.out.println("--- Settings stage ---");
	}

	public static void printEnterWord() {
		System.out.println("Enter word:");
	}
	
	public static void printSelectNumberOfHiddenChars(){
		System.out.println("Enter number of hidden characters:");
	}
	public static void printSelectTemplate() {
		System.out.println("Choose a (1) random or (2) manual template:");
	}
	
	public static void printWrongTemplateParameters() {
		System.out.println("Cannot generate puzzle, try again.");
	}
	
	public static void printEnterPuzzleTemplate() {
		System.out.println("Enter your puzzle template:");
	}


	public static void printPuzzle(char[] puzzle) {
		System.out.println(puzzle);
	}


	public static void printGameStageMessage() {
		System.out.println("--- Game stage ---");
	}

	public static void printEnterYourGuessMessage() {
		System.out.println("Enter your guess:");
	}

	public static void printHint(char[] hist){
		System.out.println(String.format("Here's a hint for you: choose either %s or %s.", hist[0] ,hist[1]));

	}
	public static void printCorrectGuess(int attemptsNum) {
		System.out.println("Correct Guess, " + attemptsNum + " guesses left.");
	}

	public static void printWrongGuess(int attemptsNum) {
		System.out.println("Wrong Guess, " + attemptsNum + " guesses left.");
	}

	public static void printWinMessage() {
		System.out.println("Congratulations! You solved the puzzle!");
	}

	public static void printGameOver() {
		System.out.println("Game over!");
	}

}

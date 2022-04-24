package il.ac.tau.cs.sw1.ex5;


import java.io.*;

public class BigramModel {
	public static final int MAX_VOCABULARY_SIZE = 14500;
	public static final String VOC_FILE_SUFFIX = ".voc";
	public static final String COUNTS_FILE_SUFFIX = ".counts";
	public static final String SOME_NUM = "some_num";
	public static final int ELEMENT_NOT_FOUND = -1;


	
	String[] mVocabulary;
	int[][] mBigramCounts;
	
	// DO NOT CHANGE THIS !!! 
	public void initModel(String fileName) throws IOException{
		mVocabulary = buildVocabularyIndex(fileName);
		mBigramCounts = buildCountsArray(fileName, mVocabulary);
		
	}

	// Sub-functions for buildVocabularyIndex

	private boolean checkIfStringIsNumber(char[] char_array){
		for(int i = 0; i < char_array.length; i++){
			if (!Character.isDigit(char_array[i])) return false;
		}
		return true;
	}

	private boolean checkIfStringHasLetter(char[] char_array){
		int ascii_value;
		for (int i = 0; i < char_array.length; i++){
			ascii_value = char_array[i];
			if ((ascii_value >= 65 && ascii_value <= 90) || (ascii_value >= 97 && ascii_value <= 122)) return true;
		}
		return false;
	}

	private boolean checkLegalWord(String word){
		char[] char_array = new char[word.length()];
		for (int i = 0; i < word.length(); i++) char_array[i] = word.charAt(i);

		if (checkIfStringIsNumber(char_array)) return true;
		if (checkIfStringHasLetter(char_array)) return true;

		return false;
	}

	private boolean checkNewWord(StringBuilder rtrn_str, String word){
		String str_so_far = rtrn_str.toString();
		if (str_so_far.contains(word)) return false;
		return true;
	}

	private String translateWord(String word){
		if (checkIfStringIsNumber(word.toCharArray())) return SOME_NUM;
		else return word.toLowerCase();
	}

	private void addLineToString(StringBuilder rtrn_str, String line){
		String[] line_array = line.split("\\s");
		for (int i = 0; i < line_array.length; i++){
			if (checkLegalWord(line_array[i])){
				String translated_word = translateWord(line_array[i]);
				if (checkNewWord(rtrn_str, translated_word)){
					rtrn_str.append(" " + translated_word);
				}
			}
		}
	}
	/*
	 * @post: mVocabulary = prev(mVocabulary)
	 * @post: mBigramCounts = prev(mBigramCounts)
	 */
	public String[] buildVocabularyIndex(String fileName) throws IOException{ // Q 1
		String[] rtrn_vocabulary;

		StringBuilder rtrn_string = new StringBuilder();

		File fromFile = new File(fileName);
		BufferedReader bufferedReader = new BufferedReader(new FileReader(fromFile));

		String line = "";
		while ((line = bufferedReader.readLine()) != null){
			addLineToString(rtrn_string, line);
		}

		String final_string = rtrn_string.toString().trim();
		rtrn_vocabulary = final_string.split(" ");

		if (rtrn_vocabulary.length > MAX_VOCABULARY_SIZE){
			String[] sub_array = new String[MAX_VOCABULARY_SIZE];
			for (int i = 0; i < MAX_VOCABULARY_SIZE; i++){
				sub_array[i] = rtrn_vocabulary[i];
			}
			return sub_array;
		}
		bufferedReader.close();

		return rtrn_vocabulary;
	}

	/*
	 * @post: mVocabulary = prev(mVocabulary)
	 * @post: mBigramCounts = prev(mBigramCounts)
	 */

	// Sub functions for buildCountsArray.

	private void updateArrayByLine(int[][] counts_array, String line, String[] voc){
		String[] word_array = line.split("\\s");
		String[] legal_arrayT = createTranslatedArray(word_array); // Creates an array of legal translated words.

		String current_word, next_word;
		int currentI;
		int currentJ;

		for (int i = 0; i < legal_arrayT.length - 1; i++){
			current_word = legal_arrayT[i];
			next_word = legal_arrayT[i + 1];
			if (!current_word.equals("0") && !next_word.equals("0")) {

				currentI = findWordIndex(voc, current_word);
				currentJ = findWordIndex(voc, next_word);

				if (currentJ != -1 && currentI != -1) { // Should always be True!
					counts_array[currentI][currentJ]++;
				}
			}
		}
	}

	private int findWordIndex(String[] voc, String word){
		for (int i = 0; i < voc.length; i++) {
			if (word.equals(voc[i])) return i;
		}
		return -1;
	}

	private String[] createTranslatedArray(String[] line_array){
		String[] t_array = new String[line_array.length];

		for (int i = 0; i < line_array.length; i++) {
			if (checkLegalWord(line_array[i])){
				t_array[i] = translateWord(line_array[i]);
			}
			else t_array[i] = "0"; // A general sign for illegal words.
		}

		return t_array;
	}

	public int[][] buildCountsArray(String fileName, String[] vocabulary) throws IOException{ // Q - 2
		int[][] counts_array = new int[vocabulary.length][vocabulary.length];

		File fromFile = new File(fileName);
		BufferedReader bufferedReader = new BufferedReader(new FileReader(fromFile));

		String line = "";
		while ((line = bufferedReader.readLine()) != null){
			updateArrayByLine(counts_array, line, vocabulary);
		}

		bufferedReader.close();
		return counts_array;
	}

	/*
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: fileName is a legal file path
	 */
	public void saveModel(String fileName) throws IOException{ // Q-3
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName + VOC_FILE_SUFFIX));

		bufferedWriter.write(this.mVocabulary.length + " words" + System.lineSeparator());

		for (int i = 0; i < this.mVocabulary.length; i++) {
			bufferedWriter.write(i + "," + this.mVocabulary[i] + System.lineSeparator());
		}

		bufferedWriter.close();

		BufferedWriter bufferedWriterCounts = new BufferedWriter(new FileWriter(fileName + COUNTS_FILE_SUFFIX));

		for (int i = 0; i < this.mBigramCounts.length; i++) {
			for (int j = 0; j < this.mBigramCounts[i].length; j++) {
				if (this.mBigramCounts[i][j] != 0) bufferedWriterCounts.write(i + "," + j + ":" + this.mBigramCounts[i][j] + System.lineSeparator());
			}
		}

		bufferedWriterCounts.close();
	}
	
	
	
	/*
	 * @pre: fileName is a legal file path
	 */

	private void loadLineVoc(String line, int index){
		String word = line.split(",")[1];
		this.mVocabulary[index] = word;
	}

	private void loadLineCounts(String line){
		String coordinates = line.split(":")[0];
		int i = Integer.parseInt(coordinates.split(",")[0]);
		int j = Integer.parseInt(coordinates.split(",")[1]);
		int value = Integer.parseInt(line.split(":")[1]);

		this.mBigramCounts[i][j] = value;
	}

	public void loadModel(String fileName) throws IOException{ // Q - 4
		BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName + VOC_FILE_SUFFIX));

		String first_line = bufferedReader.readLine();
		int array_size = Integer.parseInt(first_line.split(" ")[0]);
		this.mVocabulary = new String[array_size];

		String cur_line;
		for (int i = 0; i < array_size; i++) {
			cur_line = bufferedReader.readLine();
			loadLineVoc(cur_line, i);
		}

		bufferedReader.close();

		BufferedReader bufferedReaderCounts = new BufferedReader(new FileReader(fileName + COUNTS_FILE_SUFFIX));

		while ((cur_line = bufferedReaderCounts.readLine()) != null){
			loadLineCounts(cur_line);
		}

		bufferedReaderCounts.close();
	}

	
	
	/*
	 * @pre: word is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: word is in lowercase
	 * @post: $ret = -1 if word is not in vocabulary, otherwise $ret = the index of word in vocabulary
	 */
	public int getWordIndex(String word){  // Q - 5
		for (int i = 0; i < this.mVocabulary.length; i++) {
			if (this.mVocabulary[i].equals(word)) return i;
		}
		return -1;
	}
	
	
	
	/*
	 * @pre: word1, word2 are in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post: $ret = the count for the bigram <word1, word2>. if one of the words does not
	 * exist in the vocabulary, $ret = 0
	 */
	public int getBigramCount(String word1, String word2){ //  Q - 6
		int word1_index = getWordIndex(word1);
		int word2_index = getWordIndex(word2);
		if (word1_index == -1 || word2_index == -1) return 0;

		return this.mBigramCounts[word1_index][word2_index];
	}
	
	
	/*
	 * @pre word in lowercase, and is in mVocabulary
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post $ret = the word with the lowest vocabulary index that appears most fequently after word (if a bigram starting with
	 * word was never seen, $ret will be null
	 */
	public String getMostFrequentProceeding(String word){ //  Q - 7
		int word_index = getWordIndex(word);
		int max_word = 0;
		int max_word_index = 0;
		for (int i = 0; i < this.mVocabulary.length; i++) {
			if (this.mBigramCounts[word_index][i] > max_word){
				max_word = this.mBigramCounts[word_index][i];
				max_word_index = i;
			}
		}

		if (max_word == 0) return null;

		return this.mVocabulary[max_word_index];
	}
	
	
	/* @pre: sentence is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: each two words in the sentence are are separated with a single space
	 * @post: if sentence is is probable, according to the model, $ret = true, else, $ret = false
	 */
	public boolean isLegalSentence(String sentence){  //  Q - 8
		String[] word_array = sentence.split("\\s");
		String cur_word, next_word;

		// Special cases:

		if (sentence.equals("")) return true;
		if (word_array.length == 1){
			if (getWordIndex(word_array[0]) != -1) return true;
			else return false;
		}

		// End of special cases.

		for (int i = 0; i < word_array.length - 1; i++) {
			cur_word = word_array[i];
			next_word = word_array[i+1];
			if (getBigramCount(cur_word, next_word) == 0) return false;
		}
		return true;
	}
	
	
	// Sub-functions for calcCosineSim:

	/*
	 * @pre: A.length == B.length
	 */
	private static int calcMultiSum(int[] A, int[] B){
		int sum = 0;
		for (int i = 0; i < A.length; i++) {
			sum += A[i]*B[i];
		}

		return sum;
	}

	private static double calcSqrt(int[] A){
		double sum = 0;
		for (int i = 0; i < A.length; i++) {
			sum += Math.pow(A[i], 2);
		}

		return Math.pow(sum, 0.5);
	}

	private static boolean checkOnlyZeros(int[] A){
		for (int i = 0; i < A.length; i++) {
			if (A[i] != 0) return false;
		}
		return true;
	}


	/*
	 * @pre: arr1.length = arr2.legnth
	 * post if arr1 or arr2 are only filled with zeros, $ret = -1, otherwise calcluates CosineSim
	 */
	public static double calcCosineSim(int[] arr1, int[] arr2){ //  Q - 9
		if (checkOnlyZeros(arr1) || checkOnlyZeros(arr2)) return -1;
		double rtrn;
		rtrn = calcMultiSum(arr1, arr2) / (calcSqrt(arr1) * calcSqrt(arr2));

		return rtrn;
	}


	// Sub functions for getClosestWord.

	private int[] createStatisticsArray(String word){
		int index = getWordIndex(word);
		int[] rtrn_array = new int[this.mVocabulary.length];

		for (int i = 0; i < this.mVocabulary.length; i++) {
			rtrn_array[i] = this.mBigramCounts[index][i];
		}

		return rtrn_array;
	}


	/*
	 * @pre: word is in vocabulary
	 * @pre: the method initModel was called (the language model is initialized), 
	 * @post: $ret = w implies that w is the word with the largest cosineSimilarity(vector for word, vector for w) among all the
	 * other words in vocabulary
	 */
	public String getClosestWord(String word){ //  Q - 10
		// Special case:
		if (this.mVocabulary.length == 1) return this.mVocabulary[0];

		String best_match = "", curr_match;
		double max_cos = 0., curr_cos;
		int index = getWordIndex(word);

		for (int i = 0; i < this.mVocabulary.length; i++) {
			if (i != index){
				curr_match = this.mVocabulary[i];
				curr_cos = calcCosineSim(createStatisticsArray(word), createStatisticsArray(curr_match));

				if (curr_cos > max_cos) {
					best_match = curr_match;
					max_cos = curr_cos;
				}
			}
		}

		return best_match;
	}
	
}

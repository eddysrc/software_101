package il.ac.tau.cs.sw1.ex8.wordsRank;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import il.ac.tau.cs.sw1.ex8.histogram.HashMapHistogram;
import il.ac.tau.cs.sw1.ex8.histogram.HashMapHistogramIterator;
import il.ac.tau.cs.sw1.ex8.histogram.IHistogram;
import il.ac.tau.cs.sw1.ex8.wordsRank.RankedWord.rankType;

import javax.swing.plaf.nimbus.AbstractRegionPainter;

/**************************************
 *  Add your code to this class !!!   *
 **************************************/

public class FileIndex {

	public static final int UNRANKED_CONST = 30;

	private final HashMap<String, HashMapHistogram<String>> files_to_histogram_map = new HashMap<>();
	private final HashMap<String, HashMap<String, Integer>> word_to_file_to_rank = new HashMap<>();
	private final HashMap<String, RankedWord> word_to_ranked_word = new HashMap<>();
	private final HashMap<String, File> string_to_file = new HashMap<>();

	/*
	 * @pre: the directory is no empty, and contains only readable text files
	 */
  	public void indexDirectory(String folderPath) {
		//This code iterates over all the files in the folder. add your code wherever is needed

		File folder = new File(folderPath);
		File[] listFiles = folder.listFiles();
		HashMapHistogram<String> currentHistogram;
		for (File file : listFiles) {
			this.string_to_file.put(file.getName(), file);
			// for every file in the folder
			if (file.isFile()) {
				try {
					currentHistogram = buildHistogram(file);
					this.files_to_histogram_map.put(file.getName(), currentHistogram);
					updateWordToFileToRank(file.getName(), currentHistogram);
				}
				catch (IOException e){
					e.printStackTrace();
				}
			}
		}
		for (String word: this.word_to_file_to_rank.keySet()){
			updateWordToRank(word);
		}
	}

	private RankedWord buildRank(String word, HashMap<String, Integer> word_rank_in_files){
  		RankedWord rank = new RankedWord(word, word_rank_in_files);
  		return rank;
	}

	private void updateWordToRank(String word){
  		this.word_to_ranked_word.put(word, buildRank(word, this.word_to_file_to_rank.get(word)));
	}

	private void updateWordToFileToRank(String filename, HashMapHistogram<String> hash_map){
  		try {
			for (String word : hash_map.getItemsSet()) {
				if (this.word_to_file_to_rank.containsKey(word)) {
					this.word_to_file_to_rank.get(word).put(filename, getRankForWordInFile(filename, word));
				} else {
					HashMap<String, Integer> map = new HashMap<>();
					map.put(filename, getRankForWordInFile(filename, word));
					this.word_to_file_to_rank.put(word, map);
				}
			}
		}
  		catch (Exception e){
  			e.printStackTrace();
		}
	}

	private HashMapHistogram<String> buildHistogram(File filename) throws IOException{
  		HashMapHistogram<String> map = new HashMapHistogram<>();
		try { // Building the hashmapHistogram.
			List<String> file_content = FileUtils.readAllTokens(filename);
			for (String current: file_content){
				map.addItem(current);
			}
		}
		catch (IOException e) {
			System.out.println("Filename " + filename + " not found.");
		}
		return map;
	}
	
  	/*
	 * @pre: the index is initialized
	 * @pre filename is a name of a valid file
	 * @pre word is not null
	 */
	public int getCountInFile(String filename, String word) throws FileIndexException {
		word = word.toLowerCase();
		int count = 0;
		try {
			List<String> file_content = FileUtils.readAllTokens(this.string_to_file.get(filename));
			for (String current: file_content){
				if (word.equals(current)) count++;
			}
		}
		catch (IOException e) {
			throw new FileIndexException("Filename " + filename + " not found!");
		}
		return count;
	}
	
	/*
	 * @pre: the index is initialized
	 * @pre filename is a name of a valid file
	 * @pre word is not null
	 */
	public int getRankForWordInFile(String filename, String word) throws FileIndexException{
		HashMapHistogram map;
		word = word.toLowerCase();
		int rtrn = 1;
		try { // Getting the histogram.
			map = this.files_to_histogram_map.get(filename);
			Iterator<String> iter = map.iterator(); // Iterating over the hashmap.
			String current = null;
			while (iter.hasNext()){
				current = iter.next();
				if (current.equals(word)) return rtrn; // If we found the word in the hashmap we will return its rank.
				rtrn += 1;
			}

			return rtrn + UNRANKED_CONST;
		}
		catch (Exception e) {
			throw new FileIndexException("Filename " + filename + " not found!");
		}
	}
	
	/*
	 * @pre: the index is initialized
	 * @pre word is not null
	 */
	public int getAverageRankForWord(String word){
		return this.word_to_ranked_word.get(word).getRankByType(rankType.average);
	}

	private ArrayList<RankedWord> buildArrayList(int k, rankType t){
		ArrayList<RankedWord> rank_word_list = new ArrayList<>();
		for (String word: this.word_to_ranked_word.keySet()){
			if (this.word_to_ranked_word.get(word).getRankByType(t) < k){
				rank_word_list.add(this.word_to_ranked_word.get(word));
			}
		}
		return rank_word_list;
	}

	private ArrayList<String> buildSortedList(ArrayList<RankedWord> rk_list, rankType t){
		RankedWordComparator sort_comp = new RankedWordComparator(t);
		rk_list.sort(sort_comp);
		ArrayList<String> rtrn = new ArrayList<>();
		for (RankedWord rk: rk_list){
			rtrn.add(rk.getWord());
		}
		return rtrn;
	}
	
	public List<String> getWordsWithAverageRankSmallerThanK(int k){
		ArrayList<RankedWord> rk_list = buildArrayList(k, rankType.average);
		return buildSortedList(rk_list, rankType.average);
	}
	
	public List<String> getWordsWithMinRankSmallerThanK(int k){
		ArrayList<RankedWord> rk_list = buildArrayList(k, rankType.min);
		return buildSortedList(rk_list, rankType.min);
	}
	
	public List<String> getWordsWithMaxRankSmallerThanK(int k){
		ArrayList<RankedWord> rk_list = buildArrayList(k, rankType.max);
		return buildSortedList(rk_list, rankType.max);
	}

}

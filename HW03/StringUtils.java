package il.ac.tau.cs.sw1.hw3;

import java.lang.reflect.Array;
import java.util.Arrays;

public class StringUtils {

	public static String findSortedSequence(String str) {

		if (str.equals("")) return "";
		String[] words_array = str.split(" ");
		if (words_array.length == 1) return words_array[0];

		int current_start = 0, rtrn_start = 0, current_end = 0, rtrn_end = 0, word_count = 0, max_count = 0;

		for (int i = 0; i < words_array.length - 1; i++){
			String cur_word = words_array[i];
			String next_word = words_array[i+1];

			if (cur_word.compareTo(next_word) <= 0){
				current_end += 1;
				word_count += 1;
			}
			else {
				word_count = 0;
				current_start = i + 1;
				current_end = i + 1;
			}

			if (word_count >= max_count){
				rtrn_start = current_start;
				rtrn_end = current_end;
				max_count = word_count;
			}
		}
		int rtrn_length = rtrn_end - rtrn_start + 1;
		String[] rtrn_array = new String[rtrn_length];

		for (int j = 0; j < rtrn_length; j++){
			rtrn_array[j] = words_array[j + rtrn_start];
		}
		String rtrn_str = String.join(" ", rtrn_array);

		return rtrn_str;

	}

	public static boolean isAnagram(String a, String b) {
		String[] words1 = a.split(" ");
		String[] words2 = b.split(" ");

		String new1 = String.join("", words1);
		String new2 = String.join("", words2);

		String[] a_without_spaces = new1.split("");
		String[] b_without_spaces = new2.split("");

		if (a_without_spaces.length != b_without_spaces.length) return false;

		Arrays.sort(a_without_spaces);
		Arrays.sort(b_without_spaces);

		for (int i = 0; i < a_without_spaces.length; i++){
			if (!a_without_spaces[i].equals(b_without_spaces[i])) return false;
		}
		return true;
	}
	
	public static boolean isEditDistanceOne(String a, String b){
		int length_a = a.length();
		int length_b = b.length();
		int distance = Math.abs(length_a - length_b);
		if (distance > 1) return false;

		int count = 0;

		if (distance == 0){
			for (int i = 0; i < length_a; i++){
				if (a.charAt(i) != b.charAt(i)) count++;
			}
			if (count > 1) return false;
			return true;
		}

		if (length_b < length_a){
			String temp = a;
			a = b;
			b = temp;
		}

		length_a = a.length();

		int a_iter, b_iter = 0;
		count = 0;
		for (a_iter = 0; a_iter < length_a; a_iter++){
			if (a.charAt(a_iter) == b.charAt(b_iter)) b_iter++;
			else {
				if (count == 1) return false;
				else {
					b_iter++;
					count+= 1;
					if (a.charAt(a_iter) != b.charAt(b_iter)) return false;
					b_iter++;
				}
			}
		}

		return true;
	}
}

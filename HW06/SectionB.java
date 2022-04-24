package il.ac.tau.cs.sw1.hw6;

import java.util.Arrays;

public class SectionB {
	
	/*
	* @post $ret == true iff exists i such that array[i] == value
	*/
	public static boolean contains(int[] array, int value) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == value) return true;
		}
		return false;
	}
	
	// there is intentionally no @post condition here 
	/*
	* @pre array != null
	* @pre array.length > 2
	* @pre Arrays.equals(array, Arrays.sort(array))
	*/
	public static int unknown(int[] array) {
		if (array != null){
			int[] old_array = Arrays.copyOf(array, array.length);
			if (array.length > 2){
				Arrays.sort(array);
				if (Arrays.equals(old_array, array)) return 1;
			}
		}
		return 0;
	}
	/*
	* @pre Arrays.equals(array, Arrays.sort(array))
	* @pre array.length >= 1
	* @post for all i array[i] <= $ret
	*/
	public static int max(int[] array) {
		int max;
		if (array.length >= 1){
			max = array[0];
			for (int i = 1; i < array.length; i++) {
				if (array[i] > max){
					max = array[i];
				}
			}
			return max;
		}
		return 0; //Should never get here.
	}
	
	/*
	* @pre array.length >=1
	* @post for all i array[i] >= $ret
	* @post Arrays.equals(array, prev(array))
	*/
	public static int min(int[] array) {
		int min;
		if (array.length >= 1){
			min = array[0];
			for (int i = 1; i < array.length; i++) {
				if(min > array[i]){
					min = array[i];
				}
			}
			return min;
		}
		return 0; //should never get here.
	}
	
	/*
	* @pre word.length() >=1
	* @post for all i : $ret.charAt(i) == word.charAt(word.length() - i - 1)

	*/
	public static String reverse(String word) 
	{
		StringBuilder build_reverse = new StringBuilder();
		for (int i = word.length() - 1; i >= 0 ; i--) {
			build_reverse.append(word.charAt(i));
		}
		return build_reverse.toString();
	}
	
	/*
	* @pre array != null
	* @pre array.length > 2
	* @pre Arrays.equals(array, Arrays.sort(array))
	* @pre exist i,j such that: array[i] != array[j]
	* @post !Arrays.equals($ret, Arrays.sort($ret))
	* @post for any x: contains(prev(array),x) == true iff contains($ret, x) == true
	*/
	public static int[] guess(int[] array) { 
		// I need to find two different values in the array and switch their places.
		int first_value = array[0];
		int second_value = array[0];
		int second_index = 0;
		for (int i = 0; i < array.length; i++) {
			if (first_value != array[i]){
				second_value = array[i];
				second_index = i;
				break;
			}
		}
		array[0] = second_value;
		array[second_index] = first_value;

		return array;
	}


}

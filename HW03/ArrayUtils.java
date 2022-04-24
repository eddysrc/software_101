package il.ac.tau.cs.sw1.hw3;

import java.util.Arrays;

public class ArrayUtils {

	private static boolean checkTranspose(int[][] m) {
		if (m.length == 0 || m[0].length == 0){
			return false;
		}
		return true;
	}

	private static void printMat(int[][] m){
		int N = m.length;
		for(int i=0;i < N; i++){
			System.out.println(Arrays.toString(m[i]));
		}
	}

	public static int[][] transposeMatrix(int[][] m) {
		if (!checkTranspose(m)) return m;
		int[][] rtrn_mat = new int[m[0].length][m.length];

		for (int i = 0; i < rtrn_mat.length; i++){
			for (int j = 0; j < rtrn_mat[0].length; j++){
				rtrn_mat[i][j] = m[j][i];
			}
		}

		return rtrn_mat;

	}

	private static boolean checkCycle(char direction){
		if (direction != 'R' && direction != 'L') return false;
		return true;
	}

	private static char negative_move(char direction){
		if (direction == 'R') return 'L';
		return 'R';
	}

	public static int[] shiftArrayCyclic(int[] array, int move, char direction) {

		int[] shift_array = new int[array.length];

		if (!checkCycle(direction) || move == 0) return array;
		if (move < 0){
			direction = negative_move(direction);
			move = -move;
		}
		move = move % array.length;
		if (direction == 'L') move = -move;

		int new_index;

		for (int i = 0; i < array.length; i++) {
			new_index = i + move;
			if (new_index < 0) {
				new_index = array.length + new_index;
			}
			if (new_index > array.length - 1) {
				new_index -= array.length;
			}
			shift_array[new_index] = array[i];
		}

		for (int i = 0; i < array.length; i++) array[i] = shift_array[i];

		return array;

	}

	private static void change_signs(int[] array,int start) {
		for (int i = start + 1; i < array.length; i+=2){
			array[i] = -array[i];
		}
	}

	private static int max_sum(int[] array, int start) {
		int max = array[start];
		int sum = max;
		for (int i = start; i < array.length - 1; i++) {
			sum = sum + array[i+1];
			max = Math.max(max, sum);
		}
		return max;
	}

	public static int alternateSum(int[] array) {
		int alt_max = 0;
		for (int i = 0; i < array.length;i++){
			change_signs(array, i);
			alt_max = Math.max(max_sum(array, i), alt_max);
			change_signs(array, i);
		}
		return alt_max;

	}

	public static int findPath(int[][] m, int i, int j, int k) {

		if (k == 0) return 0;
		if (m[i][j] == 1) return 1;

		int rtrn = 0;
		for (int iter = 0; iter < m.length; iter++) {
			if (m[i][iter] == 1) {
				m[i][iter] = 0;
				if (i != iter) rtrn = findPath(m, iter, j, k - 1);
			}
			if (rtrn == 1) break;
		}
		return rtrn;
	}
}

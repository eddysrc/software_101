package il.ac.tau.cs.sw1.ex2;
import java.util.Arrays;

public class Assignment02Q05 {

	public static void main(String[] args) {
		// do not change this part below
		int N = Integer.parseInt(args[0]);
		int[][] matrix = new int[N][N]; // the input matrix to be
		for(int i=0;i < N; i++){
			for(int j=0; j < N; j++){
				matrix[i][j] = Integer.parseInt(args[1+(i*N)+j]); // the value at [i][j] is the i*N+j item in args (without considering args[0])
			}
		}
		for(int i=0;i < N; i++)
			System.out.println(Arrays.toString(matrix[i]));
		System.out.println("");
		int[][] rotatedMatrix; // the rotated matrix
		
		// your code goes here below
		rotatedMatrix = new int[N][N];
		for (int i = 0; i < N; i++){
			for (int j = 0; j < N; j++){
				rotatedMatrix[j][N-i-1] = matrix[i][j];
			}
		}

		/* A try without creating new matrix
		int num1, num2, num3, num4;
		int layers;
		if (N % 2 == 0) layers = N/2;
		else layers = (N + 1)/2;
		if (N % 2 == 0) {
			for (int i = 0; i < layers; i++) {
				for (int j = i; j < layers + 1 - i; j++) {
					num1 = matrix[i][j];
					num2 = matrix[j][N - i - 1];
					num3 = matrix[N - i - 1][N - j - 1];
					num4 = matrix[N - j - 1][i];

					matrix[i][j] = num4;
					matrix[j][N - i - 1] = num1;
					matrix[N - i - 1][N - j - 1] = num2;
					matrix[N - j - 1][i] = num3;
				}
			}
		}
		else {
			for (int i = 0; i < layers; i++) {
				for (int j = i; j < layers - i; j++) {
					num1 = matrix[i][j];
					num2 = matrix[j][N - i - 1];
					num3 = matrix[N - i - 1][N - j - 1];
					num4 = matrix[N - j - 1][i];

					matrix[i][j] = num4;
					matrix[j][N - i - 1] = num1;
					matrix[N - i - 1][N - j - 1] = num2;
					matrix[N - j - 1][i] = num3;
				}
			}
		}*/
		
		
		
		// do not change this part below
		for(int i=0;i < N; i++){
			System.out.println(Arrays.toString(rotatedMatrix[i])); // this would compile after you would put value in transposedMatrix
		}
	}
}

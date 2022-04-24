package il.ac.tau.cs.sw1.ex2;

public class Assignment02Q02 {

	public static void main(String[] args) {
		// do not change this part below
		double piEstimation = 0.0;
		// Your code goes here
		if (args.length > 0) {
			int num_sum = Integer.parseInt(args[0]);
			double add_sum = 1;

			for (int i = 0; i < num_sum; i++) {
				if (i % 2 == 0) piEstimation += 1 / add_sum;
				else piEstimation -= 1 / add_sum;
				add_sum += 2;
			}

			piEstimation *= 4;

			// do not change this part below
			System.out.println(piEstimation + " " + Math.PI);
		}
	}

}

package il.ac.tau.cs.sw1.ex2;

public class Assignment02Q03 {

	public static void main(String[] args) {
		int numOfEven = 0;
		int n = -1;
		// Your code goes here
		n = Integer.parseInt(args[0]);
		String rtrn = "1 1";
		int current;
		int prev = 1;
		int preprev = 1;
		for (int i = 2; i < n; i++){
			current = prev + preprev;
			if (current % 2 == 0) numOfEven+=1;
			rtrn += " " + current;
			preprev = prev;
			prev = current;
		}
		
		System.out.println("The first "+ n +" Fibonacci numbers are:");
		System.out.println(rtrn);
		System.out.println("The number of even numbers is: "+numOfEven);

	}

}

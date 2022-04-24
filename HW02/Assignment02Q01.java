package il.ac.tau.cs.sw1.ex2;

public class Assignment02Q01 {

	public static void main(String[] args) {
		// Your code goes here
		for (int i = 0; i < args.length; i++) {
			char ch = args[i].charAt(0);
			int asciich = (int) ch;
			if (asciich % 3 == 0 && asciich % 2 == 0){
				System.out.println(ch);
			}
		}
	}
}
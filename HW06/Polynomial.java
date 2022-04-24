package il.ac.tau.cs.sw1.hw6;

public class Polynomial {

	private double[] polynom;
	/*
	 * Creates the zero-polynomial with p(x) = 0 for all x.
	 */
	public Polynomial()
	{
		this.polynom = new double[1];
		this.polynom[0] = 0;
	}
	/*
	 * Creates a new polynomial with the given coefficients.
	 */
	public Polynomial(double[] coefficients) 
	{
		this.polynom = new double[coefficients.length];
		for (int i = 0; i < coefficients.length; i++) {
			this.polynom[i] = coefficients[i];
		}
	}

	private double[] getCoefficients(){
		double[] rtrn = new double[this.polynom.length];
		for (int i = 0; i < this.polynom.length; i++) {
			rtrn[i] = this.polynom[i];
		}
		return rtrn;
	}

	/*
	 * Addes this polynomial to the given one
	 *  and retruns the sum as a new polynomial.
	 */
	public Polynomial adds(Polynomial polynomial)
	{
		int polynom_length = Math.max(this.polynom.length, polynomial.polynom.length);
		double cur_coefA = 0, cur_coefB = 0;
		double[] coef = new double[polynom_length];

		Polynomial result = new Polynomial(coef);

		for (int i = 0; i < polynom_length; i++) {
			if (i < this.polynom.length) cur_coefA = this.polynom[i];
			else cur_coefA = 0;
			if (i < polynomial.polynom.length) cur_coefB = polynomial.polynom[i];
			else cur_coefB = 0;

			result.polynom[i] = cur_coefA + cur_coefB;
		}

		return result;
	}
	/*
	 * Multiplies a to this polynomial and returns 
	 * the result as a new polynomial.
	 */
	public Polynomial multiply(double a)
	{
		Polynomial result = new Polynomial(this.getCoefficients());
		for (int i = 0; i < result.polynom.length; i++) {
			result.polynom[i] = a * this.polynom[i];
		}

		return result;
	}
	/*
	 * Returns the degree (the largest exponent) of this polynomial.
	 */
	public int getDegree()
	{
		int degree = this.polynom.length - 1;
		for (int i = this.polynom.length - 1; i > 0; i--) {
			if (this.polynom[i] == 0) degree -= 1;
		}

		return degree;
	}
	/*
	 * Returns the coefficient of the variable x 
	 * with degree n in this polynomial.
	 */
	public double getCoefficient(int n)
	{
		return this.polynom[n];
	}

	/*
	 * n >= this.length
	 */
	private void addDegree(int n){

		double[] new_polynomial = new double[n + 1];
		for (int i = 0; i < this.polynom.length; i++) {
			new_polynomial[i] = this.polynom[i];
		}

		this.polynom = new_polynomial;
	}


	/*
	 * set the coefficient of the variable x 
	 * with degree n to c in this polynomial.
	 * If the degree of this polynomial < n, it means that that the coefficient of the variable x 
	 * with degree n was 0, and now it will change to c. 
	 */
	public void setCoefficient(int n, double c)
	{
		if(n > this.polynom.length){
			addDegree(n);
		}
		this.polynom[n] = c;
	}
	
	/*
	 * Returns the first derivation of this polynomial.
	 *  The first derivation of a polynomal a0x0 + ...  + anxn is defined as 1 * a1x0 + ... + n anxn-1.
	
	 */
	public Polynomial getFirstDerivation()
	{
		Polynomial rtrn = new Polynomial();
		rtrn.addDegree(this.polynom.length - 1);
		for (int i = 1; i < this.polynom.length; i++) {
			rtrn.setCoefficient(i-1, i*this.getCoefficient(i));
		}

		return rtrn;
	}
	
	/*
	 * given an assignment for the variable x,
	 * compute the polynomial value
	 */
	public double computePolynomial(double x)
	{
		double value = 0.;
		for (int i = 0; i < this.polynom.length; i++) {
			value += Math.pow(x, i) * this.getCoefficient(i);
		}

		return value;
	}
	
	/*
	 * given an assignment for the variable x,
	 * return true iff x is an extrema point (local minimum or local maximum of this polynomial)
	 * x is an extrema point if and only if The value of first derivation of a polynomal at x is 0
	 * and the second derivation of a polynomal value at x is not 0.
	 */
	public boolean isExtrema(double x)
	{
		Polynomial first_derivative = this.getFirstDerivation();
		Polynomial second_derivative = first_derivative.getFirstDerivation();
		if (first_derivative.computePolynomial(x) == 0 && second_derivative.computePolynomial(x) != 0) return true;
		return false;
	}
	
	
	
	

    
    

}

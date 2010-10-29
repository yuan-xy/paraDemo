package ap;

import dep.ExtendGcd;
import util.MatrixPair;

public class Equation {
	
	public Equation(int[][] A,int[] b){
		this.A=A;
		this.b=b;
	}
	
	public int[][] A;
	public int[] b;

	public MatrixPair solve(){
		return new ExtendGcd().solve(A, b);
	}
	
}

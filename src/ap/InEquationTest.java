package ap;

import java.util.Arrays;

import junit.framework.TestCase;

public class InEquationTest extends TestCase {
	
	public void test(){
		int[][] A=new int[][]{
				{1,0},
				{0,1},
				{1,-1}
		};
		int[][] ret=new InEquation().solve(A);
		System.out.println(Arrays.toString(ret));
	}
	
	public void test2(){
		long t1=System.nanoTime();
		int[][] A=new int[][]{
				{1,-1,-1},
				{7,3,-2},
				{10,5,2},
				{6,-9,-7},
		};
		int[][] ret=new InEquation().solve(A);
		long t2=System.nanoTime();
		System.out.println(t2-t1);
		System.out.println(Arrays.toString(ret));
	}

}

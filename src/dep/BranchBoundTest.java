package dep;

import java.util.ArrayList;
import java.util.List;

import util.MatrixUtils;
import util.MatrixUtilsTest;

import junit.framework.TestCase;

public class BranchBoundTest extends TestCase {

	int[][] A=new int[][]{
			{1,0},
			{-1,0},
			{-1,1},
			{0,-1}
	};
	int[] b=new int[]{0,5,0,7};
	
	BranchBound bb=new BranchBound();
	
	public void test(){
		boolean ret=bb.solve(A, b);
		assertTrue(ret);
	}
	
	public void test2(){//Exercise 11.6.7
		int[][] Ab=new int[][]{
				{1,0,0,0},
				{-1,0,0,99},
				{0,1,0,0},
				{0,-1,0,99},
				{0,0,1,0},
				{0,0,-1,99},
				{1,-1,0,-50},
				{0,1,-1,-60}
		};
		boolean ret=bb.solve(Ab);
		assertFalse(ret);
	}
	
	public void test3(){//Exercise 11.6.8
		int[][] Ab=new int[][]{
				{1,0,0,0},
				{-1,0,0,99},
				{0,1,0,0},
				{0,-1,0,99},
				{0,0,1,0},
				{0,0,-1,99},
				{1,-1,0,-50},
				{0,1,-1,40},
				{-1,0,1,20}
		};
		boolean ret=bb.solve(Ab);
		assertTrue(ret);
	}
	
	public void test4(){//Exercise 11.6.9
		int[][] Ab=new int[][]{
				{1,0,0,0},
				{-1,0,0,99},
				{0,1,0,0},
				{0,-1,0,99},
				{0,0,1,0},
				{0,0,-1,99},
				{1,-1,0,-100},
				{0,1,-1,60},
				{-1,0,1,50}
		};
		boolean ret=bb.solve(Ab);
		assertFalse(ret);
	}
	

	
	public void testReduce() {
		int[][] ret=new FourierMotzkin().elimination(A, b, 1);
		int middle=bb.findSolution(ret).calcMiddle();
		assertEquals(2, middle);
		List<Integer> solutions=new ArrayList<Integer>();
		solutions.add(middle);
		int[][] reduced=bb.reduce(MatrixUtils.columnMerge(A, b), solutions);
		MatrixUtilsTest.equalMatrix(reduced, new int[][]{
				{0,2},
				{0,3},
				{1,-2},
				{-1,7}
		});
		assertEquals(4,bb.findSolution(reduced).calcMiddle());
	}
	
	public void testDouble2Int(){
		double d1=5.0;
		double d2=0.0;
		int m=(int) ((d1+d2)/2);
		assertEquals(m, 2);
		double d3=1.3;
		int i3=(int) d3;
		assertEquals(i3, 1);
		double d4=1.6;
		int i4=(int) d4;
		assertEquals(i4, 1);
		
		double d=Math.max(Double.MAX_VALUE*-1, -0.0);
		assertEquals(d, -0.0);
	}
	
	public void testSolutionSet(){
		SolutionSet t=new SolutionSet();
		t.min=1.3;
		t.max=1.6;
		assertEquals(1, t.min());
		assertEquals(2, t.max());
		try{
			t.calcMiddle();
			fail();
		}catch(NoIntegerSolutionException e){}
	}
	
	public void testFindSolution(){
		int[][] Ab=new int[][]{
				{1,0},
				{-1,10},
				{1,-10},
				{-1,20}
		};
		SolutionSet ss=new BranchBound().findSolution(Ab);
		assertEquals(ss.min, 10.0);
		assertEquals(ss.max, 10.0);
		assertEquals(10, ss.calcMiddle());
	}

}

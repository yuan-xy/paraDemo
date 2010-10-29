package dep;
import junit.framework.TestCase;
import util.MatrixPair;
import util.MatrixUtils;
import util.MatrixUtilsTest;


public class ExtendGcdTest extends TestCase {

	public void testGcdIntInt() {
		ExtendGcd da=new ExtendGcd();
		assertEquals(da.gcd(2, 3),1);
		assertEquals(da.gcd(2, 0),2);
		assertEquals(da.gcd(-2, 0),2);
		assertEquals(da.gcd(2, 4),2);
		assertEquals(da.gcd(4, 2),2);
		assertEquals(da.gcd(12, 18),6);
	}
	
	public void testGcdIntArray() {
		ExtendGcd da=new ExtendGcd();
		assertEquals(da.gcd(new int[]{1,2,3}),1);
		assertEquals(da.gcd(new int[]{2,2,-4}),2);
		assertEquals(da.gcd(new int[]{4,6,8,10}),2);
		assertEquals(da.gcd(new int[]{120,42,24}),6);
	}
	
	public void testGcdTest(){
		ExtendGcd da=new ExtendGcd();
		assertTrue(da.gcdTest(new int[]{1,2,3},1));
		assertFalse(da.gcdTest(new int[]{4,6,8,10},1));
		assertTrue(da.gcdTest(new int[]{120,42,24},12));
		assertFalse(da.gcdTest(new int[]{120,42,24},2));
	}
	
	public void testUnimodularTest(){
		ExtendGcd da=new ExtendGcd();
		int[][] matrix=new int[][]{{0,-1,-1,1},
									{1,2,2,0},
									{0,3,2,0}};
		int[] A=new int[]{2,2,-3};
		int[][] ret=da.unimodularTransForm(A);
		MatrixUtilsTest.equalMatrix(matrix, ret);
		assertEquals(ret[0][ret[0].length-1], da.gcd(A));
	}

	public void testUnimodularTest2(){
		ExtendGcd da=new ExtendGcd();
		int[][] matrix=new int[][]{{1,0,1,1,1},
									{0,1,-1,0,-2},
									{2,1,2,0,0}};
		int[][] A=new int[][]{{3,1},{-2,-2},{-2,0}};
		int[] c=new int[]{2,0};
		int[][] ret=da.unimodularTransForm(A);
		MatrixUtilsTest.equalMatrix(matrix, ret);
		MatrixPair mp = MatrixUtils.splitColumn(ret,A.length);
		int[] solution=da.solveEchelon(mp.m2, c);
		MatrixUtilsTest.equalMatrix(solution, new int[]{2,1});
	}
	
	public void testSolveEchelon(){
		ExtendGcd da=new ExtendGcd();
		int[][] A=new int[][]{
				{1,-1},
				{-1,1}
		};
		int[] c={10,11};
		int[][] UD=new int[][]{
				{0,-1,1,-1},
				{1,1,0,0}
		};
		int[][] ret=da.unimodularTransForm(A);
		MatrixUtilsTest.equalMatrix(UD,ret);
		int[][] D=MatrixUtils.splitColumn(ret,A.length).m2;
		try{
			da.solveEchelon(D, c);
			fail();
		}catch(NoSolutionException e){}
	}
	
	public void testSolve(){
		ExtendGcd da=new ExtendGcd();
		int[][] A=new int[][]{
				{1,0},
				{0,1},
				{-1,0},
				{0,-1},
		};
		int[] c={0,-1};
//		int[][] fe=new int[][]{
//				{1,0,0},
//				{0,1,0},
//				{1,0,0},
//				{0,1,1},
//				{0,0,1},
//		};
		int[][] f=new int[][]{
				{1,0,1,0},
				{0,1,0,1},
		};
		int[][] e=new int[][]{{0,0,0,1}};
		MatrixPair ret=da.solve(A, c);
		MatrixUtilsTest.equalMatrix(ret.m1, f);
		MatrixUtilsTest.equalMatrix(ret.m2, e);
	}
	
	public void testNullSpaceOfEchelon(){
		int[][] echelon=new int[][]{
				{1,1,1,1,2},
				{0,2,0,2,0},
				{0,0,0,3,0},
				{0,0,0,0,0},
				{0,0,0,0,0},
				{0,0,0,0,0},
		};
		int[][] ret=new ExtendGcd().nullSpaceOfEchelon(echelon);
		int[][] n=new int[][]{
				{0,0,0,1,0,0},
				{0,0,0,0,1,0},
				{0,0,0,0,0,1},
		};
		MatrixUtilsTest.equalMatrix(ret, n);
	}
	
	public void testNullSpace(){
		int[][] A=new int[][]{
				{1,2,3},
				{1,2,3},
				{1,2,3},
		};
		int[][] ret=new ExtendGcd().nullSpace(A);
		int[][] n=new int[][]{
				{1,0,-1},
				{0,1,-1},
		};
		//[-1 1 0
		// -1 0 1] matlab的结果
		MatrixUtilsTest.equalMatrix(ret, n);
	}
	
	public void testNullSpace2(){
		int[][] A=new int[][]{
				{1,2,3},
				{1,2,3},
				{1,2,3},
		};
		int[][] ret=new ExtendGcd().nullSpace(MatrixUtils.transpose(A));
		int[][] n=new int[][]{
				{1,1,-1},
				{0,3,-2},
		};
		//[-2 1 0
		// -3 0 1] matlab的结果
		MatrixUtilsTest.equalMatrix(ret, n);
	}
	
	public void testNullSpace3(){
		int[][] A=new int[][]{ //magic(4)'
	    		{16, 5, 9, 4},
	    		{ 2,11, 7,14},
	    		{ 3,10, 6,15},
	    		{13, 8,12, 1},
		};
		int[][] ret=new ExtendGcd().nullSpace(A);
		int[][] n=new int[][]{
				{1,3,-3,-1}
		};
		MatrixUtilsTest.equalMatrix(ret, n);
	}
	
	public void testNullSpace4(){
		int[][] A=new int[][]{ //magic(5)'
				{17,23, 4,10,11},
				{24, 5, 6,12,18},
				{ 1, 7,13,19,25},
				{ 8,14,20,21, 2},
				{15,16,22, 3, 9}
		};
		try{
			new ExtendGcd().nullSpace(A);
			fail();
		}catch(NoSolutionException e){}
	}
	
	public void testUnimodularWithAllZeorColumn(){
		int[][] A=new int[][]{
				{1, 0, 1, 0, 0, 0},
				{0, 0, 0, 0, 1, 0},
				{1, 0, 1, 0, 0, 1},
				{0, 0, 0, 0, 0, 0},
		};
		int[][] result=new int[][]{
				{1,1,0,1},
				{0,0,1,0},
				{0,0,0,-1},
				{0,0,0,0},
				
		};
		int[][] ret=new ExtendGcd().unimodularTransForm(A);
		ret=MatrixUtils.splitColumn(ret, 4).m2;
		MatrixUtilsTest.equalMatrix(ret, result);
	}
	
	public void testNullSpace5(){
		int[][] A=new int[][]{
				{1, 0, 1, 0, 0, 0},
				{0, 0, 0, 0, 1, 0},
				{1, 0, 1, 0, 0, 1},
				{0, 0, 0, 0, 0, 0},
		};
		int[][] ret=new ExtendGcd().nullSpace(A);
		MatrixUtilsTest.equalMatrix(ret, new int[][]{{0,0,0,1}});
	}
	
	
}

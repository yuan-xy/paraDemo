package dep;

import junit.framework.TestCase;
import util.MatrixUtils;
import util.MatrixUtilsTest;

public class FourierMotzkinTest extends TestCase {
	
	public void test(){
		int[][] A=new int[][]{
				{1,0},
				{-1,0},
				{-1,1},
				{0,-1}
		};
		int[] b=new int[]{0,5,0,7};
		int[][] result=new int[][]{
				{-1,7},
				{1,0}
		};//例子来自龙书2e的Example 11.12
		int[][] ret=new FourierMotzkin().elimination(A, b, 0);
		MatrixUtilsTest.equalMatrix(ret, result);
		MatrixUtilsTest.equalMatrix(new FourierMotzkin().elimination(A, b, 1), 
				new int[][]{
					{1, 0},
					{-1, 5},
					{-1, 7}//TODO: 冗余删除
				}
			);
	}
	
	public void test2(){
		int[][] Ab=new int[][]{
				{1,0},
				{-1,-2}
		};
		try{
			new FourierMotzkin().elimination(Ab, 0);
			fail();
		}catch(NoSolutionException e){}
	}
	
	public void test3(){
		int[][] Ab=new int[][]{
				{-1,1,0},
		};
		int[][] ret1=new FourierMotzkin().elimination(Ab, 0);
		assertEquals(0, ret1.length);
		//assertEquals(MatrixUtils.ZeroMatrix, ret1);
		int[][] ret2=new FourierMotzkin().elimination(ret1, 0);
		assertEquals(0, ret2.length);
		assertEquals(MatrixUtils.ZeroMatrix, ret2);
	}
	

}

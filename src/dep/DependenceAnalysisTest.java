package dep;

import junit.framework.TestCase;

public class DependenceAnalysisTest extends TestCase {
	
	public void test(){
		int[][] A1=new int[][]{
				{1,0},
				{-1,0},
				{1,0},
				{-1,0}
		};
		int b[]=new int[]{0,10,0,10};
		int[][] A2=new int[][]{
				{1,-1},
				{-1,1}
		};
		int c[]=new int[]{10,11};
		DependenceAnalysis da=new DependenceAnalysis();
		boolean flag=da.dependent(A1, b, A2, c);
		assertFalse(flag);
		assertEquals(da.dependent(A1, b, A2, c), da.dependent2(A1, b, A2, c));
	}
	
	
	public void test2(){
		int[][] A1=new int[][]{//Example 11.35
				{1,0,0,0},
				{-1,0,0,0},
				{0,1,0,0},
				{0,-1,0,0},
				{0,0,1,0},
				{0,0,-1,0},
				{0,0,0,1},
				{0,0,0,-1}
		};
		int b[]=new int[]{0,10,0,10,0,10,0,10};
		int[][] A2=new int[][]{
				{1,0},
				{0,1},
				{0,-1},
				{-1,0}
		};
		int c[]=new int[]{10,11};
		DependenceAnalysis da=new DependenceAnalysis();
		boolean flag=da.dependent(A1, b, A2, c);
		assertFalse(flag);
		assertEquals(da.dependent(A1, b, A2, c), da.dependent2(A1, b, A2, c));
		
		int c2[]=new int[]{10,9};
		boolean flag2=da.dependent(A1, b, A2, c2);
		assertTrue(flag2);
		assertEquals(da.dependent(A1, b, A2, c), da.dependent2(A1, b, A2, c));
	}

}

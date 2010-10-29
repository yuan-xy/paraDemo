package util;
import junit.framework.TestCase;


public class MatrixUtilsTest extends TestCase {

	public void testEye() {
		int[][] eye3= new int[][]{{1,0,0},
								  {0,1,0},
								  {0,0,1}};
		equalMatrix(eye3,MatrixUtils.eye(3));
	}
	
	public static void equalMatrix(int[][] m1, int[][] m2){
		assertEquals(m1.length, m2.length);
		assertEquals(m1[0].length, m2[0].length);
		for(int i=0;i<m1.length;i++){
			for(int j=0;j<m1[0].length;j++)
				assertEquals(m1[i][j],m2[i][j]);
		}
	}
	
	public static void equalMatrix(int[] m1, int[] m2){
		assertEquals(m1.length, m2.length);
		for(int i=0;i<m1.length;i++){
			assertEquals(m1[i],m2[i]);
		}
	}
	
	public void testColumnMerge(){
		int[][] matrix= new int[][]{{1,0,0,1,0,0},
				  					{0,1,0,0,1,0},
				  					{0,0,1,0,0,1}};
		int[][] left=MatrixUtils.eye(3);
		int[][] right=MatrixUtils.eye(3);
		int[][] ret=MatrixUtils.columnMerge(left, right);
		equalMatrix(matrix,ret);
	}
	
	public void testColumnMerge2(){
		int[][] matrix= new int[][]{{1,0,0,1},
				  					{0,1,0,2},
				  					{0,0,1,3}};
		int[][] left=MatrixUtils.eye(3);
		int[] right=new int[]{1,2,3};
		int[][] ret=MatrixUtils.columnMerge(left, right);
		equalMatrix(matrix,ret);
	}
	
	public void testRowMerge(){
		int[][] up=MatrixUtils.eye(2);
		int[][] down=new int[][]{{1,1}};
		int[][] matrix=new int[][]{
				{1,0},
				{0,1},
				{1,1},
		};
		int[][] ret=MatrixUtils.rowMerge(up, down);
		equalMatrix(matrix,ret);
	}
	
	public void testSplitColumn(){
		int[][] matrix= new int[][]{{1,0,0,1},
					{0,1,0,2},
					{0,0,1,3}};
		int[][] left=MatrixUtils.eye(3);
		//int[] right=new int[]{1,2,3};
		MatrixPair mp=MatrixUtils.splitColumn(matrix,3);
		equalMatrix(mp.m1,left);
		equalMatrix(mp.m2, new int[][]{{1},{2},{3}});
	}
	
	public void testSplitRow(){
		int[][] matrix= new int[][]{{1,0,0,1},
					{0,1,0,2},
					{0,0,1,3}};
		int[][] up=new int[][]{{1,0,0,1},
				{0,1,0,2}};
		int[][] down=new int[][]{{0,0,1,3}};
		MatrixPair mp=MatrixUtils.splitRow(matrix,2);
		equalMatrix(mp.m1,up);
		equalMatrix(mp.m2,down);
	}
	
	public void testTranspose(){
		int[][] matrix= new int[][]{{1,0,0,1},
				{0,1,0,2},
				{0,0,1,3}};
		int[][] matrixT=new int[][]{
				{1,0,0},
				{0,1,0},
				{0,0,1},
				{1,2,3}
		};
		equalMatrix(matrixT, MatrixUtils.transpose(matrix));
	}
	
	public void testExpandColumn(){
		int[] m=new int[]{1,2,3};
		int[][] m2=new int[][]{{1},{2},{3}};
		equalMatrix(m2, MatrixUtils.expandColumn(m));
	}
	
	public void testExpandRow(){
		int[] m=new int[]{1,2,3};
		int[][] m2=new int[][]{{1,2,3}};
		equalMatrix(m2, MatrixUtils.expandRow(m));
	}
	
	public void testCollapse(){
		int[] m=new int[]{1,2,3};
		int[][] m2=new int[][]{{1},{2},{3}};
		int[][] m3=new int[][]{{1,2,3}};
		equalMatrix(m, MatrixUtils.collapse(m2));
		equalMatrix(m, MatrixUtils.collapse(m3));
	}
	
	
	public void testUnimodular1(){
		int[][] matrix= new int[][]{{1,0,0},
				  					{0,1,0},
				  					{0,0,-1}};
		int[][] eye=MatrixUtils.eye(3);
		MatrixUtils.unimodular1(eye, 2);
		equalMatrix(matrix,eye);
	}
	
	public void testUnimodular2(){
		int[][] matrix= new int[][]{{1,0,0},
				  					{0,0,1},
				  					{0,1,0}};
		int[][] eye=MatrixUtils.eye(3);
		MatrixUtils.unimodular2(eye,1, 2);
		equalMatrix(matrix,eye);
	}
	
	public void testUnimodular3(){
		int[][] matrix= new int[][]{{1,2,0},
				  					{0,1,0},
				  					{0,0,1}};
		int[][] eye=MatrixUtils.eye(3);
		MatrixUtils.unimodular3(eye,0, 1, 2);
		equalMatrix(matrix,eye);
	}

	public void testMulti(){
		int[][] m1=new int[][]{
				{1,0},
				{-1,0},
				{-1,1},
				{0,-1}
		};
		int[][] m2=new int[][]{
				{-1,1},
				{0,1}
		};
		int[][] multi=new int[][]{
				{-1,1},
				{1,-1},
				{1,0},
				{0,-1}
		};
		int[][] ret=MatrixUtils.multi(m1, m2);
		equalMatrix(ret, multi);
	}
	
	public void testAdd(){
		int[][] m1=new int[][]{
				{1,1},
				{1,1}
		};
		int[][] m2=new int[][]{
				{0,1},
				{2,3}
		};
		int[][] m3=new int[][]{
				{1,2},
				{3,4}
		};
		equalMatrix(m3, MatrixUtils.add(m1, m2));
		equalMatrix(m3[0], MatrixUtils.add(m1[0], m2[0]));
	}
	
	public void testSubtract(){
		int[][] m1=new int[][]{
				{1,1},
				{1,1}
		};
		int[][] m2=new int[][]{
				{0,1},
				{2,3}
		};
		int[][] m3=new int[][]{
				{1,0},
				{-1,-2}
		};
		equalMatrix(m3, MatrixUtils.subtract(m1, m2));
		equalMatrix(m3[0], MatrixUtils.subtract(m1[0], m2[0]));
	}
	
	public void testClearAllZeroColumn(){
		int[][] A=new int[][]{
				{0,1,0},
				{0,2,0},
				{0,3,0},
		};
		int[][] result=new int[][]{{1},{2},{3}};
		int[][] ret=MatrixUtils.clearAllZeroColumn(A);
		equalMatrix(ret,result);
	}
	
	public void testClone(){
		int[][] eye=MatrixUtils.eye(2);
		int[][] eyeclone=MatrixUtils.clone(eye);
		equalMatrix(eye, eyeclone);
		assertFalse(eye==eyeclone);
		assertFalse(eye[0]==eyeclone[0]);
		assertFalse(eye[1]==eyeclone[1]);
	}
	
}

package util;

import java.util.ArrayList;
import java.util.List;

public class MatrixUtils {
	
	public final static int[][] ZeroMatrix=new int[][]{};
	
	/**
	 * 生成一个n*n阶的单位矩阵
	 */
	public static int[][] eye(final int n){
		int[][] ret=new int[n][n];
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++)
				if(i==j) ret[i][j]=1;
				else ret[i][j]=0;
		}
		return ret;
	}
	
	/**
	 *矩阵转置
	 */
	public static int[][] transpose(final int[][] m){
		int[][] ret=new int[m[0].length][m.length];
		for(int i=0;i<m[0].length;i++)
			for(int j=0;j<m.length;j++) ret[i][j]=m[j][i];
		return ret;
	}
	
	/**
	 * 将一个一维数组m扩展为一个二维数组，维数为m*1
	 * @param m
	 * @return
	 */
	public static int[][] expandColumn(int[] m){
		int[][] ret=new int[m.length][1];
		for(int i=0;i<ret.length;i++) ret[i][0]=m[i];
		return ret;
	}
	
	/**
	 * 将一个一维数组m扩展为一个二维数组，维数为1*m
	 * @param m
	 * @return
	 */
	public static int[][] expandRow(int[] m){
		int[][] ret=new int[1][m.length];
		ret[0]=m;//这样做是否好？
		//for(int i=0;i<ret.length;i++) ret[0][i]=m[i];
		return ret;
	}
	
	/**
	 *将一个二维数组压缩为一个一维数组。该二维数组的行或者列的维数必须为1。
	 */
	public static int[] collapse(int[][] m){
		if(m.length==1) return m[0];
		if(m[0].length==1){
			int[] ret=new int[m.length];
			for(int i=0;i<m.length;i++) ret[i]=m[i][0];
			return ret;
		}
		throw new RuntimeException();
	}
	
	/**
	 * 水平合并左右两个矩阵
	 * @param left 左矩阵
	 * @param right 右矩阵
	 * @return 合并以后的矩阵
	 */
	public static int[][] columnMerge(int[][] left,int[][] right){
		assert left.length==right.length;
		int row=left.length;
		int col=left[0].length+right[0].length;
		int[][] ret=new int[row][col];
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				if(j<left[0].length) ret[i][j]=left[i][j];
				else ret[i][j]=right[i][j-left[0].length];
			}
		}
		return ret;
	}
	
	public static int[][] columnMerge(int[][] left,int[] right){
		assert left.length==right.length;
		int row=left.length;
		int col=left[0].length+1;
		int[][] ret=new int[row][col];
		for(int i=0;i<row;i++){
			for(int j=0;j<col-1;j++){
				ret[i][j]=left[i][j];
			}
			ret[i][col-1]=right[i];
		}
		return ret;
	}
	
	public static int[][] rowMerge(int[][] up, int[][] down) {
		assert up[0].length==down[0].length;
		int[][] ret=new int[up.length+down.length][up[0].length];
		for(int i=0;i<up.length;i++) ret[i]=up[i];
		for(int i=0;i<down.length;i++) ret[i+up.length]=down[i];
		return ret;
	}
	
	/**
	 * 从第k列分割矩阵m，使得分割后左边的矩阵为k列矩阵。
	 * @return
	 */
	public static MatrixPair splitColumn(int[][] m , int k){
		assert m[0].length>k;
		int[][] m1=new int[m.length][k];
		int[][] m2=new int[m.length][m[0].length-k];
		for(int i=0;i<m.length;i++)
			for(int j=0;j<k;j++) m1[i][j]=m[i][j];
		for(int i=0;i<m.length;i++)
			for(int j=k;j<m[0].length;j++) m2[i][j-k]=m[i][j];
		MatrixPair mp=new MatrixPair();
		mp.m1=m1;
		mp.m2=m2;
		return mp;
	}
	
	/**
	 * 从第k行分割矩阵m，使得分割后上边的矩阵为k行矩阵。
	 * @return
	 */
	public static MatrixPair splitRow(int[][] m,int k){
		assert m.length>k;
		int[][] m1=new int[k][m[0].length];
		int[][] m2=new int[m.length-k][m[0].length];
		for(int i=0;i<k;i++) m1[i]=m[i];
		for(int i=k;i<m.length;i++) m2[i-k]=m[i];
		MatrixPair mp=new MatrixPair();
		mp.m1=m1;
		mp.m2=m2;
		return mp;
	}
	
	/**
	 * 第一类幺模变换：用-1乘以一行
	 * @param matrix 要变换的矩阵
	 * @param k 被乘以-1的那一行
	 */
	public static void unimodular1(int[][] matrix,int k){
		for(int i=0;i<matrix[0].length;i++) matrix[k][i]=-matrix[k][i];
	}
	
	/**
	 * 第二类幺模变换：交换矩阵的两行
	 * @param matrix 要变换的矩阵
	 * @param k1, k2 被交换的两行
	 */
	public static void unimodular2(int[][] matrix, int k1,int k2){
		for(int i=0;i<matrix[0].length;i++){
			int tmp=matrix[k1][i];
			matrix[k1][i]=matrix[k2][i];
			matrix[k2][i]=tmp;
		}
	}
	
	public static void rowExchange(int[][] matrix, int k1,int k2){
		unimodular2(matrix,k1,k2);
	}
	
	public static void columnExchange(int[][] matrix, int k1,int k2){
		for(int i=0;i<matrix.length;i++){
			int tmp=matrix[i][k1];
			matrix[i][k1]=matrix[i][k2];
			matrix[i][k2]=tmp;
		}
	}
	
	/**
	 * 第三类幺模变换：将矩阵的第k1行加上k2行的n倍
	 * @param matrix 要变换的矩阵
	 * @param k1 被加的那一行
	 * @param k2 乘以此行
	 * @param n  乘数
	 */
	public static void unimodular3(int[][] matrix, int k1,int k2,int n){
		for(int i=0;i<matrix[0].length;i++){
			matrix[k1][i]+=matrix[k2][i]*n;
		}
	}
	
	/**
	 * 矩阵m1和矩阵m2相乘
	 * @param m1
	 * @param m2
	 * @return
	 */
	public static int[][] multi(int[][] m1,int[][] m2){
		assert m1[0].length==m2.length;
		int[][] ret=new int[m1.length][m2[0].length];
		for(int i=0;i<m1.length;i++){
			for(int j=0;j<m2[0].length;j++){
				int tmp=0;
				for(int k=0;k<m2.length;k++){
					tmp+=m1[i][k]*m2[k][j];
				}
				ret[i][j]=tmp;
			}
		}
		return ret;
	}
	
	public static void scalarOperation(int[][] matrix,int value,char op){
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix[0].length;j++){
				switch(op){
					case '+': matrix[i][j]=matrix[i][j]+value;break;
					case '-': matrix[i][j]=matrix[i][j]-value;break;
					case '*': matrix[i][j]=matrix[i][j]*value;break;
					case '/': matrix[i][j]=matrix[i][j]/value;break;
					default: throw new RuntimeException("不支持的运算符:"+op);
				}
			}
		}
	}
	
	public static void scalarOperation(int[] array,int value,char op){
		for(int i=0;i<array.length;i++){
				switch(op){
					case '+': array[i]=array[i]+value;break;
					case '-': array[i]=array[i]-value;break;
					case '*': array[i]=array[i]*value;break;
					case '/': array[i]=array[i]/value;break;
					default: throw new RuntimeException("不支持的运算符:"+op);
				}
		}
	}
	

	/**
	 * 矩阵m1和矩阵m2相加
	 * @param m1
	 * @param m2
	 * @return
	 */
	public static int[][] add(int[][] m1, int[][] m2) {
		assert m1.length==m2.length;
		assert m1[0].length==m2[0].length;
		int[][] ret=new int[m1.length][m1[0].length];
		for(int i=0;i<ret.length;i++){
			for(int j=0;j<ret[0].length;j++){
				ret[i][j]=m1[i][j]+m2[i][j];
			}
		}
		return ret;
	}
	
	public static int[] add(int[] m1, int[] m2) {
		assert m1.length==m2.length;
		int[] ret=new int[m1.length];
		for(int i=0;i<ret.length;i++) ret[i]=m1[i]+m2[i];
		return ret;
	}
	
	/**
	 * 矩阵m1减去矩阵m2
	 * @param m1
	 * @param m2
	 * @return
	 */
	public static int[][] subtract(int[][] m1, int[][] m2) {
		assert m1.length==m2.length;
		assert m1[0].length==m2[0].length;
		int[][] ret=new int[m1.length][m1[0].length];
		for(int i=0;i<ret.length;i++){
			for(int j=0;j<ret[0].length;j++){
				ret[i][j]=m1[i][j]-m2[i][j];
			}
		}
		return ret;
	}
	
	public static int[] subtract(int[] m1, int[] m2) {
		assert m1.length==m2.length;
		int[] ret=new int[m1.length];
		for(int i=0;i<ret.length;i++) ret[i]=m1[i]-m2[i];
		return ret;
	}
	
	/**
	 * 删除矩阵中全部为0的列
	 */
	public static int[][] clearAllZeroColumn(int[][] A){
		List<Integer> zeroCol=new ArrayList<Integer>();
		for(int i=0;i<A[0].length;i++){
			boolean allZero=true;
			for(int j=0;j<A.length;j++){
				if(A[j][i]!=0) {
					allZero=false;
					break;
				}
			}
			if(allZero) zeroCol.add(i);
		}
		int[][] ret=new int[A.length][A[0].length-zeroCol.size()];
		for(int i=0,k=0;i<A[0].length;i++){
			if(zeroCol.contains(i)) continue;
			for(int j=0;j<A.length;j++){
				ret[j][k]=A[j][i];
			}
			k++;
		}
		return ret;
	}

	public static int[][] clone(int[][] A) {
		int[][] ret=new int[A.length][A[0].length];
		for(int i=0;i<ret.length;i++){
			for(int j=0;j<ret.length;j++){
				ret[i][j]=A[i][j];
			}
		}
		return ret;
	}

}


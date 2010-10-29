package dep;
import java.util.ArrayList;
import java.util.List;

import util.MatrixPair;
import util.MatrixUtils;


public class ExtendGcd {

	/**
	 * 判断整数方程a<sub>1</sub>x<sub>1</sub>+a<sub>2</sub>x<sub>2</sub>
	 * 	+...+a<sub>n</sub>x<sub>n</sub>=b是否存在整数解。
	 * @param A 系数数组[a<sub>1</sub>,a<sub>2</sub>,...,a<sub>n</sub>]
	 * @param b
	 * @return 如果存在整数解返回true
	 */
	public boolean gcdTest(int[] A,int b){
		if(b%gcd(A)==0) return true;
		return false;
	}
	
	/**
	 * 计算两个数的最大公约数
	 */
	public int gcd(int a1,int a2){
		if(a1<0) a1=-a1;
		if(a2<0) a2=-a2;
		while(a1!=0&&a2!=0){
			if(a1>a2) a1%=a2;
			else a2%=a1;
		}
		return a1+a2;
	}
	
	/**
	 * 计算一个数组中所有整数的最大公约数
	 * @param a 整数数组
	 * @return 其最大公约数
	 */
	public  int gcd(int[] a){
		if(a.length==2) return gcd(a[0],a[1]);
		else return gcd0(a,a.length-2,gcd(a[a.length-2],a[a.length-1]));
	}
	
	private int gcd0(int[] a, int len, int gcd) {
		if(len==0) return gcd;
		if(len==1) return gcd(a[0],gcd);
		return gcd0(a,len-2,gcd(a[len-2],a[len-1]));
	}

	/**
	 * 将方程a<sub>1</sub>x<sub>1</sub>+a<sub>2</sub>x<sub>2</sub>
	 * 	+...+a<sub>n</sub>x<sub>n</sub>=b的系数数组A进行幺模变换，使得UA=D,
	 *  其中U是unimodular幺模矩阵，D是矩阵[d,0,...,0]',d=gcd(a<sub>1</sub>,a<sub>2</sub>,...,a<sub>n</sub>)。
	 *  算法见"Dependence Analysis For Supercomputing"一书的算法5.4.1
	 * @param A 系数数组[a<sub>1</sub>,a<sub>2</sub>,...,a<sub>n</sub>]
	 * @return 返回增广矩阵[U,D]
	 */
	public int[][] unimodularTransForm(int[] A){
		int[][] UD=MatrixUtils.columnMerge(MatrixUtils.eye(A.length),A);
		int row=UD.length;
		int col=UD[0].length;
		int i = row-1;
		while(i>0){
			if(UD[i][col-1]==0){
				i--;
				continue;
			}
			int q=Math.abs(UD[i-1][col-1])/Math.abs(UD[i][col-1]);
			if(q!=0){
				int s=(int)Math.signum(UD[i-1][col-1]/UD[i][col-1]);
				MatrixUtils.unimodular3(UD,i-1,i,-1*s*q);
			}
			MatrixUtils.unimodular2(UD, i, i-1);
		}
		if(UD[0][col-1]<0){
			MatrixUtils.unimodular1(UD, 0);
		}
		return UD;
	}
	
	/**
	 * 将方程组<br>a<sub>11</sub>x<sub>1</sub>+a<sub>12</sub>x<sub>2</sub>
	 * 	+...+a<sub>1n</sub>x<sub>n</sub>=b<sub>1</sub><br>
	 * a<sub>21</sub>x<sub>1</sub>+a<sub>22</sub>x<sub>2</sub>
	 * 	+...+a<sub>2n</sub>x<sub>n</sub>=b<sub>2</sub><br>
	 *  ...<br>
	 * a<sub>n1</sub>x<sub>n</sub>+a<sub>n2</sub>x<sub>2</sub>
	 * +...+a<sub>nn</sub>x<sub>n</sub>=b<sub>n</sub><br>
	 *  表示为xA=b的形式。对系数矩阵A进行幺模变换，使得UA=D,
	 *  其中U是unimodular幺模矩阵，D是echelon梯形矩阵。
	 * @param A 系数数组<br>
	 * [a<sub>11</sub>,a<sub>21</sub>,...,a<sub>n1</sub><br>
	 *  a<sub>12</sub>,a<sub>22</sub>,...,a<sub>n2</sub><br>
	 *  ...
	 *  a<sub>1n</sub>,a<sub>2n</sub>,...,a<sub>nn</sub>]<br>
	 * ,注意A的行和列的标号
	 * @return 返回增广矩阵[U,D]
	 */
	public int[][] unimodularTransForm(int[][] A){
		A=MatrixUtils.clearAllZeroColumn(A);
		int[][] UD=MatrixUtils.columnMerge(MatrixUtils.eye(A.length),A);
		int row=UD.length;
		int col=UD[0].length;
		int j=col-A[0].length;
		//A的最左列的幺模变换，代码类似一维矩阵的幺模变换
		int i = row-1;
		while(i>0){
			if(UD[i][j]==0){
				i--;
				continue;
			}
			int q=Math.abs(UD[i-1][j])/Math.abs(UD[i][j]);
			if(q!=0){
				int s=(int)Math.signum(UD[i-1][j]/UD[i][j]);
				MatrixUtils.unimodular3(UD,i-1,i,-1*s*q);
			}
			MatrixUtils.unimodular2(UD, i, i-1);
		}
		if(UD[0][row]<0){//要求d11>0
			MatrixUtils.unimodular1(UD, 0);
		}
		//
		j++;
		for(;j<col;j++){//后面列的幺模转换,使得D成为梯形矩阵
	 		int k = row-1;
			while(UD[k-1][j-1]==0){
				if(UD[k][j]==0){
					k--;
					continue;
				}
				int q=Math.abs(UD[k-1][j])/Math.abs(UD[k][j]);
				if(q!=0){
					int s=(int)Math.signum(UD[k-1][j]/UD[k][j]);
					MatrixUtils.unimodular3(UD,k-1,k,-1*s*q);
				}
				MatrixUtils.unimodular2(UD, k, k-1);
			}
		}
		return UD;
	}
	
	/**
	 * 求解方程组tD=c，其中D是echelon矩阵
	 * @param D
	 * @param c
	 * @return
	 * @throws NoSolutionException
	 */
	int[] solveEchelon(int[][] D,int[] c) throws NoSolutionException{
		assert D[0].length==c.length; //等式方程的个数
		List<Integer> solution=new ArrayList<Integer>();
		for(int i=0;i<c.length;i++){
			if(D[i][i]==0){
				int zero=0;
				for(int j=0;j<i;j++){
					zero+=solution.get(j)*D[j][i];
				}
				if(zero==0) break;
				throw new NoSolutionException();
			}
			int sum=0;
			for(int j=0;j<i;j++){
				sum+=solution.get(j)*D[j][i];
			}
			int tmp=c[i]-sum;
			if(tmp%D[i][i]!=0) throw new NoSolutionException();
			solution.add(tmp/D[i][i]);
		}
		int[] ret=new int[solution.size()];
		for(int i=0;i<ret.length;i++) ret[i]=solution.get(i);
		return ret;
	}
	
	/**
	 * 解方程xA=c,使得x=tf+e（其中t是自由变量）
	 * @param A 
	 * @param c 
	 * @return 返回矩阵对[f;e]
	 * @throws NoSolutionException
	 */
	public MatrixPair solve(int[][] A,int [] c) throws NoSolutionException{
		int[][] UD=unimodularTransForm(A);
		MatrixPair mp = MatrixUtils.splitColumn(UD, A.length);
		int[] solution=solveEchelon(mp.m2, c);
		int[][] e2=MatrixUtils.multi(
				MatrixUtils.expandRow(solution),
				MatrixUtils.splitRow(mp.m1, solution.length).m1);
		int[][] f=MatrixUtils.splitRow(mp.m1, solution.length).m2;
		MatrixPair ret=new MatrixPair();
		ret.m1=f;
		ret.m2=e2;
		return ret;
	}
	
	
	int[][] nullSpaceOfEchelon(int[][] D) throws NoSolutionException{
		int rank=0;
		for(int i=0,j=0;j<D[0].length;){
			if(D[i][j]==0){
				j++;
			}else{
				i++;
				j++;
				rank++;
			}
		}
		if(rank==D.length) throw new NoSolutionException();
		int[][] zeros=new int[D.length-rank][rank];
		int[][] eyes=MatrixUtils.eye(D.length-rank);
		int[][] nullD=MatrixUtils.columnMerge(zeros, eyes);
		return nullD;
	}
	
	public int[][] nullSpace(int[][] A)  throws NoSolutionException{
		int[][] UD=unimodularTransForm(A);
		MatrixPair mp2 = MatrixUtils.splitColumn(UD, A.length);
		int[][] nullD = nullSpaceOfEchelon(mp2.m2);
		return MatrixUtils.multi(nullD, mp2.m1);
	}
	
	
}


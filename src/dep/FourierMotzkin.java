package dep;

import java.util.ArrayList;
import java.util.List;

import util.MatrixUtils;

public class FourierMotzkin {

	/**
	 * 见龙书2e的算法11.11。
	 * 将方程组<br>a<sub>11</sub>x<sub>1</sub>+a<sub>12</sub>x<sub>2</sub>
	 * 	+...+a<sub>1n</sub>x<sub>n</sub>+b<sub>1</sub>>=0<br>
	 * a<sub>21</sub>x<sub>1</sub>+a<sub>22</sub>x<sub>2</sub>
	 * 	+...+a<sub>2n</sub>x<sub>n</sub>+b<sub>2</sub>>=0<br>
	 *  ...<br>
	 * a<sub>p1</sub>x<sub>1</sub>+a<sub>p2</sub>x<sub>2</sub>
	 * +...+a<sub>pn</sub>x<sub>n</sub>+b<sub>p</sub>>=0<br>
	 *  表示为AX+b>=0的形式。消除其中的变量x<sub>m</sub>，新系统的约束表示为A'X'+b'>=0。
	 * @param A 系数矩阵<br>
	 * [a<sub>11</sub>,a<sub>12</sub>,...,a<sub>1n</sub><br>
	 *  a<sub>21</sub>,a<sub>22</sub>,...,a<sub>2n</sub><br>
	 *  ...<br>
	 *  a<sub>p1</sub>,a<sub>p2</sub>,...,a<sub>pn</sub>]<br>
	 * @param b 系数矩阵[b<sub>1</sub>,b<sub>2</sub>,...,b<sub>p</sub>]
	 * @param m 被消除的变量x在X向量中的位置
	 * @return 返回增广矩阵[A',b']
	 */
	public int[][] elimination(final int[][] A, int[] b,int m){
		int[][] Ab=MatrixUtils.columnMerge(A, b);
		return elimination(Ab,m);
	}

	public int[][] elimination(final int[][] Ab,int m) {
		if(Ab.length==0) return MatrixUtils.ZeroMatrix;
		List<int[]> list=new ArrayList<int[]>();
		for(int i=0;i<Ab.length;i++){
			if(Ab[i][m]==0){
				int[] inequal=new int[Ab[0].length-1];
				for(int k=0,k2=0;k<inequal.length;k2++){
					if(k2==m) continue;
					inequal[k]=Ab[i][k2];
					k++;
				}
				list.add(inequal);//原系统中不涉及到m的约束
			}
		}
		for(int i=0;i<Ab.length-1;i++){
			if(Ab[i][m]==0) continue;
			boolean flag=Ab[i][m]>0;
			for(int j=i+1;j<Ab.length;j++){
				if(Ab[j][m]==0) continue;
				boolean flag2=Ab[j][m]>0;
				if(flag&&!flag2){
					doElimination(Ab, m, list, i, j);
				}else if(!flag&&flag2){
					doElimination(Ab, m, list, j, i);
				}
			}
		}
		int[][] ret=new int[list.size()][Ab[0].length-1];
		for(int i=0;i<ret.length;i++) ret[i]=list.get(i);
		return ret;
	}

	private void doElimination(int[][] Ab, int m, List<int[]> list, int i, int j) {
		int[] ineuqal=new int[Ab[0].length-1];
		boolean allZero=true;//系数ai全部为0
		int c1=Ab[i][m];
		int c2=Ab[j][m];
		int gcd=new ExtendGcd().gcd(c1, c2);
		if(gcd>1){
			c1/=gcd;c2/=gcd;
		}
		for(int k=0,k2=0;k<ineuqal.length;k2++){
			if(k2==m) continue;
			ineuqal[k]=c1*Ab[j][k2]-c2*Ab[i][k2];
			if(k<ineuqal.length-1) allZero&=(ineuqal[k]==0);
			k++;
		}
		if(allZero){
			if(ineuqal[ineuqal.length-1]<0) throw new NoSolutionException();//b<0,条件不能满足
		}else{
			list.add(ineuqal);
		}
	}

}


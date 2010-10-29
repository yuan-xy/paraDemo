package dep;

import java.util.Collections;
import util.MatrixUtils;

public class LoopBoundsGeneration {

	/**
	 * 见龙书2e的算法11.13。
	 * 将方程组<br>a<sub>11</sub>x<sub>1</sub>+a<sub>12</sub>x<sub>2</sub>
	 * 	+...+a<sub>1n</sub>x<sub>n</sub>+b<sub>1</sub>>=0<br>
	 * a<sub>21</sub>x<sub>1</sub>+a<sub>22</sub>x<sub>2</sub>
	 * 	+...+a<sub>2n</sub>x<sub>n</sub>+b<sub>2</sub>>=0<br>
	 *  ...<br>
	 * a<sub>p1</sub>x<sub>1</sub>+a<sub>p2</sub>x<sub>2</sub>
	 * +...+a<sub>pn</sub>x<sub>n</sub>+b<sub>p</sub>>=0<br>
	 *  表示为AX+b>=0的形式，它代表一个convex polyhedron。
	 *  依次计算出X<sub>order[i]</sub>的边界，此order的顺序是从外层循环变量到内层循环变量。
	 *  实际计算时是先计算出内层循环变量的边界，然后是外层循环变量。
	 * @param A 系数矩阵<br>
	 * [a<sub>11</sub>,a<sub>12</sub>,...,a<sub>1n</sub><br>
	 *  a<sub>21</sub>,a<sub>22</sub>,...,a<sub>2n</sub><br>
	 *  ...<br>
	 *  a<sub>p1</sub>,a<sub>p2</sub>,...,a<sub>pn</sub>]<br>
	 * @param b 系数矩阵[b<sub>1</sub>,b<sub>2</sub>,...,b<sub>p</sub>]
	 * @param order 向量X中各元素计算的先后位置
	 * @return 
	 */
	public LoopBounds gen(int[][] A,int[] b,int[] order){
		int[][] Ab=MatrixUtils.columnMerge(A, b);
		LoopBounds list=new LoopBounds();
		for(int i=order.length-1;i>=0;i--){
			LoopBound bound=signleBound(Ab, order[i]);
			list.add(bound);
			int[][] ret=new FourierMotzkin().elimination(Ab,order[i]);
			if(i>0) Ab=insertColByZero(ret,order[i]);
		}
		Collections.reverse(list.getList());
		return list;
	}
	
	

	private int[][] insertColByZero(int[][] ret, int index) {
		int[][] ret2=new int[ret.length][ret[0].length+1];
		for(int i=0;i<ret2.length;i++){
			for(int j=0,k=0;j<ret2[0].length;){
				if(j==index){
					ret2[i][j++]=0;
				}else{
					ret2[i][j++]=ret[i][k++];
				}
			}
		}
		return ret2;
	}



	/**
	 * 将矩阵Ax+b>=0中的第X<sub>k</sub>替换为其他X变量的表示
	 * @param Ab 增广矩阵[A,b]
	 * @param k 要被替换的X的下标
	 * @return 以其他变量表示的X<sub>k</sub>的上下界限
	 */
	private LoopBound signleBound(int[][] Ab, int k) {
		LoopBound bound=new LoopBound(k);
		for(int j=0;j<Ab.length;j++){
			if(Ab[j][k]==0){
				continue;
			}else if(Ab[j][k]>0){
				int[] low=new int[Ab[0].length+1];
				low[0]=Ab[j][k];
				for(int p=0;p<Ab[0].length;p++){
					if(p==k) continue;
					low[p+1]=-Ab[j][p];
				}
				bound.getLower().add(low);
				//TODO: 冗余条件删除
			}else{
				int[] up=new int[Ab[0].length+1];
				up[0]=-Ab[j][k];
				for(int p=0;p<Ab[0].length;p++){
					if(p==k) continue;
					up[p+1]=Ab[j][p];
				}
				bound.getUpper().add(up);
			}
		}
		return bound;
	}
	
}

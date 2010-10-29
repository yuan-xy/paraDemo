package ap;

import java.util.List;

import util.MatrixUtils;
import dep.ExtendGcd;



public class Order0 {
	
	/**
	 * 
	 * @param sts 循环中的语句
	 * @param list 循环中所有的依赖对
	 * @param n 循环的层次
	 * @return
	 */
	public int[][] gen(List<Statement> sts,List<DependentPair> list,int n){
		int s=sts.size();
		if(list.isEmpty()){
			return MatrixUtils.eye(n*s+s);// 如果循环无依赖，返回什么单位矩阵吗？
		}
		assert n==list.get(0).F1.length;//循环的层数，其实是循环变量的个数（如果存在符号参数呢？）
		int[][] result=null;
		for(int i=0;i<list.size();i++){
			DependentPair dp=list.get(i);
			int[][] solution=dp.independentSolution();
			assert solution[0].length==2*n+1;
			int[][] solutionExpand=expandSolution(sts, s, n, dp, solution);
			if(i==0){
				result=solutionExpand;
			}else{
				result=merge(result,solutionExpand);
			}
		}
		return result;
	}

	/**
	 * 合并矩阵的null space，该算法的原理？？
	 * TODO: 这个算法有问题，原因是它对0的处理有错误。
	 * 对于null space涉及到的变量，0的意思是该变量的值必须为0;而对于未涉及到的变量，0代表可以任意取值。
	 * @param oldSolution
	 * @param newSolution
	 * @return
	 */
	public int[][] merge(int[][] oldSolution, int[][] newSolution) {
		int[][] m=MatrixUtils.rowMerge(oldSolution, newSolution);
		int[][] nul=new ExtendGcd().nullSpace(m);
		int[][] left=MatrixUtils.splitColumn(nul, oldSolution.length).m1;
		return MatrixUtils.multi(left, oldSolution);
	}

	/**
	 * 依赖对dp的解对应于[C1,C2,d1-d2]，是一个(k,2*n+1)维的向量，其中k是解空间的秩,n是循环的层数。
	 * 现在要把该解扩展为一个(k+1,s*n+s)维的向量，其中s循环中语句的数量。
	 * 增加的一维是一个trival的解。扩展以后矩阵将d1-d2列分割为d1列和d2列,对应的解也需要增加一维。
	 * @param sts 循环中语句的列表
	 * @param s 循环中语句的数量
	 * @param n 循环的层数
	 * @param dp 依赖对
	 * @param solution 依赖对原来的解
	 * @return 扩展后的解空间向量
	 */
	int[][] expandSolution(List<Statement> sts, int s, int n,DependentPair dp, int[][] solution) {
		int c1=sts.indexOf(dp.s1);
		int c2=sts.indexOf(dp.s2);
		int[][] solutionExpand=new int[solution.length+1][s*n+s];
		//solutionExpand行数是解空间的秩，列数s*n+s表示[c1,c2,...,cs,d1,d2,...ds],其中c1是n维行向量。d1是标量
		for(int i=0;i<solution.length;i++){
			for(int j=c1*n;j<c1*n+n;j++){
				solutionExpand[i][j]=solution[i][j-c1*n];
			}
		}
		for(int i=0;i<solution.length;i++){
			for(int j=c2*n;j<c2*n+n;j++){
				solutionExpand[i][j]=solution[i][j-c2*n];
			}
		}
		for(int i=0;i<solution.length;i++){
			if(solution[i][solution[0].length-1]!=0){
				solutionExpand[i][s*n+c1]=solution[i][solution[0].length-1];
			}
		}
		solutionExpand[solution.length][s*n+c1]=1;
		solutionExpand[solution.length][s*n+c2]=1;
		return solutionExpand;
	}
	
	/**
	 * 方程C<sub>1</sub>i<sub>1</sub>+d1=C<sub>2</sub>i<sub>2</sub>+d2的一个trival的解。
	 * 这个解是没有意义的。因为它把所有运算都放到同一个处理器，也就是没有并行化。
	 * @param k [C<sub>1</sub>,C<sub>2</sub>,d1,d2]的维数
	 * @return
	 */
	private int[][] trivalSolution(int k){
		int[][] ret=new int[1][k];
		ret[0][k-2]=1;
		ret[0][k-1]=1;
		return ret;
	}



}

class MappingMatrix{
	Statement s;
	int[] c;//n*1维矩阵，其中n是数组所在循环的嵌套层数
	int d;
}

class Statement{
	
}
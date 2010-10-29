package ap;

import util.MatrixPair;
import util.MatrixUtils;
import dep.ExtendGcd;

public class DependentPair{
	public int[][] F1; //m*n维矩阵，其中m是数组的维数，n是数组所在循环的嵌套层数
	public int[] f1;   //m*1维矩阵
	public Statement s1;
	public int[][] F2;
	public int[] f2;
	public Statement s2;
	
	
	int[][] independentSolution(){
		assert F1.length==f1.length;
		if(s1.equals(s2)) return new int[1][2*F1[0].length+1];
		//如果同一个语句间的两个数组访问存在依赖，该语句无法并行化，返回2n+1维的零向量
		int[][] A=MatrixUtils.rowMerge(F1, F2);
		//如果这里采用[F1;-F2],也就是书中给出的公式，那么最后求得的是[C1,-C2,(c1-c2)]的解向量。
		//采用的是[F1;F2]，是否最后求得的就是[C1,C2,(c1-c2)]的解向量，也就是我们想要的结果。
		int[] c =MatrixUtils.subtract(f2, f1);
		ExtendGcd gcd=new ExtendGcd();
		MatrixPair mp=gcd.solve(A, c);
		int[][] F_E=MatrixUtils.transpose(MatrixUtils.rowMerge(mp.m1, mp.m2));
		int[][] low=new int[1][F_E[0].length];
		low[0][low[0].length-1]=1;
		int[][] U=MatrixUtils.rowMerge(F_E,low);
		//求得P837页步骤2(b)中提到的矩阵U（符号上稍微有差别）,[C1,C2,(c1-c2)]U=0
		return gcd.nullSpace(U);
	}
	
}
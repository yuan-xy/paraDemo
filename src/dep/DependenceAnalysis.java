package dep;

import util.MatrixPair;
import util.MatrixUtils;

public class DependenceAnalysis {

	/**
	 * 判断由A1x+b>=0和x'A2=c组成的循环是否存在依赖
	 * 
	 * @param A1
	 * @param b
	 * @param A2
	 * @param c
	 * @return 如果存在依赖，返回true;如果不存在依赖，返回false
	 */
	public boolean dependent(int[][] A1, int[] b, int[][] A2, int[] c) {
		assert A1[0].length == A2.length;// 向量x的维数
		ExtendGcd gcd = new ExtendGcd();
		MatrixPair mp = null;
		try{
			mp = gcd.solve(A2, c);
		}catch(NoSolutionException e){
			return false;
		}
		// U*A2=D,解方程TD=c得到T，因为x'A2=c,所以x'=T*U。T中包含k个解和n-k个未知量，n是x的维数。
		// 将x'表示为e+t.f，其中t为T中的未知向量
		// x=f't'+e'
		// Ax+b=A(f't'+e')+b
		// _A1=A1.f'
		// _b=A1.e'+b
		// _A1,_b是将x替换为t以后的不等式约束
		int[][] _A1 = MatrixUtils.multi(A1, MatrixUtils.transpose(mp.m1));
		int[] _b = MatrixUtils.add(MatrixUtils.collapse(MatrixUtils.multi(A1,
				MatrixUtils.transpose(mp.m2))), b);
//		IndependentVariableds  iv=new IndependentVariableds();
//		 try{
//			 return iv.dependent(_A1, _b);
//		 }catch (NotApplicableCaseException nace) {
//			 _A1=iv.A; _b=iv.b;
//		 }
		 Acyclic acyclic=new Acyclic();
		 try{
			 return acyclic.dependent(_A1, _b);
		 }catch (NotApplicableCaseException nace) {
			 _A1=acyclic.A; _b=acyclic.b;
		 }
		 try{
			 new LoopResidue().dependent(_A1, _b);
		 }catch (NotApplicableCaseException nace) {}
		return new BranchBound().solve(_A1, _b);
	}
	
	/**
	 * 应该和dependent方法返回同样的结果
	 */
	boolean dependent2(int[][] A1, int[] b, int[][] A2, int[] c) {
		assert A1[0].length == A2.length;// 向量x的维数
		ExtendGcd gcd = new ExtendGcd();
		MatrixPair mp = null;
		try{
			mp = gcd.solve(A2, c);
		}catch(NoSolutionException e){
			return false;
		}
		int[][] _A1 = MatrixUtils.multi(A1, MatrixUtils.transpose(mp.m1));
		int[] _b = MatrixUtils.add(MatrixUtils.collapse(MatrixUtils.multi(A1,
				MatrixUtils.transpose(mp.m2))), b);
		return new BranchBound().solve(_A1, _b);
	}

}

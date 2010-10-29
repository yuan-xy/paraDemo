package dep;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import util.MatrixUtils;

/**
 * 使用分支定界法解整数线性规划
 * @author ylt
 *
 */
public class BranchBound {
	/**
	 * 求Ax+b>=0的方程组是否存在整数解
	 * @param Ab 系数矩阵
	 * @return 如果有整数解返回true，否则返回false
	 */
	public boolean solve(int[][] Ab){
		List<int[][]> ss=new ArrayList<int[][]>();
		ss.add(Ab);
		int k=Ab[0].length;
		for(int i=k-2;i>=0;i--){
			try{
				Ab=new FourierMotzkin().elimination(Ab, i);
			}catch(NoSolutionException e){
				return false;
			}
			ss.add(Ab);
		}
		Collections.reverse(ss);
		List<Integer> sampleSolutions=new ArrayList<Integer>();
		int i=1;
		SolutionSet solution=null;
		for(;i<ss.size();i++){
			int[][] reduce=reduce(ss.get(i),sampleSolutions);
			try{
				solution=findSolution(reduce);
				sampleSolutions.add(solution.calcMiddle());
			}catch(NoSolutionException e){
				//对某些采样值无解是否就一定无解？
				return false;
			}catch(NoIntegerSolutionException e){
				break;
			}
		}
		if(i==k) return true;
		if(i==1) return false;
		if(solve(addMinOrMaxConstraint(Ab, i, solution.min(), true)) || 
				solve(addMinOrMaxConstraint(Ab, i, solution.max(), false))) return true;
		return false;
	}
	
	public boolean solve(int[][] A,int[] b){
		int[][] Ab=MatrixUtils.columnMerge(A, b);
		return solve(Ab);

	}
	
	private int[][] addMinOrMaxConstraint(int[][] Ab,int k,int minOrMaxValue,boolean min){
		int[][] ret=new int[Ab.length+1][Ab[0].length];
		System.arraycopy(Ab, 0, ret, 0, Ab.length);
		for(int i=0;i<Ab[0].length;i++){
			ret[Ab.length][i]=0;
		}
		if(min){
			ret[Ab.length][k]=1;
			ret[Ab.length][Ab[0].length-1]=-minOrMaxValue;
		}else{
			ret[Ab.length][k]=-1;
			ret[Ab.length][Ab[0].length-1]=minOrMaxValue;
		}
		return ret;
	}
	
	/**
	 * 化简约束Ax+b>=0，将前m个变量用sampleSolutions中的整数常数值替换。
	 * @param Ab 系数矩阵
	 * @param sampleSolutions m个整数值的列表
	 * @return 替换整数后的Ab矩阵
	 */
	int[][] reduce(int[][] Ab,List<Integer> sampleSolutions) {
		int ks=sampleSolutions.size();
		if(ks==0) return Ab;
		int[][] ret=new int[Ab.length][Ab[0].length-ks];
		for(int i=0;i<ret.length;i++){
			for(int j=0;j<ret[0].length;j++){
				ret[i][j]=Ab[i][j+ks];
			}
			for(int k=0;k<ks;k++){
				ret[i][ret[0].length-1]+=sampleSolutions.get(k)*Ab[i][k];
			}
		}
		return ret;
	}

	/**
	 * 解只包含一个变量的方程组,返回解的上下界。<br>
	 * A<sub>1</sub>x+b<sub>1</sub>>=0<br>
	 * A<sub>2</sub>x+b<sub>2</sub>>=0<br>
	 * ...<br>
	 * A<sub>n</sub>x+b<sub>n</sub>>=0<br>
	 * @param Ab 系数矩阵
	 * @return 满足方程的界
	 */
		SolutionSet findSolution(int[][] Ab) {
		assert Ab[0].length==2;
		SolutionSet tri=new SolutionSet();
		for(int i=0;i<Ab.length;i++){
			if(Ab[i][0]>0){
				double d=(-1.0*Ab[i][1])/Ab[i][0];
				tri.min=Math.max(tri.min, d);
			}else if(Ab[i][0]==0){
				if(Ab[i][1]<0) throw new NoSolutionException();
			}else{
				double d=Ab[i][1]/(-1.0*Ab[i][0]);
				tri.max=Math.min(tri.max, d);
			}
		}
		return tri;
	}

}

final class SolutionSet{
	public SolutionSet(){
		max=Double.MAX_VALUE;
		min=-max;//注意：Double的MIN_VALUE指的是最小正整数，不是最大的负数
		//min=Double.MIN_VALUE;
	}
	Double min;
	Double max;
	
	public int calcMiddle(){
		double precision=0.00001;
		if(max-min+precision<0) throw new NoSolutionException();
		int m2=(int) min.doubleValue();
		int m1=(int) max.doubleValue();
		if(m1-m2>1) return (m1+m2)/2;
		if(m1-m2==1){
			double difference=max-min;
			if(difference+precision<1) throw new NoIntegerSolutionException();
			return m1;
		}
		if(max-m1>precision&&min-m1>precision) throw new NoIntegerSolutionException();
		if(m1-max>precision&&m1-max>precision) throw new NoIntegerSolutionException();
		return m1;//m1==m2
	}
	
	public int min(){
		int ret=min.intValue();
		if((min-ret)>=0.5) ret+=1;
		return ret;
	}
	
	public int max(){
		int ret=max.intValue();
		if((max-ret)>=0.5) ret+=1;
		return ret;
	}
}

class NoIntegerSolutionException extends RuntimeException{}


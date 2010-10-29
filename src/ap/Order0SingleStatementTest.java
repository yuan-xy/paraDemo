package ap;

import util.MatrixUtilsTest;

public class Order0SingleStatementTest extends Order0Test {
	
	public void test(){
		Statement s3=new Statement();
		DependentPair dp3=null;
		dp3=new DependentPair();
		dp3.F1=new int[][]{{1}};
		dp3.f1=new int[]{0};
		dp3.s1=s3;
		dp3.F2=new int[][]{{1}};
		dp3.f2=new int[]{-2};
		dp3.s2=s3;
		sts.add(s3);
		dps.add(dp3);
		
		int[][] ret=new Order0().gen(sts, dps,2);
		int[][] result=new int[][]{
				{0, 0, 0, 0, 0, 0, 0, 0, 0}
		};
		//TODO: 如果语句S1和S2可以并行化，语句S3无法并行化，难道仿射划分算法就无法并行化？可能是算法实现的问题？
		MatrixUtilsTest.equalMatrix(result, ret);
	}
}

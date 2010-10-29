package ap;

import junit.framework.TestCase;
import util.MatrixUtilsTest;

public class DependentPairInOneStatementTest extends TestCase {


	Statement s3=new Statement();
	DependentPair dp3=null;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		dp3=new DependentPair();
		dp3.F1=new int[][]{{1}};
		dp3.f1=new int[]{0};
		dp3.s1=s3;
		dp3.F2=new int[][]{{1}};
		dp3.f2=new int[]{-2};
		dp3.s2=s3;
		//i1=i2-2
	}

	@Override
	protected void tearDown() throws Exception {
		dp3=null;
		super.tearDown();
	}
	
	public void test(){
		int[][] ret=dp3.independentSolution();
		int[][] nullSpace=new int[][]{
				{0,0,0}
		};
		//Exercise 11.7.1
		MatrixUtilsTest.equalMatrix(nullSpace, ret);
	}
	
}

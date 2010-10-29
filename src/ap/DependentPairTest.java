package ap;

import junit.framework.TestCase;
import util.MatrixUtilsTest;

public class DependentPairTest extends TestCase {

	DependentPair dp=new DependentPair();
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		dp.F1=new int[][]{
				{1,0},
				{0,1},
		};
		dp.f1=new int[]{0,0};
		dp.s1=new Statement();
		dp.F2=new int[][]{
				{1,0},
				{0,1},
		};
		dp.f2=new int[]{0,-1};
		dp.s2=new Statement();
		//上面的矩阵代表约束i1=i2;j1=j2-1
	}


	@Override
	protected void tearDown() throws Exception {
		dp=null;
		super.tearDown();
	}

	public void testIndependentSolution() {
		int[][] ret=dp.independentSolution();
		int[][] nullSpace=new int[][]{
				{1,0,1,0,0},
				{0,1,0,1,1},
		};
		//上式代表C11=C21或者C12=C22=d1-d2
		MatrixUtilsTest.equalMatrix(nullSpace, ret);
	}

	
}

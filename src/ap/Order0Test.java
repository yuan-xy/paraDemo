package ap;

import java.util.ArrayList;
import java.util.List;

import dep.NoSolutionException;

import util.MatrixUtilsTest;
import junit.framework.TestCase;

public class Order0Test extends TestCase {
	
	Statement s1=new Statement();
	Statement s2=new Statement();
	DependentPair dp1=null;
	DependentPair dp2=null;
	List<Statement> sts=null;
	List<DependentPair> dps=null;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		dp1=new DependentPair();
		dp1.F1=new int[][]{
				{1,0},
				{0,1},
		};
		dp1.f1=new int[]{0,0};
		dp1.s1=s1;
		dp1.F2=new int[][]{
				{1,0},
				{0,1},
		};
		dp1.f2=new int[]{0,-1};
		dp1.s2=s2;
		dp2=new DependentPair();
		dp2.F1=new int[][]{
				{1,0},
				{0,1},
		};
		dp2.f1=new int[]{0,0};
		dp2.s1=s2;
		dp2.F2=new int[][]{
				{1,0},
				{0,1},
		};
		dp2.f2=new int[]{-1,0};
		dp2.s2=s1;
		sts=new ArrayList<Statement>();
		sts.add(s1);
		sts.add(s2);
		dps=new ArrayList<DependentPair>();
		dps.add(dp1);
		dps.add(dp2);
	}

	@Override
	protected void tearDown() throws Exception {
		dp1=null;
		dp2=null;
		sts=null;
		dps=null;
		super.tearDown();
	}
	
	public void testMerge(){
		int[][] oldSolution=new int[][]{
				{1,0,1,0,0},
				{0,1,0,1,1},
		};
		int[][] newSolution=new int[][]{
				{0,1,0,1,0},
				{1,0,1,0,-1},				
				
		};
		int[][] merge=new int[][]{
				{1,-1,1,-1,-1}
		};
		int[][] ret=new Order0().merge(oldSolution, newSolution);
		MatrixUtilsTest.equalMatrix(merge, ret);
	}
	
	public void testMerge2(){
		//将testMerge中的最后一项也就是d1-d2项拆开
		int[][] oldSolution=new int[][]{
				{1,0,1,0,0,0},
				{0,1,0,1,1,0},
		};
		int[][] newSolution=new int[][]{
				{0,1,0,1,0,0},
				{1,0,1,0,0,1},
		};
		try{
			new Order0().merge(oldSolution, newSolution);
			fail();
		}catch(NoSolutionException e){}
	}
	
	
	public void testSolutionMerge(){
		int[][] solution1=dp1.independentSolution();
		int[][] oldSolution=new int[][]{
				{1,0,1,0,0},
				{0,1,0,1,1},
		};
		MatrixUtilsTest.equalMatrix(solution1, oldSolution);
		int[][] solution2=dp2.independentSolution();
		int[][] newSolution=new int[][]{
				{1,0,1,0,1},
				{0,1,0,1,0},
		};
		MatrixUtilsTest.equalMatrix(solution2, newSolution);
		int[][] solutionExpand1=new Order0().expandSolution(sts, 2, 2, dp1, oldSolution);
		MatrixUtilsTest.equalMatrix(solutionExpand1,
				new int[][]{
				{1,0,1,0,0,0},
				{0,1,0,1,1,0},
				{0,0,0,0,1,1}}
		);
		int[][] solutionExpand2=new Order0().expandSolution(sts, 2, 2, dp2, newSolution);
		MatrixUtilsTest.equalMatrix(solutionExpand2,
				new int[][]{
				{1,0,1,0,0,1},
				{0,1,0,1,0,0},
				{0,0,0,0,1,1}}
		);	
		int[][] sMerge=new Order0().merge(solutionExpand1, solutionExpand2);
		MatrixUtilsTest.equalMatrix(sMerge,
				new int[][]{
				{1,-1,1,-1,-1,0},
				{0,0,0,0,1,1}}
		);	
	}
	
	public void test(){
		int[][] ret=new Order0().gen(sts, dps,2);
		int[][] result=new int[][]{
				{1,-1,1,-1,-1,0},
				{0,0,0,0,1,1}
		};
		//Example 11.42
		MatrixUtilsTest.equalMatrix(result, ret);
	}


}

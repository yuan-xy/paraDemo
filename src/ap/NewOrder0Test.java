package ap;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import util.MatrixPair;
import util.MatrixUtils;
import util.MatrixUtilsTest;

@Deprecated
public class NewOrder0Test extends TestCase {
	
	
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
	
	public void testExpandF(){
		int[][] ret=new NewOrder0().expandF(dp1, sts, 2, 2);
		int[][] F2clone=MatrixUtils.clone(dp1.F2);
		MatrixUtils.scalarOperation(F2clone, -1, '*');
		int[][] result=MatrixUtils.rowMerge(dp1.F1, F2clone);
		MatrixUtilsTest.equalMatrix(ret, result);
		int[] e=new NewOrder0().expandE(dp1);
		MatrixUtilsTest.equalMatrix(e,new int[]{0,-1});
	}
	
	public void testMergeEquation(){
		int[][] F=new int[][]{
				{1, 0, -1, 0},
				{0, 1, 0, -1},
				{-1, 0, 1, 0},
				{0, -1, 0, 1}
		};
		int[] E=new int[]{0,-1,-1,0};
		//方程为[i1,j1,i2,j2]*F=E
		Equation equ=new NewOrder0().mergeEquation(sts, dps);
		MatrixUtilsTest.equalMatrix(equ.A,F);
		MatrixUtilsTest.equalMatrix(equ.b,E);
		MatrixPair mp=equ.solve();
		int[][] f=new int[][]{
				{1,0,1,0},
				{0,1,0,1}
		};
		int[][] e=new int[][]{{0,0,0,1}};
		//这里给出的解并不正确，
		//它表示[i1,j1,i2,j2]=[t1,t2]*f+e，也就是i1=i2,j1=j2-1,这其实只是F左边两列的解。
		//F右边两列表示i1-1=i2,j1=j2,在最终的结果里没有反应出来。
		MatrixUtilsTest.equalMatrix(f,mp.m1);
		MatrixUtilsTest.equalMatrix(e,mp.m2);
	}


	public void test1(){
		List<DependentPair> list2=new ArrayList<DependentPair>();
		list2.add(dp1);
		int[][] m=new NewOrder0().gen(sts,list2);
		MatrixUtilsTest.equalMatrix(m,
				new int[][]{
				{1,0,1,0,0,0},
				{0,1,0,1,0,-1},
				{0,0,0,0,1,1}}
		);
	}
	
	public void test2(){
		List<DependentPair> list2=new ArrayList<DependentPair>();
		list2.add(dp2);
		int[][] m=new NewOrder0().gen(sts,list2);
		MatrixUtilsTest.equalMatrix(m,
				new int[][]{
				{1,0,1,0,0,1},
				{0,1,0,1,0,0},
				{0,0,0,0,1,1}}
		);
	}
	
	
	public void test(){
		int[][] m=new NewOrder0().gen(sts,dps);
		int i=m.length;
	}
	
}

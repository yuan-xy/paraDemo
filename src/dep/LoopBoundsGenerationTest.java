package dep;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import junit.framework.TestCase;
import util.MatrixUtils;

public class LoopBoundsGenerationTest extends TestCase {
	
	LoopBoundsGeneration lbg=new LoopBoundsGeneration();

	public void testGen() {
		int[][] A=new int[][]{
				{1,0},
				{-1,0},
				{-1,1},
				{0,-1}
		};
		int[] b=new int[]{0,5,0,7};
		matchedBounds("for(int x1=0;x1<=7;x1++)\n" +
						" for(int x0=0;x0<=min(5,x1);x0++)\n",
				lbg.gen(A, b, new int[]{1,0}));
		//龙书2e P794 Example11.10
		matchedBounds("for(int j=0;j<=7;j++)\n" +
				" for(int i=0;i<=min(5,j);i++)\n",
				lbg.gen(A, b, new int[]{1,0}).setNames(new String[]{"i","j"}));
		
		matchedBounds("for(int i=0;i<=min(5,7);i++)\n" +
				" for(int j=i;j<=7;j++)\n",//龙书2e P790  Example 11.6
		lbg.gen(A, b, new int[]{0,1}).setNames(new String[]{"i","j"}));
		
		int[][] m2=new int[][]{
				{-1,1},
				{0,1}
		};
		A=MatrixUtils.multi(A, m2);
		matchedBounds("for(int k=0;k<=7;k++)\n" +
				" for(int j=k;j<=min(k+5,7);j++)\n",//龙书2e P795 Example11.15
		lbg.gen(A, b, new int[]{0,1}).setNames(new String[]{"k","j"}));
	}
	
	private void matchedBounds(String s,LoopBounds list){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		list.prettyPrint(new PrintStream(out));
		System.out.println(out.toString());
		assertEquals(s, out.toString());
		//list.prettyPrint(System.out);	
	}
	
	
	public void testGen2() {
		int[][] A=new int[][]{
				{1,0},
				{-1,0},
				{-1,1},
				{-1,-1}
		};
		int[] b=new int[]{-1,29,-2,39};
		//Exercise 11.3.4 (a)
		LoopBounds lb=lbg.gen(A, b, new int[]{1,0}).setNames(new String[]{"i","j"});
		lb.prettyPrint(System.out);
		//for(int j=3;j<=38;j++)
		//	 for(int i=1;i<=min(29,j-2,-1*j+39);i++)
		//TODO：冗余条件的删除,参见CLoogTest.java
	}
	
	public void test3(){
		int[][] A=new int[][]{
				{1,0,0,0,0},
				{-1,0,0,1,0},
				{0,1,0,0,0},
				{0,-1,0,0,1},
				{0,0,1,0,0},
				{1,1,-1,0,0},
		};
		int[] b=new int[]{0,0,0,0,0,0};
		LoopBounds lb=lbg.gen(A, b, new int[]{2,1,0}).setNames(new String[]{"i","j","k","m","n"});
		lb.prettyPrint(System.out);
		matchedBounds("for(int k=0;k<=m+n;k++)\n" +
				" for(int j=max(0,k-1*m);j<=n;j++)\n" +
				"  for(int i=max(0,-1*j+k);i<=m;i++)\n",lb);
		//支持符号变量，如上例中的m、n
		}
	

}
